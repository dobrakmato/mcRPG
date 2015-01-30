/*
 *  mcRPG is a open source rpg bukkit/spigot plugin.
 *  Copyright (C) 2015 Matej Kormuth 
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
package eu.matejkormuth.rpgdavid;

import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class NoWaterWalkUpdater implements Runnable {
    private Map<Player, Location> locations;

    public NoWaterWalkUpdater() {
        this.locations = new WeakHashMap<Player, Location>();
    }

    @Override
    public void run() {
        Location currentLoc = null;
        for (Player p : Bukkit.getOnlinePlayers()) {
            currentLoc = p.getLocation();
            if (this.isWater(currentLoc.getBlock().getType())) {
                if (this.locations.containsKey(p)) {
                    p.teleport(this.locations.get(p));
                } else {
                    // Player in water and we dont have last location!
                    // Perform search for nearest non-water block.
                    Location solidBlock = this.findNearsetSolid(currentLoc);
                    if (solidBlock != null) {
                        p.teleport(solidBlock);
                        this.locations.put(p, solidBlock);
                    } else {
                        // Can't find solid block in specified time. Giving
                        // up...
                        System.out
                                .println("Can't find block using BlockWalker!");
                    }
                }
            } else {
                this.locations.put(p, currentLoc);
            }
        }
    }

    private Location findNearsetSolid(Location currentLoc) {
        return new BlockWalker(currentLoc).findSolid();
    }

    private boolean isWater(Material type) {
        return type == Material.WATER || type == Material.STATIONARY_WATER;
    }

    public class BlockWalker {
        private Location loc;
        private BlockFace direction;

        public BlockWalker(Location loc) {
            this.loc = loc;
        }

        public Location findSolid() {
            for (int length = 1; length < 9; length++) {
                for (int i = 0; i < length; i++) {
                    this.walk();
                    if (this.loc.getBlock().getType().isSolid()) {
                        return this.loc;
                    }
                }
                this.turnRight();
            }
            return null;
        }

        public void walk() {
            this.loc.add(direction.getModX(), direction.getModY(),
                    direction.getModZ());
        }

        public void walk(int length) {
            this.loc.add(direction.getModX() * length, direction.getModY()
                    * length, direction.getModZ() * length);
        }

        public void turnRight() {
            switch (this.direction) {
            case EAST:
                this.turn(BlockFace.SOUTH);
                break;
            case WEST:
                this.turn(BlockFace.NORTH);
                break;
            case SOUTH:
                this.turn(BlockFace.WEST);
                break;
            case NORTH:
                this.turn(BlockFace.EAST);
                break;
            default:
                throw new UnsupportedOperationException();
            }
        }

        public void turn(BlockFace direction) {
            this.direction = direction;
        }
    }
}
