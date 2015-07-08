package eu.matejkormuth.rpgdavid.starving.cinematics.v4;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import eu.matejkormuth.rpgdavid.starving.cinematics.FrameAction;
import eu.matejkormuth.rpgdavid.starving.cinematics.v4.frameactions.AbstractAction;
import eu.matejkormuth.rpgdavid.starving.cinematics.v4.frameactions.V4CameraLocationAction;

public class V4ActionRegistry {

    private static Map<Byte, Constructor<? extends AbstractAction>> actions;
    private static Map<Class<? extends AbstractAction>, Byte> classes;

    public V4ActionRegistry() {
        actions = new HashMap<Byte, Constructor<? extends AbstractAction>>();
        classes = new HashMap<Class<? extends AbstractAction>, Byte>();
        register();
    }

    private static void register() {
        // Register all V4 frame actions.
        add(0, V4CameraLocationAction.class);
    }

    public static void add(int type, Class<? extends AbstractAction> action) {
        try {
            Constructor<? extends AbstractAction> ctr = action.getConstructor();
            actions.put((byte) type, ctr);
            classes.put(action, (byte) type);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static AbstractAction createAction(byte type) {
        Constructor<? extends AbstractAction> ctr = actions.get(type);
        if (ctr != null) {
            try {
                AbstractAction aa = ctr.newInstance();
                return aa;
            } catch (InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Unsupported action type.");
        }
    }

    public static byte getType(FrameAction fa) {
        return classes.get(fa.getClass());
    }
}
