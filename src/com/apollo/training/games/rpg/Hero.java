package com.apollo.training.games.rpg;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.apollo.training.games.GameFunction;
import com.apollo.training.games.rpg.Skill.Type;

public class Hero extends Character {
	// hero's statistics
	private int mana;
	
	private int maxMP;
	
	private int experience;
	
	public int[] expRequired = {200, 500, 750, 1000, 1500, 2000, 3000, 5000, 7500, 10000, 15000, 25000, 50000, 75000, 150000};
	
	private double criticalChance;
	
	private double dodgeChance;
	
	private double blockChance;
	
	// hero's skills
	public ArrayList<Skill> skills = null;
	SkillSet skillSet = null;
	
	// hero's class
	private HeroClass myClass;
	
	public enum HeroClass{ SWORDSMAN, ARCHER, MAGE, THIEF }
	
	// hero's status
	private HeroStatus status;
	public int buffDuration;
	
	public enum HeroStatus { NONE, FLAME_SHIELD, COUNTER_HELIX, AGILITY_SPURT }
	
	// hero's inventory
	private Inventory inventory;
	
	// hero's equipment
	private Equipment[] body = new Equipment[6];
	private Equipment head = body[0];
	private Equipment armor = body[1];
	private Equipment pants = body[2];
	private Equipment gloves = body[3];
	private Equipment shoes = body[4];
	private Equipment weapon = body[5];
	
	// skill has been cancelled
	public boolean cancelledCastingSkill;
	
	// the latest skill the monster has been affected
	public Skill fireWallCasted;
	public Skill lastCastedSkillWithEffect;
	
	// target monster id
	public int targetMonsterId;
	
	
	
	public Hero(String name, int classNo) throws NumberFormatException {
		super();
		setName(name);
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
		
		setName(getName());
		if (myClass == HeroClass.SWORDSMAN) {
			setMaxHP(350);
		} else {
			setMaxHP(250);
		}
		setHealth(getMaxHP());
		if (myClass == HeroClass.MAGE) {
			maxMP = 200;
		} else {
			maxMP = 120;
		}
		mana = maxMP;
		if (myClass == HeroClass.ARCHER) {
			setLowAtk(randomizer.nextInt(10) + 80);
		} else {
			setLowAtk(randomizer.nextInt(10) + 50);
		}
		setHighAtk(randomizer.nextInt(20) + 5 + getLowAtk());
		experience = 0;
		setLevel(1);
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
		System.out.println(getName() + " [" + myClass + "]");
		System.out.println("Level: " + getLevel());
		System.out.println(displayHP());
		System.out.println(displayMP());
		System.out.println("ATK: " + getLowAtk() + "-" + getHighAtk());
		System.out.println("CRIT: " + round(criticalChance, 2) + "%");
		System.out.println("DODGE: " + round(dodgeChance, 2) + "%");
		System.out.println("BLOCK: " + round(blockChance, 2) + "%");
		return super.toString();
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
		experience -= expRequired[getLevel() - 1];
		setLevel(getLevel() + 1);
		System.out.println("\n---LEVEL UP!!!---");
		System.out.println(getName() + " is now Level " + getLevel());
		
		// upgrade stats
		setMaxHP(getMaxHP() + randomizer.nextInt(200) + (10 * getLevel()) + 50);
		setHealth(getMaxHP());
		maxMP += randomizer.nextInt(100) + (5 * getLevel()) + 20;
		mana = maxMP;
		
		// for increasing the attack power
		int tempLowAtk;
		int tempHighAtk;
		do {
			tempLowAtk = 0;
			tempHighAtk = 0;
			tempLowAtk = randomizer.nextInt(12 * getLevel()) + getLowAtk();
			tempHighAtk = randomizer.nextInt(15 * getLevel()) + getHighAtk();
		} while (tempLowAtk >= tempHighAtk);
		setLowAtk(tempLowAtk);
		setHighAtk(tempHighAtk);
		
		criticalChance += randomizer.nextDouble() * 5.0;
		dodgeChance += randomizer.nextDouble() * 5.0;
		blockChance += randomizer.nextDouble() * 10.0;

		toString();
		
		// check also if there are available skills
		for (Skill skill : skills) {
			if (skill.getLevelRequired() == getLevel()) {
				System.out.println("\nYou have learned: " + skill.getName() + " [" + skill.getSkillType() + "]");
				System.out.println(skill.getDescription());
				if (skill.getSkillType() == Type.ACTIVE) {
					System.out.println("Base Damage: " + skill.getDamage() + "; MP Cost: " + skill.getMpCost());
				}
			}
		}
		
		if (myClass == HeroClass.SWORDSMAN && getLevel() == 3) {
			// activate passive skill
			status = HeroStatus.COUNTER_HELIX;
		}
		
		if (myClass == HeroClass.THIEF && getLevel() == 4) {
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
			if (skill.getLevelRequired() <= getLevel() && mana >= skill.getMpCost() && skill.getSkillType() == Type.ACTIVE) {
				availableSkills.add(skill);
			}
		}
		
		// check if available skills has something
		if (availableSkills.size() <= 0) {
			in.close();
			return null;
		} else {
			valid = false;
			while (!valid) {
				System.out.println("\nSelect Skills below:");
				for (int i = 0; i < availableSkills.size(); i++) {
					System.out.println("["+ (i+1) +"] " + availableSkills.get(i).getName() + " MP: " + availableSkills.get(i).getMpCost());
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
		in.close();
		return null;
	}

	public void attack(Monster target) {
		int damage;
		
		damage = getNormalDamage();
		if (chanceIsEnabled(criticalChance)) {
			damage = damage * 2;
			System.out.println(getName() + " dealt " + damage + " damage!!! to " + target.getName());
		} else {
			System.out.println(getName() + " dealt " + damage + " damage to " + target.getName());
		}
		target.decreaseHP(damage);
		
		if (myClass == HeroClass.THIEF) {
			// Lifesteal
			double lifeStealPercentage = 10 + (5 * getLevel());
			int stealChance = 20 + (5 * getLevel());
			int lifeStealed = (int) ((lifeStealPercentage / 100.0) * damage);
			
			if (chanceIsEnabled(stealChance)) {
				System.out.println(getName() + " steals " + lifeStealed + " HP");
				increaseHP(lifeStealed);
			}
		}
	}

	public void skillAttack(Skill usedSkill, Monster target) {
//		--LIST OF ALL SKILLS--
//		+----+-----------------+
//		| id | name            |
//		+----+-----------------+
//		|  1 | Bash            |
//		|  2 | Fireball        |
//		|  3 | Magnum Break    |
//		|  4 | Fireball 2      |
//		|  5 | Magnum Break 2  |
//		|  6 | Double Strafe   |
//		|  7 | Arrow Shower    |
//		|  8 | Double Strafe 2 |
//		|  9 | Arrow Shower 2  |
//		| 10 | Fireball        |
//		| 11 | Fire Wall       |
//		| 12 | Fireball 2      |
//		| 13 | Fire Wall 2     |
//		| 14 | Poison Stab     |
//		| 15 | Throat Slice    |
//		| 16 | Poison Stab 2   |
//		| 17 | Throat Slice 2  |
//		| 18 | Counter Helix   |
//		| 19 | Agility Spurt   |
//		| 20 | Blade Fury      |
//		| 21 | Multi-shot      |
//		| 22 | Fire Wave       |
//		| 23 | Dagger Throw    |
//		+----+-----------------+

		int damage = 0;
		
		System.out.println(getName() + " casts " + usedSkill.getName());
		decreaseMP(usedSkill.getMpCost());
		if (chanceIsEnabled(usedSkill.getAccuracy())) {
			// special conditions for double strafe and double strafe 2
			if (usedSkill.getId() == 6 || usedSkill.getId() == 8) {
				if (usedSkill.getId() == 6) {
					damage = (getNormalDamage() * 2) + randomizer.nextInt(getLevel() * 10);
				} else {
					damage = (getNormalDamage() * 3) + randomizer.nextInt(getLevel() * 10);
				}
				
				if (chanceIsEnabled(criticalChance)) {
					damage = damage * 2;
					System.out.println(getName() + " dealt " + damage + " damage to " + target.getName() + "!!!");
				} else {
					System.out.println(getName() + " dealt " + damage + " damage to " + target.getName() + ".");
				}
			} else if (usedSkill.getId() == 7 || usedSkill.getId() == 9) {
				int attackRepetition = 0;
				if (usedSkill.getId() == 7) {
					attackRepetition = 5;
				} else {
					attackRepetition = 8;
				}
				
				System.out.print(getName() + " dealts... ");
				int totalDamage = 0;
				for (int i = attackRepetition; i > 0; i--) {
					damage = getNormalDamage();
					System.out.print(damage);
					
					if (chanceIsEnabled(criticalChance)) {
						damage = damage * 2;
						System.out.print("!!");
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
			} else if (usedSkill.getId() == 15 || usedSkill.getId() == 17) {
				int instantKillChance;
				if (usedSkill.getId() == 15) {
					instantKillChance = getLevel();
				} else {
					instantKillChance = 2 * getLevel();
				}
				
				if (chanceIsEnabled(instantKillChance)) {
					target.setHealth(0);
					System.out.println("INSTANT DEATH!!");
				} else {
					damage = usedSkill.getDamage() + randomizer.nextInt(getLevel() * 10);
					System.out.println(getName() + " dealt " + damage + " damage to " + target.getName() + ".");
				}
			} else {
				if (usedSkill.getId() == 11 || usedSkill.getId() == 13) {
					// activate FLAME SHIELD!
					System.out.println(HeroStatus.FLAME_SHIELD + " activated!");
					status = HeroStatus.FLAME_SHIELD;
					fireWallCasted = usedSkill;
				} else if (usedSkill.getId() == 18) {
					// activate COUNTER HELIX!
					System.out.println(HeroStatus.COUNTER_HELIX + " activated!");
					status = HeroStatus.COUNTER_HELIX;
					buffDuration = 3;
				}
				damage = usedSkill.getDamage() + randomizer.nextInt(getLevel() * 10);
				System.out.println(getName() + " dealt " + damage + " damage to " + target.getName() + ".");
			}
			
			// check if the skill has an effect
			if (usedSkill.hasEffect() && !(usedSkill.getId() == 11 || usedSkill.getId() == 13)) {
				// check if effect kicks in the skill
				double improvedEffectChance = usedSkill.getEffectChance() + (5 * getLevel());
				if (chanceIsEnabled(improvedEffectChance)) {
					lastCastedSkillWithEffect = usedSkill;
					// check if the effect of the skill is already felt by the target
					if (target.hasThisBuff(usedSkill.getEffectToTarget())) {
						int equalBuff = target.getEqualBuff(usedSkill.getEffectToTarget());
						// refresh duration to 3 turns
						target.refreshBuffDuration(equalBuff);
					} else {
						target.addNewBuff(usedSkill.getEffectToTarget(), 3);
					}
					System.out.println(target.getName() + " is " + usedSkill.getEffectToTarget());
				}
			}
			
			target.decreaseHP(damage);
		} else {
			System.out.println("...Casting failed...");
		}		
	}

	private void decreaseMP(int mpCost) {
		mana -= mpCost;
		if (mana < 0) {
			mana = 0;
		}
	}

	public void skillAttackAll(Skill skill, ArrayList<Monster> monsterList) {
		int damage = 0;
		
		System.out.println(getName() + " casts " + skill.getName());
		decreaseMP(skill.getMpCost());
		if (chanceIsEnabled(skill.getAccuracy())) {
			// attack all the monsters
			for (Monster monster : monsterList) {
				damage = skill.getDamage() + randomizer.nextInt(10 * getLevel());
				System.out.println(getName() + " dealt " + damage + " damage to " + monster.getName());
				monster.decreaseHP(damage);
			}
		} else {
			System.out.println("...Casting failed...");
		}
	}
	
	public void block(Monster monster, boolean blockSelected) {
		int damage;
		double improvedBlockChance;
		
		if (blockSelected) {
			System.out.println(getName() + " tries to block...");
			improvedBlockChance = blockChance + (randomizer.nextDouble() * (5 * getLevel()));
		} else {
			improvedBlockChance = blockChance;
		}
		
		damage = monster.getNormalDamage();
		
		if (chanceIsEnabled(improvedBlockChance)) {
			System.out.println(getName() + " successfully blocked!");
		} else {
			decreaseHP(damage);
			System.out.println(monster.getName() + "'s attack pushed through your block. " + monster.getName() + " dealts " + damage + " damage.");
		}
		
		if (!isDead()) {
			regenerateHpAndMp();
		}
	}

	public boolean flee(ArrayList<Monster> monstersList) {
		boolean fleeSuccess = false;
		System.out.println(getName() + " tries to flee...");
		GameFunction.delay(1500);
		GameFunction.clearScreen();
		
		
		boolean hasGreaterLevelThanYou = false;
		int currentHighestLevel = 0;
		Monster highestLevelMonster = null;
		
		for (int i = 0; i < monstersList.size(); i++) {
			if (monstersList.get(i).getLevel() > currentHighestLevel) {
				highestLevelMonster = monstersList.get(i);
				currentHighestLevel = highestLevelMonster.getLevel();
			}
		}
		
		for (int i = 0; i < monstersList.size(); i++) {
			if (getLevel() < monstersList.get(i).getLevel()) {
				hasGreaterLevelThanYou = true;
				break;
			}
		}
		
		if (hasGreaterLevelThanYou) {
			fleeSuccess = Math.random() < 0.5;
		} else {
			double fleeChance = 80 + ((getLevel() - highestLevelMonster.getLevel()) * 2);
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
				System.out.println("You can't escape from " + monstersList.get(0).getName());
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

	public void regenerateHpAndMp() {
		int hpRegen = randomizer.nextInt(10 * getLevel());
		int mpRegen = randomizer.nextInt(5 * getLevel());
		
		increaseHP(hpRegen);
		increaseMP(mpRegen);
	}

	private void increaseMP(int mpRegen) {
		mana += mpRegen;
		
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
							System.out.println(getName() + " used " + itemOnlyList.get(itemNumber-1).getName());
							increaseHP(itemOnlyList.get(itemNumber-1).getHealthBoost());
							increaseMP(itemOnlyList.get(itemNumber-1).getManaBoost());

							if (itemOnlyList.get(itemNumber-1).getHealthBoost() > 0) {
								System.out.println(getName() + " recovered " + itemOnlyList.get(itemNumber-1).getHealthBoost() + " HP: " + displayHP());
							}
							if (itemOnlyList.get(itemNumber-1).getManaBoost() > 0) {
								System.out.println(getName() + " recovered " + itemOnlyList.get(itemNumber-1).getManaBoost() + " MP" + displayMP());
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
				in.close();
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
				in.close();
			} else {
				System.out.println("There are no available equipment in the inventory.");
				return true;
			}
		}
		return false;
	}
	
	public Object getItemDrop(Monster monster) {
		inventory.addItem(monster.getItemDrop());
		return monster.getItemDrop();
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getMaxMP() {
		return maxMP;
	}

	public void setMaxMP(int maxMP) {
		this.maxMP = maxMP;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public double getCriticalChance() {
		return criticalChance;
	}

	public void setCriticalChance(double criticalChance) {
		this.criticalChance = criticalChance;
	}

	public double getDodgeChance() {
		return dodgeChance;
	}

	public void setDodgeChance(double dodgeChance) {
		this.dodgeChance = dodgeChance;
	}

	public double getBlockChance() {
		return blockChance;
	}

	public void setBlockChance(double blockChance) {
		this.blockChance = blockChance;
	}

	public HeroClass getMyClass() {
		return myClass;
	}

	public void setMyClass(HeroClass myClass) {
		this.myClass = myClass;
	}

	public HeroStatus getStatus() {
		return status;
	}

	public void setStatus(HeroStatus status) {
		this.status = status;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Equipment[] getBody() {
		return body;
	}

	public void setBody(Equipment[] body) {
		this.body = body;
	}

	public Equipment getHead() {
		return head;
	}

	public void setHead(Equipment head) {
		this.head = head;
	}

	public Equipment getArmor() {
		return armor;
	}

	public void setArmor(Equipment armor) {
		this.armor = armor;
	}

	public Equipment getPants() {
		return pants;
	}

	public void setPants(Equipment pants) {
		this.pants = pants;
	}

	public Equipment getGloves() {
		return gloves;
	}

	public void setGloves(Equipment gloves) {
		this.gloves = gloves;
	}

	public Equipment getShoes() {
		return shoes;
	}

	public void setShoes(Equipment shoes) {
		this.shoes = shoes;
	}

	public Equipment getWeapon() {
		return weapon;
	}

	public void setWeapon(Equipment weapon) {
		this.weapon = weapon;
	}

	
}
