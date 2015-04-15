package eu.matejkormuth.rpgdavid.starving.cinematics;

import eu.matejkormuth.rpgdavid.starving.cinematics.v4.V4ClipPlayer;

public interface PlayerServer {

    public abstract void addClipPlayer(V4ClipPlayer player);

    public abstract void removeClipPlayer(V4ClipPlayer player);

}