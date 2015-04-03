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

public class FieldFilter implements Filter {

    private static final FilterProperties PROPS = new FilterProperties();
    private static final String NAME = "GrassFilter";
    static {
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
        Random r = new Random();
        for (Block b : definition) {
            b.getRelative(0, -1, 0).setType(Material.SOIL);
            switch (r.nextInt(5)) {
                case 0:
                    b.setTypeIdAndData(Material.CROPS.getId(),
                            (byte) r.nextInt(7),
                            false);
                    break;
                case 1:
                    b.setTypeIdAndData(Material.PUMPKIN_STEM.getId(),
                            (byte) r.nextInt(7),
                            false);
                    break;
                case 2:
                    b.setTypeIdAndData(Material.MELON_STEM.getId(),
                            (byte) r.nextInt(7),
                            false);
                    break;
                case 3:
                    b.setTypeIdAndData(Material.POTATO.getId(),
                            (byte) r.nextInt(7),
                            false);
                    break;
                case 4:
                    b.setTypeIdAndData(Material.CARROT.getId(),
                            (byte) r.nextInt(7),
                            false);
                    break;
                case 5:
                    b.setTypeIdAndData(Material.LONG_GRASS.getId(),
                            (byte) r.nextInt(2),
                            false);
                    break;
            }
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isPropertySupported(String property) {
        return false;
    }

}
