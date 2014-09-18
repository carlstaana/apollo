package com.apollo.training.games.rpg;

import java.util.ArrayList;

import com.apollo.training.games.rpg.Hero.HeroClass;

public class Inventory {
	private ArrayList<Object> inventory = new ArrayList<Object>();

	public Inventory() {
		inventory.clear();
	}

	public void refresh() {
		ArrayList<Object> tempList = new ArrayList<Object>();
		for (Object item : inventory) {
			if (item instanceof Items) {
				Items tempItem = (Items) item;
				if (tempItem.getQuantity() > 0) {
					tempList.add(item);
				}
			} else if (item instanceof Equipment) {
				Equipment tempEquipment = (Equipment) item;
				if (tempEquipment.getQuantity() > 0) {
					tempList.add(item);
				}
			}
		}
		inventory.clear();
		inventory.addAll(tempList);
	}

	public void addItem(Object itemDrop) {
		Items tempItem = null;
		Equipment tempEquipment = null;
		
		if (itemDrop instanceof Items) {
			tempItem = (Items) itemDrop;
		} else if (itemDrop instanceof Equipment) {
			tempEquipment = (Equipment) itemDrop;
		}
		
		if (tempItem != null) {
			for (int i = 0; i < inventory.size(); i++) {
				if (inventory.get(i) instanceof Items) {
					Items oldItem = (Items) inventory.get(i);
					if (oldItem.getId() == tempItem.getId()) {
						oldItem.setQuantity(oldItem.getQuantity() + 1);
						tempItem.setQuantity(tempItem.getQuantity() - 1);
						inventory.set(i, oldItem);
					}
				}
			}
			
			if (tempItem.getQuantity() > 0) {
				inventory.add(tempItem);
			}
		} else if (tempEquipment != null) {
			for (int i = 0; i < inventory.size(); i++) {
				if (inventory.get(i) instanceof Equipment) {
					Equipment oldEquipment = (Equipment) inventory.get(i);
					if (oldEquipment.getId() == tempEquipment.getId()) {
						oldEquipment.setQuantity(oldEquipment.getQuantity() + 1);
						tempEquipment.setQuantity(tempEquipment.getQuantity() - 1);
						inventory.set(i, oldEquipment);
					}
				}
			}
			
			if (tempEquipment.getQuantity() > 0) {
				inventory.add(tempEquipment);
			}
		}
		
		refresh();
	}

	public ArrayList<Object> getInventory() {
		return inventory;
	}

	public void setInventory(ArrayList<Object> inventory) {
		this.inventory = inventory;
	}

	public ArrayList<Items> getItemsOnly() {
		ArrayList<Items> outputItems = new ArrayList<Items>();
		for (Object obj : inventory) {
			if (obj instanceof Items) {
				Items item = (Items) obj;
				outputItems.add(item);
			}
		}
		return outputItems;
	}

	public void decreaseQuantity(int id) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) instanceof Items) {
				Items item = (Items) inventory.get(i);
				if (item.getId() == id) {
					item.setQuantity(item.getQuantity() - 1);
					inventory.set(i, item);
				}		
			} else {
				Equipment equip = (Equipment) inventory.get(i);
				if (equip.getId() == id) {
					equip.setQuantity(equip.getQuantity() - 1);
					inventory.set(i, equip);
				}
			}
		}
		
		refresh();
	}

	public ArrayList<Equipment> getEquipmentListOnly(HeroClass heroClass) {
		ArrayList<Equipment> outputItems = new ArrayList<Equipment>();
		for (Object obj : inventory) {
			if (obj instanceof Equipment) {
				Equipment equip = (Equipment) obj;
				if (equip.getHeroClass().equals(heroClass)) {
					outputItems.add(equip);
				}
			}
		}
		return outputItems;
	}
}
