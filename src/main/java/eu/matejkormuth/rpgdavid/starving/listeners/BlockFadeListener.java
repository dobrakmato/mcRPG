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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;

import eu.matejkormuth.rpgdavid.starving.persistence.Persist;

public class BlockFadeListener implements Listener {

    @Persist(key = "DISABLE_BLOCK_FADE")
    private static boolean DISABLE_BLOCK_FADE = true;

    @Persist(key = "DISABLE_BLOCK_FORM")
    private static boolean DISABLE_BLOCK_FORM = true;

    @Persist(key = "DISABLE_BLOCK_SPREAD")
    private static boolean DISABLE_BLOCK_SPREAD = true;

    @Persist(key = "DISABLE_BLOCK_FROMTO")
    private static boolean DISABLE_BLOCK_FROMTO = true;

    @Persist(key = "DISABLE_BLOCK_GROW")
    private static boolean DISABLE_BLOCK_GROW = true;

    @Persist(key = "DISABLE_LEAVES_DECAY")
    private static boolean DISABLE_LEAVES_DECAY = true;

    @EventHandler
    private void onBlockFade(final BlockFadeEvent event) {
        Starving.getInstance().getLogger().info("BlockFadeEvent fired!");
        if (DISABLE_BLOCK_FADE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onBlockForm(final BlockFormEvent event) {
        if (DISABLE_BLOCK_FORM) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onBlockSpread(final BlockSpreadEvent event) {
        if (DISABLE_BLOCK_SPREAD) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onBlockFromTo(final BlockFromToEvent event) {
        if (DISABLE_BLOCK_FROMTO) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onBlockGrow(final BlockGrowEvent event) {
        if (DISABLE_BLOCK_GROW) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onLeavesDecay(final LeavesDecayEvent event) {
        if (DISABLE_LEAVES_DECAY) {
            event.setCancelled(true);
        }
    }
}
