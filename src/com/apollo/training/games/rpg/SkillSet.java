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
			selectedSkill.setId(rs.getInt("id"));
			selectedSkill.setName(rs.getString("name"));
			switch (rs.getString("type")) {
			case "swordsman":
				selectedSkill.setType(HeroClass.SWORDSMAN);
				break;
			case "archer":
				selectedSkill.setType(HeroClass.ARCHER);
				break;
			case "mage":
				selectedSkill.setType(HeroClass.MAGE);
				break;
			case "thief":
				selectedSkill.setType(HeroClass.THIEF);
				break;
			}
			switch (rs.getString("skill_type")) {
			case "active":
				selectedSkill.setSkillType(Type.ACTIVE);
				break;
			case "passive":
				selectedSkill.setSkillType(Type.PASSIVE);
				break;
			}
			selectedSkill.setDamage(rs.getInt("damage"));
			selectedSkill.setAccuracy(rs.getInt("accuracy"));
			selectedSkill.setLevelRequired(rs.getInt("level_required"));
			selectedSkill.setMpCost(rs.getInt("mp_cost"));
			selectedSkill.setDescription(rs.getString("description"));
			// single, all
			try {
				switch (rs.getString("attack_type")) {
				case "single":
					selectedSkill.setAtkType(AttackType.SINGLE);
					break;
				case "all":
					selectedSkill.setAtkType(AttackType.ALL);
					break;
				}
			} catch (NullPointerException e) {

			}
			
			// burn, stun, poison
			try {
				switch (rs.getString("effect")) {
				case "burn":
					selectedSkill.setEffectToTarget(Status.BURNED);
					break;
				case "stun":
					selectedSkill.setEffectToTarget(Status.STUNNED);
					break;
				case "poison":
					selectedSkill.setEffectToTarget(Status.POISONED);			
					break;
				}
			} catch (NullPointerException e) {
				
			}
			
			selectedSkill.setDuration(rs.getInt("duration"));
			selectedSkill.setEffectChance(rs.getInt("effect_chance"));
			// add to arrayList
			skills.add(selectedSkill);
		}
	}

	public ArrayList<Skill> getSkills() {
		return skills;
	}

}
