package com.apollo.training.games.rpg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class Items {
	private int id;
	private String name;
	private String description;
	private int healthBoost;
	private int manaBoost;
	private int quantity;
	
	//database mgmt
	Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    String url = "jdbc:mysql://localhost:3306/game";
    String user = "root";
    String password = "root";

	public Items getItemDrop(Monster monster) {
		Random random = new Random();
		int itemID;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();
			rs = st.executeQuery("SELECT id FROM items WHERE level <= "+(monster.getLevel() + 1)+"");
			
			// save the items here
			ArrayList<Integer> itemsList = new ArrayList<Integer>();
			
			while (rs.next()) {
				itemsList.add(rs.getInt("id"));
			}
			
			itemID = itemsList.get(random.nextInt(itemsList.size()));
			
			// select from database where id = item ID
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM items WHERE id = "+itemID+"");
			rs.next();
			
			id = itemID;
			name = rs.getString("name");
			description = rs.getString("description");
			healthBoost = rs.getInt("health");
			manaBoost = rs.getInt("mana");
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

	public int getHealthBoost() {
		return healthBoost;
	}

	public void setHealthBoost(int healthBoost) {
		this.healthBoost = healthBoost;
	}

	public int getManaBoost() {
		return manaBoost;
	}

	public void setManaBoost(int manaBoost) {
		this.manaBoost = manaBoost;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
