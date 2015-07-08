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
package eu.matejkormuth.rpgdavid.starving.worldgen.affectedblocks;

import java.util.NoSuchElementException;

import org.bukkit.Location;
import org.bukkit.block.Block;

import eu.matejkormuth.rpgdavid.starving.Region;

public class RegionAffectedBlocksDef implements AffectedBlocksDefinition {

    private Region region;

    public RegionAffectedBlocksDef(Region region) {
        this.region = region;
    }

    @Override
    public Location getCenter() {
        return null;
    }

    @Override
    public boolean hasCenter() {
        return false;
    }

    @Override
    public Block[] getAffectedBlocks() {
        throw new UnsupportedOperationException("please use iterator");
    }

    @Override
    public boolean isAffected(Block block) {
        return this.region.isInside(block.getLocation()
                                         .toVector());
    }

    @Override
    public boolean isFullHeight() {
        return true;
    }

    @Override
    public RegionAffectedBlocksIterator iterator() {
        return new RegionAffectedBlocksIterator();
    }

    public HighestBlockRegionAffectedBlocksIterator highestBlocksIterator() {
        return new HighestBlockRegionAffectedBlocksIterator();
    }

    public class RegionAffectedBlocksIterator implements
            AffectedBlocksIterator {

        protected int currentX = region.getMinXFloor();
        protected int currentY = region.getMinYFloor();
        protected int currentZ = region.getMinZFloor();

        @Override
        public boolean hasNext() {
            return !(region.getMaxXFloor() == currentX
                    && region.getMaxYFloor() == currentY
                    && region.getMaxZFloor() == currentZ);
        }

        @Override
        public Block next() {
            updateCurrentBlock();
            return region
                         .getWorld()
                         .getBlockAt(currentX, currentY, currentZ);
        }

        protected void updateCurrentBlock() {
            if (currentX != region.getMaxXFloor()) {
                currentX++;
            } else {
                if (currentZ != region.getMaxZFloor()) {
                    currentZ++;
                    currentX = region.getMinXFloor();
                } else {
                    if (currentY != region.getMaxYFloor()) {
                        currentY++;
                        currentX = region.getMinXFloor();
                        currentZ = region.getMinZFloor();
                    } else {
                        throw new NoSuchElementException("Region ends at "
                                + region.getMaxVector()
                                        .toString() + " and current pos is: ["
                                + currentX + ", " + currentY + ", " + currentZ
                                + "]");
                    }
                }
            }
        }
    }

    public final class HighestBlockRegionAffectedBlocksIterator extends
            RegionAffectedBlocksIterator {
        @Override
        public Block next() {
            updateCurrentBlock();
            return region
                         .getWorld()
                         .getHighestBlockAt(currentX, currentZ);
        }

        @Override
        protected void updateCurrentBlock() {
            if (currentX != region.getMaxXFloor()) {
                currentX++;
            } else {
                if (currentZ != region.getMaxZFloor()) {
                    currentZ++;
                    currentX = region.getMinXFloor();
                } else {
                    throw new NoSuchElementException("Region ends at "
                            + region.getMaxVector()
                                    .toString() + " and current pos is: ["
                            + currentX + ", " + currentY + ", " + currentZ
                            + "]");
                }
            }
        }
    }

}
