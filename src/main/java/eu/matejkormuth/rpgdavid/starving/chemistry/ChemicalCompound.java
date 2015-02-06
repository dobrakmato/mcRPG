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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ChemicalCompound {
    private Map<Chemical, MutableFloat> contents;

    public ChemicalCompound() {
        contents = new HashMap<Chemical, MutableFloat>();
    }

    public Reaction add(Chemical chemical, float amount) {
        if (this.contents.containsKey(chemical)) {
            this.contents.get(chemical).add(amount);
        } else {
            this.contents.put(chemical, new MutableFloat(amount));
        }
        return chemical.reactionWith(this);
    }

    public boolean contains(Chemical chemical) {
        return this.contents.containsKey(chemical);
    }

    public boolean containsAtLeast(Chemical chemical, float amount) {
        if (this.contents.containsKey(chemical)) {
            return this.contents.get(chemical).getValue() >= amount;
        }
        return false;
    }

    public boolean containsOnly(Chemical chemical) {
        return this.contents.size() == 1 && this.contents.containsKey(chemical);
    }

    public float getAmount(Chemical chemical) {
        if (!this.contents.containsKey(chemical)) {
            return 0f;
        }
        return this.contents.get(chemical).getValue();
    }

    public Collection<Chemical> getChemicals() {
        return this.contents.keySet();
    }

    public Map<Chemical, MutableFloat> getContents() {
        return this.contents;
    }

    public static final class MutableFloat {
        private float value;

        public MutableFloat(final float value) {
            this.value = value;
        }

        public void add(float value) {
            this.value += value;
        }

        public void subtract(float value) {
            this.value -= value;
        }

        public void multiply(float value) {
            this.value *= value;
        }

        public void divide(float value) {
            this.value /= value;
        }

        public void setValue(float value) {
            this.value = value;
        }

        public float getValue() {
            return this.value;
        }
    }
}
