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
package eu.matejkormuth.rpgdavid.starving.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.starving.chemistry.ChemicalCompound;
import eu.matejkormuth.rpgdavid.starving.chemistry.Chemicals;

public abstract class ChemicalItem extends ConsumableItem implements Craftable {
    /**
     * Contents of water bottle.
     */
    private ChemicalCompound contents = new ChemicalCompound();

    public ChemicalItem(String name) {
        // Chemicals do not modify any food values.
        super(0, 0, Material.POTION, name);
    }

    public ChemicalCompound getContents() {
        return this.contents;
    }

    @Override
    public void onConsume(Player player) {
        this.evaluateEffects(player);
        // Post event to sub class.
        this.onConsume0(player);
    }

    private void evaluateEffects(Player player) {
        // TODO: Need discussion about effects.
        if (contents.containsOnly(Chemicals.ACID)) {

        }
    }

    protected abstract void onConsume0(Player player);

    @Override
    public ItemStack toItemStack() {
        // TODO: Implement item meta change.
        return super.toItemStack();
    }

    @Override
    public ItemStack toItemStack(int amount) {
        return super.toItemStack(amount);
    }
}