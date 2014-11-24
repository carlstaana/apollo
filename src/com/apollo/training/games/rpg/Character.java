package com.apollo.training.games.rpg;

import java.util.Random;

public class Character {
	
	private String name;
	
	private int level;
	
	private int health;
	
	private int maxHP;
	
	private int lowAtk;
	
	private int highAtk;
	
	private int attackOrderNumber;
	
	// programmatic variables
	public Random randomizer = new Random();
	
	
	
	public Character() {
		// TODO Auto-generated constructor stub
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getLowAtk() {
		return lowAtk;
	}

	public void setLowAtk(int lowAtk) {
		this.lowAtk = lowAtk;
	}

	public int getHighAtk() {
		return highAtk;
	}

	public void setHighAtk(int highAtk) {
		this.highAtk = highAtk;
	}
	
	public int getAttackOrderNumber() {
		return attackOrderNumber;
	}



	public void setAttackOrderNumber(int attackOrderNumber) {
		this.attackOrderNumber = attackOrderNumber;
	}

	public String displayHP() {
		return "HP: " + displayGauge(health, maxHP) + " " + health + "/" + maxHP;
	}
	
	public String displayGauge(int current, int maximum) {
		String result = "|";
		double percentage = ((double) current/ (double) maximum) * 100;
		if (percentage > 0) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 5) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 10) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 15) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 20) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 25) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 30) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 35) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 40) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 45) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 50) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 55) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 60) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 65) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 70) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 75) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 80) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 85) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 90) {
			result += "=";
		} else {
			result += " ";
		}
		if (percentage > 95) {
			result += "=";
		} else {
			result += " ";
		}
		result += "|";
		return result;
	}
	
	public int getNormalDamage() {
		Random random = new Random();
		return random.nextInt((highAtk - lowAtk) + 1) + lowAtk;
	}
	
	public void decreaseHP(int damage) {
		health -= damage;
	}
	
	public void increaseHP(int regen) {
		health += regen;
		if (health > maxHP) {
			health = maxHP;
		}
	}
	
	public boolean isDead() {
		return health <= 0;
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
}
