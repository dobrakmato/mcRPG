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
package eu.matejkormuth.rpgdavid.starving.items.itemmeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.inventory.meta.ItemMeta;

import eu.matejkormuth.rpgdavid.starving.chemistry.Chemical;
import eu.matejkormuth.rpgdavid.starving.chemistry.ChemicalCompound;
import eu.matejkormuth.rpgdavid.starving.chemistry.Chemicals;
import eu.matejkormuth.rpgdavid.starving.chemistry.ChemicalCompound.MutableFloat;
import eu.matejkormuth.rpgdavid.starving.chemistry.Chemicals.CompoundRecipe;

public class ChemicalItemMetaWrapper implements ItemMetaWrapper<ChemicalCompound> {
    private static final String REGEX = Pattern.quote("-");

    private ItemMeta meta;

    public ChemicalItemMetaWrapper(final ItemMeta meta) {
        this.meta = meta;
    }

    public ItemMeta set(ChemicalCompound compound) {
        List<String> lore = new ArrayList<>(1 + compound.getChemicalsCount());
        if (compound.isPure()) {
            // Set name from known source chemicals.
            this.meta.setDisplayName(ChatColor.LIGHT_PURPLE
                    + compound.getChemicals().iterator().next().getName());
            lore.add(ChatColor.GREEN.toString() + ChatColor.ITALIC
                    + "Pure chemical");
        } else {
            // Check for name from known compounds.
            for (CompoundRecipe recipe : Chemicals.Compounds.getAll()) {
                if (recipe.isRecipeOf(compound)) {
                    this.meta.setDisplayName(ChatColor.BLUE + recipe.getName());
                    break;
                }
            }
            lore.add(ChatColor.GREEN.toString() + ChatColor.ITALIC
                    + "Compound chemical");
        }
        // Add contents to lore.
        for (Entry<Chemical, MutableFloat> entry : compound.getContents()
                .entrySet()) {
            lore.add(ChatColor.WHITE + entry.getKey().getName() + " - "
                    + entry.getValue().getValue() + " ml");
        }
        this.meta.setLore(lore);
        return this.meta;
    }

    public ChemicalCompound get() {
        ChemicalCompound c = new ChemicalCompound();
        // Parse itemMeta.
        List<String> lore = this.meta.getLore();
        boolean skipped = false;
        for (String s : lore) {
            // Skip first line.
            if (!skipped) {
                skipped = true;
                continue;
            }

            // Parse amount and chemical.
            String parts[] = s.split(REGEX);
            String name = parts[0].trim().replace(ChatColor.WHITE.toString(),
                    "");
            float amount = Float.valueOf(
                    parts[1].substring(0, parts[1].lastIndexOf(" ")))
                    .floatValue();

            for (Chemical chemical : Chemicals.all()) {
                if (chemical.getName().equals(name)) {
                    c.add(chemical, amount);
                }
            }
        }
        return c;
    }

    public ItemMeta getItemMeta() {
        return this.meta;
    }
}
