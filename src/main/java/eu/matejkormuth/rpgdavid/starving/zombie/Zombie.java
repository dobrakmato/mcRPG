package eu.matejkormuth.rpgdavid.starving.zombie;

import java.lang.reflect.Field;
import java.util.Random;

import org.bukkit.craftbukkit.v1_8_R2.util.UnsafeList;

import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;
import eu.matejkormuth.rpgdavid.starving.zombie.goals.PathfinderGoalNearestFOVVisibleAttackableTarget;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityZombie;
import net.minecraft.server.v1_8_R2.GenericAttributes;
import net.minecraft.server.v1_8_R2.IAttribute;
import net.minecraft.server.v1_8_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R2.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R2.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R2.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R2.World;

@NMSHooks(version = "v1_8_R2")
public class Zombie extends EntityZombie {

    // Random instance.
    private static final Random random = new Random();

    // Attributes.
    private static final IAttribute maxHealth = GenericAttributes.maxHealth;
    private static final IAttribute followRange = GenericAttributes.b;
    private static final IAttribute knockbackResitence = GenericAttributes.c;
    private static final IAttribute movementSpeed = GenericAttributes.d;
    private static final IAttribute attackDamage = GenericAttributes.e;

    public Zombie(World world) {
        super(world);

        setAttributes();
        try {
            clearPathfindingGoals();
        } catch (NoSuchFieldException | SecurityException |
                IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        setPathfindingGoals();
    }

    private void clearPathfindingGoals() throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = PathfinderGoalSelector.class;
        Field list_b = clazz.getDeclaredField("distanceComparator");
        Field list_c = clazz.getDeclaredField("c");

        if (!list_b.isAccessible())
            list_b.setAccessible(true);

        if (!list_c.isAccessible())
            list_c.setAccessible(true);

        list_b.set(this.goalSelector, new UnsafeList<>());
        list_c.set(this.goalSelector, new UnsafeList<>());

        list_b.set(this.targetSelector, new UnsafeList<>());
        list_c.set(this.targetSelector, new UnsafeList<>());
    }

    private void setPathfindingGoals() {
        // Follow and Attack EntityHuman
        this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1, false));
        // Look at player.
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));

        // Random movement
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1));
        // Random look around
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));

        // Choose EntityHuman by it's FOV.
        this.targetSelector.a(2, new PathfinderGoalNearestFOVVisibleAttackableTarget<EntityHuman>(this,
                EntityHuman.class, true, 90, 45));
        // this.targetSelector.a(2, new
        // PathfinderGoalNearestAttackableTarget<EntityHuman>(this,
        // EntityHuman.class, true));
    }

    private void setAttributes() {
        this.getAttributeInstance(maxHealth).setValue(18D + random.nextInt(5));
        this.getAttributeInstance(followRange).setValue(32D + random.nextInt(5));
        this.getAttributeInstance(movementSpeed).setValue(0.2899999988f +
                random.nextFloat() * 0.0499999892f);
        this.getAttributeInstance(knockbackResitence).setValue(random.nextFloat());
        this.getAttributeInstance(attackDamage).setValue(1D + random.nextInt(3));
    }

}
