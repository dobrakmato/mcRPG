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
package eu.matejkormuth.rpgdavid.quests;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import eu.matejkormuth.rpgdavid.RpgPlugin;

public class QuestManager {
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final File questsDirectory;
    private final List<Quest> quests;
    private boolean loadedAll = false;

    public QuestManager() {
        this.quests = new ArrayList<Quest>();
        this.questsDirectory = RpgPlugin.getInstance().getFile("quests");
    }

    public void shutdown() {
        // Release all objects from lists.
        this.quests.clear();
    }

    public Quest getQuestById(final String id) {
        for (Quest q : this.quests) {
            if (q.getId().equals(id)) {
                return q;
            }
        }
        return null;
    }

    public Quest getQuestByName(final String name) {
        for (Quest q : this.quests) {
            if (q.getName().equals(name)) {
                return q;
            }
        }
        return null;
    }

    public void loadAll() {
        if (this.loadedAll) {
            throw new RuntimeException("Reloading quests is not yet supported!");
        }

        this.log.info("Loading all quests...");

        for (String file : questsDirectory.list()) {
            this.prepeareOne(file);
        }

        this.log.info("Loaded and prepeared " + quests.size() + " quests!");
        this.loadedAll = true;
    }

    public Collection<Quest> getQuests() {
        return this.quests;
    }

    public void addQuest(final Quest quest) {
        this.quests.add(quest);
    }

    private void prepeareOne(final String fullName) {
        switch (Files.getFileExtension(fullName)) {
        case "js":
            this.prepeareJs(fullName);
            break;
        case "java":
            this.prepeareJava(fullName);
            break;
        case "groovy":
            this.prepeareGoovy(fullName);
            break;
        default:
            this.log.severe("File " + fullName
                    + " is not supported quest script file!");
            break;
        }
    }

    private void prepeareGoovy(final String fullName) {
        this.log.severe("Groovy script files are not supported yet!");
    }

    private void prepeareJava(final String fullName) {
        this.log.severe("Java script files are not supported yet!");
    }

    private void prepeareJs(final String fullName) {
        // Initialize JS engine if needed.
        Context ctx = Context.enter();
        Scriptable scope = ctx.initStandardObjects();
        // Evaluate script.
        try {
            String script = Files.toString(new File(fullName), Charsets.UTF_8);

            // Try to fix missing addQuest call.
            if (!script.contains("manager.addQuest")
                    && script.contains("var quest")) {
                script += "\n\nmanager.addQuest(quest);";
            }

            ctx.evaluateString(scope, script, fullName, 1, null);

        } catch (Exception e) {
            this.log.severe("Falied to load file " + fullName);
            e.printStackTrace();
        } finally {
            Context.exit();
        }
    }
}
