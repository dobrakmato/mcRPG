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

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TimeModifiersUpdater implements Runnable {
    /**
     * Default walking speed.
     */
    public static final float DEFAULT_WALK_SPEED = 0.2F;

    public void run() {
        Character character;
        for (Player p : Bukkit.getOnlinePlayers()) {
            Profile profile = RpgPlugin.getInstance().getProfile(p);
            if (profile != null) {
                if (profile.getCharacter() != null) {
                    character = profile.getCharacter();

                    // Update player's character.
                    if (character == Characters.WEREWOLF) {
                        updateWerewolf(p);
                        updateWalkSpeedByArmor(character, p);
                    } else if (character == Characters.VAMPIRE) {
                        updateVampire(p);
                        updateWalkSpeedByArmor(character, p);
                    } else {
                        updateWalkSpeedByArmor(character, p);
                    }
                }
            }
        }
    }

    private void updateWalkSpeedByArmor(Character character, Player p) {
        // Evaluate armor slowdown.
        float slowdown = 1.0F; // No slowdown.

        /*
         * Each leather armor has slowdown of 1/4 of 10% of previous (character
         * default probably) speed. Each iron armor has slowdown of 1/4 of 25%
         * of previous (character default probably) speed. Each gold armor has
         * slowdown of 1/4 of 35% of previous (character default probably)
         * speed. Each diamond armor has slowdown of 1/4 of 45% of previous
         * (character default probably) speed.
         */

        if (p.getInventory().getHelmet() != null) {
            if (p.getInventory().getHelmet().getType() == Material.LEATHER_HELMET) {
                slowdown -= 0.025F;
            } else if (p.getInventory().getHelmet().getType() == Material.IRON_HELMET) {
                slowdown -= 0.0625F;
            } else if (p.getInventory().getHelmet().getType() == Material.GOLD_HELMET) {
                slowdown -= 0.0875F;
            } else if (p.getInventory().getHelmet().getType() == Material.DIAMOND_HELMET) {
                slowdown -= 0.1125F;
            }

        }

        if (p.getInventory().getChestplate() != null) {
            if (p.getInventory().getChestplate().getType() == Material.LEATHER_CHESTPLATE) {
                slowdown -= 0.025F;
            } else if (p.getInventory().getChestplate().getType() == Material.IRON_CHESTPLATE) {
                slowdown -= 0.0625F;
            } else if (p.getInventory().getChestplate().getType() == Material.GOLD_CHESTPLATE) {
                slowdown -= 0.0875F;
            } else if (p.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE) {
                slowdown -= 0.1125F;
            }
        }

        if (p.getInventory().getLeggings() != null) {
            if (p.getInventory().getLeggings().getType() == Material.LEATHER_LEGGINGS) {
                slowdown -= 0.025F;
            } else if (p.getInventory().getLeggings().getType() == Material.IRON_LEGGINGS) {
                slowdown -= 0.0625F;
            } else if (p.getInventory().getLeggings().getType() == Material.GOLD_LEGGINGS) {
                slowdown -= 0.0875F;
            } else if (p.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS) {
                slowdown -= 0.1125F;
            }
        }

        if (p.getInventory().getBoots() != null) {
            if (p.getInventory().getBoots().getType() == Material.LEATHER_BOOTS) {
                slowdown -= 0.025F;
            } else if (p.getInventory().getBoots().getType() == Material.IRON_BOOTS) {
                slowdown -= 0.0625F;
            } else if (p.getInventory().getBoots().getType() == Material.GOLD_BOOTS) {
                slowdown -= 0.0875F;
            } else if (p.getInventory().getBoots().getType() == Material.DIAMOND_BOOTS) {
                slowdown -= 0.1125F;
            }
        }

        // Apply slowdown.
        float speed = character.getModifiers().getWalkSpeedModifier()
                * DEFAULT_WALK_SPEED;
        p.setWalkSpeed(speed * slowdown);
    }

    private void updateVampire(Player p) {
        if (p.getWorld().getTime() > 13000 && p.getWorld().getTime() < 23000) {
            // Vampires have night vision
            if (!p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                p.addPotionEffect(new PotionEffect(
                        PotionEffectType.NIGHT_VISION, 20 * 65 * 5, 0));
            }

            // Vampires are 2.3x faster in night.
            p.setWalkSpeed(DEFAULT_WALK_SPEED
                    * Characters.VAMPIRE.getModifiers().getWalkSpeedModifier()
                    * 2.3F);
        } else {
            if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }

            // Vampires get damaged on sunlight, when not wearing gold helmet.
            if (p.getInventory().getHelmet() == null
                    || p.getInventory().getHelmet().getType() != Material.GOLD_HELMET) {
                p.damage(1D);

            }

            // Vampires are normal fast in day.
            p.setWalkSpeed(Characters.VAMPIRE.getModifiers()
                    .getWalkSpeedModifier() * DEFAULT_WALK_SPEED);
        }
    }

    private void updateWerewolf(Player p) {
        if (p.getWorld().getTime() > 13000 && p.getWorld().getTime() < 23000) {
            if (!p.hasPotionEffect(PotionEffectType.SPEED)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
                        20 * 65 * 5, 1));
            }

            if (!p.hasPotionEffect(PotionEffectType.JUMP)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,
                        20 * 65 * 5, 1));
            }

            if (!p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                p.addPotionEffect(new PotionEffect(
                        PotionEffectType.NIGHT_VISION, 20 * 65 * 5, 0));
            }
        } else {
            if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                p.removePotionEffect(PotionEffectType.SPEED);
            }

            if (p.hasPotionEffect(PotionEffectType.JUMP)) {
                p.removePotionEffect(PotionEffectType.JUMP);
            }

            if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }
        }
    }
}
