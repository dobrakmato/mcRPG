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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.chemistry.Chemical;
import eu.matejkormuth.rpgdavid.starving.chemistry.ChemicalCompound;
import eu.matejkormuth.rpgdavid.starving.chemistry.ChemicalCompound.MutableFloat;
import eu.matejkormuth.rpgdavid.starving.chemistry.Chemicals;
import eu.matejkormuth.rpgdavid.starving.chemistry.Chemicals.CompoundRecipe;
import eu.matejkormuth.rpgdavid.starving.items.itemmeta.deprecated.ChemicalItemMetaWrapper;

public abstract class ChemicalItem extends ConsumableItem implements Craftable {
    /**
     * Contents of water bottle.
     */
    private ChemicalCompound contents;

    public ChemicalItem(String name) {
        this(name, new ChemicalCompound());
    }

    public ChemicalItem(String name, ChemicalCompound compound) {
        // Chemicals do not modify any food values.
        super(0, 0, Material.POTION, name);
        this.contents = compound;
    }

    public ChemicalCompound getContents() {
        return this.contents;
    }

    @Override
    public void onConsume(Player player) {
        this.evaluateEffects(player);
        // Add total amount of liquid to player's hydaration.
        Data.of(player).incrementHydrationLevel(this.contents.getTotalAmount());
        // Post event to sub class.
        this.onConsume0(player);
    }

    private void evaluateEffects(Player player) {
        // TODO: Need discussion about effects.

        // Consumed pure chemical.
        if (this.contents.getChemicalsCount() == 1) {
            Chemical chemical = this.contents.getChemicals().iterator().next();
            chemical.onPureConsumedBy(player, this.contents.getAmount(chemical));
        }

        // Consumed pure compound.
        for (CompoundRecipe recipe : Chemicals.Compounds.getAll()) {
            if (recipe.isRecipeOf(this.contents)) {
                float amount = 0;
                for (MutableFloat f : this.contents.getContents().values()) {
                    amount += f.getValue();
                }
                recipe.onPureConsumedBy(player, amount);
            }
        }
    }

    protected abstract void onConsume0(Player player);

    @Override
    public ItemStack toItemStack() {
        ItemStack is = super.toItemStack();
        is.setItemMeta(new ChemicalItemMetaWrapper(is.getItemMeta())
                .set(this.contents));
        return is;
    }

    @Override
    public ItemStack toItemStack(int amount) {
        ItemStack is = this.toItemStack();
        is.setAmount(amount);
        return is;
    }
}