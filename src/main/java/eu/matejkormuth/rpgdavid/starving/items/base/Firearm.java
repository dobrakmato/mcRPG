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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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
import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.Time;
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
    private float scopedInaccurancy = 0.2f;
    private float recoil = 0.5f;

    private final String reloadSound;
    private final String fireSound;

    public Firearm(Material material, String name) {
        super(material, name);
        this.setCategory(Category.FIREARMS);
        this.setRarity(Rarity.UNCOMMON);

        // Setup sounds.
        this.reloadSound = this.getClass().getSimpleName() + "_reload";
        this.fireSound = this.getClass().getSimpleName() + "_fire";
    }

    protected void setAmmoType(AmunitionType ammoType) {
        this.ammoType = ammoType;
    }

    protected void setClipSize(int clipSize) {
        this.clipSize = clipSize;
        this.ammo = this.clipSize;
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

    protected void setScopedInaccurancy(float scopedInaccurancy) {
        this.scopedInaccurancy = scopedInaccurancy;
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

    public float getScopedInaccurancy() {
        return this.scopedInaccurancy;
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

    public String getFireSound() {
        return this.fireSound;
    }

    public String getReloadSound() {
        return this.reloadSound;
    }

    @Override
    public InteractResult onInteract(Player player, Action action,
            Block clickedBlock, BlockFace clickedFace) {
        if (Actions.isRightClick(action)) {

            Vector projectileVelocity = computeAndFire(player);

            // Play fire sound.
            playFireSound(player);

            // Make recoil.
            makeRecoil(player, projectileVelocity);

            // Lower ammo count.
            if (ammo == 1) {
                // Reload
                this.playReloadSound(player);
                this.ammo = this.clipSize;
            } else {
                this.ammo--;
            }
            Starving.NMS.sendAboveActionBarMessage(player,
                    ChatColor.YELLOW.toString() + this.ammo + "/"
                            + this.clipSize);
        } else if (Actions.isLeftClick(action)) {
            toggleScope(player);
        }
        return InteractResult.useNone();
    }

    protected Vector computeAndFire(Player player) {
        // Compute values.
        Location projectileSpawn = player.getEyeLocation().add(
                player.getEyeLocation().getDirection().multiply(2));
        Vector randomVec;
        if (Data.of(player).isScoped()) {
            randomVec = Vector.getRandom().subtract(HALF_VECTOR)
                    .multiply(this.inaccurancy);
        } else {
            randomVec = Vector.getRandom().subtract(HALF_VECTOR)
                    .multiply(this.scopedInaccurancy);
        }

        Vector projectileVelocity = player.getEyeLocation().getDirection()
                .add(randomVec).multiply(this.projectileSpeed);

        // Spawn projectile.
        Snowball projectile = (Snowball) player.getWorld().spawnEntity(
                projectileSpawn, EntityType.SNOWBALL);
        projectile.setVelocity(projectileVelocity);
        return projectileVelocity;
    }

    protected void playFireSound(Player player) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Starving.NMS.playNamedSoundEffect(p, this.getFireSound(),
                    player.getLocation(), 2, 1);
        }
    }

    protected void playReloadSound(Player player) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Starving.NMS.playNamedSoundEffect(p, this.getReloadSound(),
                    player.getLocation(), 2, 1);
        }
    }

    protected void makeRecoil(Player player, Vector projectileVelocity) {
        Vector recoil = projectileVelocity.multiply(-0.01f);
        recoil.setY(player.getVelocity().getY());
        player.setVelocity(recoil);
    }

    protected void toggleScope(Player player) {
        // Scope tha gun.
        if (Data.of(player).switchScoped()) {
            player.removePotionEffect(PotionEffectType.SLOW);
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Time
                    .ofMinutes(30).toTicks(), 2));
        }
    }
}
