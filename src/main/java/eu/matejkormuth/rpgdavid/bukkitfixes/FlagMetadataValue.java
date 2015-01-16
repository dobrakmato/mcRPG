package eu.matejkormuth.rpgdavid.bukkitfixes;

import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import eu.matejkormuth.rpgdavid.RpgPlugin;

public class FlagMetadataValue implements MetadataValue {
    @Override
    public boolean asBoolean() {
        throw new UnsupportedOperationException("FlagMetadataValue does not have value!");
    }

    @Override
    public byte asByte() {
        throw new UnsupportedOperationException("FlagMetadataValue does not have value!");
    }

    @Override
    public double asDouble() {
        throw new UnsupportedOperationException("FlagMetadataValue does not have value!");
    }

    @Override
    public float asFloat() {
        throw new UnsupportedOperationException("FlagMetadataValue does not have value!");
    }

    @Override
    public int asInt() {
        throw new UnsupportedOperationException("FlagMetadataValue does not have value!");
    }

    @Override
    public long asLong() {
        throw new UnsupportedOperationException("FlagMetadataValue does not have value!");
    }

    @Override
    public short asShort() {
        throw new UnsupportedOperationException("FlagMetadataValue does not have value!");
    }

    @Override
    public String asString() {
        throw new UnsupportedOperationException("FlagMetadataValue does not have value!");
    }

    @Override
    public Plugin getOwningPlugin() {
        return RpgPlugin.getInstance();
    }

    @Override
    public void invalidate() {
        throw new UnsupportedOperationException("FlagMetadataValue does not have value!");
    }

    @Override
    public Object value() {
        throw new UnsupportedOperationException("FlagMetadataValue does not have value!");
    }
}
