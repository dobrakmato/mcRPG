package eu.matejkormuth.rpgdavid.starving.items;

public class Firearm extends Item {
    private int clipSize;
    private int ammo;

    private int fireRate; // per second
    private float noiseLevel;

    public int getClipSize() {
        return this.clipSize;
    }

    public int getAmmo() {
        return this.ammo;
    }

    public int getFireRate() {
        return this.fireRate;
    }

    public float getNoiseLevel() {
        return this.noiseLevel;
    }
}
