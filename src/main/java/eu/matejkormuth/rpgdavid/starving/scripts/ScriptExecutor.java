package eu.matejkormuth.rpgdavid.starving.scripts;

import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import eu.matejkormuth.rpgdavid.starving.Starving;

public class ScriptExecutor {

    private final String GLOBAL_METHODS = "for(var f in u)\"function\"==typeof u[f]&&(this[f]=function(){var n=u[f];return function(){return n.apply(u,arguments)}}());";

    private ScriptEngineManager manager;
    private ScriptEngine jsEngine;

    public ScriptExecutor() {
        this.manager = new ScriptEngineManager();
        this.jsEngine = this.manager.getEngineByExtension("js");

        try {
            this.createGlobalMethods();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    private void createGlobalMethods() throws ScriptException {
        Map<String, Object> map = new HashMap<>();
        // Inject ScriptAPI object.
        map.put("u", new Object()); // TODO: ScriptAPI
        // Register global methods.
        this.jsEngine.eval(GLOBAL_METHODS, new SimpleBindings(map));
    }

    public void execute(String script) {
        try {
            this.jsEngine.eval(script);
        } catch (ScriptException e) {
            e.printStackTrace();
            Starving.getInstance().debug("Script/" + e.toString());
        }
    }
}
