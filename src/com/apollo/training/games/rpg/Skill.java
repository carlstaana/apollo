package com.apollo.training.games.rpg;

import com.apollo.training.games.rpg.Buff.Status;
import com.apollo.training.games.rpg.Hero.HeroClass;

public class Skill {
	private int id;
	
	private String name;
	
	private Type skillType;
	
	private AttackType atkType;
	
	private HeroClass type;
	
	private int damage;
	
	private int accuracy;
	
	private int levelRequired;
	
	private int mpCost;
	
	private String description;
	
	private Status effectToTarget = null;
	
	private int duration = 0;
	
	private int effectChance = 0;
	
	
	public enum Type { ACTIVE, PASSIVE }
	
	public enum AttackType { SINGLE, ALL }

	
	
	public boolean hasEffect() {
		if (getEffectToTarget() == null) {
			return false;
		} else {
			return true;
		}	
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Type getSkillType() {
		return skillType;
	}



	public void setSkillType(Type skillType) {
		this.skillType = skillType;
	}



	public AttackType getAtkType() {
		return atkType;
	}



	public void setAtkType(AttackType atkType) {
		this.atkType = atkType;
	}



	public HeroClass getType() {
		return type;
	}



	public void setType(HeroClass type) {
		this.type = type;
	}



	public int getDamage() {
		return damage;
	}



	public void setDamage(int damage) {
		this.damage = damage;
	}



	public int getAccuracy() {
		return accuracy;
	}



	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}



	public int getLevelRequired() {
		return levelRequired;
	}



	public void setLevelRequired(int levelRequired) {
		this.levelRequired = levelRequired;
	}



	public int getMpCost() {
		return mpCost;
	}



	public void setMpCost(int mpCost) {
		this.mpCost = mpCost;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Status getEffectToTarget() {
		return effectToTarget;
	}



	public void setEffectToTarget(Status effectToTarget) {
		this.effectToTarget = effectToTarget;
	}



	public int getDuration() {
		return duration;
	}



	public void setDuration(int duration) {
		this.duration = duration;
	}



	public int getEffectChance() {
		return effectChance;
	}



	public void setEffectChance(int effectChance) {
		this.effectChance = effectChance;
	}
	
	
}
