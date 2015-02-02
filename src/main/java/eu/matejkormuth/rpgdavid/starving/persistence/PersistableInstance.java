package eu.matejkormuth.rpgdavid.starving.persistence;

/**
 * Class that injects {@link Persist} annotated configuration values to
 * sub-class automatically in constructor.
 * 
 * @see Persist
 * @see PersistInjector
 * @see Persistable
 */
public abstract class PersistableInstance {
    /**
     * Id of this instance of this object.
     */
    private int persistableInstanceId;

    /**
     * Main constructor with this implementation:
     * 
     * <pre>
     * public Persistable() {
     *     PersistInjector.inject(this, instanceId);
     * }
     * </pre>
     * 
     * @param instanceId
     *            id of instance of persistable object to load
     */
    public PersistableInstance(final int instanceId) {
        // Automatically inject values from configuration.
        this.persistableInstanceId = instanceId;
        PersistInjector.inject(this, this.persistableInstanceId);
    }

    public int getPersistableInstanceId() {
        return this.persistableInstanceId;
    }

    /**
     * Re-loads annotated values from properties file on HDD.
     */
    public void reloadConfiguration() {
        PersistInjector.inject(this, this.persistableInstanceId);
    }

    /**
     * Stores annotated values in properties file on HDD.
     */
    public void saveConfiguration() {
        PersistInjector.store(this, this.persistableInstanceId);
    }
}
