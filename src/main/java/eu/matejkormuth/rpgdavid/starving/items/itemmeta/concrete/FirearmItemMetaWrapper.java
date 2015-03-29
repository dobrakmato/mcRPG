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
package eu.matejkormuth.rpgdavid.starving.items.itemmeta.concrete;

import org.bukkit.inventory.ItemStack;

import eu.matejkormuth.rpgdavid.starving.items.itemmeta.ItemMetaWrapper;

public class FirearmItemMetaWrapper extends ItemMetaWrapper {

    // Keys
    private static final String SCOPE_KEY = "Scope";
    private static final String FOREGRIP_KEY = "Foregrip";
    private static final String SILENCER_KEY = "Silencer";
    private static final String AMMO_KEY = "Ammo";

    public FirearmItemMetaWrapper(ItemStack stack) {
        super(stack);
    }

    // Ammo

    public int getCurrentAmmo() {
        return this.valueHandler.getInteger(AMMO_KEY);
    }

    public void setCurrentAmmo(int amount) {
        this.valueHandler.set(AMMO_KEY, amount);
    }

    // Attachments

    public boolean hasSilencer() {
        return this.valueHandler.getBoolean(SILENCER_KEY);
    }

    public void setSilencer(boolean silencer) {
        this.valueHandler.set(SILENCER_KEY, silencer);
    }

    public boolean hasForegrip() {
        return this.valueHandler.getBoolean(FOREGRIP_KEY);
    }

    public void setForegrip(boolean foregrip) {
        this.valueHandler.set(FOREGRIP_KEY, foregrip);
    }

    public boolean hasScope() {
        return this.valueHandler.getBoolean(SCOPE_KEY);
    }

    public void setScope(boolean scope) {
        this.valueHandler.set(SCOPE_KEY, scope);
    }
}
