package com.apollo.training.games.rpg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.apollo.training.games.rpg.Hero.HeroClass;

public class Equipment {
	private int id;
	private String name;
	private String description;
	private int level;
	private int quantity;
	private HeroClass heroClass;
	private BodyPart bodyPart;
	public enum BodyPart { HEAD, ARMOR, PANTS, GLOVES, SHOES, WEAPON }
	// increased stats
	private int atk;
	private int HP;
	private int MP;
	private double crit;
	private double dodge;
	private double block;
	
	//database mgmt
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String url = "jdbc:mysql://localhost:3306/game";
	String user = "root";
	String password = "root";
	
	public Equipment getEquipmentDrop(Monster monster) {
		Random random = new Random();
		int equipmentID;

		try {
			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();
			rs = st.executeQuery("SELECT id FROM equipment WHERE level <= "+(monster.getLevel() + 1)+"");

			// save the items here
			ArrayList<Integer> equipmentList = new ArrayList<Integer>();

			while (rs.next()) {
				equipmentList.add(rs.getInt("id"));
			}

			equipmentID = equipmentList.get(random.nextInt(equipmentList.size()));

			// select from database where id = item ID
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM equipment WHERE id = "+equipmentID+"");
			rs.next();

			id = equipmentID;
			name = rs.getString("name");
			description = rs.getString("description");
			level = rs.getInt("level");
			atk = rs.getInt("atk");
			HP = rs.getInt("health");
			MP = rs.getInt("mana");
			crit = rs.getDouble("crit");
			dodge = rs.getDouble("dodge");
			block = rs.getDouble("block");
			switch (rs.getInt("hero_type")) {
			case 1:
				heroClass = HeroClass.SWORDSMAN;
				break;
			case 2:
				heroClass = HeroClass.ARCHER;
				break;
			case 3:
				heroClass = HeroClass.MAGE;
				break;
			case 4:
				heroClass = HeroClass.THIEF;
				break;
			}
			switch (rs.getInt("hero_part")) {
			case 1:
				bodyPart = BodyPart.HEAD;
				break;
			case 2:
				bodyPart = BodyPart.ARMOR;
				break;
			case 3:
				bodyPart = BodyPart.PANTS;
				break;
			case 4:
				bodyPart = BodyPart.GLOVES;
				break;
			case 5:
				bodyPart = BodyPart.SHOES;
				break;
			case 6:
				bodyPart = BodyPart.WEAPON;
				break;
			}
			quantity = 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public HeroClass getHeroClass() {
		return heroClass;
	}

	public void setHeroClass(HeroClass heroClass) {
		this.heroClass = heroClass;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public int getMP() {
		return MP;
	}

	public void setMP(int mP) {
		MP = mP;
	}

	public double getCrit() {
		return crit;
	}

	public void setCrit(double crit) {
		this.crit = crit;
	}

	public double getDodge() {
		return dodge;
	}

	public void setDodge(double dodge) {
		this.dodge = dodge;
	}

	public double getBlock() {
		return block;
	}

	public void setBlock(double block) {
		this.block = block;
	}

	public BodyPart getBodyPart() {
		return bodyPart;
	}

	public void setBodyPart(BodyPart bodyPart) {
		this.bodyPart = bodyPart;
	}

	public static void compare(Hero hero, Equipment originalEquipment, Equipment equipment) {
		Scanner in = new Scanner(System.in);
		boolean valid = false;
		String input;
		
		System.out.println(originalEquipment.toString());
		System.out.println("--VS--");
		System.out.println(equipment.toString());
		System.out.println("Are you sure you want to replace " + originalEquipment.getName() + " with " + equipment.getName() + "? [y/n]");
		input = in.nextLine();
		
		
		while (!valid) {
			if (input.equalsIgnoreCase("y")) {
				equip(hero, originalEquipment, equipment);
				valid = true;
			} else if (input.equalsIgnoreCase("n")) {
				valid = true;
			} else {
				valid = false;
			}
		}
	}
	
	public static void equip(Hero hero, Equipment originalEquipment, Equipment equipment) {
		if (originalEquipment != null) {
			changeStats(hero, originalEquipment, false);
		}
		changeStats(hero, equipment, true);
	}

	private static void changeStats(Hero hero, Equipment equipment,
			boolean isIncrease) {
		if (isIncrease) {
			hero.setLowAtk(hero.getLowAtk() + equipment.getAtk());
			hero.setHighAtk(hero.getHighAtk() + equipment.getAtk());
			hero.setMaxHP(hero.getMaxHP() + equipment.getHP());
			hero.setHealth(hero.getHealth() + equipment.getHP());
			hero.setMaxMP(hero.getMaxMP() + equipment.getMP());
			hero.setMana(hero.getMana() + equipment.getMP());
			hero.setCriticalChance(hero.getCriticalChance() + equipment.getCrit());
			hero.setDodgeChance(hero.getDodgeChance() + equipment.getDodge());
			hero.setBlockChance(hero.getBlockChance() + equipment.getBlock());
			
			addEquipment(hero, equipment);
			System.out.println(hero.getName() + " equipped:");
			System.out.println(equipment.toString());
			System.out.println(hero.toString());
		} else {
			hero.setLowAtk(hero.getLowAtk() - equipment.getAtk());
			hero.setHighAtk(hero.getHighAtk() - equipment.getAtk());
			hero.setMaxHP(hero.getMaxHP() - equipment.getHP());
			hero.setHealth(hero.getHealth() - equipment.getHP());
			hero.setMaxMP(hero.getMaxMP() - equipment.getMP());
			hero.setMana(hero.getMana() - equipment.getMP());
			hero.setCriticalChance(hero.getCriticalChance() - equipment.getCrit());
			hero.setDodgeChance(hero.getDodgeChance() - equipment.getDodge());
			hero.setBlockChance(hero.getBlockChance() - equipment.getBlock());
			
			removeEquipment(hero, equipment);
		}
	}

	private static void addEquipment(Hero hero, Equipment equipment) {
		switch (equipment.getBodyPart()) {
		case HEAD:
			hero.setHead(equipment);
			break;
		case ARMOR:
			hero.setArmor(equipment);
			break;
		case PANTS:
			hero.setPants(equipment);
			break;
		case GLOVES:
			hero.setGloves(equipment);
			break;
		case SHOES:
			hero.setShoes(equipment);
			break;
		case WEAPON:
			hero.setWeapon(equipment);
			break;
		}
		
		hero.getInventory().decreaseQuantity(equipment.id);
	}

	private static void removeEquipment(Hero hero, Equipment equipment) {
		switch (equipment.getBodyPart()) {
		case HEAD:
			hero.setHead(null);
			break;
		case ARMOR:
			hero.setArmor(null);
			break;
		case PANTS:
			hero.setPants(null);
			break;
		case GLOVES:
			hero.setGloves(null);
			break;
		case SHOES:
			hero.setShoes(null);
			break;
		case WEAPON:
			hero.setWeapon(null);
			break;
		}
		
		hero.getInventory().addItem(equipment);
	}

	@Override
	public String toString() {
		String output = "";
		output += name + "\n";
		output += "[" + bodyPart + "]\n";
		output += "Level: " + level + "\n";
		if (description != null) {
			output += "Description: " + description + "\n";
		}
		if (atk != 0) {
			output += "ATK: " + atk + "\n";
		}
		if (HP != 0) {
			output += "HP: " + HP + "\n";
		}
		if (MP != 0) {
			output += "MP: " + MP + "\n";
		}
		if (crit != 0) {
			output += "CRIT: " + crit + "\n";
		}
		if (dodge != 0) {
			output += "DODGE: " + dodge + "\n";
		}
		if (block != 0) {
			output += "BLOCK: " + block + "\n";
		}		
		return output;
	}
}
