package com.apollo.training.games.rpg;

import static org.junit.Assert.*;

import org.junit.Test;

import com.apollo.training.games.rpg.Hero;
import com.apollo.training.games.rpg.Items;
import com.apollo.training.games.rpg.Monster;

public class ItemsTests {

	@Test
	public void test() {
		Items item = new Items();
		Hero hero = new Hero("Carl", 1);
		Monster monster = new Monster(hero);
		item = (Items) monster.getItemDrop();
		assertNotNull(item);
	}

}
