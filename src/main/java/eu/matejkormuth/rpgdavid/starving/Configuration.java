package eu.matejkormuth.rpgdavid.starving;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Configuration {
    private YamlConfiguration yaml;
    private File file;

    public Configuration(File file) {
        this.file = file;
        if (file.exists()) {
            this.yaml = YamlConfiguration.loadConfiguration(file);
        } else {
            this.yaml = new YamlConfiguration();
        }
    }

    public void addDefault(String path, Object value) {
        yaml.addDefault(path, value);
    }

    public void addDefaults(org.bukkit.configuration.Configuration defaults) {
        yaml.addDefaults(defaults);
    }

    public void addDefaults(Map<String, Object> defaults) {
        yaml.addDefaults(defaults);
    }

    public Set<String> getKeys(boolean deep) {
        return yaml.getKeys(deep);
    }

    public String getCurrentPath() {
        return yaml.getCurrentPath();
    }

    public ConfigurationSection getDefaultSection() {
        return yaml.getDefaultSection();
    }

    public Object get(String path) {
        return yaml.get(path);
    }

    public Object get(String path, Object def) {
        return yaml.get(path, def);
    }

    public ConfigurationSection createSection(String path) {
        return yaml.createSection(path);
    }

    public ConfigurationSection createSection(String path, Map<?, ?> map) {
        return yaml.createSection(path, map);
    }

    public int getInt(String path) {
        return yaml.getInt(path);
    }

    public int getInt(String path, int def) {
        return yaml.getInt(path, def);
    }

    public boolean getBoolean(String path) {
        return yaml.getBoolean(path);
    }

    public boolean getBoolean(String path, boolean def) {
        return yaml.getBoolean(path, def);
    }

    public double getDouble(String path) {
        return yaml.getDouble(path);
    }

    public double getDouble(String path, double def) {
        return yaml.getDouble(path, def);
    }

    public List<?> getList(String path) {
        return yaml.getList(path);
    }

    public List<?> getList(String path, List<?> def) {
        return yaml.getList(path, def);
    }

    public List<Integer> getIntegerList(String path) {
        return yaml.getIntegerList(path);
    }

    public List<Boolean> getBooleanList(String path) {
        return yaml.getBooleanList(path);
    }

    public List<Double> getDoubleList(String path) {
        return yaml.getDoubleList(path);
    }

    public List<Float> getFloatList(String path) {
        return yaml.getFloatList(path);
    }

    public List<Byte> getByteList(String path) {
        return yaml.getByteList(path);
    }

    public List<Character> getCharacterList(String path) {
        return yaml.getCharacterList(path);
    }

    public ItemStack getItemStack(String path) {
        return yaml.getItemStack(path);
    }

    public ItemStack getItemStack(String path, ItemStack def) {
        return yaml.getItemStack(path, def);
    }

    public Color getColor(String path) {
        return yaml.getColor(path);
    }

    public Color getColor(String path, Color def) {
        return yaml.getColor(path, def);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return yaml.getConfigurationSection(path);
    }

    public org.bukkit.configuration.Configuration getDefaults() {
        return yaml.getDefaults();
    }

    public Map<String, Object> getValues(boolean deep) {
        return yaml.getValues(deep);
    }

    public boolean isSet(String path) {
        return yaml.isSet(path);
    }

    public String getName() {
        return yaml.getName();
    }

    public org.bukkit.configuration.Configuration getRoot() {
        return yaml.getRoot();
    }

    public String getString(String path) {
        return yaml.getString(path);
    }

    public String getString(String path, String def) {
        return yaml.getString(path, def);
    }

    public boolean isString(String path) {
        return yaml.isString(path);
    }

    public boolean isInt(String path) {
        return yaml.isInt(path);
    }

    public boolean isBoolean(String path) {
        return yaml.isBoolean(path);
    }

    public boolean isDouble(String path) {
        return yaml.isDouble(path);
    }

    public long getLong(String path) {
        return yaml.getLong(path);
    }

    public long getLong(String path, long def) {
        return yaml.getLong(path, def);
    }

    public boolean isLong(String path) {
        return yaml.isLong(path);
    }

    public boolean isList(String path) {
        return yaml.isList(path);
    }

    public List<String> getStringList(String path) {
        return yaml.getStringList(path);
    }

    public List<Long> getLongList(String path) {
        return yaml.getLongList(path);
    }

    public List<Short> getShortList(String path) {
        return yaml.getShortList(path);
    }

    public List<Map<?, ?>> getMapList(String path) {
        return yaml.getMapList(path);
    }

    public Vector getVector(String path) {
        return yaml.getVector(path);
    }

    public Vector getVector(String path, Vector def) {
        return yaml.getVector(path, def);
    }

    public boolean isVector(String path) {
        return yaml.isVector(path);
    }

    public OfflinePlayer getOfflinePlayer(String path) {
        return yaml.getOfflinePlayer(path);
    }

    public OfflinePlayer getOfflinePlayer(String path, OfflinePlayer def) {
        return yaml.getOfflinePlayer(path, def);
    }

    public ConfigurationSection getParent() {
        return yaml.getParent();
    }

    public boolean isOfflinePlayer(String path) {
        return yaml.isOfflinePlayer(path);
    }

    public boolean isItemStack(String path) {
        return yaml.isItemStack(path);
    }

    public boolean isColor(String path) {
        return yaml.isColor(path);
    }

    public boolean isConfigurationSection(String path) {
        return yaml.isConfigurationSection(path);
    }

    public void setDefaults(org.bukkit.configuration.Configuration defaults) {
        yaml.setDefaults(defaults);
    }

    public boolean contains(String path) {
        return yaml.contains(path);
    }

    public YamlConfigurationOptions options() {
        return yaml.options();
    }

    public void save() throws RuntimeException {
        try {
            yaml.save(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(File file) throws IOException {
        yaml.save(file);
    }

    public void save(String file) throws IOException {
        yaml.save(file);
    }

    public String saveToString() {
        return yaml.saveToString();
    }

    public void set(String path, Object value) {
        yaml.set(path, value);
    }

    public String toString() {
        return yaml.toString();
    }

    public void loadFromString(String contents)
            throws InvalidConfigurationException {
        yaml.loadFromString(contents);
    }

    public void load(File file) throws FileNotFoundException, IOException,
            InvalidConfigurationException {
        yaml.load(file);
    }

    @Deprecated
    public void load(InputStream stream) throws IOException,
            InvalidConfigurationException {
        yaml.load(stream);
    }

    public void load(Reader reader) throws IOException,
            InvalidConfigurationException {
        yaml.load(reader);
    }

    public void load(String file) throws FileNotFoundException, IOException,
            InvalidConfigurationException {
        yaml.load(file);
    }
}
