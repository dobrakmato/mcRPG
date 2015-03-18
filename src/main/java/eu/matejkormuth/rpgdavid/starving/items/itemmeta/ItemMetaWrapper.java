package eu.matejkormuth.rpgdavid.starving.items.itemmeta;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class ItemMetaWrapper implements LoreAccessor {
	protected ItemMeta meta;
	protected KeyValueHandler valueHandler;

	public ItemMetaWrapper(ItemStack stack) {
		this.meta = stack.getItemMeta();
		this.valueHandler = new StdLoreHandler(this);
	}

	public ItemMetaWrapper(ItemStack stack, KeyValueHandler valueHandler) {
		this.meta = stack.getItemMeta();
		this.valueHandler = valueHandler;
	}

	@Override
	public List<String> getLore() {
		if (this.meta.getLore() == null) {
			return new ArrayList<>();
		}

		return this.meta.getLore();
	}

	@Override
	public void setLore(List<String> lore) {
		this.meta.setLore(lore);
	}

	public void apply(ItemStack is) {
		is.setItemMeta(this.meta);
	}
}
