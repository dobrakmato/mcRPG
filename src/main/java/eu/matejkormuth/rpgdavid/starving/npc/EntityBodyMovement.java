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
package eu.matejkormuth.rpgdavid.starving.npc;

import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityInsentient;

@NMSHooks(version = "v1_8_R1")
public class EntityBodyMovement {
    private EntityInsentient e;

    public EntityBodyMovement(EntityInsentient e) {
        this.e = e;
    }

    public void lookAt(double x, double y, double z) {
        this.e.getControllerLook().a(x, y, z, e.yaw, e.pitch);
        this.e.getControllerLook().a();
    }

    public void lookAt(Entity entity) {
        this.e.getControllerLook().a(entity.locX, entity.locY, entity.locZ,
                e.yaw, e.pitch);
        this.e.getControllerLook().a();
    }

    public void moveTo(double locX, double locY, double locZ) {
        this.e.getControllerMove().a(locX, locY, locZ, 1);
    }

    public void moveBy(double dX, double dY, double dZ) {
        this.e.getControllerMove().a(this.e.locX + dX, this.e.locY + dY,
                this.e.locZ + dZ, 1);
    }

    public void jump() {
        this.e.getControllerJump().a();
        this.e.getControllerJump().b();
    }
}
