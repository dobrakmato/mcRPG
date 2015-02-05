package eu.matejkormuth.rpgdavid.starving.items;

import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.chemistry.ChemicalCompound;

public abstract class ChemicalItem extends ConsumableItem {
    private ChemicalCompound contents = new ChemicalCompound();

    public ChemicalCompound getContents() {
        return this.contents;
    }

    @Override
    public void onConsume(Player player) {
        // TODO: Need discussion about effects.
        
        // Post event to sub class.
        this.onConsume0(player);
    }
    
    public abstract void onConsume0(Player player);
}