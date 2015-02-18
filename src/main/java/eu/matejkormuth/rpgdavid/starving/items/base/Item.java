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
package eu.matejkormuth.rpgdavid.starving.items.base;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import eu.matejkormuth.rpgdavid.starving.items.Category;
import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.Rarity;

public abstract class Item extends ItemBase {
    private Rarity rarity = Rarity.COMMON;
    private Category category;
    private int maxStackAmount;

    public Item(Material material, String name) {
        super(material, 1, name);
    }

    public InteractResult onInteract(Player player, Action action,
            Block clickedBlock, BlockFace clickedFace) {
        return InteractResult.useNone();
    }

    public void onInteractWith(Player player, Entity entity) {
    }

    protected void setCategory(Category category) {
        this.category = category;
    }

    protected void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public Category getCategory() {
        return this.category;
    }

    public Rarity getRarity() {
        return this.rarity;
    }

    protected void setMaxStackAmount(int amount) {
        this.maxStackAmount = amount;
    }

    public int getMaxStackAmount() {
        return this.maxStackAmount;
    }
}
