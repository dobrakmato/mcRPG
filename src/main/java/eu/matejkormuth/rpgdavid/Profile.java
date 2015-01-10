package eu.matejkormuth.rpgdavid;

import java.util.UUID;

public class Profile {
	private UUID uuid;
	private Character character;
	
	
	
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

	public boolean hasCharacter() {
		return this.character != null;
	}

	// -------------------- VAMPIRE METHODS
	
	public void vampire_SetLastBitten() {
		this.vampire_lastBitten = System.currentTimeMillis();
	}
	
	public boolean vampire_CanBite() {
		return System.currentTimeMillis()  > this.vampire_lastBitten + 1000 * 60;
	}
}
