package eu.matejkormuth.rpgdavid.starving;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

import eu.matejkormuth.rpgdavid.starving.annotations.Persist;

public class PersistInjector {
    private static String confPath;

    public static void setConfiguration(String confPath) {
        PersistInjector.confPath = confPath;
    }

    public static void store(final Object object) {
        store(object, object.getClass().getName());
    }

    public static void store(final Object object, final int instancePersistId) {
        store(object, object.getClass().getName() + "-" + instancePersistId);
    }

    public static void store(final Object object, final String fileName) {
        store(object, new File(confPath + "/" + fileName + ".properties"));
    }

    public static void store(final Object object, final File file) {
        Properties properties = new Properties();
        store(object, properties);
        try {
            // Open file.
            FileOutputStream fos = new FileOutputStream(file);
            properties.store(fos,
                    "Persist file of " + object.getClass().getCanonicalName()
                            + " / " + object.hashCode());
            // Close file.
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void store(final Object object, final Properties properties) {
        for (Field field : object.getClass().getDeclaredFields()) {
            // If is field persist.
            if (field.isAnnotationPresent(Persist.class)) {
                // Make annotated fields accessible.
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Persist persist = field.getAnnotation(Persist.class);
                try {
                    // Put value to properties object.
                    properties.put(persist.key(),
                            String.valueOf(field.get(object)));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void inject(final Object object) {
        inject(object, object.getClass().getName());
    }

    public static void inject(final Object object, final int instancePersistId) {
        inject(object, object.getClass().getName() + "-" + instancePersistId);
    }

    public static void inject(final Object object, final String fileName) {
        inject(object, new File(confPath + "/" + fileName + ".properties"));
    }

    public static void inject(final Object object, final File file) {
        try {
            // Open file.
            FileInputStream fis = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fis);
            fis.close();
            // Inject using loaded properties file.
            inject(object, properties);
        } catch (IOException e) {
            // File does not exists!
            inject(object, new Properties());
        }
    }

    public static void inject(final Object object, final Properties properties) {
        for (Field field : object.getClass().getDeclaredFields()) {
            // If is field persist.
            if (field.isAnnotationPresent(Persist.class)) {
                // Make annotated fields accessible.
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Persist persist = field.getAnnotation(Persist.class);
                if (properties.containsKey(persist.key())) {
                    // Inject value from properties.
                    try {
                        setField(field, object,
                                properties.getProperty(persist.key()));
                    } catch (RuntimeException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    private static void setField(final Field field, final Object object,
            final String value) {
        // Get type of field.
        Class<?> type = field.getType();
        try {
            // Set value by type.
            if (type == int.class || type == Integer.class) {
                field.set(object, Integer.valueOf(value));
            } else if (type == long.class || type == Long.class) {
                field.set(object, Long.valueOf(value));
            } else if (type == short.class || type == Short.class) {
                field.set(object, Short.valueOf(value));
            } else if (type == byte.class || type == Byte.class) {
                field.set(object, Byte.valueOf(value));
            } else if (type == boolean.class || type == Boolean.class) {
                field.set(object, Boolean.valueOf(value));
            } else if (type == String.class) {
                field.set(object, String.valueOf(value));
            } else if (type == float.class || type == Float.class) {
                field.set(object, Float.valueOf(value));
            } else if (type == double.class || type == Double.class) {
                field.set(object, Double.valueOf(value));
            } else if (Enum.class.isAssignableFrom(type)) {
                Object[] consts = type.getEnumConstants();
                // Try to find right enum constant.
                for (Object _const : consts) {
                    if (_const.toString().equals(value)) {
                        field.set(object, _const);
                        // End method execution.
                        return;
                    }
                }
                throw new RuntimeException(
                        "Can't inject enum property of enum type "
                                + type.getName() + " for value '" + value + "'");
            } else {
                throw new RuntimeException("Can't inject property of type "
                        + type.getName());
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
