package com.apollo.training.games.rpg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.apollo.training.games.rpg.Buff.Status;
import com.apollo.training.games.rpg.Hero.HeroClass;
import com.apollo.training.games.rpg.Skill.AttackType;
import com.apollo.training.games.rpg.Skill.Type;

public class SkillSet {
	public ArrayList<Skill> skills = new ArrayList<Skill>();

	//database mgmt
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	String url = "jdbc:mysql://localhost:3306/game";
	String user = "root";
	String password = "root";

	public SkillSet(HeroClass myClass) throws SQLException {
		con = DriverManager.getConnection(url, user, password);
		st = con.createStatement();
	
		switch (myClass) {
		case SWORDSMAN:
			rs = st.executeQuery("SELECT * FROM skills WHERE type = 'swordsman' ORDER BY level_required");
			break;
		case ARCHER:
			rs = st.executeQuery("SELECT * FROM skills WHERE type = 'archer' ORDER BY level_required");
			break;
		case MAGE:
			rs = st.executeQuery("SELECT * FROM skills WHERE type = 'mage' ORDER BY level_required");
			break;
		case THIEF:
			rs = st.executeQuery("SELECT * FROM skills WHERE type = 'thief' ORDER BY level_required");
			break;
		}
		
		skills.clear();
		while (rs.next()) {
			// get the skills
			Skill selectedSkill = new Skill();
			selectedSkill.name = rs.getString("name");
			switch (rs.getString("type")) {
			case "swordsman":
				selectedSkill.type = HeroClass.SWORDSMAN;
				break;
			case "archer":
				selectedSkill.type = HeroClass.ARCHER;
				break;
			case "mage":
				selectedSkill.type = HeroClass.MAGE;
				break;
			case "thief":
				selectedSkill.type = HeroClass.THIEF;
				break;
			}
			switch (rs.getString("skill_type")) {
			case "active":
				selectedSkill.skillType = Type.ACTIVE;
				break;
			case "passive":
				selectedSkill.skillType = Type.PASSIVE;
				break;
			}
			selectedSkill.damage = rs.getInt("damage");
			selectedSkill.accuracy = rs.getInt("accuracy");
			selectedSkill.levelRequired = rs.getInt("level_required");
			selectedSkill.mpCost = rs.getInt("mp_cost");
			selectedSkill.description = rs.getString("description");
			// single, all
			try {
				switch (rs.getString("attack_type")) {
				case "single":
					selectedSkill.atkType = AttackType.SINGLE;
					break;
				case "all":
					selectedSkill.atkType = AttackType.ALL;
					break;
				}
			} catch (NullPointerException e) {

			}
			
			// burn, stun, poison
			try {
				switch (rs.getString("effect")) {
				case "burn":
					selectedSkill.effectToTarget = Status.BURNED;
					break;
				case "stun":
					selectedSkill.effectToTarget = Status.STUNNED;
					break;
				case "poison":
					selectedSkill.effectToTarget = Status.POISONED;			
					break;
				}
			} catch (NullPointerException e) {
				
			}
			
			selectedSkill.duration = rs.getInt("duration");
			selectedSkill.effectChance = rs.getInt("effect_chance");
			// add to arrayList
			skills.add(selectedSkill);
		}
	}

	public ArrayList<Skill> getSkills() {
		return skills;
	}

}
