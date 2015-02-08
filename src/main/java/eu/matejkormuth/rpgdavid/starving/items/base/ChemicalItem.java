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

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.matejkormuth.rpgdavid.starving.chemistry.Chemical;
import eu.matejkormuth.rpgdavid.starving.chemistry.ChemicalCompound;
import eu.matejkormuth.rpgdavid.starving.chemistry.ChemicalCompound.MutableFloat;
import eu.matejkormuth.rpgdavid.starving.chemistry.Chemicals;
import eu.matejkormuth.rpgdavid.starving.chemistry.Chemicals.CompoundRecipe;

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

        // Consumed clean chemical.
        if (this.contents.getChemicalsCount() == 1) {
            Chemical chemical = this.contents.getChemicals().iterator().next();
            chemical.onPureConsumedBy(player, this.contents.getAmount(chemical));
        }
    }

    protected abstract void onConsume0(Player player);

    @Override
    public ItemStack toItemStack() {
        ItemStack is = super.toItemStack();
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(
                1 + this.contents.getChemicalsCount());
        if (this.contents.isPure()) {
            // Set name from known source chemicals.
            im.setDisplayName(ChatColor.LIGHT_PURPLE
                    + this.contents.getChemicals().iterator().next().getName());
            lore.add(ChatColor.ITALIC + "Pure");
        } else {
            // Check for name from known compounds.
            for (CompoundRecipe recipe : Chemicals.Compounds.getAll()) {
                if (recipe.isRecipeOf(this.contents)) {
                    im.setDisplayName(ChatColor.BLUE + recipe.getName());
                    break;
                }
            }
            lore.add(ChatColor.ITALIC + "Compound");
        }
        // Add contents to lore.
        for (Entry<Chemical, MutableFloat> entry : this.contents.getContents()
                .entrySet()) {
            lore.add(ChatColor.WHITE + entry.getKey().getName() + " - "
                    + entry.getValue().getValue() + " ml");
        }
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    @Override
    public ItemStack toItemStack(int amount) {
        ItemStack is = this.toItemStack();
        is.setAmount(amount);
        return is;
    }
}