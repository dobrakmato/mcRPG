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
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import eu.matejkormuth.bukkit.Actions;
import eu.matejkormuth.rpgdavid.starving.items.Category;
import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.Mappings;
import eu.matejkormuth.rpgdavid.starving.items.Rarity;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;
import eu.matejkormuth.rpgdavid.starving.rockets.Rocket;
import eu.matejkormuth.rpgdavid.starving.rockets.RocketUniverse;

public class RPG7 extends Item {

    public static RocketUniverse mainUniverse = new RocketUniverse();

    public RPG7() {
        super(Mappings.RPG7, "RPG-7");
        this.setCategory(Category.FIREARMS);
        this.setRarity(Rarity.RARE);
        this.setMaxStackAmount(1);
    }

    @Override
    public InteractResult onInteract(Player player, Action action,
            Block clickedBlock, BlockFace clickedFace) {
        if (Actions.isRightClick(action)) {
            mainUniverse.addRocket(new Rocket(player, player.getEyeLocation(),
                    player.getEyeLocation().getDirection()));
        }
        return super.onInteract(player, action, clickedBlock, clickedFace);
    }
}
