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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.google.common.io.Files;

import eu.matejkormuth.rpgdavid.RpgPlugin;

public class QuestManager {
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final File questsDirectory;
    private final List<Quest> quests;

    private ScriptEngineManager scriptEngineManager;
    private ScriptEngine engine;
    private Bindings bindings;

    public QuestManager() {
        this.quests = new ArrayList<Quest>();
        this.questsDirectory = RpgPlugin.getInstance().getFile("quests");
    }

    public void loadAll() {
        this.log.info("Loading all quests...");

        for (String file : questsDirectory.list()) {
            this.prepeareOne(file);
        }

        this.log.info("Loaded and prepeared " + quests.size() + " quests!");
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

    private void loadJSEngine() {
        if (this.engine == null) {
            this.scriptEngineManager = new ScriptEngineManager();
            this.engine = this.scriptEngineManager
                    .getEngineByName("JavaScript");
            this.bindings = this.engine.createBindings();
            this.bindings.put("manager", this);
        }
    }

    private void prepeareGoovy(final String fullName) {
        this.log.severe("Groovy script files are not supported yet!");
    }

    private void prepeareJava(final String fullName) {
        this.log.severe("Java script files are not supported yet!");
    }

    private void prepeareJs(final String fullName) {
        // Initialize JS engine.
        this.loadJSEngine();
        // Evaluate script.
        try {
            this.engine.eval(new FileReader(fullName), this.bindings);
        } catch (FileNotFoundException | ScriptException e) {
            this.log.severe("Falied to load quest " + fullName);
            e.printStackTrace();
        }
    }
}
