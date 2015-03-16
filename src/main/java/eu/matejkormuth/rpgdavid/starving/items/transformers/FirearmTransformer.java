/*
 *  Starving is a open source bukkit/spigot mmo game.
 *  Copyright (C) 2014-2015 Matej Kormuth
 *  This file is a part of Starving. <http://www.starving.eu>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package eu.matejkormuth.rpgdavid.starving.items.transformers;

import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.items.base.Firearm;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;
import eu.matejkormuth.rpgdavid.starving.items.firearms.AK47;
import eu.matejkormuth.rpgdavid.starving.items.firearms.ColtAnaconda;
import eu.matejkormuth.rpgdavid.starving.items.firearms.Dragunov;
import eu.matejkormuth.rpgdavid.starving.items.firearms.Glock;
import eu.matejkormuth.rpgdavid.starving.items.firearms.M16;
import eu.matejkormuth.rpgdavid.starving.items.firearms.MP5;
import eu.matejkormuth.rpgdavid.starving.items.firearms.Mossberg500;
import eu.matejkormuth.rpgdavid.starving.items.firearms.NickyAnaconda;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedAK47;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedColtAnaconda;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedDragunov;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedGlock;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedM16;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedMP5;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedMossberg500;
import eu.matejkormuth.rpgdavid.starving.items.firearms.scoped.ScopedNickyAnaconda;

public class FirearmTransformer {

	public static ItemStack toScoped(ItemStack unscoped) {
		// Create new scoped and copy properties.
		Item item = Starving.getInstance().getItemManager().findItem(unscoped);
		if (item instanceof AK47) {
			return createWithMeta(ScopedAK47.class, unscoped);
		} else if (item instanceof ColtAnaconda) {
			return createWithMeta(ScopedColtAnaconda.class, unscoped);
		} else if (item instanceof Dragunov) {
			return createWithMeta(ScopedDragunov.class, unscoped);
		} else if (item instanceof Glock) {
			return createWithMeta(ScopedGlock.class, unscoped);
		} else if (item instanceof M16) {
			return createWithMeta(ScopedM16.class, unscoped);
		} else if (item instanceof Mossberg500) {
			return createWithMeta(ScopedMossberg500.class, unscoped);
		} else if (item instanceof MP5) {
			return createWithMeta(ScopedMP5.class, unscoped);
		} else if (item instanceof NickyAnaconda) {
			return createWithMeta(ScopedNickyAnaconda.class, unscoped);
		} else {
			throw new IllegalArgumentException(
					"Itemstack must be supported firearm. Found: "
							+ item.getClass().getSimpleName());
		}
	}

	private static ItemStack createWithMeta(Class<? extends Firearm> clazz,
			ItemStack metadataSource) {
		ItemStack scoped = createItemStack(clazz);
		scoped.setItemMeta(metadataSource.getItemMeta());
		return scoped;
	}

	private static ItemStack createItemStack(Class<? extends Firearm> clazz) {
		return Starving.getInstance().getItemManager().newItemStack(clazz);
	}

	public static ItemStack fromScoped(ItemStack scoped) {
		// Create new unscoped and copy properties.
		Item item = Starving.getInstance().getItemManager().findItem(scoped);
		if (item instanceof ScopedAK47) {
			return createWithMeta(AK47.class, scoped);
		} else if (item instanceof ScopedColtAnaconda) {
			return createWithMeta(ColtAnaconda.class, scoped);
		} else if (item instanceof ScopedDragunov) {
			return createWithMeta(Dragunov.class, scoped);
		} else if (item instanceof ScopedGlock) {
			return createWithMeta(Glock.class, scoped);
		} else if (item instanceof ScopedM16) {
			return createWithMeta(M16.class, scoped);
		} else if (item instanceof ScopedMossberg500) {
			return createWithMeta(Mossberg500.class, scoped);
		} else if (item instanceof ScopedMP5) {
			return createWithMeta(MP5.class, scoped);
		} else if (item instanceof ScopedNickyAnaconda) {
			return createWithMeta(NickyAnaconda.class, scoped);
		} else {
			throw new IllegalArgumentException(
					"Itemstack must be supported firearm. Found: "
							+ item.getClass().getSimpleName());
		}
	}
}
