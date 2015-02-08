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
package eu.matejkormuth.rpgdavid.starving.items.consumables;

import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.base.ConsumableItem;

public class MagicMushroom extends ConsumableItem {
    public MagicMushroom() {
        super(0, 0, Material.RED_MUSHROOM, "Magic mushroom");
    }

    @Override
    protected void onConsume0(Player player) {
    }

    @Override
    public InteractResult onInteract(final Player player, Action action,
            Block clickedBlock, BlockFace clickedFace) {

        // Simulate trip.
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,
                20 * 30, 2));

        // Find teleport location.
        int x = player.getLocation().getBlockX() + RandomUtils.nextInt(200)
                - 100;
        int z = player.getLocation().getBlockZ() + RandomUtils.nextInt(200)
                - 100;
        int y = player.getWorld().getHighestBlockYAt(x, z);
        final Location targetLocation = new Location(player.getWorld(), x, y, z);

        // Play wierd sounds.
        final int wsrId = Bukkit.getScheduler().scheduleSyncRepeatingTask(
                Starving.getInstance().getPlugin(),
                new WierdSoundsRunnable(player, targetLocation), 20L, 8L);

        // Schedule blindness effect.
        Bukkit.getScheduler().scheduleSyncDelayedTask(
                Starving.getInstance().getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        player.addPotionEffect(new PotionEffect(
                                PotionEffectType.BLINDNESS, 20 * 10, 3));
                        player.addPotionEffect(new PotionEffect(
                                PotionEffectType.NIGHT_VISION, 20 * 7, 3));
                    }
                }, 20 * 15L);

        // Schedule teleport.
        Bukkit.getScheduler().scheduleSyncDelayedTask(
                Starving.getInstance().getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        targetLocation
                                .setPitch(player.getLocation().getPitch());
                        targetLocation.setYaw(player.getLocation().getYaw());
                        player.teleport(targetLocation);
                        // Cancel wierd sounds.
                        Bukkit.getScheduler().scheduleSyncDelayedTask(
                                Starving.getInstance().getPlugin(),
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        Bukkit.getScheduler().cancelTask(wsrId);
                                    }
                                }, 100L);
                    }
                }, 20 * 20L);

        return InteractResult.useOne();
    }

    private static class WierdSoundsRunnable implements Runnable {
        private static final Random random = new Random();
        private static final Sound[] sounds = Sound.values();

        private final Player player;
        private int count = 0;
        private Location tl;

        public WierdSoundsRunnable(Player player, Location targetLoc) {
            this.player = player;
            this.tl = targetLoc;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void run() {
            this.player.playSound(
                    player.getLocation().add(Vector.getRandom().multiply(2)),
                    randomSound(), 1F, (float) (1F + Math.random()));

            if ((this.count % 3) == 0) {
                this.player.playSound(player.getLocation(),
                        Sound.PORTAL_TRAVEL, 1, 1F);
                this.player.playSound(tl, Sound.PORTAL_TRAVEL, 1, 1F);
            }

            // Also apply weird blindness
            if (this.count < 7) {
                player.addPotionEffect(new PotionEffect(
                        PotionEffectType.BLINDNESS, 20, 0));
            }

            this.player.playEffect(player.getLocation(), Effect.VOID_FOG, 100);

            if (this.count > 10 && this.count < 28) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(
                        Starving.getInstance().getPlugin(), new Runnable() {
                            @Override
                            public void run() {
                                player.playSound(
                                        player.getLocation().add(
                                                Vector.getRandom().multiply(2)),
                                        randomSound(), 1F,
                                        (float) (1F + Math.random() * 0.5));
                            }
                        }, 5L);

            }

            if (this.count > 15 && this.count < 23) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(
                        Starving.getInstance().getPlugin(), new Runnable() {
                            @Override
                            public void run() {
                                player.playSound(
                                        player.getLocation().add(
                                                Vector.getRandom().multiply(2)),
                                        randomSound(), 1F,
                                        (float) (1F + Math.random() * 0.5));
                            }
                        }, 3L);

            }
            this.count++;
        }

        private Sound randomSound() {
            return sounds[random.nextInt(sounds.length)];
        }
    }
}
