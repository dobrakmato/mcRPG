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
package eu.matejkormuth.rpgdavid.starving.chemistry;

import org.bukkit.Location;

public abstract class Reaction {
    public static final Reaction NONE = new BasicReaction(ReactionResult.NONE);

    private ReactionResult result;

    public Reaction(ReactionResult result) {
        this.result = result;
    }

    public ReactionResult getResult() {
        return this.result;
    }

    public abstract void occureAt(Location loc);

    public enum ReactionResult {
        NONE, EXPLOSION;
    }

    private static class BasicReaction extends Reaction {
        public BasicReaction(ReactionResult result) {
            super(result);
        }

        @Override
        public void occureAt(Location loc) {
        }
    }
}
