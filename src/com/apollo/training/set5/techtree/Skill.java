package com.apollo.training.set5.techtree;

import com.apollo.training.set5.graph2.Edge;
import com.apollo.training.set5.graph2.Node;
/**
 * Skill.class holds information about the skills of a character.
 * @author apollo/carlstaana
 *
 */
public class Skill {
	public enum SkillType {
		COMMON, UNIQUE
	}

	private Node skill;
	private String description;
	private Edge experience;
	private SkillType skillType;

	/**
	 * Constructor for declaring a skill.
	 * @param skill the skill name
	 * @param description details of what the skill can do
	 * @param experience required experience to attain this skill
	 * @param skillType type of skill whether it is unique or common
	 */
	public Skill(Node skill, String description, Edge experience,
			SkillType skillType) {
		this.skill = skill;
		this.description = description;
		this.experience = experience;
		this.skillType = skillType;
	}

	public Node getSkill() {
		return skill;
	}

	public String getDescription() {
		return description;
	}

	public Edge getExperience() {
		return experience;
	}

	public SkillType getSkillType() {
		return skillType;
	}
	
	
}
