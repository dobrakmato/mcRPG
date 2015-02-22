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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import eu.matejkormuth.bukkit.Actions;
import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.Time;
import eu.matejkormuth.rpgdavid.starving.items.AmunitionType;
import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.Mappings;
import eu.matejkormuth.rpgdavid.starving.items.base.Firearm;

public class Dragunov extends Firearm {
    public Dragunov() {
        super(Mappings.DRAGUNOV, "Dragunov");
        this.setAmmoType(AmunitionType.LONG);
        this.setClipSize(10);
        this.setFireRate(1);
        this.setInaccurancy(0.02f);
        this.setScopedInaccurancy(0.0001f);
        this.setNoiseLevel(1);
        this.setProjectileSpeed(4f);
        this.setRecoil(1);
        this.setReloadTime(100);
    }

    @Override
    public InteractResult onInteract(Player player, Action action,
            Block clickedBlock, BlockFace clickedFace) {
        if (Actions.isRightClick(action)) {

            // Compute values.
            Location projectileSpawn = player.getEyeLocation().add(
                    player.getEyeLocation().getDirection());
            Vector randomVec;
            if (Data.of(player).isScoped()) {
                randomVec = Vector.getRandom().subtract(HALF_VECTOR)
                        .multiply(this.getScopedInaccurancy());
            } else {
                randomVec = Vector.getRandom().subtract(HALF_VECTOR)
                        .multiply(this.getInaccurancy());
            }

            Vector projectileVelocity = player.getEyeLocation().getDirection()
                    .add(randomVec).multiply(this.getProjectileSpeed());

            // Spawn projectile.
            Snowball projectile = (Snowball) player.getWorld().spawnEntity(
                    projectileSpawn, EntityType.SNOWBALL);
            projectile.setVelocity(projectileVelocity);

            // Play fire sound.
            player.playSound(player.getLocation(), Sound.NOTE_PIANO, 0.5F, 2F);

            // Make recoil.
            Vector recoil = projectileVelocity.multiply(-1);
            recoil.setY(player.getVelocity().getY());
            player.setVelocity(recoil);
        } else if (Actions.isLeftClick(action)) {
            // Scope tha gun.
            if (Data.of(player).switchScoped()) {
                player.removePotionEffect(PotionEffectType.SLOW);
            } else {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
                        Time.ofMinutes(30).toTicks(), 6));
            }
        }
        return InteractResult.useNone();
    }
}
