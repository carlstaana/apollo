package com.apollo.training.set5.techtree;

import java.util.ArrayList;

import com.apollo.training.set5.graph2.Edge;
import com.apollo.training.set5.graph2.LinkedList;
import com.apollo.training.set5.graph2.ListIterator;
import com.apollo.training.set5.graph2.Node;
import com.apollo.training.set5.graph2.WeightedGraph;
import com.apollo.training.set5.techtree.Skill.SkillType;
/**
 * Character.class stores information about a character.
 * @author apollo/carlstaana
 *
 */
public class Character {
	@SuppressWarnings("unused")
	private String username;
	private CharacterType type;
	private ArrayList<Skill> skillset = new ArrayList<Skill>();
	WeightedGraph<Node, Edge> dwg = null;
	/**
	 * Main constructor in declaring a character.
	 * @param username the username or the name of the character
	 * @param type the type or the race of the character
	 */
	public Character(String username, CharacterType type) {
		dwg = new WeightedGraph<Node, Edge>();
		this.username = username;
		this.type = type;

		setupSkillTree(type);
		System.out.println("\n--------------------------------------------------\n"
				+ "Welcome! " + username + " [" + type.toString()
				+ "]");
	}

	public enum CharacterType {
		SWORDSMAN, ARCHER, MAGE, THIEF
	}

	public ArrayList<Skill> getSkillset() {
		return skillset;
	}
	/**
	 * Setting up the skill tree depending of what character type should be considered.
	 * @param type the type or the race of the character
	 */
	public void setupSkillTree(CharacterType type) {
		dwg.addNode("Attack");
		Node node = (Node) dwg.getNodeList().getFirst();
		Skill attack = new Skill(node,
				"Character will damage a foe with a single blow.", null, SkillType.COMMON);
		
		skillset.clear();
		skillset.add(attack);
		switch (type) {
		case SWORDSMAN:
			dwg.addNode("Bash");
			Node node2 = (Node) dwg.getNodeList().getFirst();
			dwg.connect(node, node2, 500);
			Edge edge = (Edge) dwg.getEdgeList().getFirst();
			Skill bash = new Skill(
					node2,
					"Character will attack the head of the foe with a 10% chance of stun in 3 secs.",
					edge, SkillType.UNIQUE);
			skillset.add(bash);

			dwg.addNode("Fireball");
			Node node4 = (Node) dwg.getNodeList().getFirst();
			dwg.connect(node2, node4, 1650);
			Edge edge3 = (Edge) dwg.getEdgeList().getFirst();
			Skill fireBall = new Skill(
					node4,
					"Character will cast a ball of fire that will inflict damage and 5% chance of Burn.",
					edge3, SkillType.COMMON);
			skillset.add(fireBall);

			dwg.addNode("Magnum Break");
			Node node6 = (Node) dwg.getNodeList().getFirst();
			dwg.connect(node2, node6, 1500);
			Edge edge5 = (Edge) dwg.getEdgeList().getFirst();
			Skill magnumBreak = new Skill(
					node6,
					"Character will focus his inner strength and cast it on the blade destroying the enemy's armor. It also reveals invisible units.",
					edge5, SkillType.UNIQUE);
			skillset.add(magnumBreak);
			break;
		case ARCHER:
			dwg.addNode("Double Strafe");
			Node node3 = (Node) dwg.getNodeList().getFirst();
			dwg.connect(node, node3, 500);
			Edge edge2 = (Edge) dwg.getEdgeList().getFirst();
			Skill dbStrafe = new Skill(node3, "Archer will attack twice swiftly.",
					edge2, SkillType.UNIQUE);
			skillset.add(dbStrafe);

			dwg.addNode("Arrow Shower");
			Node node7 = (Node) dwg.getNodeList().getFirst();
			dwg.connect(node3, node7, 1550);
			Edge edge6 = (Edge) dwg.getEdgeList().getFirst();
			Skill arrowShower = new Skill(node7,
					"Archer will deploy multiple arrows in a strafing manner.",
					edge6, SkillType.UNIQUE);

			skillset.add(arrowShower);
			break;
		case MAGE:
			dwg.addNode("Fireball");
			Node node10 = (Node) dwg.getNodeList().getFirst();
			dwg.connect(node, node10, 550);
			Edge edge11 = (Edge) dwg.getEdgeList().getFirst();
			Skill fireBall2 = new Skill(
					node10,
					"Character will cast a ball of fire that will inflict damage and 5% chance of Burn.",
					edge11, SkillType.COMMON);
			skillset.add(fireBall2);

			dwg.addNode("Fire Wall");
			Node node8 = (Node) dwg.getNodeList().getFirst();
			dwg.connect(node10, node8, 1750);
			Edge edge7 = (Edge) dwg.getEdgeList().getFirst();
			Skill fireWall = new Skill(
					node8,
					"Mage will cast a wall of fire that will block the enemy's attack.",
					edge7, SkillType.UNIQUE);
			skillset.add(fireWall);
			break;
		case THIEF:
			dwg.addNode("Poison Stab");
			Node node5 = (Node) dwg.getNodeList().getFirst();
			dwg.connect(node, node5, 650);
			Edge edge4 = (Edge) dwg.getEdgeList().getFirst();
			Skill poisonStab = new Skill(
					node5,
					"Character will stab a poisonous blade to the enemy leading to a 30% chance of being poisoned(60 damage per second).",
					edge4, SkillType.UNIQUE);
			skillset.add(poisonStab);

			dwg.addNode("Hiding");
			Node node9 = (Node) dwg.getNodeList().getFirst();
			dwg.connect(node5, node9, 1250);
			Edge edge8 = (Edge) dwg.getEdgeList().getFirst();
			Skill hiding = new Skill(node9,
					"Makes the character invisible for 15 secs.", edge8, SkillType.UNIQUE);
			skillset.add(hiding);
			break;
		default:
			break;
		}
	}
	/**
	 * Lists all available skills next to a targeted skill.
	 * @param skill the targeted skill
	 * @return a List of skills
	 */
	public ArrayList<String> listAvailable(Skill skill) {
		LinkedList available = dwg.getEdgeList();
		ListIterator iter = available.listIterator();
		ArrayList<String> listConnected = new ArrayList<String>();
		while (iter.hasNext()) {
			Edge checkEdge = (Edge) iter.next();
			if (checkEdge.node1.equals(skill.getSkill())) {
				listConnected.add(checkEdge.node2.getNodeValue());
			}
		}
		System.out.println("\nSkills available to upgrade from: " + skill.getSkill().getNodeValue());
		for (String str : listConnected) {
			for (int i = 0; i < skillset.size(); i++) {
				if (str.equals(skillset.get(i).getSkill().getNodeValue())) {
					System.out.println(skillset.get(i).getSkill().getNodeValue() + ": " + skillset.get(i).getDescription());
				}
			}
		}
		return listConnected;
	}
	/**
	 * Gets the minimum experience needed between the path of two skills
	 * @param skill1 the starting point of getting the minimum experience
	 * @param skill2 the destination point
	 * @return the computation of minimum experience required
	 */
	public int getMinimumExp(Skill skill1, Skill skill2) {
		int minimum = dwg.getMinimumTotalWeight(skill1.getSkill(), skill2.getSkill());
		System.out.println("\nMinimum EXP required ["+skill1.getSkill().getNodeValue()+" --> "+skill2.getSkill().getNodeValue()+"]: " + minimum);
		return minimum;
	}
	/**
	 * Gets the maximum experience needed between the path of two skills
	 * @param skill1 the starting point of getting the minimum experience
	 * @param skill2 the destination point
	 * @return the computation of maximum experience required
	 */
	public int getMaximumExp(Skill skill1, Skill skill2) {
		int maximum = dwg.getMaximumTotalWeight(skill1.getSkill(), skill2.getSkill());
		System.out.println("\nMaximum EXP required ["+skill1.getSkill().getNodeValue()+" --> "+skill2.getSkill().getNodeValue()+"]: " + maximum);
		return maximum;
	}
	/**
	 * Lists skills available for upgrade considering a certain amount of experience.
	 * @param experience the experience to be considered
	 * @return a list of results
	 */
	public ArrayList<Skill> listTechAvailableForExperience(int experience) {
		ArrayList<Skill> techList = new ArrayList<Skill>();
		System.out.println("\nSkills Available to upgrade for: " + experience + " EXP");
		for (int i = 1; i < skillset.size(); i++) {
			int difference = experience - getMinimumExp(skillset.get(0), skillset.get(i));
			if (difference >= 0) {
				System.out.println(skillset.get(i).getSkill().getNodeValue() + "[EXP remaining: "+difference+"]: " + skillset.get(i).getDescription());
				techList.add(skillset.get(i));;
			}
		}
		return techList;
	}
	/**
	 * Gets the list of skills required to upgrade to reach the targeted skill.
	 * @param skill the targeted skill
	 * @return a list of results
	 */
	public ArrayList<Skill> getUpgradeRequirements(Skill skill) {
		ArrayList<Skill> outputList = new ArrayList<Skill>();
		System.out.println("\nSkills that are required to upgrade to reach: " + skill.getSkill().getNodeValue());
		for (int i = skillset.size() - 1; i > 0; i--) {
			if (getSkillset().get(i) != skill && !dwg.isUnreachable(getSkillset().get(i).getSkill(), skill.getSkill())) {
				System.out.println(skillset.get(i).getSkill().getNodeValue() + ": " + skillset.get(i).getDescription());
				outputList.add(skillset.get(i));
			}
		}
		return outputList;
	}
	/**
	 * Displays the unique skills of your selected character
	 * @return a list of results
	 */
	public ArrayList<Skill> getUniqueSkills() {
		ArrayList<Skill> outputList = new ArrayList<Skill>();
		System.out.println();
		System.out.println(type.toString() + "'s Unique Skills");
		for (int i = 1; i < skillset.size(); i++) {
			if (skillset.get(i).getSkillType() == SkillType.UNIQUE) {
				// unique
				System.out.println(skillset.get(i).getSkill().getNodeValue() + ": " + skillset.get(i).getDescription());
				outputList.add(skillset.get(i));
			}
		}
		return outputList;
	}
	/**
	 * Displays the common skills of your selected character.
	 * @return a list of results
	 */
	public ArrayList<Skill> getCommonSkills() {
		ArrayList<Skill> outputList = new ArrayList<Skill>();
		System.out.println();
		System.out.println(type.toString() + "'s Common Skills");
		for (int i = 0; i < skillset.size(); i++) {
			if (skillset.get(i).getSkillType() == SkillType.COMMON) {
				// common
				System.out.println(skillset.get(i).getSkill().getNodeValue() + ": " + skillset.get(i).getDescription());
				outputList.add(skillset.get(i));
			}
		}
		return outputList;
	}
	/**
	 * Lists all characters available to be chosen in the game.
	 */
	public void listAllCharacters() {
		System.out.println("\nList of all Characters:");
		for (CharacterType character : CharacterType.class.getEnumConstants()) {
			System.out.println(character.toString());
		}
	}

}
