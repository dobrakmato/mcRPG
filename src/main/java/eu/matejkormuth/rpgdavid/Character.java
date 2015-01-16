/*
 *  mcRPG is a open source rpg bukkit/spigot plugin.
 *  Copyright (C) 2015 Matej Kormuth 
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
package eu.matejkormuth.rpgdavid;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.base.Strings;

import eu.matejkormuth.rpgdavid.inventoryutils.Armor;
import eu.matejkormuth.rpgdavid.inventoryutils.ItemStackBuilder;

public class Character {
    private final String name;
    private final String id;
    private final String special;

    private final Modifiers modifiers;
    private final Armor armor;
    private final ItemStack[] items;

    public Character(final String name, final String special,
            final Modifiers modifiers, final Armor armor,
            final ItemStack... items) {
        this.name = name;
        this.modifiers = modifiers;
        this.items = items;
        this.armor = armor;

        this.special = special;

        this.id = this.getClass().getSimpleName();
    }

    public Modifiers getModifiers() {
        return modifiers;
    }

    public String getName() {
        return name;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public Armor getArmor() {
        return armor;
    }

    public String getId() {
        return id;
    }

    public void applyTo(Player p) {
        // Clear player.
        p.getInventory().clear();

        // Apply character name before player name.
        p.setDisplayName("[" + this.name + "]" + p.getName());

        // Apply armor.
        p.getInventory().setHelmet(this.armor.getHelmet());
        p.getInventory().setChestplate(this.armor.getChestplate());
        p.getInventory().setLeggings(this.armor.getLeggings());
        p.getInventory().setBoots(this.armor.getBoots());

        // Apply modifiers.
        p.setWalkSpeed(0.2F * this.modifiers.getWalkSpeedModifier());
        p.setMaxHealth(20D * this.modifiers.getHealthModifier());
        p.setHealth(p.getMaxHealth());

        // Give items.
        for (ItemStack item : this.items) {
            p.getInventory().addItem(item);
        }
    }

    public ItemStack getIcon(final Material material) {
        return this.getIcon(material, (byte) 0);
    }

    public ItemStack getIcon(final ItemStack itemStack) {
        List<String> lore;
        if (this.special != null && this.special.contains("\n")) {
            lore = new ArrayList<String>(20);
        } else {
            lore = new ArrayList<String>(8);
        }

        lore.add(ChatColor.GREEN + "Health: " + ChatColor.GOLD
                + +(int) (20D * this.modifiers.getHealthModifier()) + " HP");
        lore.add(ChatColor.YELLOW + "Health regain: " + ChatColor.GOLD
                + +(int) (100D * this.modifiers.getHealthRegainModifier())
                + "%");
        lore.add(ChatColor.RED + "Criticals chance: " + ChatColor.GOLD
                + +(int) (100D * this.modifiers.getCriticalModifier()) + "%");
        lore.add(ChatColor.RED + "Damage: " + ChatColor.GOLD
                + +(int) (100D * this.modifiers.getDamageModifier()) + "%");
        lore.add(ChatColor.BLUE + "Walk speed: " + ChatColor.GOLD
                + +(int) (100D * this.modifiers.getWalkSpeedModifier()) + "%");

        lore.add("");
        if (Strings.isNullOrEmpty(this.special)) {
            lore.add(ChatColor.LIGHT_PURPLE + "Special: " + ChatColor.WHITE
                    + "nothing");
        } else {
            if (this.special.contains("\n")) {
                String[] specials = this.special.split("\n");
                lore.add(ChatColor.LIGHT_PURPLE + "Special: " + ChatColor.WHITE
                        + specials[0]);
                for (int i = 1; i < specials.length; i++) {
                    lore.add("    " + ChatColor.WHITE + specials[i]);
                }
            } else {
                lore.add(ChatColor.LIGHT_PURPLE + "Special: " + ChatColor.WHITE
                        + this.special);
            }
        }
        lore.add("");

        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(ChatColor.RESET + this.getName());
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return itemStack;
    }

    @SuppressWarnings("deprecation")
    public ItemStack getIcon(final Material material, final byte dataId) {
        List<String> lore;
        if (this.special != null && this.special.contains("\n")) {
            lore = new ArrayList<String>(20);
        } else {
            lore = new ArrayList<String>(8);
        }

        lore.add(ChatColor.GREEN + "Health: " + ChatColor.GOLD
                + +(int) (20D * this.modifiers.getHealthModifier()) + " HP");
        lore.add(ChatColor.YELLOW + "Health regain: " + ChatColor.GOLD
                + +(int) (100D * this.modifiers.getHealthRegainModifier())
                + "%");
        lore.add(ChatColor.RED + "Criticals chance: " + ChatColor.GOLD
                + +(int) (100D * this.modifiers.getCriticalModifier()) + "%");
        lore.add(ChatColor.RED + "Damage: " + ChatColor.GOLD
                + +(int) (100D * this.modifiers.getDamageModifier()) + "%");
        lore.add(ChatColor.BLUE + "Walk speed: " + ChatColor.GOLD
                + +(int) (100D * this.modifiers.getWalkSpeedModifier()) + "%");

        lore.add("");
        if (Strings.isNullOrEmpty(this.special)) {
            lore.add(ChatColor.LIGHT_PURPLE + "Special: " + ChatColor.WHITE
                    + "nothing");
        } else {
            if (this.special.contains("\n")) {
                String[] specials = this.special.split("\n");
                lore.add(ChatColor.LIGHT_PURPLE + "Special: " + ChatColor.WHITE
                        + specials[0]);
                for (int i = 1; i < specials.length; i++) {
                    lore.add("    " + ChatColor.WHITE + specials[i]);
                }
            } else {
                lore.add(ChatColor.LIGHT_PURPLE + "Special: " + ChatColor.WHITE
                        + this.special);
            }
        }
        lore.add("");

        return new ItemStackBuilder(material)
                .name(ChatColor.RESET + this.getName()).lore(lore).data(dataId)
                .build();
    }
}
