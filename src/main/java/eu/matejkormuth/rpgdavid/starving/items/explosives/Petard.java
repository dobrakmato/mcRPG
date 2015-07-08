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
package eu.matejkormuth.rpgdavid.starving.items.explosives;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.darkblade12.particleeffect.ParticleEffect;

import eu.matejkormuth.bukkit.Actions;
import eu.matejkormuth.bukkit.ItemDrops;
import eu.matejkormuth.bukkit.Items;
import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.Time;
import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.Mappings;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;
import eu.matejkormuth.rpgdavid.starving.tasks.RepeatingTask;

public class Petard extends Item {
    public Petard() {
        super(Mappings.PETARD, "Petard");
    }

    @Override
    public InteractResult onInteract(Player player, Action action,
            Block clickedBlock, BlockFace clickedFace) {
        if (Actions.isRightClick(action)) {
            ItemStack is = Items.of(Mappings.PETARD.getMaterial());
            org.bukkit.entity.Item i = ItemDrops.drop(player.getEyeLocation(),
                    is);
            i.setPickupDelay(Time.ofSeconds(6).toTicks());
            i.setVelocity(player.getEyeLocation().getDirection());
            // Play sound.
            for (Entity e : player.getNearbyEntities(32, 32, 32)) {
                if (e.getType() == EntityType.PLAYER) {
                    Starving.NMS.playNamedSoundEffect((Player) e,
                            "pyrotechnics.petard.burn",
                            player.getLocation(), 1.5f, 1f);
                }
            }
            Starving.NMS.playNamedSoundEffect(player,
                    "pyrotechnics.petard.burn",
                    player.getLocation(), 1.5f, 1f);

            // edit

            new RepeatingTask() {
                private int ticks = 0;

                @Override
                public void run() {
                    if (ticks >= 20 * 5) {
                        this.cancel();
                        this.explode();
                    }
                    ParticleEffect.FLAME.display(0.1f, 0.1f, 0.1f, 0, 1,
                            i.getLocation().add(0, 0.3, 0), Double.MAX_VALUE);
                    ticks++;
                }

                private void explode() {
                    ParticleEffect.FLAME.display(0.1f, 0.1f, 0.1f, 0.5f, 100,
                            i.getLocation(), Double.MAX_VALUE);
                    // Play sound.
                    Starving.NMS.playNamedSoundEffect(player,
                            "pyrotechnics.petard.explosion",
                            player.getLocation(), 1.5f, 1f);
                    for (Entity e : player.getNearbyEntities(32, 32, 32)) {
                        if (e.getType() == EntityType.PLAYER) {
                            Starving.NMS.playNamedSoundEffect((Player) e,
                                    "pyrotechnics.petard.explosion",
                                    player.getLocation(), 1f, 1f);
                        }
                    }
                    i.remove();
                }
            }.schedule(1L);
        }

        return InteractResult.useOne();
    }
}
