package eu.matejkormuth.rpgdavid.starving.persistence;

public interface PersistableInstance {

    public abstract int getPersistableInstanceId();

    /**
     * Re-loads annotated values from properties file on HDD.
     */
    public abstract void reloadConfiguration();

    /**
     * Stores annotated values in properties file on HDD.
     */
    public abstract void saveConfiguration();

}