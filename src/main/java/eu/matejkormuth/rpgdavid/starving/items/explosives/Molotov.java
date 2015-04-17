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

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.block.Action;

import eu.matejkormuth.bukkit.Actions;
import eu.matejkormuth.rpgdavid.bukkitfixes.FlagMetadataValue;
import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.Mappings;
import eu.matejkormuth.rpgdavid.starving.items.Rarity;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;

public class Molotov extends Item {
    public Molotov() {
        super(Mappings.MOLOTOV, "Molotov");
        this.setRarity(Rarity.UNCOMMON);
        this.setMaxStackAmount(8);
        // TODO: Category.
    }

    @Override
    public InteractResult onInteract(Player player, Action action,
            Block clickedBlock, BlockFace clickedFace) {
        if (Actions.isRightClick(action)) {
            // Launch thrown potion.
            ThrownPotion potion = (ThrownPotion) player.getWorld().spawnEntity(
                    player.getLocation().add(
                            player.getEyeLocation().getDirection()),
                    EntityType.SPLASH_POTION);

            potion.setVelocity(player.getEyeLocation().getDirection().multiply(
                    1.5f));
            potion.setMetadata("isMolotov", new FlagMetadataValue());

            player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
        }
        return InteractResult.useOne();
    }
}