package com.apollo.training.games.rpg;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.apollo.training.games.GameFunction;
import com.apollo.training.games.rpg.Skill.AttackType;

public class RPG extends GameFunction {
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String input = null;
		int command = 0;
		Hero hero = null;
		boolean valid = false;
		String characterName = "";
		boolean tryAgain = true;
		
		while (tryAgain) {
			System.out.println("Welcome to Java Strategy Game!");
			// Prompt to enter your character's name
			while (!valid) {
				try {
					System.out.print("Enter your name: ");
					input = in.nextLine();
					if (input.equalsIgnoreCase("")) {
						throw new NullPointerException();
					}
					characterName = input;
					valid = true;
				} catch (NullPointerException e) {
					System.out.println(e.getMessage() + ": Try again.");
					continue;
				}
			}

			valid = false;
			while (!valid) {
				System.out.println("Pick what class do you want.\n"
						+ "[1] Swordsman (Stunner)\n"
						+ "[2] Archer (Hitter)\n"
						+ "[3] Mage (Arsonist)\n"
						+ "[4] Thief (Lifestealer)");
				input = in.nextLine();

				try {
					hero = new Hero(characterName, Integer.parseInt(input));
					valid = true;
				} catch (NumberFormatException e) {
					System.out.println("Error! Try again.");
					input = null;
					hero = null;
					continue;
				} 
			}

			while (!hero.isDead()) {
				// maximum monsters to be spawned - 3
				ArrayList<Monster> monstersList = new ArrayList<Monster>();
				monstersList.clear();
				for (int i = 0; i < 3 ; i++) {
					Monster monster = new Monster(hero);
					monstersList.add(monster);
					if (monster.level > hero.level - i - 1) {
						break;
					} else {
						continue;
					}
				}
				boolean fleed = false;
				do {
					fleed = false;
					valid = false;
					while (!valid) {
						System.out.println();
						System.out.print(hero.name + ": ["+hero.myClass+" LVL: "+hero.level+"] " + hero.displayHP() + " " + hero.displayMP() + "\tVS.\t");
						for (int i = 0; i < monstersList.size(); i++) {
							System.out.print(monstersList.get(i).name + " [LVL: "+monstersList.get(i).level+"]: " + monstersList.get(i).displayHP() + " | ");
						}
						System.out.println("\nWhat are you going to do?\n"
								+ "[1] Attack\n"
								+ "[2] Skill\n"
								+ "[3] Block\n"
								+ "[4] Items\n"
								+ "[5] Equipment\n"
								+ "[6] Flee");
						input = in.nextLine();

						try {
							command = Integer.parseInt(input);
							if (command < 1 || command > 6) {
								throw new NumberFormatException();
							}
							valid = true;
						} catch (NumberFormatException e) {
							System.out.println("Error input! Try again.");
							continue;
						}
					}

					switch (command) {
					case 1:
						// select target monster
						valid = false;
						while (!valid) {
							int monsterSelector;
							if (monstersList.size() > 1) {
								System.out.println("Choose the number of the monster you want to target");
								for (int i = 0; i < monstersList.size(); i++) {
									System.out.println("[" + (i+1) + "] " + monstersList.get(i).name + ": " + monstersList.get(i).displayHP());
								}
								System.out.println("[0] CANCEL");
								input = in.nextLine();
							}
							
							if (!input.contains("0")) {
								try {
									if (monstersList.size() > 1) {
										monsterSelector = Integer.parseInt(input);
									} else {
										monsterSelector = 1;
									}
									if (monsterSelector < 1 || monsterSelector > monstersList.size()) {
										throw new NumberFormatException();
									}
									valid = true;
									hero.targetMonsterId = monstersList.get(monsterSelector-1).id;
								} catch (NumberFormatException e) {
									System.out.println("Error input! Try again.");
									continue;
								}
							} else {
								valid = true;
							}
						}

						if (!input.contains("0")) {
							// get the attack order, simulate the attack
							ArrayList<Object> attackOrderList = getAttackOrder(hero, monstersList);
							Skill nullSkill = new Skill();
							nullSkill.name = "null";
							startAttack(attackOrderList, hero, nullSkill);
						}
						break;
					case 2:
						Skill skill = hero.listSkills();

						if (!hero.cancelledCastingSkill) {
							if (skill != null) {
								if (skill.atkType == AttackType.SINGLE) {
									// select a target monster
									valid = false;
									while (!valid) {
										int monsterSelector;
										if (monstersList.size() > 1) {
											System.out.println("Select monster to target:");
											for (int i = 0; i < monstersList.size(); i++) {
												System.out.println("[" + (i+1) + "] " + monstersList.get(i).name + ": " + monstersList.get(i).displayHP());
											}
											System.out.println("[0] CANCEL");
											input = in.nextLine();
										}

										if (!input.contains("0")) {
											try {
												if (monstersList.size() > 1) {
													monsterSelector = Integer.parseInt(input);
												} else {
													monsterSelector = 1;
												}
												if (monsterSelector < 1 || monsterSelector > monstersList.size()) {
													throw new NumberFormatException();
												}
												valid = true;
												hero.targetMonsterId = monstersList.get(monsterSelector-1).id;
											} catch (NumberFormatException e) {
												System.out.println("Error input! Try again.");
												continue;
											}
										} else {
											valid = true;
										}
									}
								} else {
									hero.targetMonsterId = -1;
								}
					
								if (!input.contains("0")) {
									ArrayList<Object> skillAttackOrder = getAttackOrder(hero, monstersList);
									startAttack(skillAttackOrder, hero, skill);
								}
							} else {
								System.out.println("There are no skills available yet.");
							}
						}
						break;
					case 3:
						attackBlockingHero(monstersList, hero);
						break;
					case 4:
						boolean noItems;
						noItems = hero.listInventory();
						if (!noItems) {
							for (Monster monster : monstersList) {
								monster.attack(hero);
							}
						}
						break;
					case 5:
						hero.listEquipment();
						break;
					case 6:
						fleed = hero.flee(monstersList);
						break;
					}

					ArrayList<Monster> tempList = new ArrayList<Monster>();
					for (int i = 0; i < monstersList.size(); i++) {
						if (monstersList.get(i).isDead()) {
							getExp(monstersList.get(i), hero);
						} else {
							tempList.add(monstersList.get(i));
						}
					}
					monstersList.clear();
					monstersList.addAll(tempList);
					
					if (fleed || hero.isDead()) {
						break;
					}
				} while (monstersList.size() > 0);

				if (!hero.isDead() && !fleed) {
					hero.regenerateHpAndMp();
					System.out.println("---BATTLE END---");
				}
			}

			System.out.println("---BATTLE END---");
			System.out.println("You are dead!");
			valid = false;
			while (!valid) {
				System.out.print("Do you want to try again? [y/n] \t");
				input = in.nextLine();
				if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
					valid = true;
					tryAgain = true;
					System.out.println("\n\n\n");
				} else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
					valid = true;
					tryAgain = false;
					break;
				} else {
					System.out.println("Error! Try again.");
					continue;
				}
			}
		}
		System.out.println("Goodbye!");
		in.close();
		System.exit(-1);
	}



	private static ArrayList<Object> getAttackOrder(Hero hero, ArrayList<Monster> monstersList) {
		ArrayList<Object> resultList = new ArrayList<Object>();
		boolean hasGreaterLevelThanHero = false;
		for (int i = 0; i < monstersList.size(); i++) {
			if (monstersList.get(i).level > hero.level) {
				hasGreaterLevelThanHero = true;
			}
		}
		
		int herosOrder = 0;
		if (hasGreaterLevelThanHero) {
			herosOrder = getRandomInteger(monstersList.size());
		}
		
		int count = 0;
		for (int i = 0; i <= monstersList.size(); i++) {
			if (herosOrder == i) {
				resultList.add(hero);
			} else {
				resultList.add(monstersList.get(count));
				count++;
			}
		}
		
		return resultList;
	}

	private static void startAttack(ArrayList<Object> attackOrderList, Hero hero, Skill skill) {
		for (int i = 0; i < attackOrderList.size(); i++) {
			Object selectedObj = attackOrderList.get(i);

			if (selectedObj instanceof Hero) {
				if (hero.targetMonsterId < 0) {
					ArrayList<Monster> tempMonsterList = getMonsters(attackOrderList);
					if (!hero.isDead()) {
						hero.skillAttackAll(skill, tempMonsterList);
						delay(700);
					}
				} else {
					Monster target = null;

					for (int j = 0; j < attackOrderList.size(); j++) {
						Object targetMonster = attackOrderList.get(j);
						if (targetMonster instanceof Monster) {
							Monster tempMonster = (Monster) targetMonster;
							if (tempMonster.id == hero.targetMonsterId) {
								target = (Monster) tempMonster;
								break;
							}
						}
					}
					if (!hero.isDead()) {
						if (!skill.name.equalsIgnoreCase("null")) {
							hero.skillAttack(skill, target);
						} else {
							hero.attack(target);
						}
						delay(700);
					}
				}
			} else {
				Monster monster = (Monster) selectedObj;
				if (!monster.isDead()) {
					monster.attack(hero);
					delay(700);
				}
			}
		}
		
		if (!hero.isDead()) {
			hero.regenerateHpAndMp();
		}
	}
	
	private static ArrayList<Monster> getMonsters(
			ArrayList<Object> attackOrderList) {
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		
		for (Object selectedObj : attackOrderList) {
			if (selectedObj instanceof Monster) {
				monsters.add((Monster) selectedObj);
			}
		}
		return monsters;
	}



	private static void attackBlockingHero(ArrayList<Monster> monstersList,
			Hero hero) {
		for (int i = 0; i < monstersList.size(); i++) {
			hero.block(monstersList.get(i), true);
		}
		if (!hero.isDead()) {
			hero.regenerateHpAndMp();
		}
	}
	
	private static void getExp(Monster monster, Hero hero) {
		System.out.println(monster.name + " is dead.");
		delay(500);
		int tempExperience = hero.experience + monster.experienceDrop;
		double percentage = ((double) tempExperience / hero.expRequired[hero.level - 1]) * 100.0;
		System.out.println(hero.name + " gains " + monster.experienceDrop + " EXP [" + tempExperience + "/" + hero.expRequired[hero.level - 1] + "] " + hero.round(percentage, 1) + "%");
		delay(500);
		hero.experience += monster.experienceDrop;
		// get the item if it exists
		if (monster.itemDrop != null) {
			Object drop = hero.getItemDrop(monster);
			System.out.print(hero.name + " got new ");
			if (drop instanceof Items) {
				Items tempItem = (Items) drop;
				System.out.println("Item: " + tempItem.getName());
			} else {
				Equipment tempEquipment = (Equipment) drop;
				System.out.println("Equipment: " + tempEquipment.getName() + " ["+tempEquipment.getHeroClass()+"]");
			}
			delay(1000);
		}
		// check if the hero will level up
		while (hero.experience >= hero.expRequired[hero.level - 1]) {
			hero.levelUp();		
			delay(2000);
		}
	}
	
	private static int getRandomInteger(int i) {
		Random random = new Random();
		return random.nextInt(i + 1);
	}
}
