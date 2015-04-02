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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

@SerializableAs("Region")
public class Region implements ConfigurationSerializable {
    static {
        ConfigurationSerialization.registerClass(Region.class);
    }

    private Vector minVector;
    private Vector maxVector;
    private World world;
    private Random random;

    public Region(Map<String, Object> serialized) {
        this.minVector = (Vector) serialized.get("minVector");
        this.maxVector = (Vector) serialized.get("maxVector");
        this.world = Bukkit.getWorld(String.valueOf(serialized.get("world")));
        this.random = new Random();
    }

    public Region(Location center, int size) {
        this.minVector = center.toVector()
                               .subtract(
                                       new Vector(size, size, size));
        this.maxVector = center.toVector()
                               .subtract(
                                       new Vector(size, size, size));
        this.world = center.getWorld();
        this.random = new Random();
    }

    private double randX() {
        return this.random.nextDouble()
                * (this.maxVector.getX() - this.minVector.getX())
                + this.minVector.getX();
    }

    private double randY() {
        return this.random.nextDouble()
                * (this.maxVector.getY() - this.minVector.getY())
                + this.minVector.getY();
    }

    private double randZ() {
        return this.random.nextDouble()
                * (this.maxVector.getZ() - this.minVector.getZ())
                + this.minVector.getZ();
    }

    public Region(Vector minVector, Vector maxVector, World world) {
        this.minVector = new Vector(
                Math.min(minVector.getX(), minVector.getX()), Math.min(
                        minVector.getY(), minVector.getY()), Math.min(
                        minVector.getZ(), minVector.getZ()));
        this.maxVector = new Vector(
                Math.max(minVector.getX(), minVector.getX()), Math.max(
                        minVector.getY(), minVector.getY()), Math.max(
                        minVector.getZ(), minVector.getZ()));
        this.world = world;
        this.random = new Random();
    }

    public World getWorld() {
        return world;
    }

    public Location getRandomLocation() {
        return new Vector(this.randX(), this.randY(), this.randZ())
                                                                   .toLocation(this.world);
    }

    public boolean isInside(Vector vector) {
        return (this.minVector.getX() < vector.getX() && vector.getX() < this.maxVector
                                                                                       .getX())
                && (this.minVector.getY() < vector.getY() && vector.getY() < this.maxVector
                                                                                           .getY())
                && (this.minVector.getZ() < vector.getZ() && vector.getZ() < this.maxVector
                                                                                           .getZ());
    }

    public void forEachBlock(BlockFunction function) {
        int maxX = this.maxVector.getBlockX();
        int maxY = this.maxVector.getBlockY();
        int maxZ = this.maxVector.getBlockZ();
        for (int x = this.minVector.getBlockX(); x <= maxX; x++) {
            for (int y = this.minVector.getBlockY(); y <= maxY; y++) {
                for (int z = this.minVector.getBlockZ(); z <= maxZ; z++) {
                    function.block(this.world.getBlockAt(x, y, z));
                }
            }
        }
    }

    public double getMaxX() {
        return this.maxVector.getX();
    }

    public double getMaxY() {
        return this.maxVector.getY();
    }

    public double getMaxZ() {
        return this.maxVector.getZ();
    }

    public double getMinX() {
        return this.minVector.getX();
    }

    public double getMinY() {
        return this.minVector.getY();
    }

    public double getMinZ() {
        return this.minVector.getZ();
    }

    public int getMaxXFloor() {
        return (int) Math.floor(this.maxVector.getX());
    }

    public int getMaxYFloor() {
        return (int) Math.floor(this.maxVector.getY());
    }

    public int getMaxZFloor() {
        return (int) Math.floor(this.maxVector.getZ());
    }

    public int getMinXFloor() {
        return (int) Math.floor(this.minVector.getX());
    }

    public int getMinYFloor() {
        return (int) Math.floor(this.minVector.getY());
    }

    public int getMinZFloor() {
        return (int) Math.floor(this.minVector.getZ());
    }

    public void forEachEntity(EntityFunction function) {
        // Get chunks.
        throw new NotImplementedException();
    }

    public static interface BlockFunction {
        void block(Block block);
    }

    public static interface EntityFunction {
        void entity(Entity entity);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new HashMap<String, Object>();
        serialized.put("minVector", this.minVector);
        serialized.put("maxVector", this.maxVector);
        serialized.put("world", this.world.getName());
        return serialized;
    }

    public Vector getMaxVector() {
        return this.maxVector.clone();
    }
}
