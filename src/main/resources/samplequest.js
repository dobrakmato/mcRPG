/*
 * Quest 01 - Introduction to magic.
 * 
 * Player has to go find old jerk Jack. Jack will give player magic stick. Player 
 * then have to go to Alice on the other side of map. Alice will tell player 
 * how to use magic stick (she will teach player first spell).
 * 
 * Author: dobrakmato.
 */

// Create quest object by extending JavascriptQuest class.
var quest = new JavaAdapter(eu.matejkormuth.rpgdavid.quests.JavascriptQuest, {
	// Id of this quest used for internal purposes. 
	id: "01-intruction-to-magic",
	// Name of the quest.
	name : "Introduction to magic",
	// Location of quest start.
	locX : 0,
	locY : 0,
	locZ : 0,

	// Called when quest is loaded.
	prepeare : function() {
		// Spawn NPCs and prepeare everything for quest.
	}
});

// Optional: Explicitly add quest to game.
manager.addQuest(quest);