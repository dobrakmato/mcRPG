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
package eu.matejkormuth.rpgdavid.starving.particles;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import com.darkblade12.particleeffect.ParticleEffect;
import com.darkblade12.particleeffect.ParticleEffect.ParticleColor;
import com.darkblade12.particleeffect.ParticleEffect.ParticleData;
import com.darkblade12.particleeffect.ParticleEffect.ParticleProperty;

public class ParticleEmitter {

    private Location location;
    private float speed;
    private int amount;
    private ParticleEffect effect;

    private ParticleColor color;

    private ParticleData data;

    private Vector direction;

    private float offsetX;
    private float offsetY;
    private float offsetZ;

    public ParticleEmitter(Location location, float speed, int amount,
            ParticleEffect effect) {
        this.location = location;
        this.speed = speed;
        this.amount = amount;
        this.effect = effect;
    }

    public Location getLocation() {
        return location;
    }

    public float getSpeed() {
        return speed;
    }

    public int getAmount() {
        return amount;
    }

    public ParticleEffect getEffect() {
        return effect;
    }

    public void emit() {
        if (this.data != null) {
            if (this.direction != null) {
                this.effect.display(data, direction, speed, this.location,
                        Double.MAX_VALUE);
            } else {
                this.effect.display(data, offsetX, offsetY, offsetZ, speed,
                        amount, this.location, Double.MAX_VALUE);
            }
        } else if (this.color != null) {
            this.effect.display(color, this.location, Double.MAX_VALUE);
        } else {
            if (this.direction != null) {
                this.effect.display(direction, speed, this.location,
                        Double.MAX_VALUE);
            } else {
                this.effect.display(offsetX, offsetY, offsetZ, speed, amount,
                        this.location, Double.MAX_VALUE);
            }
        }
    }

    public ParticleColor getColor() {
        return color;
    }

    public void setColor(ParticleColor color) {
        if (!this.isColorable()) {
            throw new UnsupportedOperationException("particle "
                    + this.effect.name() + " does not supports color.");
        }

        this.color = color;
    }

    public ParticleData getData() {
        return data;
    }

    public void setData(ParticleData data) {
        if (!this.requiresData()) {
            throw new UnsupportedOperationException("particle "
                    + this.effect.name() + " does not supports data.");
        }

        this.data = data;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        if (!this.isDirectional()) {
            throw new UnsupportedOperationException("particle "
                    + this.effect.name() + " does not supports direction.");
        }
        this.direction = direction;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public float getOffsetZ() {
        return offsetZ;
    }

    public void setOffsetZ(float offsetZ) {
        this.offsetZ = offsetZ;
    }

    public void setOffsets(float offsetX, float offsetY, float offsetZ) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isDirectional() {
        return this.effect.hasProperty(ParticleProperty.DIRECTIONAL);
    }

    public boolean isColorable() {
        return this.effect.hasProperty(ParticleProperty.COLORABLE);
    }

    public boolean requiresData() {
        return this.effect.hasProperty(ParticleProperty.REQUIRES_DATA);
    }

    public boolean requiresWater() {
        return this.effect.hasProperty(ParticleProperty.REQUIRES_WATER);
    }
}
