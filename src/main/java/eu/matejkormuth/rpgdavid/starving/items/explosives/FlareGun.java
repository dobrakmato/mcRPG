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

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import com.darkblade12.particleeffect.ParticleEffect;

import eu.matejkormuth.bukkit.Actions;
import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.Mappings;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;
import eu.matejkormuth.rpgdavid.starving.tasks.RepeatingTask;

public class FlareGun extends Item {
    public FlareGun() {
        super(Mappings.FLAREGUN, "FlareGun");
    }

    @Override
    public InteractResult onInteract(Player player, Action action,
            Block clickedBlock, BlockFace clickedFace) {
        if (Actions.isRightClick(action)) {
            new FlareSimTask(player.getEyeLocation(),
                    player.getEyeLocation().getDirection().multiply(2)).schedule(20L);
        }
        return InteractResult.useNone();
    }

    public class FlareSimTask extends RepeatingTask {
        private int lifeTime = 0;
        private boolean burning = false;

        private Location loc;
        private Vector vel;

        public FlareSimTask(Location spawn, Vector vel) {
            this.loc = spawn;
            this.vel = vel;
        }

        @Override
        public void run() {
            if (this.lifeTime >= 10 * 20) {
                this.cancel();
            }

            if (!burning && this.lifeTime >= 20 * 3) {
                burning = true;
            }

            if (!burning && this.loc.getBlock().getType().isSolid()) {
                burning = true;
            }

            if (burning) {
                Firework f = (Firework) loc.getWorld().spawnEntity(loc,
                        EntityType.FIREWORK);
                FireworkMeta fm = f.getFireworkMeta();
                fm.addEffect(FireworkEffect.builder().withColor(Color.RED).build());
            } else {
                ParticleEffect.FLAME.display(0, 0, 0, 0, 1, this.loc,
                        Double.MAX_VALUE);
            }

            this.loc.add(vel);

            this.lifeTime++;
        }
    }
}
