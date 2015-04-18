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
package eu.matejkormuth.rpgdavid.starving.listeners;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import net.minecraft.server.v1_8_R2.PacketPlayOutGameStateChange;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.Recipe;

import eu.matejkormuth.bukkit.ItemDrops;
import eu.matejkormuth.bukkit.Items;
import eu.matejkormuth.bukkit.Worlds;
import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.chemistry.Chemical;
import eu.matejkormuth.rpgdavid.starving.chemistry.ChemicalCompound;
import eu.matejkormuth.rpgdavid.starving.chemistry.Chemicals;
import eu.matejkormuth.rpgdavid.starving.items.ItemManager;
import eu.matejkormuth.rpgdavid.starving.items.base.ChemicalItem;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;
import eu.matejkormuth.rpgdavid.starving.items.comparators.ItemNameComparator;
import eu.matejkormuth.rpgdavid.starving.npc.NPC;
import eu.matejkormuth.rpgdavid.starving.tasks.TimeUpdater;
import eu.matejkormuth.rpgdavid.starving.zombie.ZombieWithDog;

public class HiddenCommandsListener implements Listener {
    @EventHandler
    private void onCommand(final PlayerCommandPreprocessEvent event) {
        // Command for listing all items.
        if (event.getMessage().equalsIgnoreCase("/items")) {
            List<Item> items = Starving.getInstance().getItemManager()
                    .getItems();
            for (int j = 0; j < items.size(); j++) {
                Item i = items.get(j);
                event.getPlayer().sendMessage(j + " - " + i.getName());
            }
        }
        // Command for giving custom items.
        else if (event.getMessage().contains("/itemsgive")) {
            List<Item> items = Starving.getInstance().getItemManager()
                    .getItems();
            for (int j = 0; j < items.size(); j++) {
                Item i = items.get(j);
                if (j == Integer.valueOf(event.getMessage().split(
                        Pattern.quote(" "))[1])) {
                    event.getPlayer().getInventory().addItem(i.toItemStack());
                }

            }
        }
        // Command for giving custom chemicals.
        else if (event.getMessage().contains("/chemical")) {
            if (!event.getMessage().contains(" ")) {
                event.getPlayer().sendMessage("args missing!");
            }

            String args = event.getMessage().split(Pattern.quote(" "))[1];
            String[] chemicals = args.split(Pattern.quote(","));

            ChemicalItem ci = new ChemicalItem("spawnedChemicalItem",
                    new ChemicalCompound()) {
                @Override
                public Recipe getRecipe() {
                    return null;
                }

                @Override
                protected void onConsume0(Player player) {
                }
            };

            for (String chemical : chemicals) {
                String[] parts = chemical.split(Pattern.quote(":"));
                String name = parts[0];
                String amount = parts[1];
                for (Chemical ch : Chemicals.all()) {
                    if (ch.getName().equalsIgnoreCase(name)) {
                        ci.getContents().add(ch, Float.valueOf(amount));
                    }
                }
            }

            event.getPlayer().getInventory().addItem(ci.toItemStack());
        }
        // Command for setting entities target.
        else if (event.getMessage().contains("/settarget")) {
            String[] parts = event.getMessage().split(" ");
            int entity = Integer.valueOf(parts[1]);
            int target = Integer.valueOf(parts[2]);
            for (Entity e : Worlds.first().getEntities()) {
                if (e.getEntityId() == target) {
                    Starving.getInstance().getZombieManager().get(entity)
                            .setFollowTarget(e);
                }
            }
        }
        // Command for spawning zombie walking the dog.
        else if (event.getMessage().contains("/zombieeaster")) {
            ZombieWithDog.spawn(event.getPlayer().getLocation());
        }
        // Command for settings time.
        else if (event.getMessage().contains("/time set")) {
            String[] parts = event.getMessage().split(" ");
            int time = Integer.valueOf(parts[2]);
            event.getPlayer().sendMessage(
                    ChatColor.YELLOW + "[Starving] Shifting time...");
            Starving.getInstance().getRegistered(TimeUpdater.class)
                    .vanllaSetMoveTime(time);
            event.getPlayer().sendMessage(
                    ChatColor.GREEN + "[Starving] Time set!");
        }
        // Command for spawning zombie.
        else if (event.getMessage().contains("/zombie")) {
            String[] parts = event.getMessage().split(" ");
            if (parts.length == 2) {
                int count = Integer.valueOf(parts[1]);

                for (int i = 0; i < count; i++) {
                    Starving.getInstance()
                            .getZombieManager()
                            .spawnAt(
                                    event.getPlayer()
                                            .getLocation()
                                            .add((Math.random() - 0.5) * count
                                                    / 4,
                                                    0,
                                                    (Math.random() - 0.5)
                                                            * count / 4));
                }
            } else {
                Starving.getInstance().getZombieManager()
                        .spawnAt(event.getPlayer().getLocation());
            }
        }
        // Command for testing some random things.
        else if (event.getMessage().contains("/darkness")) {
            ((CraftPlayer) event.getPlayer()).getHandle().playerConnection
                    .sendPacket(new PacketPlayOutGameStateChange(7, 0.001f));
            ((CraftPlayer) event.getPlayer()).getHandle().playerConnection
                    .sendPacket(new PacketPlayOutGameStateChange(8, 160));
        }
        // Command for testing some random things.
        else if (event.getMessage().contains("/npctest")) {
            String name = "debilko" + (int) Math.floor(Math.random() * 1000000);
            NPC npc = Starving.getInstance().getNPCManager().getMainRegistry()
                    .createPlayer().withProfile(
                            UUID.nameUUIDFromBytes(name.getBytes()), name)
                    .withSpawnLocation(
                            event.getPlayer().getLocation()).spawn();

            event.getPlayer().teleport(npc.getLocation());
        }
        // Command for generating access key.
        else if (event.getMessage().contains("/genkey")) {
            char[] VALID_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
            int keyLength = 32;
            char[] key = new char[keyLength];
            for (int i = 0; i < keyLength; i++) {
                key[i] = VALID_CHARS[RandomUtils.nextInt(VALID_CHARS.length - 1)];
            }
            event.getPlayer().sendMessage(
                    "Your new accesskey is: " + new String(key));
            Data.of(event.getPlayer()).setRemoteAccesKey(new String(key));
            event.getPlayer().sendMessage(
                    "http://starving.eu/key.php?key=" + new String(key));
        }
        // Command for opening custom items inventory.
        else if (event.getMessage().contains("/itemsinv")) {
            ItemManager im = Starving.getInstance().getItemManager();
            int size = (im.getItems().size() / 9);
            Inventory inv = Bukkit.createInventory(null, 9 * (size + 1),
                    ChatColor.GOLD + "Custom items: ");
            List<Item> sorted = im.getItems();
            Collections.sort(sorted, new ItemNameComparator());
            for (Item i : sorted) {
                inv.addItem(i.toItemStack());
            }
            event.getPlayer().openInventory(inv);
        }
        // Command for testing some random things.
        else if (event.getMessage().contains("/itemdropvehicle")) {
            org.bukkit.entity.Item i = ItemDrops.drop(
                    event.getPlayer().getLocation(), Items.of(Material.APPLE));
            i.setVelocity(event.getPlayer().getEyeLocation().getDirection().multiply(
                    3));
            i.setPassenger(event.getPlayer());
            i.setPickupDelay(20 * 15);
        }
    }
}
