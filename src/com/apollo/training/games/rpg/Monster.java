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

public class Monster extends Character {
	
	private int id;
	
	private int experienceDrop;
	
	private Object itemDrop;
	
	private Hero targetHero;
		
	// status
	private ArrayList<Buff> negativeBuffs = new ArrayList<Buff>(); 
	
	//database mgmt
	Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    String url = "jdbc:mysql://localhost:3306/game";
    String user = "root";
    String password = "root";
	
	public Monster(Hero hero) {
		super();
		this.targetHero = hero;
		getMonster();
		
		toString();
	}
	

	private void getMonster() {
		int monsterID;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();
			rs = st.executeQuery("SELECT id FROM monsters WHERE level <= "+(targetHero.getLevel() + 1)+"");
			
			// get a monster depending on the hero's level and save it into a list
			ArrayList<Integer> monsterIds = new ArrayList<Integer>();
			
			monsterIds.clear();
			while (rs.next()) {
				monsterIds.add(rs.getInt("id"));
			}
			
			// randomize the index of the arraylist to get a monster ID
			monsterID = monsterIds.get(randomizer.nextInt(monsterIds.size()));
			
			// select from database where id = monster ID
			rs = st.executeQuery("SELECT * FROM monsters WHERE id = "+monsterID+"");
			rs.next();
			
			// get the values
			id = rs.getInt("id");
			setName(rs.getString("name"));
			setLevel(rs.getInt("level"));
			setMaxHP(rs.getInt("maxHP"));
			setHealth(getMaxHP());
			setLowAtk(rs.getInt("atk_low"));
			setHighAtk(rs.getInt("atk_high"));
			experienceDrop = rs.getInt("exp_drop");
			
//			if (targetHero.chanceIsEnabled(50)) {
//				try {
//					Items item = new Items();
//					itemDrop = item.getItemDrop(this);
//				} catch (NullPointerException e) {
//					itemDrop = null;
//				}
//			} else {
//				try {
//					Equipment equipment = new Equipment();
//					itemDrop = equipment.getEquipmentDrop(this);
//				} catch (NullPointerException e) {
//					itemDrop = null;
//				}
//			}
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
		System.out.println("\n" + getName() + " appeared!");
		System.out.println("Level: " + getLevel());
		System.out.println(displayHP());
		System.out.println("(secret) ATK: " + getLowAtk() + "-" + getHighAtk());
		System.out.println("(secret) EXP Drop: " + experienceDrop);
		return super.toString();
	}
	
	public void attack(Hero target) {
		Random randomizer = new Random();
		int damage = 0;
		
		if (negativeBuffs.size() > 0) {
			for (int i = 0; i < negativeBuffs.size(); i++) {
				Buff buff = negativeBuffs.get(i);
				if (hasResisted()) {
					System.out.println(getName() + " resisted from being " + buff.buff);
					buff.duration = 0;
					negativeBuffs.set(i, buff);
				} else {
					if (buff.duration > 0) {
						switch (buff.buff) {
						case STUNNED:
							System.out.println(getName() + " is still " + Status.STUNNED + " attack is disabled.");
							break;
						case BURNED:
							if (target.lastCastedSkillWithEffect.getName().equalsIgnoreCase("Fireball")) {
								damage = 50 + randomizer.nextInt(target.getLevel() * 10);
							} else if (targetHero.lastCastedSkillWithEffect.getName().equalsIgnoreCase("Fireball 2")) {
								damage = 100 + randomizer.nextInt(target.getLevel() * 10);
							}
							System.out.println(getName() + " still suffers from BURNING. Effect dealts " + damage + " damage");
							break;
						case POISONED:
							if (target.lastCastedSkillWithEffect.getName().equalsIgnoreCase("Poison Stab")) {
								damage = 60 + randomizer.nextInt(target.getLevel() * 10);
							} else if (targetHero.lastCastedSkillWithEffect.getName().equalsIgnoreCase("Poison Stab 2")) {
								damage = 120 + randomizer.nextInt(target.getLevel() * 10);
							}
							System.out.println(getName() + " still suffers from POISON. Effect dealts " + damage + " damage");
							break;
						}
						// decrease health to buff damage
						decreaseHP(damage);
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
			if (!target.chanceIsEnabled(targetHero.getDodgeChance())) {
				// check if flame shield is activated
				if (target.getStatus() == HeroStatus.FLAME_SHIELD) {
					double flameShieldBlockChance = 0;
					double effectChance = target.fireWallCasted.getEffectChance() + (5 * getLevel());;
					if (target.fireWallCasted.getName().equalsIgnoreCase("Fire Wall")) {
						flameShieldBlockChance = 50 + (5 * (target.getLevel() - 4));
					} else if (target.fireWallCasted.getName().equalsIgnoreCase("Fire Wall 2")) {
						flameShieldBlockChance = 60 + (5 * (target.getLevel() - 4));
					}

					if (target.chanceIsEnabled(flameShieldBlockChance)) {
						System.out.println(getName() + " attacks... bit it is blocked by the Flame Shield");
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
					target.setStatus(HeroStatus.NONE);
					target.fireWallCasted = null;
				} else if (target.getStatus() == HeroStatus.COUNTER_HELIX) {
					int counterHelixChance = 30 + (5 * (target.getLevel() - 3)) - (getLevel() * 3);
					if (target.chanceIsEnabled(counterHelixChance)) {
						int returnDamage = getNormalDamage();
						System.out.println(target.getName() + " countered " + getName() + "'s attack, returns " + returnDamage + " damage.");
						decreaseHP(returnDamage);
					} else {
						successAttack(target);
					}
				} else if (target.getStatus() == HeroStatus.AGILITY_SPURT) {
					int confusionChance = 30 + (5 * (target.getLevel() - 4)) - (getLevel() * 3);
					if (target.chanceIsEnabled(confusionChance)) {
						int returnDamage = getNormalDamage();
						decreaseHP(returnDamage);
						System.out.println(getName() + " is CONFUSED and dealt " + returnDamage + " damage to himself.");
					} else {
						successAttack(target);
					}
				} else {
					successAttack(target);
				}
			} else {
				System.out.println(getName() + " attacks... but " + targetHero.getName() + " dodged!");
			}
		}
	}
	

	private void successAttack(Hero target) {
		int damage = getNormalDamage();
		target.setHealth(target.getHealth() - damage);
		System.out.println(getName() + " dealt " + damage + " damage.");
	}
	

	private boolean hasResisted() {
		double resistingChance;
		if (targetHero.getLevel() > getLevel()) {
			resistingChance = 100 - ((targetHero.getLevel() - getLevel()) * 30);
		} else {
			resistingChance = ((getLevel() - targetHero.getLevel()) * 10) + 15;
		}
		
		if (resistingChance < 0) {
			return false;
		} else {
			if (targetHero.chanceIsEnabled(resistingChance)) {
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


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getExperienceDrop() {
		return experienceDrop;
	}


	public void setExperienceDrop(int experienceDrop) {
		this.experienceDrop = experienceDrop;
	}


	public Object getItemDrop() {
		return itemDrop;
	}


	public void setItemDrop(Object itemDrop) {
		this.itemDrop = itemDrop;
	}


	public Hero getTargetHero() {
		return targetHero;
	}


	public void setTargetHero(Hero targetHero) {
		this.targetHero = targetHero;
	}


	public ArrayList<Buff> getNegativeBuffs() {
		return negativeBuffs;
	}


	public void setNegativeBuffs(ArrayList<Buff> negativeBuffs) {
		this.negativeBuffs = negativeBuffs;
	}
}
