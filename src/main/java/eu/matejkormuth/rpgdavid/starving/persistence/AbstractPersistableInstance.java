package eu.matejkormuth.rpgdavid.starving.persistence;

/**
 * Class that injects {@link Persist} annotated configuration values to
 * sub-class automatically in constructor.
 * 
 * @see Persist
 * @see PersistInjector
 * @see AbstractPersistable
 */
public abstract class AbstractPersistableInstance implements PersistableInstance {
    /**
     * Id of this instance of this object.
     */
    private int persistableInstanceId;

    /**
     * Main constructor with this implementation:
     * 
     * <pre>
     * public AbstractPersistable() {
     *     PersistInjector.inject(this, instanceId);
     * }
     * </pre>
     * 
     * @param instanceId
     *            id of instance of persistable object to load
     */
    public AbstractPersistableInstance(final int instanceId) {
        // Automatically inject values from configuration.
        this.persistableInstanceId = instanceId;
        PersistInjector.inject(this, this.persistableInstanceId);
    }

    /* (non-Javadoc)
     * @see eu.matejkormuth.rpgdavid.starving.persistence.PersistableInstance#getPersistableInstanceId()
     */
    @Override
    public int getPersistableInstanceId() {
        return this.persistableInstanceId;
    }

    /* (non-Javadoc)
     * @see eu.matejkormuth.rpgdavid.starving.persistence.PersistableInstance#reloadConfiguration()
     */
    @Override
    public void reloadConfiguration() {
        PersistInjector.inject(this, this.persistableInstanceId);
    }

    /* (non-Javadoc)
     * @see eu.matejkormuth.rpgdavid.starving.persistence.PersistableInstance#saveConfiguration()
     */
    @Override
    public void saveConfiguration() {
        PersistInjector.store(this, this.persistableInstanceId);
    }
}
