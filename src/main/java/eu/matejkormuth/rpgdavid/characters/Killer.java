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
package eu.matejkormuth.rpgdavid.characters;

import org.bukkit.Color;
import org.bukkit.potion.PotionType;

import eu.matejkormuth.bukkit.Potion;
import eu.matejkormuth.bukkit.inventory.Armor;
import eu.matejkormuth.rpgdavid.Character;
import eu.matejkormuth.rpgdavid.Modifiers;
import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.items.Dagger;
import eu.matejkormuth.rpgdavid.items.GrapplingHook;

public class Killer extends Character {
    public Killer() {
        super("Vrah", RpgPlugin.getInstance().getConfig()
                .getString("translation_killer"), new Modifiers(1, 1, 1, 1.5F,
                1), Armor.createLether(Color.BLACK), new Potion(
                PotionType.INSTANT_DAMAGE, 1).splash().toItemStack(4),
                new Dagger(), new GrapplingHook());
    }
}
