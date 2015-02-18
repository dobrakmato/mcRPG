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

import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Recipe;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.chemistry.Chemical;
import eu.matejkormuth.rpgdavid.starving.chemistry.ChemicalCompound;
import eu.matejkormuth.rpgdavid.starving.chemistry.Chemicals;
import eu.matejkormuth.rpgdavid.starving.items.base.ChemicalItem;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;
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
        // Command for spawning zombie walking the dog.
        else if (event.getMessage().contains("/zombieeaster")) {
            ZombieWithDog.spawn(event.getPlayer().getLocation());
        }
        // Command for spawning zombie.
        else if (event.getMessage().contains("/zombie")) {
            Starving.getInstance().getZombieManager()
                    .spawnAt(event.getPlayer().getLocation());
        }
    }
}
