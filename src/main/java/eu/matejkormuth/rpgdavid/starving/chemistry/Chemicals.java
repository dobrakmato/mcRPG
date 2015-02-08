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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.starving.chemistry.chemicals.Acid;
import eu.matejkormuth.rpgdavid.starving.chemistry.chemicals.Alkali;
import eu.matejkormuth.rpgdavid.starving.chemistry.chemicals.Chlorine;
import eu.matejkormuth.rpgdavid.starving.chemistry.chemicals.Ethanol;
import eu.matejkormuth.rpgdavid.starving.chemistry.chemicals.Water;

public final class Chemicals {
    private Chemicals() {
    }

    // Source basic chemicals.
    public static final Chemical WATER = new Water();
    public static final Chemical ETHANOL = new Ethanol();
    public static final Chemical ACID = new Acid();
    public static final Chemical ALKALI = new Alkali();
    public static final Chemical CHLORINE = new Chlorine();

    public static List<Chemical> all() {
        return chemicals;
    }

    private final static List<Chemical> chemicals;
    static {
        chemicals = new ArrayList<>();
        try {
            for (Field f : Chemicals.class.getDeclaredFields()) {
                if (f.getType().isAssignableFrom(Chemical.class)) {
                    chemicals.add((Chemical) f.get(null));
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // Chemical chemicals.
    public static final class Compounds {
        private Compounds() {
        }

        // Constants
        public static final CompoundRecipe VODKA = new RatioCompoundOf2(
                "Vodka", Chemicals.ETHANOL, .4F, Chemicals.WATER, .6F);

        // Methods.
        public static final List<CompoundRecipe> getAll() {
            return compounds;
        }

        // Cache list of all chemicals.
        private final static List<CompoundRecipe> compounds;
        static {
            compounds = new ArrayList<>();
            try {
                for (Field f : Compounds.class.getDeclaredFields()) {
                    if (f.getType().isAssignableFrom(CompoundRecipe.class)) {
                        compounds.add((CompoundRecipe) f.get(null));
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    // Helper classes.
    public static abstract class CompoundRecipe extends Chemical {
        public CompoundRecipe(String name) {
            super(name);
        }

        public abstract boolean isRecipeOf(ChemicalCompound compound);

        public void onPureConsumedBy(Player player) {
        }
    }

    public static final class RatioCompoundOf2 extends CompoundRecipe {
        private Chemical ch1;
        private float ratio;
        private Chemical ch2;

        public RatioCompoundOf2(String name, Chemical chemical1, float p1,
                Chemical chemical2, float p2) {
            super(name);

            Objects.requireNonNull(chemical1);
            Objects.requireNonNull(chemical2);

            if (p1 == 0 || p2 == 0) {
                throw new IllegalArgumentException(
                        "Percentages cannot be 0.0F!");
            }

            this.ch1 = chemical1;
            this.ch2 = chemical2;
            this.ratio = p1 / p2;
        }

        @Override
        public boolean isRecipeOf(ChemicalCompound compound) {
            if (compound.getChemicalsCount() == 2) {
                float r = compound.getAmount(this.ch1)
                        / compound.getAmount(this.ch2);
                return this.ratio == r;
            }
            return false;
        }
    }
}
