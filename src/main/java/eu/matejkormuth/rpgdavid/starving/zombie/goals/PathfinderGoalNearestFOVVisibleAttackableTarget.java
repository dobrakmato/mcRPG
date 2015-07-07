package eu.matejkormuth.rpgdavid.starving.zombie.goals;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.craftbukkit.v1_8_R2.TrigMath;
import org.bukkit.event.entity.EntityTargetEvent;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;
import net.minecraft.server.v1_8_R2.AxisAlignedBB;
import net.minecraft.server.v1_8_R2.Entity;
import net.minecraft.server.v1_8_R2.EntityCreature;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityLiving;
import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.IEntitySelector;
import net.minecraft.server.v1_8_R2.PathfinderGoalTarget;

@NMSHooks(version = "v1_8_R2")
public class PathfinderGoalNearestFOVVisibleAttackableTarget<T extends EntityLiving> extends PathfinderGoalTarget {
    // Type of target entity.
    protected final Class<T> targetEntityClass;
    // Each time the a() method is called a random number is generated in range
    // of zero to targetingSpeed. If the generated number is not zero, a method
    // will run.
    private final int targetingSpeed;
    // Instance of comparator used for sorting entities in list by their
    // distance to pathfinding entity.
    protected final DistanceComparator distanceComparator;
    // Represents Predicate by which are found entities filtered before they are
    // ordered by distance.
    protected Predicate<? super T> customFilter;
    // Represents current target.
    protected EntityLiving currentTarget;
    // Horizontal field of view.
    private final int halfHorizontalFov;
    // Vertical field of view.
    private final int halfVerticalFov;

    // EntityCreature holder - the holder of this goal.
    public PathfinderGoalNearestFOVVisibleAttackableTarget(EntityCreature holder, Class<T> targetEntityType,
            boolean flag, int horizontalFov, int verticalFov) {
        this(holder, targetEntityType, flag, false, horizontalFov, verticalFov);
    }

    public PathfinderGoalNearestFOVVisibleAttackableTarget(EntityCreature holder, Class<T> targetEntityType,
            boolean flag, boolean flag1, int horizontalFov, int verticalFov) {
        this(holder, targetEntityType, 10, flag, flag1, null, horizontalFov, verticalFov);
    }

    public PathfinderGoalNearestFOVVisibleAttackableTarget(EntityCreature holder, Class<T> targetEntityType,
            int targetingSpeed, boolean flag, boolean flag1, Predicate<? super T> predicate, int horizontalFov,
            int verticalFov) {
        super(holder, flag, flag1);
        this.targetEntityClass = targetEntityType;
        this.targetingSpeed = targetingSpeed;
        this.halfHorizontalFov = horizontalFov / 2;
        this.halfVerticalFov = verticalFov / 2;
        this.distanceComparator = new DistanceComparator(holder);
        // Have no idea what this does.
        a(1);
        // Setup custom filter for entities.
        this.customFilter = new Predicate<T>() {
            public boolean apply(T entity) {
                // Check if parent predicate applies.
                if ((predicate != null) && (!(predicate.apply(entity)))) {
                    return false;
                }

                if (entity instanceof EntityHuman) {
                    // Get follow range from entity attributes.
                    double followRange = PathfinderGoalNearestFOVVisibleAttackableTarget.this.f();

                    // Lower the follow range if player is sneaking.
                    if (entity.isSneaking()) {
                        followRange *= 0.800000011920929D;
                    }

                    // Keep invisibility logic.
                    if (entity.isInvisible()) {
                        float f = ((EntityHuman) entity).bY();

                        if (f < 0.1F) {
                            f = 0.1F;
                        }

                        followRange *= 0.7F * f;
                    }

                    // Check if the entity isn't too far.
                    if (entity.g(PathfinderGoalNearestFOVVisibleAttackableTarget.this.e) > followRange) {
                        return false;
                    }
                }

                // Check for FOV.
                double alpha = TrigMath.atan2(e.locZ - entity.locZ, e.locX - entity.locX);
                double beta = TrigMath.atan2(e.locY - entity.locY, e.locX - entity.locX);
                boolean isInFieldOfView = alpha < halfHorizontalFov && beta < halfVerticalFov;

                return PathfinderGoalNearestFOVVisibleAttackableTarget.this.a(entity, false) && isInFieldOfView;
            }
        };
    }

    // Chooses target by some rules, returns false if no entity was chosen. True
    // when entity was chosen.
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public boolean a() {
        // Check if we are going to find a target.
        if ((this.targetingSpeed > 0) && (this.e.bc().nextInt(this.targetingSpeed) != 0)) {
            return false;
        }

        // Get follow range from entity attributes.
        double followRange = f();

        // Create search region based on follow range attribute.
        AxisAlignedBB searchRegion = this.e.getBoundingBox().grow(followRange, 4.0D, followRange);

        // This predicate filters players that are not spectators.
        Predicate playersNotSpectators = IEntitySelector.d;

        // Select by class this.a and specified predicate that works as filter.
        List entities = this.e.world.a(this.targetEntityClass, searchRegion, Predicates.and(this.customFilter,
                playersNotSpectators));

        // Sort player entities by distance to zombie.
        Collections.sort(entities, this.distanceComparator);

        if (entities.isEmpty()) {
            // Return that we have not chosen target.
            return false;
        }

        // Select first entity in list as target.
        this.currentTarget = ((EntityLiving) entities.get(0));

        // Return that we have chosen target.
        return true;
    }

    // Probably called when entity chosen by a() method should be pathfinding
    // entity's new target.
    public void c()
    {
        // Determinate reason.
        EntityTargetEvent.TargetReason reason = (this.currentTarget instanceof EntityPlayer) ? EntityTargetEvent.TargetReason.CLOSEST_PLAYER
                : EntityTargetEvent.TargetReason.CLOSEST_ENTITY;
        // Set goal target.
        this.e.setGoalTarget(this.currentTarget, reason, true);
        // Reset.
        super.c();
    }

    public static class DistanceComparator implements Comparator<Entity> {
        // Origin entity used to measure distances to.
        private final Entity origin;

        public DistanceComparator(Entity origin) {
            this.origin = origin;
        }

        // Checks which of two entities is closer to origin entity.
        public int check(Entity entity, Entity entity1) {
            // Get distance to first entity.
            double distance0 = this.origin.h(entity);
            // Get distance to second entity.
            double distance1 = this.origin.h(entity1);

            return ((distance0 > distance1) ? 1 : (distance0 < distance1) ? -1 : 0);
        }

        // Checks which of two entities is closer to origin entity.
        public int compare(Entity object, Entity object1) {
            return check(object, object1);
        }
    }
}
