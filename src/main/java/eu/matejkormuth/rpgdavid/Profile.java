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
}
