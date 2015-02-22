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
package eu.matejkormuth.rpgdavid.starving.items.firearms;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;

import eu.matejkormuth.bukkit.Actions;
import eu.matejkormuth.rpgdavid.starving.items.AmunitionType;
import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.base.Firearm;

public class Mossberg500 extends Firearm {
    public Mossberg500() {
        super("Mossberg500");
        this.setAmmoType(AmunitionType.LONG);
        this.setClipSize(6);
        this.setFireRate(1);
        this.setInaccurancy(0.4f);
        this.setNoiseLevel(1);
        this.setProjectileSpeed(2.1f);
        this.setRecoil(0.3f);
        this.setReloadTime(40);
    }

    @Override
    public InteractResult onInteract(Player player, Action action,
            Block clickedBlock, BlockFace clickedFace) {
        if (Actions.isRightClick(action)) {

            Vector projectileVelocity = fire(player);
            for (int i = 0; i < 5; i++) {
                fire(player);
            }

            // Play fire sound.
            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 0.5F, 2F);

            // Make recoil.
            Vector recoil = projectileVelocity.multiply(-1);
            recoil.setY(player.getVelocity().getY());
            player.setVelocity(recoil);
        } else if (Actions.isLeftClick(action)) {
            // Scope tha gun.
            // not now...
        }
        return InteractResult.useNone();
    }

    private Vector fire(Player player) {
        // Compute values.
        Location projectileSpawn = player.getEyeLocation().add(
                player.getEyeLocation().getDirection());
        Vector randomVec = Vector.getRandom().subtract(HALF_VECTOR)
                .multiply(this.getInaccurancy());
        Vector projectileVelocity = player.getEyeLocation().getDirection()
                .add(randomVec).multiply(this.getProjectileSpeed());

        // Spawn projectile.
        Snowball projectile = (Snowball) player.getWorld().spawnEntity(
                projectileSpawn, EntityType.SNOWBALL);
        projectile.setVelocity(projectileVelocity);
        return projectileVelocity;
    }
}
