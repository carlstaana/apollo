package com.apollo.training.games.rpg;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.apollo.training.games.rpg.Skill.Type;

public class Hero {
	// hero's statistics
	public String name;
	public int health;
	public int maxHP;
	public int mana;
	public int maxMP;
	public int lowAtk;
	public int highAtk;
	public int experience;
	public int level;
	public int[] expRequired = {200, 500, 750, 1000, 1500, 2000, 3000, 5000, 7500, 10000, 15000, 25000, 50000, 75000, 150000};
	public double criticalChance;
	public double dodgeChance;
	public double blockChance;
	
	// hero's skills
	public ArrayList<Skill> skills = null;
	SkillSet skillSet = null;
	
	// hero's class
	public HeroClass myClass;
	
	public enum HeroClass{ SWORDSMAN, ARCHER, MAGE, THIEF }
	
	// hero's status
	public HeroStatus status;
	public int buffDuration;
	
	public enum HeroStatus { NONE, FLAME_SHIELD, COUNTER_HELIX, AGILITY_SPURT }
	
	// hero's inventory
	public Inventory inventory;
	
	// hero's equipment
	public Equipment[] body = new Equipment[6];
	public Equipment head = body[0];
	public Equipment armor = body[1];
	public Equipment pants = body[2];
	public Equipment gloves = body[3];
	public Equipment shoes = body[4];
	public Equipment weapon = body[5];
	
	// programmatic variables
	public Random randomizer = new Random();
	
	// skill has been cancelled
	public boolean cancelledCastingSkill;
	
	// the latest skill the monster has been affected
	public Skill fireWallCasted;
	public Skill lastCastedSkillWithEffect;
	
	// attack order number
	public int attackOrderNumber;
	
	// target monster id
	public int targetMonsterId;
	
	public Hero(String name, int classNo) throws NumberFormatException {
		// code for selecting the hero class
		switch (classNo) {
		case 1:
			myClass = HeroClass.SWORDSMAN;
			break;
		case 2:
			myClass = HeroClass.ARCHER;
			break;
		case 3:
			myClass = HeroClass.MAGE;
			break;
		case 4:
			myClass = HeroClass.THIEF;
			break;
		default:
			throw new NumberFormatException("Error! Try again.");
		}
		
		this.name = name;
		if (myClass == HeroClass.SWORDSMAN) {
			maxHP = 350;
		} else {
			maxHP = 250;
		}
		health = maxHP;
		if (myClass == HeroClass.MAGE) {
			maxMP = 200;
		} else {
			maxMP = 120;
		}
		mana = maxMP;
		if (myClass == HeroClass.ARCHER) {
			lowAtk = randomizer.nextInt(10) + 80;
		} else {
			lowAtk = randomizer.nextInt(10) + 50;
		}
		highAtk = randomizer.nextInt(20) + 5 + lowAtk;
		experience = 0;
		level = 1;
		if (myClass == HeroClass.THIEF) {
			criticalChance = randomizer.nextDouble() * 5.0;
			dodgeChance = 10 + randomizer.nextDouble() * 5.0;
		} else if (myClass == HeroClass.ARCHER) {
			criticalChance = 10 + randomizer.nextDouble() * 5.0;
			dodgeChance = randomizer.nextDouble() * 5.0;
		} else {
			criticalChance = randomizer.nextDouble() * 5.0;
			dodgeChance = randomizer.nextDouble() * 5.0;
		}
		if (myClass == HeroClass.SWORDSMAN) {
			blockChance = 10 + randomizer.nextDouble() * 5.0;
		} else {
			blockChance = randomizer.nextDouble() * 5.0;
		}
		
		inventory = new Inventory();

		// status
		status = HeroStatus.NONE;
		
		// get the skillset from database
		try {
			skillSet = new SkillSet(myClass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		skills = skillSet.getSkills();
		
		toString();
	}

	@Override
	public String toString() {
		System.out.println(name + " [" + myClass + "]");
		System.out.println("Level: " + level);
		System.out.println(displayHP());
		System.out.println(displayMP());
		System.out.println("ATK: " + lowAtk + "-" + highAtk);
		System.out.println("CRIT: " + round(criticalChance, 2) + "%");
		System.out.println("DODGE: " + round(dodgeChance, 2) + "%");
		System.out.println("BLOCK: " + round(blockChance, 2) + "%");
		return super.toString();
	}
	
	public String displayHP() {
		return "HP: " + health + "/" + maxHP;
	}

	public String displayMP() {
		String output = "MP: " + mana + "/" + maxMP;
		if (status != HeroStatus.NONE) {
			output += " [" + status + "]";
		}
		return output;
	}
	
	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	public void levelUp() {
		Random random = new Random();
		experience -= expRequired[level - 1];
		level++;
		System.out.println("\n---LEVEL UP!!!---");
		System.out.println(name + " is now Level " + level);
		
		// upgrade stats
		maxHP += random.nextInt(200) + (10 * level) + 50;
		health = maxHP;
		maxMP += random.nextInt(100) + (5 * level) + 20;
		mana = maxMP;
		
		// for increasing the attack power
		int tempLowAtk;
		int tempHighAtk;
		do {
			tempLowAtk = 0;
			tempHighAtk = 0;
			tempLowAtk = random.nextInt(12 * level) + lowAtk;
			tempHighAtk = random.nextInt(15 * level) + highAtk;
		} while (tempLowAtk >= tempHighAtk);
		lowAtk = tempLowAtk;
		highAtk = tempHighAtk;
		
		criticalChance += random.nextDouble() * 5.0;
		dodgeChance += random.nextDouble() * 5.0;
		blockChance += random.nextDouble() * 10.0;

		toString();
		
		// check also if there are available skills
		for (Skill skill : skills) {
			if (skill.levelRequired == level) {
				System.out.println("\nYou have learned: " + skill.name + " [" + skill.skillType + "]");
				System.out.println(skill.description);
				if (skill.skillType == Type.ACTIVE) {
					System.out.println("Base Damage: " + skill.damage + "; MP Cost: " + skill.mpCost);
				}
			}
		}
		
		if (myClass == HeroClass.SWORDSMAN && level == 3) {
			// activate passive skill
			status = HeroStatus.COUNTER_HELIX;
		}
		
		if (myClass == HeroClass.THIEF && level == 4) {
			status = HeroStatus.AGILITY_SPURT;
		}
	}

	public Skill listSkills() {
		ArrayList<Skill> availableSkills = new ArrayList<Skill>();
		Scanner in = new Scanner(System.in);
		String input;
		int chosenSkill = 0;
		boolean valid;
		for (Skill skill : skills) {
			if (skill.levelRequired <= level && mana >= skill.mpCost && skill.skillType == Type.ACTIVE) {
				availableSkills.add(skill);
			}
		}
		
		// check if available skills has something
		if (availableSkills.size() <= 0) {
			//in.close();
			return null;
		} else {
			valid = false;
			while (!valid) {
				System.out.println("\nSelect Skills below:");
				for (int i = 0; i < availableSkills.size(); i++) {
					System.out.println("["+ (i+1) +"] " + availableSkills.get(i).name + " MP: " + availableSkills.get(i).mpCost);
				}
				System.out.println("[0] CANCEL");
				input = in.nextLine();

				try {
					chosenSkill = Integer.parseInt(input);
					cancelledCastingSkill = false;
					if (chosenSkill < 0 && chosenSkill > availableSkills.size()) {
						throw new IndexOutOfBoundsException();
					} else if (chosenSkill == 0) {
						cancelledCastingSkill = true;
						return null;
					}
					valid = true;
					return availableSkills.get(chosenSkill-1);
				} catch (NumberFormatException e) {
					System.out.println("Error! Try again.");
					continue;
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Error! Try again.");
					continue;
				}
			}
		}
		return null;
	}

	public void attack(Monster target) {
		Random random = new Random();
		int damage;
		
		damage = random.nextInt((highAtk - lowAtk) + 1) + lowAtk;
		if (chanceIsEnabled(criticalChance)) {
			damage = damage * 2;
			target.health -= damage;
			System.out.println(name + " dealt " + damage + " damage!!! to " + target.name);
		} else {
			target.health -= damage;
			System.out.println(name + " dealt " + damage + " damage to " + target.name);
		}
		
		if (myClass == HeroClass.THIEF) {
			// Lifesteal
			double lifeStealPercentage = 10 + (5 * level);
			int stealChance = 20 + (5 * level);
			int lifeStealed = (int) ((lifeStealPercentage / 100.0) * damage);
			
			if (chanceIsEnabled(stealChance)) {
				System.out.println(name + " steals " + lifeStealed + " HP");
				health += lifeStealed;
				if (health > maxHP) {
					health = maxHP;
				}
			}
		}
	}

	public boolean chanceIsEnabled(double chancePercentage) {
		Random random = new Random();
		double output = random.nextDouble() * 100;
		if (output <= chancePercentage) {
			return true;
		} else {
			return false;
		}
	}

	public void skillAttack(Skill usedSkill, Monster target) {
		Random random = new Random();
		int damage = 0;
		
		System.out.println(name + " casts " + usedSkill.name);
		mana -= usedSkill.mpCost;
		if (chanceIsEnabled(usedSkill.accuracy)) {
			// special conditions for double strafe and double strafe 2
			if (usedSkill.name.equalsIgnoreCase("Double Strafe") || usedSkill.name.equalsIgnoreCase("Double Strafe 2")) {
				if (usedSkill.name.equalsIgnoreCase("Double Strafe")) {
					damage = ((random.nextInt((highAtk - lowAtk) + 1) + lowAtk) * 2) + random.nextInt(level * 10);
				} else {
					damage = ((random.nextInt((highAtk - lowAtk) + 1) + lowAtk) * 3) + random.nextInt(level * 10);
				}
				if (chanceIsEnabled(criticalChance)) {
					damage = damage * 2;
					System.out.println(name + " dealt " + damage + " damage to " + target.name + "!!!");
				} else {
					System.out.println(name + " dealt " + damage + " damage to " + target.name + ".");
				}
			} else if (usedSkill.name.equalsIgnoreCase("Arrow Shower") || usedSkill.name.equalsIgnoreCase("Arrow Shower 2")) {
				int attackRepetition = 0;
				if (usedSkill.name.equalsIgnoreCase("Arrow Shower")) {
					attackRepetition = 5;
				} else {
					attackRepetition = 8;
				}
				
				System.out.print(name + " dealts... ");
				int totalDamage = 0;
				for (int i = attackRepetition; i > 0; i--) {
					if (chanceIsEnabled(criticalChance)) {
						damage = damage * 2;
						System.out.print(damage + "!!");
					} else {
						damage = random.nextInt((highAtk - lowAtk) + 1) + lowAtk;
						System.out.print(damage);
					}
					
					if (i > 1) {
						System.out.print(" + ");
					} else {
						System.out.print(" = ");
					}
					totalDamage += damage;
				}
				System.out.println(totalDamage + " damage");
				damage = totalDamage;
			} else if (usedSkill.name.equalsIgnoreCase("Throat Slice") || usedSkill.name.equalsIgnoreCase("Throat Slice 2")) {
				int instantKillChance;
				if (usedSkill.name.equalsIgnoreCase("Throat Slice")) {
					instantKillChance = level;
				} else {
					instantKillChance = 2 * level;
				}
				
				if (chanceIsEnabled(instantKillChance)) {
					target.health = 0;
					System.out.println("INSTANT DEATH!!");
				} else {
					damage = usedSkill.damage + random.nextInt(level * 10);
					System.out.println(name + " dealt " + damage + " damage to " + target.name + ".");
				}
			} else {
				if (usedSkill.name.equalsIgnoreCase("Fire Wall") || usedSkill.name.equalsIgnoreCase("Fire Wall 2")) {
					// activate FLAME SHIELD!
					System.out.println(HeroStatus.FLAME_SHIELD + " activated!");
					status = HeroStatus.FLAME_SHIELD;
					fireWallCasted = usedSkill;
				} else if (usedSkill.name.equalsIgnoreCase("Counter Helix")) {
					// activate COUNTER HELIX!
					System.out.println(HeroStatus.COUNTER_HELIX + " activated!");
					status = HeroStatus.COUNTER_HELIX;
					buffDuration = 3;
				}
				damage = usedSkill.damage + random.nextInt(level * 10);
				System.out.println(name + " dealt " + damage + " damage to " + target.name + ".");
			}
			
			// check if the skill has an effect
			if (usedSkill.hasEffect() && !(usedSkill.name.equalsIgnoreCase("Fire Wall") || usedSkill.name.equalsIgnoreCase("Fire Wall 2"))) {
				// check if effect kicks in the skill
				double improvedEffectChance = usedSkill.effectChance + (5 * level);
				if (chanceIsEnabled(improvedEffectChance)) {
					lastCastedSkillWithEffect = usedSkill;
					// check if the effect of the skill is already felt by the target
					if (target.hasThisBuff(usedSkill.effectToTarget)) {
						int equalBuff = target.getEqualBuff(usedSkill.effectToTarget);
						// refresh duration to 3 turns
						target.refreshBuffDuration(equalBuff);
					} else {
						target.addNewBuff(usedSkill.effectToTarget, 3);
					}
					System.out.println(target.name + " is " + usedSkill.effectToTarget);
				}
			}
			
			target.health -= damage;
		} else {
			System.out.println("...Casting failed...");
		}		
	}

	public void skillAttackAll(Skill skill, ArrayList<Monster> monsterList) {
		Random random = new Random();
		int damage = 0;
		
		System.out.println(name + " casts " + skill.name);
		mana -= skill.mpCost;
		if (chanceIsEnabled(skill.accuracy)) {
			// attack all the monsters
			for (Monster monster : monsterList) {
				damage = skill.damage + random.nextInt(10 * level);
				System.out.println(name + " dealt " + damage + " damage to " + monster.name);
				monster.health -= damage;
			}
		} else {
			System.out.println("...Casting failed...");
		}
	}
	
	public void block(Monster monster, boolean blockSelected) {
		Random random = new Random();
		int damage;
		double improvedBlockChance;
		
		if (blockSelected) {
			System.out.println(name + " tries to block...");
			improvedBlockChance = blockChance + (random.nextDouble() * (5 * level));
		} else {
			improvedBlockChance = blockChance;
		}
		
		damage = random.nextInt((monster.highAtk - monster.lowAtk) + 1) + monster.lowAtk;
		
		if (chanceIsEnabled(improvedBlockChance)) {
			System.out.println(name + " successfully blocked!");
		} else {
			health -= damage;
			System.out.println(monster.name + "'s attack pushed through your block. " + monster.name + " dealts " + damage + " damage.");
		}
		
		if (!isDead()) {
			regenerateHpAndMp();
		}
	}

	public boolean flee(ArrayList<Monster> monstersList) {
		boolean fleeSuccess = false;
		System.out.println(name + " tries to flee...");
		
		boolean hasGreaterLevelThanYou = false;
		int currentHighestLevel = 0;
		Monster highestLevelMonster = null;
		
		for (int i = 0; i < monstersList.size(); i++) {
			if (monstersList.get(i).level > currentHighestLevel) {
				highestLevelMonster = monstersList.get(i);
				currentHighestLevel = highestLevelMonster.level;
			}
		}
		
		for (int i = 0; i < monstersList.size(); i++) {
			if (level < monstersList.get(i).level) {
				hasGreaterLevelThanYou = true;
				break;
			}
		}
		
		if (hasGreaterLevelThanYou) {
			fleeSuccess = Math.random() < 0.5;
		} else {
			double fleeChance = 80 + ((level - highestLevelMonster.level) * 2);
			if (chanceIsEnabled(fleeChance)) {
				fleeSuccess = true;
			} else {
				fleeSuccess = false;
			}
		}
		
		if (!fleeSuccess) {
			if (monstersList.size() > 1) {
				System.out.println("You can't escape from the monsters");
			} else {
				System.out.println("You can't escape from " + monstersList.get(0).name);
			}
			
			for (int i = 0; i < monstersList.size(); i++) {
				monstersList.get(i).attack(this);
			}
		}
		
		if (!isDead()) {
			regenerateHpAndMp();
		}
		
		return fleeSuccess;
	}

	public boolean isDead() {
		if (health <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public void regenerateHpAndMp() {
		Random random = new Random();
		int hpRegen = random.nextInt(10 * level);
		int mpRegen = random.nextInt(5 * level);
		
		health += hpRegen;
		mana += mpRegen;
		
		if (health > maxHP) {
			health = maxHP;
		}
		
		if (mana > maxMP) {
			mana = maxMP;
		}
	}

	public boolean listInventory() {
		ArrayList<Items> itemOnlyList = new ArrayList<Items>();
		if (inventory.getInventory().size() > 0) {
			itemOnlyList = inventory.getItemsOnly();
			// filter selection to items only
			if (itemOnlyList.size() > 0) {
				// give user a list what to select
				System.out.println("Select an item from below.");
				for (int i = 0; i < itemOnlyList.size(); i++) {
					Items selectedItem = itemOnlyList.get(i);
					System.out.println("["+(i+1)+"] " + selectedItem.getName() + "\t("+selectedItem.getQuantity()+"x)");
				}
				System.out.println("[0] CANCEL");

				Scanner in = new Scanner(System.in);
				String input;
				int itemNumber;
				boolean valid = false;

				while (!valid) {
					input = in.nextLine();
					try {
						itemNumber = Integer.parseInt(input);
						if (itemNumber < 0 || itemNumber > itemOnlyList.size()) {
							throw new NumberFormatException();
						} else if (itemNumber > 0) {
							// use the selected item and deduct its quantity
							System.out.println(name + " used " + itemOnlyList.get(itemNumber-1).getName());
							health += itemOnlyList.get(itemNumber-1).getHealthBoost();
							mana += itemOnlyList.get(itemNumber-1).getManaBoost();

							if (itemOnlyList.get(itemNumber-1).getHealthBoost() > 0) {
								System.out.println(name + " recovered " + itemOnlyList.get(itemNumber-1).getHealthBoost() + " HP: " + displayHP());
							}
							if (itemOnlyList.get(itemNumber-1).getManaBoost() > 0) {
								System.out.println(name + " recovered " + itemOnlyList.get(itemNumber-1).getManaBoost() + " MP" + displayMP());
							}

							if (health > maxHP) {
								health = maxHP;
							}
							if (mana > maxMP) {
								mana = maxMP;
							}

							// decrease the number of the item selected in the inventory
							inventory.decreaseQuantity(itemOnlyList.get(itemNumber-1).getId());
							inventory.refresh();

							return false;
						} else {
							return true;
						}
					} catch (NumberFormatException e) {
						System.out.println("Error. Try again.");
						valid = false;
					}
				}

			} else {
				System.out.println("There are no items in the inventory.");
				return true;
			}
		} else {
			System.out.println("There are no items in the inventory.");
			return true;
		}
		return false;
	}

	public boolean listEquipment() {
		ArrayList<Equipment> equipmentOnlyList = new ArrayList<Equipment>();
		if (inventory.getInventory().size() > 0) {
			equipmentOnlyList = inventory.getEquipmentListOnly(this.myClass);
			if (equipmentOnlyList.size() > 0) {
				// filter selections for equipment only
				// list all by body part
				// Head
				// [1]
				// [2]
				// ...
				// Body
				// ...
				for (int i = 0; i < equipmentOnlyList.size(); i++) {
					System.out.println("["+(i+1)+"] ("+equipmentOnlyList.get(i).getBodyPart()+") " + equipmentOnlyList.get(i).getName());
				}
				System.out.println("[0] CANCEL");
				
				Scanner in = new Scanner(System.in);
				String input;
				int selectedEquipment;
				boolean valid = false;
				
				while (!valid) {
					input = in.nextLine();
					try {
						selectedEquipment = Integer.parseInt(input);
						if (selectedEquipment < 0 || selectedEquipment > equipmentOnlyList.size()) {
							throw new NumberFormatException();
						} else if (selectedEquipment > 0) {
							// check if there is an existing equipment in the body part to be replaced by the selected equipment
							switch (equipmentOnlyList.get(selectedEquipment-1).getBodyPart()) {
							case HEAD:
								if (head != null) {
									Equipment.compare(this, head, equipmentOnlyList.get(selectedEquipment-1));
								} else {
									Equipment.equip(this, head, equipmentOnlyList.get(selectedEquipment-1));
								}
								break;
							case ARMOR:
								if (armor != null) {
									Equipment.compare(this, armor, equipmentOnlyList.get(selectedEquipment-1));
								} else {
									Equipment.equip(this, armor, equipmentOnlyList.get(selectedEquipment-1));
								}
								break;
							case PANTS:
								if (pants != null) {
									Equipment.compare(this, pants, equipmentOnlyList.get(selectedEquipment-1));
								} else {
									Equipment.equip(this, pants, equipmentOnlyList.get(selectedEquipment-1));
								}
								break;
							case GLOVES:
								if (gloves != null) {
									Equipment.compare(this, gloves, equipmentOnlyList.get(selectedEquipment-1));
								} else {
									Equipment.equip(this, gloves, equipmentOnlyList.get(selectedEquipment-1));
								}
								break;
							case SHOES:
								if (shoes != null) {
									Equipment.compare(this, shoes, equipmentOnlyList.get(selectedEquipment-1));
								} else {
									Equipment.equip(this, shoes, equipmentOnlyList.get(selectedEquipment-1));
								}
								break;
							case WEAPON:
								if (weapon != null) {
									Equipment.compare(this, weapon, equipmentOnlyList.get(selectedEquipment-1));
								} else {
									Equipment.equip(this, weapon, equipmentOnlyList.get(selectedEquipment-1));
								}
								break;
							}
							
							return false;
						} else {
							return true;
						}
					} catch (NumberFormatException e) {
						System.out.println("Error. Try again.");
						valid = false;
					}
				}
			} else {
				System.out.println("There are no available equipment in the inventory.");
				return true;
			}
		}
		return false;
	}
	
	public Object getItemDrop(Monster monster) {
		inventory.addItem(monster.itemDrop);
		return monster.itemDrop;
	}

	
}
