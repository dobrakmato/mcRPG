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

import net.minecraft.server.v1_8_R2.AxisAlignedBB;
import net.minecraft.server.v1_8_R2.MovingObjectPosition;
import net.minecraft.server.v1_8_R2.Vec3D;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.darkblade12.particleeffect.ParticleEffect;

import eu.matejkormuth.bukkit.Actions;
import eu.matejkormuth.rpgdavid.starving.Scheduler;
import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.Time;
import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;
import eu.matejkormuth.rpgdavid.starving.items.AmunitionType;
import eu.matejkormuth.rpgdavid.starving.items.Category;
import eu.matejkormuth.rpgdavid.starving.items.InteractResult;
import eu.matejkormuth.rpgdavid.starving.items.Mapping;
import eu.matejkormuth.rpgdavid.starving.items.Rarity;
import eu.matejkormuth.rpgdavid.starving.items.itemmeta.concrete.FirearmItemMetaWrapper;
import eu.matejkormuth.rpgdavid.starving.items.transformers.FirearmTransformer;

public abstract class Firearm extends Item {
    protected static final Vector HALF_VECTOR = new Vector(0.5, 0.5, 0.5);

    private int clipSize;
    // private int ammo;

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

    /**
     * 
     * @param mapping
     * @param name
     * @param soundSourceClass Class that should be used to determinate sounds from.
     */
    public Firearm(Mapping mapping, String name, Class<?> soundSourceClass) {
        super(mapping, name);
        this.setCategory(Category.FIREARMS);
        this.setRarity(Rarity.UNCOMMON);
        this.setMaxStackAmount(1);

        // Setup sounds.
        this.reloadSound = "firearms." + soundSourceClass
                .getSimpleName().toLowerCase() + ".reload";
        this.fireSound = "firearms." + soundSourceClass
                .getSimpleName().toLowerCase() + ".fire";
    }

    protected void setAmmoType(AmunitionType ammoType) {
        this.ammoType = ammoType;
    }

    protected void setClipSize(int clipSize) {
        this.clipSize = clipSize;
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
    public void onInteractWith(Player player, Entity entity) {
        this.doFire(player);
    }

    @Override
    public InteractResult onInteract(Player player, Action action,
            Block clickedBlock, BlockFace clickedFace) {
        if (action == Action.RIGHT_CLICK_AIR) {
            doFire(player);
        } else if (Actions.isLeftClick(action)) {
            ItemStack is = player.getItemInHand();
            toggleScope(player, is);
        }
        return InteractResult.useNone();
    }

    private void doFire(Player player) {
        ItemStack is = player.getItemInHand();
        FirearmItemMetaWrapper wrapper = new FirearmItemMetaWrapper(is);

        Vector projectileVelocity = computeAndFire(player);

        // Play fire sound.
        playFireSound(player);

        // Make recoil.
        makeRecoil(player, projectileVelocity);

        // Lower ammo count.
        int ammo = wrapper.getCurrentAmmo();
        if (ammo == 1) {
            // Reload
            this.playReloadSound(player);
            wrapper.setCurrentAmmo(this.getClipSize());
        } else {
            wrapper.setCurrentAmmo(ammo - 1);
        }
        wrapper.apply(is);

        Starving.NMS.sendAboveActionBarMessage(player,
                ChatColor.YELLOW.toString() + ammo + "/" + this.clipSize);
    }

    @SuppressWarnings("deprecation")
    @NMSHooks(version = "1_8_R2")
    protected Vector computeAndFire(Player player) {

        Vector randomVec;
        if (this.isScoped()) {
            randomVec = Vector.getRandom().subtract(HALF_VECTOR).multiply(
                    this.scopedInaccurancy);
        } else {
            randomVec = Vector.getRandom().subtract(HALF_VECTOR).multiply(
                    this.inaccurancy);
        }

        // Entity tracing.
        int maxDistance = 64;
        Location loc = player.getEyeLocation();

        // Ray trace.
        Location playerLocation = player.getLocation();
        Vector projectileStart = playerLocation.toVector();
        Vector projectileDirection = player.getEyeLocation().getDirection().normalize();
        int entitiesHit = 0;
        for (LivingEntity e : player.getWorld().getLivingEntities()) {
            // Skip player.
            if (player == e) {
                continue;
            }

            if (e.getType() == EntityType.ARMOR_STAND) {
                continue;
            }

            // Limit entities hit.
            if (entitiesHit >= 2) {
                break;
            }

            double distance = playerLocation.distance(e.getLocation());
            if (distance < maxDistance) {
                Vector projectilePosition = projectileStart.clone().add(
                        projectileDirection.clone().multiply(distance));
                // Check for collision of entity's AABB and point.
                AxisAlignedBB bBox = ((CraftLivingEntity) e).getHandle().getBoundingBox();
                AxisAlignedBB rightBB = new AxisAlignedBB(bBox.a, bBox.b
                        - e.getEyeHeight(), bBox.c, bBox.d, bBox.e
                        - e.getEyeHeight(), bBox.f);
                if (rightBB.a(new Vec3D(projectilePosition.getX(),
                        projectilePosition.getY(),
                        projectilePosition.getZ()))) {
                    // We hit this entity.
                    entitiesHit++;

                    // Do not headshot dead entities.
                    if (e.isDead()) {
                        continue;
                    }

                    // Is this a headshot?
                    if (projectilePosition.getY() - 0.1f > e.getLocation().getY() - 0.3f) {
                        ParticleEffect.BLOCK_CRACK.display(
                                new ParticleEffect.BlockData(
                                        Material.REDSTONE_BLOCK, (byte) 0),
                                0.25f, 0.25f, 0.25f, 1, 80, e.getEyeLocation(),
                                256);
                        player.getWorld().playSound(e.getLocation(),
                                Sound.HURT_FLESH, 1, 1);

                        for (int i = 0; i < 40; i++) {
                            ParticleEffect.BLOCK_CRACK.display(
                                    new ParticleEffect.BlockData(
                                            Material.REDSTONE_BLOCK, (byte) 0),
                                    player.getVelocity().multiply(5), 1,
                                    e.getEyeLocation().add(
                                            Math.random() - 0.5,
                                            Math.random() - 0.5,
                                            Math.random() - 0.5),
                                    Double.MAX_VALUE);
                        }
                        e.damage(9999999D);
                        // TODO: Improve particle effect on headshot.
                    } else {
                        e.damage(4);
                    }

                    // Simulate blood on blocks.
                    Location loc3 = null;
                    for (int x = -2; x < 2; x++) {
                        for (int y = -2; y < 2; y++) {
                            for (int z = -2; z < 2; z++) {

                                if (Math.random() < 0.25) {
                                    loc3 = e.getLocation().clone().add(x, y, z);
                                    if (loc3.getBlock().getType() != Material.AIR) {
                                        Starving.NMS.displayBloodEffects(loc3);
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

        // Block raytracing.
        World world = player.getWorld();
        Vector end = loc.toVector().add(
                loc.getDirection().add(randomVec).multiply(64));

        Vector startPosVec = loc.toVector();
        Vec3D startPos = new Vec3D(startPosVec.getX(), startPosVec.getY(),
                startPosVec.getZ());
        Vec3D endPos = new Vec3D(end.getX(), end.getY(), end.getZ());

        MovingObjectPosition hit = ((CraftWorld) world).getHandle().rayTrace(
                startPos, endPos, true, true, true);

        if (hit != null && hit.pos != null) {
            Location blockLoc = new Location(player.getWorld(), hit.pos.a
                    + projectileDirection.getX() / 5,
                    hit.pos.b + projectileDirection.getY() / 5, hit.pos.c
                            + projectileDirection.getZ() / 5);
            ParticleEffect.BLOCK_CRACK.display(
                    new ParticleEffect.BlockData(blockLoc.getBlock().getType(),
                            blockLoc.getBlock().getData()),
                    0.5f, 0.5f, 0.5f, 1, 25, blockLoc, Double.MAX_VALUE);

            // Do not crack some types.
            if (blockLoc.getBlock().getType() == Material.AIR
                    || blockLoc.getBlock().getType() == Material.LEAVES) {
            } else {
                // Break block.
                Starving.NMS.displayMaterialBreak(blockLoc);
            }
        }

        // Display fire effect.
        ParticleEffect.SMOKE_NORMAL.display(0, 0, 0, 0, 20,
                player.getEyeLocation().add(
                        player.getEyeLocation().getDirection().multiply(3)),
                Double.MAX_VALUE);

        return player.getEyeLocation().getDirection().add(randomVec).multiply(
                this.projectileSpeed);
    }

    protected void playFireSound(Player player) {
        for (Player p : Bukkit
                .getOnlinePlayers()) {
            Starving.NMS
                    .playNamedSoundEffect(p, this
                            .getFireSound(),
                            player.getLocation(), 2, 1);
        }
    }

    protected void playReloadSound(Player player) {
        for (Player p : Bukkit
                .getOnlinePlayers()) {
            Starving.NMS
                    .playNamedSoundEffect(p, this
                            .getReloadSound(),
                            player.getLocation(), 2, 1);
        }
    }

    protected void makeRecoil(Player player, Vector projectileVelocity) {
        Vector recoil = projectileVelocity
                .multiply(-0.01f);
        recoil.setY(player
                .getVelocity()
                .getY());
        player.setVelocity(recoil);
    }

    protected void toggleScope(Player player, ItemStack is) {
        this.toggleScope(player, is, 2);
    }

    protected void toggleScope(Player player, ItemStack is, int slownessLevel) {
        Bukkit.broadcastMessage("Player " + player.getName()
                + " is toggling scope with current itemStack " + is.toString()
                + " with slowness level: " + slownessLevel);
        Bukkit.broadcastMessage("Executing in class "
                + this.getClass().getSimpleName() + " and isScoped() = "
                + this.isScoped());

        // Scope tha gun.
        if (this.isScoped()) {
            // Transform item.
            ItemStack nonScoped = FirearmTransformer
                    .fromScoped(is);

            Bukkit.broadcastMessage("Created non-scoped itemstack: "
                    + nonScoped.toString());

            Bukkit.broadcastMessage("Scheduling setItemInHand() to 5 ticks...");
            Scheduler.delay(() -> {
                Bukkit.broadcastMessage("Setting item in hand!");
                player.setItemInHand(nonScoped);
            }, Time.ofTicks(5));

            Bukkit.broadcastMessage("Removing slowness effect from player.");
            player.removePotionEffect(PotionEffectType.SLOW);
        } else {
            // Transform item.
            ItemStack scoped = FirearmTransformer
                    .toScoped(is);

            Bukkit.broadcastMessage("Created scoped itemstack: "
                    + scoped.toString());

            Bukkit.broadcastMessage("Scheduling setItemInHand() to 5 ticks...");
            Scheduler.delay(() -> {
                Bukkit.broadcastMessage("Setting item in hand!");
                player.setItemInHand(scoped);
            }, Time.ofTicks(5));

            Bukkit.broadcastMessage("Adding slowness effect to player.");
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
                    Time.ofMinutes(30).toTicks(), slownessLevel));
        }
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack raw = super
                .toItemStack();
        FirearmItemMetaWrapper wrapper = new FirearmItemMetaWrapper(raw);
        wrapper.setCurrentAmmo(this
                .getClipSize());
        wrapper.apply(raw);
        return raw;
    }

    @Override
    public ItemStack toItemStack(int amount) {
        ItemStack raw = super
                .toItemStack(amount);
        FirearmItemMetaWrapper wrapper = new FirearmItemMetaWrapper(raw);
        wrapper.setCurrentAmmo(this
                .getClipSize());
        wrapper.apply(raw);
        return raw;
    }

    public boolean isScoped() {
        return false;
    }
}
