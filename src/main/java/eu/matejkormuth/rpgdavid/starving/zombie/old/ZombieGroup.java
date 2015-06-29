package eu.matejkormuth.rpgdavid.starving.zombie.old;

import java.util.HashSet;
import java.util.Set;

public class ZombieGroup {
    private final Set<Zombie> members;
    private Zombie leader;

    public ZombieGroup() {
        members = new HashSet<>(10);
    }

    public void addMember(Zombie zombie) {
        this.members.add(zombie);

        if (this.leader != null && this.leader.getFollowTarget() != null) {
            zombie.setFollowTarget(this.leader.getFollowTarget());
        }
    }

    public void removeMember(Zombie zombie) {
        this.members.remove(zombie);
    }

    public void setLeader(Zombie zombie) {
        this.leader = zombie;
    }

    public void disposeGroup() {
        this.members.clear();
        this.leader = null;
    }

    public void update() {
        // Use leader's position to move others.
        for (Zombie z : this.members) {
            z.setFollowTarget(this.leader.getFollowTarget());
        }
    }
}
