package eu.matejkormuth.rpgdavid.starving.items.itemmeta;

import java.util.List;

public class StdLoreHandler implements KeyValueHandler {

	private static final char SEPARATOR = ':';
	private LoreAccessor accessor;

	public StdLoreHandler(LoreAccessor loreAccessor) {
		this.accessor = loreAccessor;
	}

	@Override
	public void set(String key, String value) {
		List<String> lore = accessor.getLore();
		for (int i = 0; i < lore.size(); i++) {
			String line = lore.get(i);
			if (line.startsWith(key + SEPARATOR)) {
				lore.set(i, key + SEPARATOR + " " + value);
				accessor.setLore(lore);
				return;
			}
		}
		lore.add(key + SEPARATOR + " " + value);
		accessor.setLore(lore);
	}

	@Override
	public String get(String key) {
		for (String line : accessor.getLore()) {
			if (line.startsWith(key + SEPARATOR)) {
				return line.substring(line.indexOf(SEPARATOR) + 1).trim();
			}
		}
		return null;
	}
}
