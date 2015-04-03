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
package eu.matejkormuth.rpgdavid.starving.worldgen;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import eu.matejkormuth.bukkit.Actions;
import eu.matejkormuth.rpgdavid.starving.worldgen.affectedblocks.AffectedBlocksDefinition;

public class WandListener implements Listener {

    private static final Material WAND_MATERIAL = Material.BLAZE_ROD;
    private WorldGenManager manager;

    public WandListener(WorldGenManager manager) {
        this.manager = manager;
    }

    @EventHandler
    private void onWandInteract(final PlayerInteractEvent event) {
        if (Actions.isRightClick(event.getAction())) {
            if (event.getItem() != null
                    && event.getItem().getType().equals(WAND_MATERIAL)) {
                performAction(event.getPlayer());
            }
        }
    }

    private void performAction(Player player) {
        // Retrieve session.
        PlayerSession session = manager.getSession(player);

        // Create definition.
        AffectedBlocksDefinition definition = session.getBrush().createDefinition(
                player.getTargetBlock((Set<Material>) null, session.getMaxDistance()));

        session.getFilter().apply(definition, session.getFilterProperties());
    }
}
