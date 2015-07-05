package eu.matejkormuth.rpgdavid.achievement;

public class Achievement {
    private final String name;
    private final String description;

    public Achievement(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    public String getName() {
        return name;
    }
}
