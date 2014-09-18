package com.apollo.training.games.rpg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import com.apollo.training.games.rpg.Buff.Status;
import com.apollo.training.games.rpg.Hero.HeroStatus;

public class Monster {
	public int id;
	public String name;
	public int level;
	public int health;
	public int maxHP;
	public int lowAtk;
	public int highAtk;
	public int experienceDrop;
	public Object itemDrop;
	public Hero hero;
	public Random randomizer = new Random();
	
	// status
	public ArrayList<Buff> negativeBuffs = new ArrayList<Buff>(); 
	
	// attack order
	public int attackOrderNumber;
	
	//database mgmt
	Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    String url = "jdbc:mysql://localhost:3306/game";
    String user = "root";
    String password = "root";
	
	public Monster(Hero hero) {
		this.hero = hero;
		getMonster();
		
		toString();
	}
	

	private void getMonster() {
		Random random = new Random();
		int monsterID;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();
			rs = st.executeQuery("SELECT id FROM monsters WHERE level <= "+(hero.level + 1)+"");
			
			// get a monster depending on the hero's level and save it into a list
			ArrayList<Integer> monsterIds = new ArrayList<Integer>();
			
			monsterIds.clear();
			while (rs.next()) {
				monsterIds.add(rs.getInt("id"));
			}
			
			// randomize the index of the arraylist to get a monster ID
			monsterID = monsterIds.get(random.nextInt(monsterIds.size()));
			
			// select from database where id = monster ID
			rs = st.executeQuery("SELECT * FROM monsters WHERE id = "+monsterID+"");
			rs.next();
			
			// get the values
			id = rs.getInt("id");
			name = rs.getString("name");
			level = rs.getInt("level");
			maxHP = rs.getInt("maxHP");
			health = maxHP;
			lowAtk = rs.getInt("atk_low");
			highAtk = rs.getInt("atk_high");
			experienceDrop = rs.getInt("exp_drop");
			if (hero.chanceIsEnabled(50)) {
				try {
					Items item = new Items();
					itemDrop = item.getItemDrop(this);
				} catch (NullPointerException e) {
					itemDrop = null;
				}
			} else {
				try {
					Equipment equipment = new Equipment();
					itemDrop = equipment.getEquipmentDrop(this);
				} catch (NullPointerException e) {
					itemDrop = null;
				}
			}
			negativeBuffs.clear();
			
		} catch (SQLException e) {
			 System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e2) {
				System.out.println(e2.getMessage());
			}
		}
		
	}

	@Override
	public String toString() {
		System.out.println("\n" + name + " appeared!");
		System.out.println("Level: " + level);
		System.out.println(displayHP());
		System.out.println("(secret) ATK: " + lowAtk + "-" + highAtk);
		System.out.println("(secret) EXP Drop: " + experienceDrop);
		return super.toString();
	}
	
	public String displayHP() {
		String output = "HP: " + health + "/" + maxHP;
		if (negativeBuffs.size() > 0) {
			output += " [";
			for (int i = 0; i < negativeBuffs.size(); i++) {
				output += negativeBuffs.get(i).buff;
				if (i < negativeBuffs.size() - 1) {
					output += ", ";
				} else {
					output += "]";
				}
			}
		}
		return output;
	}

	public boolean isDead() {
		if (health <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public void attack(Hero target) {
		Random random = new Random();
		int damage = 0;
		
		if (negativeBuffs.size() > 0) {
			for (int i = 0; i < negativeBuffs.size(); i++) {
				Buff buff = negativeBuffs.get(i);
				if (hasResisted()) {
					System.out.println(name + " resisted from being " + buff.buff);
					buff.duration = 0;
					negativeBuffs.set(i, buff);
				} else {
					if (buff.duration > 0) {
						switch (buff.buff) {
						case STUNNED:
							System.out.println(name + " is still " + Status.STUNNED + " attack is disabled.");
							break;
						case BURNED:
							if (target.lastCastedSkillWithEffect.name.equalsIgnoreCase("Fireball")) {
								damage = 50 + random.nextInt(target.level * 10);
							} else if (hero.lastCastedSkillWithEffect.name.equalsIgnoreCase("Fireball 2")) {
								damage = 100 + random.nextInt(target.level * 10);
							}
							System.out.println(name + " still suffers from BURNING. Effect dealts " + damage + " damage");
							break;
						case POISONED:
							if (target.lastCastedSkillWithEffect.name.equalsIgnoreCase("Poison Stab")) {
								damage = 60 + random.nextInt(target.level * 10);
							} else if (hero.lastCastedSkillWithEffect.name.equalsIgnoreCase("Poison Stab 2")) {
								damage = 120 + random.nextInt(target.level * 10);
							}
							System.out.println(name + " still suffers from POISON. Effect dealts " + damage + " damage");
							break;
						}
						// decrease health to buff damage
						health -= damage;
						// decrease buff duration
						buff.duration--;
						if (buff.duration <= 0) {
							buff.duration = 0;
							negativeBuffs.set(i, buff);
						}
					}
				}
			}

			ArrayList<Buff> newNegativeBuffs = new ArrayList<Buff>();
			for (int i = 0; i < negativeBuffs.size(); i++) {
				if (negativeBuffs.get(i).duration > 0) {
					newNegativeBuffs.add(negativeBuffs.get(i));
				}
			}
			negativeBuffs.clear();
			negativeBuffs.addAll(newNegativeBuffs);
		}
		
		if (!hasThisBuff(Status.STUNNED)) {
			// monster can attack
			if (!target.chanceIsEnabled(hero.dodgeChance)) {
				// check if flame shield is activated
				if (target.status == HeroStatus.FLAME_SHIELD) {
					double flameShieldBlockChance = 0;
					double effectChance = target.fireWallCasted.effectChance + (5 * level);;
					if (target.fireWallCasted.name.equalsIgnoreCase("Fire Wall")) {
						flameShieldBlockChance = 50 + (5 * (target.level - 4));
					} else if (target.fireWallCasted.name.equalsIgnoreCase("Fire Wall 2")) {
						flameShieldBlockChance = 60 + (5 * (target.level - 4));
					}

					if (target.chanceIsEnabled(flameShieldBlockChance)) {
						System.out.println(name + " attacks... bit it is blocked by the Flame Shield");
						if (target.chanceIsEnabled(effectChance)) {
							int buffIndex = getEqualBuff(Status.BURNED);
							if (buffIndex >= 0) {
								refreshBuffDuration(buffIndex);
							} else {
								addNewBuff(Status.BURNED, 3);
							}
						}
					} else {
						successAttack(target);
					}
					target.status = HeroStatus.NONE;
					target.fireWallCasted = null;
				} else if (target.status == HeroStatus.COUNTER_HELIX) {
					int counterHelixChance = 30 + (5 * (target.level - 3)) - (level * 3);
					if (target.chanceIsEnabled(counterHelixChance)) {
						int returnDamage = random.nextInt((highAtk - lowAtk) + 1) + lowAtk;
						System.out.println(target.name + " countered " + name + "'s attack, returns " + returnDamage + " damage.");
						health -= returnDamage;
					} else {
						successAttack(target);
					}
				} else if (target.status == HeroStatus.AGILITY_SPURT) {
					int confusionChance = 30 + (5 * (target.level - 4)) - (level * 3);
					if (target.chanceIsEnabled(confusionChance)) {
						int returnDamage = random.nextInt((highAtk - lowAtk) + 1) + lowAtk;
						health -= returnDamage;
						System.out.println(name + " is CONFUSED and dealt " + returnDamage + " damage to himself.");
					} else {
						successAttack(target);
					}
				} else {
					successAttack(target);
				}
			} else {
				System.out.println(name + " attacks... but " + hero.name + " dodged!");
			}
		}
	}
	

	private void successAttack(Hero target) {
		Random random = new Random();
		int damage = random.nextInt((highAtk - lowAtk) + 1) + lowAtk;
		target.health -= damage;
		System.out.println(name + " dealt " + damage + " damage.");
	}



	private boolean hasResisted() {
		double resistingChance;
		if (hero.level > level) {
			resistingChance = 100 - ((hero.level - level) * 30);
		} else {
			resistingChance = ((level - hero.level) * 10) + 15;
		}
		
		if (resistingChance < 0) {
			return false;
		} else {
			if (hero.chanceIsEnabled(resistingChance)) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean hasBuffs() {
		if (negativeBuffs.size() > 0) {
			return true;
		} else {
			return false;
		}
	}



	public boolean hasThisBuff(Status effectToTarget) {
		for (int i = 0; i < negativeBuffs.size(); i++) {
			if (negativeBuffs.get(i).buff.equals(effectToTarget) && negativeBuffs.get(i).duration > 0) {
				return true;
			}
		}
		return false;
	}



	public int getEqualBuff(Status effectToTarget) {
		if (negativeBuffs.size() > 0) {
			for (int i = 0; i < negativeBuffs.size(); i++) {
				if (negativeBuffs.get(i).buff.equals(effectToTarget) && negativeBuffs.get(i).duration > 0) {
					return i;
				}
			}
		}
		return -1;
	}



	public void refreshBuffDuration(int buffIndex) {
		Buff newBuff = negativeBuffs.get(buffIndex);
		newBuff.duration = 3;
		negativeBuffs.set(buffIndex, newBuff);
	}



	public void addNewBuff(Status effectToTarget, int duration) {
		Buff newBuff = new Buff();
		newBuff.buff = effectToTarget;
		newBuff.duration = 3;
		negativeBuffs.add(newBuff);
	}
}
