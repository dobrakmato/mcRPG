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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.darkblade12.particleeffect.ParticleEffect;

import eu.matejkormuth.bukkit.Actions;
import eu.matejkormuth.rpgdavid.starving.items.Category;
import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.Mappings;
import eu.matejkormuth.rpgdavid.starving.items.Rarity;
import eu.matejkormuth.rpgdavid.starving.items.base.Item;
import eu.matejkormuth.rpgdavid.starving.tasks.RepeatingTask;

public class RPG7 extends Item {
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
            new RocketFlight(player.getLocation(), player.getEyeLocation().getDirection()).schedule(1L);
        }
        return super.onInteract(player, action, clickedBlock, clickedFace);
    }

    public static class RocketFlight extends RepeatingTask {
        private static int maxLifetime = 20 * 5;
        private static int maxY = 300;

        private int lifeTime = 0;
        private Vector vel;
        private ArmorStand armorStand;
        
        public RocketFlight(Location spawn, Vector vel) {
            this.vel = vel;
            this.armorStand = (ArmorStand) spawn.getWorld().spawnEntity(spawn, EntityType.ARMOR_STAND);
            
            this.armorStand.setGravity(false);
            this.armorStand.setHelmet(new ItemStack(Material.YELLOW_FLOWER));
        }

        @Override
        public void run() {
            lifeTime++;

            if (lifeTime > maxLifetime) {
                this.remove();
            }

            if (armorStand.getLocation().getY() > maxY) {
                this.remove();
            }

            armorStand.teleport(armorStand.getLocation().add(vel));
            ParticleEffect.FIREWORKS_SPARK.display(vel.clone().multiply(-1), 1, armorStand.getLocation(), Double.MAX_VALUE);

        }

        private void remove() {
            armorStand.remove();
            this.cancel();
        }
    }
}
