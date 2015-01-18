package eu.matejkormuth.rpgdavid.spells.impl;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import eu.matejkormuth.rpgdavid.spells.Spell;

public class HealingSpell extends Spell {
    public HealingSpell() {
        super(Sound.LEVEL_UP, "Healing spell", 300);
    }

    @Override
    protected void cast0(Player invoker, Location location, Vector velocity) {
        invoker.setHealth(0.80 * invoker.getMaxHealth());
        invoker.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,
                20 * 10, 1));
    }
}
