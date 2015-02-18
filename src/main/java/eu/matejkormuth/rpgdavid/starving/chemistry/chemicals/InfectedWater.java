package eu.matejkormuth.rpgdavid.starving.chemistry.chemicals;

import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.Data;
import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.chemistry.Chemical;

public class InfectedWater extends Chemical {
    public InfectedWater() {
        super("Dirty Water");
    }

    @Override
    public void onPureConsumedBy(Player player, float amount) {
        // Probability 60% of getting sick from drinking dirty water.
        if (Starving.getInstance().getRandom().nextInt(9) > 6) {
            // TODO: Make player sick.
            Data.of(player).setInfected(true);
        }
    }
}
