package eu.matejkormuth.rpgdavid.starving.npc.interfaces;

import org.bukkit.entity.Entity;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.npc.PlayerAnimation;

public interface Animatable {
    default void playAnimation(PlayerAnimation anitmation) {
        Starving.NMS.sendAnimation((Entity) this, anitmation.ordinal());
    }

    default void swingArm() {
        this.playAnimation(PlayerAnimation.SWING_ARM);
    }
}
