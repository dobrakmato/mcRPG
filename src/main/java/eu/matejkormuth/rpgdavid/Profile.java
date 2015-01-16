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

import java.io.Serializable;
import java.util.UUID;

public class Profile implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UUID uuid;
	private Character character;
	private long xp = 0L;
	
	private long vampire_lastBitten;
	
	public Profile() {
	}
	
	protected Profile(UUID uuid, Character character) {
		this.uuid = uuid;
		this.character = character;
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public void setCharacter(Character character) {
		this.character = character;
	}
	
	public UUID getUniqueId() {
		return uuid;
	}
	
	public void setUniqueId(UUID uuid) {
		this.uuid = uuid;
	}

	public boolean hasCharacter() {
		return this.character != null;
	}
	
	public long getXp() {
		return xp;
	}
	
	public void setXp(long xp) {
		this.xp = xp;
	}

	// -------------------- VAMPIRE METHODS
	
	public void setLastBittenNow() {
		this.vampire_lastBitten = System.currentTimeMillis();
	}
	
	public boolean canBite() {
		return System.currentTimeMillis()  > this.vampire_lastBitten + 1000 * 60;
	}

	public long getLastBitten() {
		return this.vampire_lastBitten;
	}
}
