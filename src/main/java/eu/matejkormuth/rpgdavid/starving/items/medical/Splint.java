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
package eu.matejkormuth.rpgdavid.starving.items.medical;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import eu.matejkormuth.bukkit.Actions;
import eu.matejkormuth.rpgdavid.starving.items.Category;
import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.Mappings;
import eu.matejkormuth.rpgdavid.starving.items.Rarity;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;

public class Splint extends Item {
    public Splint() {
        super(Mappings.SPLINT, "Splint");
        this.setCategory(Category.MEDICAL);
        this.setRarity(Rarity.RARE);
        this.setMaxStackAmount(1);
    }

    @Override
    public InteractResult onInteract(Player player, Action action,
            Block clickedBlock, BlockFace clickedFace) {
        // Lower slowness if level greather then or equal to 2.
        if (Actions.isRightClick(action)) {
            if (player.hasPotionEffect(PotionEffectType.SLOW)) {
                int currentLevel = 0;
                int duration = 1;
                // Find level of potion effect.
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    if (effect.getType().equals(PotionEffectType.SLOW)) {
                        currentLevel = effect.getAmplifier();
                        duration = effect.getDuration();
                    }
                }
                if (currentLevel >= 2) {
                    // Lower the level by one.
                    player.removePotionEffect(PotionEffectType.SLOW);
                    player.addPotionEffect(new PotionEffect(
                            PotionEffectType.SLOW, duration, currentLevel - 1));
                }
                return InteractResult.useOne();
            }
        }
        return InteractResult.useNone();
    }
}
