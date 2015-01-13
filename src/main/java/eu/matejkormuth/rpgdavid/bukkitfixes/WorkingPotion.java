package eu.matejkormuth.rpgdavid.bukkitfixes;

import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class WorkingPotion extends Potion {
	public WorkingPotion(PotionType type) {
		super(type);
	}
	
	public WorkingPotion(PotionType type, int level) {
		super(type, level);
	}

	public Potion splash() {
		this.setSplash(true);
		return this;
	}
}
