package com.apollo.training.games.rpg;

import com.apollo.training.games.rpg.Buff.Status;
import com.apollo.training.games.rpg.Hero.HeroClass;

public class Skill {
	public String name;
	public Type skillType;
	public AttackType atkType;
	public HeroClass type;
	public int damage;
	public int accuracy;
	public int levelRequired;
	public int mpCost;
	public String description;
	public Status effectToTarget = null;
	public int duration = 0;
	public int effectChance = 0;
	
	public enum Type { ACTIVE, PASSIVE }
	public enum AttackType { SINGLE, ALL }

	public boolean hasEffect() {
		if (effectToTarget == null) {
			return false;
		} else {
			return true;
		}	
	}
}
