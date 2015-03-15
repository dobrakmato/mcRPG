package eu.matejkormuth.rpgdavid.starving.items.itemmeta;

import org.bukkit.inventory.ItemStack;

public class FirearmItemMetaWrapper extends ItemMetaWrapper {

	public FirearmItemMetaWrapper(ItemStack stack) {
		super(stack);
	}
	
	// Ammo

	public int getCurrentAmmo() {
		return this.valueHandler.getInteger("Ammo");
	}

	public void setCurrentAmmo(int amount) {
		this.valueHandler.set("Ammo", amount);
	}

	// Attachments

	public boolean hasSilencer() {
		return this.valueHandler.getBoolean("Silencer");
	}

	public void setSilencer(boolean silencer) {
		this.valueHandler.set("Silencer", silencer);
	}

	public boolean hasForegrip() {
		return this.valueHandler.getBoolean("Foregrip");
	}

	public void setForegrip(boolean foregrip) {
		this.valueHandler.set("Foregrip", foregrip);
	}

	public boolean hasScope() {
		return this.valueHandler.getBoolean("Scope");
	}

	public void setScope(boolean scope) {
		this.valueHandler.set("Scope", scope);
	}
}
