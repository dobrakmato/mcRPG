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
package eu.matejkormuth.rpgdavid.starving.npc.behaviours;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import eu.matejkormuth.rpgdavid.starving.npc.behaviours.base.ListenerAbstractBehaiour;

public abstract class InteractBehaviour extends ListenerAbstractBehaiour {

    @EventHandler
    private void onInteract(final PlayerInteractEntityEvent event) {
        if (event.getRightClicked() == this.owner) {
            this.onRightClick(event.getPlayer());
        }
    }

    @EventHandler
    private void onHit(final EntityDamageByEntityEvent event) {
        if (event.getEntity() == this.owner) {
            if (event.getDamager() instanceof Player) {
                event.setDamage(0);
                this.onLeftClick((Player) event.getDamager());
            }
        }
    }

    public void onLeftClick(Player player) {
    }

    public void onRightClick(Player player) {
    }
}
