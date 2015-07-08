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
package eu.matejkormuth.rpgdavid.starving.worldgen.filters;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;

import eu.matejkormuth.rpgdavid.starving.worldgen.affectedblocks.AffectedBlocksDefinition;
import eu.matejkormuth.rpgdavid.starving.worldgen.filters.base.Filter;
import eu.matejkormuth.rpgdavid.starving.worldgen.filters.base.FilterProperties;
import eu.matejkormuth.rpgdavid.starving.worldgen.filters.base.FilterProperty;

/**
 * <p>
 * Generates grass on top of grass blocks with specified proeprties (eg. whether
 * to generate flowers).
 * </p>
 * <p>
 * Properties:
 * <ul>
 * <li>cover: 0.00 (0%) to 1.00 (100%)</li>
 * <li>longGrass: false / true</li>
 * <li>flowers: false / true</li>
 * <li>grass type: fern / grass / dead bush / all
 * <li>clear existing grass: false / true</li>
 * </ul>
 * </p>
 * 
 * @author Matej Kormuth
 */
public class GrassFilter implements Filter {

    private static final FilterProperties PROPS = new FilterProperties();
    public static final String PROPERTY_COVER = "COVER";
    public static final String PROPERTY_LONGGRASS = "LONGGRASS";
    public static final String PROPERTY_FLOWERS = "FLOWERS";
    public static final String PROPERTY_CLEAR_EXISTING_GRASS = "CLEAREXISTING";
    private static final String NAME = "GrassFilter";
    static {
        PROPS.set(new FilterProperty(PROPERTY_COVER, 0.5f));
        PROPS.set(new FilterProperty(PROPERTY_LONGGRASS, false));
        PROPS.set(new FilterProperty(PROPERTY_FLOWERS, false));
        PROPS.set(new FilterProperty(PROPERTY_CLEAR_EXISTING_GRASS, true));
        PROPS.lock();
    }

    @Override
    public FilterProperties getDefaultProperties() {
        return PROPS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void apply(AffectedBlocksDefinition definition,
            FilterProperties properties) {
        float chance = properties.getProperty(PROPERTY_COVER).asFloat();
        boolean longgrass = properties.getProperty(PROPERTY_LONGGRASS).asBoolean();
        boolean clearExistingGrass = properties.getProperty(
                PROPERTY_CLEAR_EXISTING_GRASS).asBoolean();
        Random r = new Random();
        for (Block b : definition) {
            // clear
            if (clearExistingGrass
                    && (b.getType() == Material.LONG_GRASS || b.getType() == Material.DOUBLE_PLANT)) {
                b.setType(Material.AIR);
            }

            if (r.nextFloat() < chance) {
                if (longgrass && r.nextBoolean()) {
                    b.setTypeIdAndData(Material.DOUBLE_PLANT.getId(), (byte) 2,
                            false);
                    b.getRelative(0, 1, 0).setTypeIdAndData(
                            Material.DOUBLE_PLANT.getId(), (byte) 10, false);
                } else {
                    b.setType(Material.LONG_GRASS);
                    if (r.nextBoolean()) {
                        b.setData((byte) 2);
                    } else {
                        b.setData((byte) 1);
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isPropertySupported(String property) {
        return property.equalsIgnoreCase(PROPERTY_COVER)
                || property.equalsIgnoreCase(PROPERTY_CLEAR_EXISTING_GRASS)
                || property.equalsIgnoreCase(PROPERTY_FLOWERS)
                || property.equalsIgnoreCase(PROPERTY_LONGGRASS);
    }

}
