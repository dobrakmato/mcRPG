package eu.matejkormuth.rpgdavid.starving.items.itemmeta;

public interface KeyValueHandler {
	void set(String key, String value);

	default void set(String key, boolean value) {
		set(key, Boolean.toString(value));
	}

	default void set(String key, byte value) {
		set(key, Byte.toString(value));
	}

	default void set(String key, short value) {
		set(key, Short.toString(value));
	}

	default void set(String key, int value) {
		set(key, Integer.toString(value));
	}

	default void set(String key, long value) {
		set(key, Long.toString(value));
	}

	default void set(String key, float value) {
		set(key, Float.toString(value));
	}

	default void set(String key, double value) {
		set(key, Double.toString(value));
	}

	String get(String key);

	default byte getByte(String key) {
		return Byte.valueOf(get(key));
	}

	default short getShort(String key) {
		return Short.valueOf(get(key));
	}

	default int getInteger(String key) {
		return Integer.valueOf(get(key));
	}

	default long getLong(String key) {
		return Long.valueOf(get(key));
	}

	default float getFloat(String key) {
		return Float.valueOf(get(key));
	}

	default boolean getBoolean(String key) {
		return Boolean.valueOf(get(key));
	}

	default double getDouble(String key) {
		return Double.valueOf(get(key));
	}
}
