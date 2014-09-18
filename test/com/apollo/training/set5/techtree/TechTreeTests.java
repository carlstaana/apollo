package com.apollo.training.set5.techtree;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.apollo.training.set5.techtree.Character.CharacterType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TechTreeTests {
	
	@Test
	public void test1ListAvailable() {
		Character swordsman = new Character("Carl", CharacterType.SWORDSMAN);
		ArrayList<Skill> skillSet = swordsman.getSkillset();
		Skill bash = skillSet.get(1);
		ArrayList<String> skillsAvailable = swordsman.listAvailable(bash); //expected are magnum break and fire ball
		assertTrue("Magnum Break".equals(skillsAvailable.get(0)));
		assertTrue("Fireball".equals(skillsAvailable.get(1)));
	}
	
	@Test
	public void test2MinimumExperience() throws Exception {
		Character archer = new Character("Alleria", CharacterType.ARCHER);
		ArrayList<Skill> skillSet = archer.getSkillset();
		Skill atk = skillSet.get(0);
		Skill arrowShower = skillSet.get(2);
		
		int actual = archer.getMinimumExp(atk, arrowShower);
		int expected = 2050;
		
		assertEquals(expected, actual);
		
		Skill doubleStrafe = skillSet.get(1);
		
		actual = archer.getMinimumExp(atk, doubleStrafe);
		
		assertNotEquals(expected, actual);
	}
	
	@Test
	public void test3MaximumExperience() throws Exception {
		Character mage = new Character("Necromancer", CharacterType.MAGE);
		ArrayList<Skill> skillSet = mage.getSkillset();
		Skill atk = skillSet.get(0);
		Skill fireWall = skillSet.get(2);
		
		int actual = mage.getMaximumExp(atk, fireWall);
		int expected = 2300;
		
		assertEquals(expected, actual);
		
		Skill fireBall = skillSet.get(1);
		
		actual = mage.getMaximumExp(fireBall, fireWall);
		
		assertNotEquals(expected, actual);
	}
	
	@Test
	public void test4TechRequired() throws Exception {
		Character thief = new Character("Bilbo Baggins", CharacterType.THIEF);
		ArrayList<Skill> skillSet = thief.getSkillset();
		Skill hiding = skillSet.get(2);
		
		ArrayList<Skill> thiefReqs = thief.getUpgradeRequirements(hiding); //expected "Poison Stab"
		assertTrue(thiefReqs.get(0).equals(skillSet.get(1)));
	}
	
	@Test
	public void test5TechAvailableForEXP() throws Exception {
		Character swordsman = new Character("Yurnero", CharacterType.SWORDSMAN);
		ArrayList<Skill> skillSet = swordsman.getSkillset();
		
		int experience = 2000;
		
		ArrayList<Skill> availableTech = swordsman.listTechAvailableForExperience(experience); //expected Bash and Magnum Break
		assertEquals(availableTech.get(0), skillSet.get(1));
		assertEquals(availableTech.get(1), skillSet.get(3));
	}
	
	@Test
	public void test6GetUniqueSkills() throws Exception {
		Character mage = new Character("Rotund'jere", CharacterType.MAGE);
		ArrayList<Skill> skillSet = mage.getUniqueSkills(); // expected FireWall
		assertTrue(skillSet.get(0).getSkill().getNodeValue().equals("Fire Wall"));
		assertTrue(skillSet.size() == 1);
	}
	
	@Test
	public void test7GetCommonskills() throws Exception {
		Character swordsman = new Character("Knight Davion", CharacterType.SWORDSMAN);
		ArrayList<Skill> skillSet = swordsman.getCommonSkills(); // expected Attack, Fireball
		assertTrue(skillSet.get(0).getSkill().getNodeValue().equals("Attack"));
		assertTrue(skillSet.get(1).getSkill().getNodeValue().equals("Fireball"));
		assertTrue(skillSet.size() == 2);
	}

}
