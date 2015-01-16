package eu.matejkormuth.rpgdavid;

import eu.matejkormuth.rpgdavid.quests.concrete.TestQuest;

public class Debug {
    public static void onEnable() {
        RpgPlugin.getInstance().getQuestManager().addQuest(new TestQuest());
    }
    
    public static void onDisable() {
        
    }
}
