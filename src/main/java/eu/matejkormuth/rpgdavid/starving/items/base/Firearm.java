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
package eu.matejkormuth.rpgdavid.starving.items.base;

import org.bukkit.Location;
import org.bukkit.Material;
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
import eu.matejkormuth.rpgdavid.starving.items.Category;
import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.Rarity;

public abstract class Firearm extends Item {
    protected static final Vector HALF_VECTOR = new Vector(0.5, 0.5, 0.5);

    private int clipSize;
    private int ammo;

    private AmunitionType ammoType;

    private int fireRate = 1; // per second
    private float noiseLevel = 1; // impulse power
    private float projectileSpeed = 2; // multiplier
    private int reloadTime = 40; // ticks
    private float inaccurancy = 0.5f;
    private float recoil = 0.5f;

    public Firearm(String name) {
        super(Material.GOLD_BARDING, name);
        this.setCategory(Category.FIREARMS);
        this.setRarity(Rarity.COMMON);
    }

    protected void setAmmoType(AmunitionType ammoType) {
        this.ammoType = ammoType;
    }

    protected void setClipSize(int clipSize) {
        this.clipSize = clipSize;
    }

    protected void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    protected void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }

    protected void setNoiseLevel(float noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    protected void setProjectileSpeed(float projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    protected void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    protected void setInaccurancy(float inaccurancy) {
        this.inaccurancy = inaccurancy;
    }

    protected void setRecoil(float recoil) {
        this.recoil = recoil;
    }

    public AmunitionType getAmmoType() {
        return this.ammoType;
    }

    public int getClipSize() {
        return this.clipSize;
    }

    public int getAmmo() {
        return this.ammo;
    }

    public int getFireRate() {
        return this.fireRate;
    }

    public float getNoiseLevel() {
        return this.noiseLevel;
    }

    public float getInaccurancy() {
        return this.inaccurancy;
    }

    public float getProjectileSpeed() {
        return this.projectileSpeed;
    }

    public float getRecoil() {
        return this.recoil;
    }

    public int getReloadTime() {
        return this.reloadTime;
    }

    @Override
    public InteractResult onInteract(Player player, Action action,
            Block clickedBlock, BlockFace clickedFace) {
        if (Actions.isRightClick(action)) {

            // Compute values.
            Location projectileSpawn = player.getEyeLocation().add(
                    player.getEyeLocation().getDirection());
            Vector randomVec = Vector.getRandom().subtract(HALF_VECTOR)
                    .multiply(this.inaccurancy);
            Vector projectileVelocity = player.getEyeLocation().getDirection()
                    .add(randomVec).multiply(this.projectileSpeed);

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
            // not now...
        }
        return InteractResult.useNone();
    }
}
