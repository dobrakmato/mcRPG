package eu.matejkormuth.rpgdavid.quests;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;

public abstract class AbstractQuest {
	public NPCRegistry getNPCRegistry() {
		return CitizensAPI.getNPCRegistry();
	}
}
