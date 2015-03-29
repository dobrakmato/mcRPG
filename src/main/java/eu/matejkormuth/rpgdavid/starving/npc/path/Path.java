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
package eu.matejkormuth.rpgdavid.starving.npc.path;

import java.util.Iterator;

public class Path implements Iterable<PathNode> {
    private PathNode[] nodes;

    public Path(PathNode[] nodes) {
        this.nodes = nodes;
    }

    @Override
    public Iterator<PathNode> iterator() {
        return new Itr();
    }

    public PathNode getNode(int index) {
        return this.nodes[index];
    }

    public int getNodeCount() {
        return this.nodes.length;
    }

    private class Itr implements Iterator<PathNode> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < nodes.length && nodes[index] != null;
        }

        @Override
        public PathNode next() {
            return nodes[index++];
        }

        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }
}
