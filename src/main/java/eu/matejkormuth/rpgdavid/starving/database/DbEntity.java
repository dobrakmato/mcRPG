package eu.matejkormuth.rpgdavid.starving.database;

import java.util.ArrayList;
import java.util.List;

public abstract class DbEntity {
    private final static List<DbEntity> instances = new ArrayList<>();
    private final static List<DbEntity> dirty = new ArrayList<>();
    
    protected static String table = null;
    
    public void loadAll() {
        
    }
    
    public synchronized void commit() {
        // Query builder...
        
        for(DbEntity dirtyEntity : dirty) {
            
        }
        
        dirty.clear();
    }
    
    public synchronized void setDirty() {
        dirty.add(this);
    }
}
