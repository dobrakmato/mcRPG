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
package eu.matejkormuth.rpgdavid.starving;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class StarvingItemStack extends org.bukkit.inventory.ItemStack {
    private Object object;

    protected StarvingItemStack(Object data) {
        this.object = data;
    }
    
    @Override
    public StarvingItemStack clone() {
        try {
            StarvingItemStack itemStack = (StarvingItemStack) super.clone();

            if (this.getItemMeta() != null) {
                itemStack.setItemMeta(this.getItemMeta().clone());
            }

            if (this.getData() != null) {
                itemStack.setData(this.getData().clone());
            }
            
            itemStack.object = this.object;
            return itemStack;
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    @Deprecated
    public StarvingItemStack(int type, Object data) {
        super(type, 1);
        this.object = data;
    }

    public StarvingItemStack(Material type, Object data) {
        super(type, 1);
        this.object = data;
    }

    @Deprecated
    public StarvingItemStack(int type, int amount, Object data) {
        super(type, amount, (short) 0);
        this.object = data;
    }

    @Deprecated
    public StarvingItemStack(Material type, int amount, Object data) {
        super(type.getId(), amount);
        this.object = data;
    }
    
    public Object getObject() {
        return this.object;
    }
    
    public void onInteract(final PlayerInteractEvent event) {
    }
}
