/*
 *  Starving is a open source bukkit/spigot mmo game.
 *  Copyright (C) 2014-2015 Matej Kormuth
 *  This file is a part of Starving. <http://www.starving.eu>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package eu.matejkormuth.rpgdavid.starving.cinematics.v4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eu.matejkormuth.rpgdavid.starving.cinematics.Frame;
import eu.matejkormuth.rpgdavid.starving.cinematics.FrameAction;
import eu.matejkormuth.rpgdavid.starving.cinematics.v4.frameactions.AbstractAction;
import eu.matejkormuth.rpgdavid.starving.cinematics.v4.streams.V4InputStream;
import eu.matejkormuth.rpgdavid.starving.cinematics.v4.streams.V4OutputStream;

public class V4Frame implements Frame, V4Serializable {
    private List<FrameAction> frameActions;

    public V4Frame() {
        this.frameActions = new ArrayList<>();
    }

    @Override
    public Collection<FrameAction> getActions() {
        return this.frameActions;
    }

    @Override
    public void addAction(FrameAction action) {
        if (action instanceof AbstractAction) {
            this.frameActions.add((AbstractAction) action);
        }

        throw new RuntimeException(
                "Specified action is not compatibile with V4Frame.");
    }

    @Override
    public void writeTo(V4OutputStream os) throws IOException {
        // Write action count.
        os.writeShort(this.frameActions.size());
        // Write actions.
        for (FrameAction fa : this.frameActions) {
            // Write action type.
            os.writeByte(V4ActionRegistry.getType(fa));
            // Write action content.
            ((AbstractAction) fa).writeTo(os);
        }
    }

    @Override
    public void readFrom(V4InputStream os) throws IOException {
        // Read action count.
        short actionCount = os.readShort();
        this.frameActions = new ArrayList<FrameAction>(actionCount);
        for (int i = 0; i < actionCount; i++) {
            // Read action type.
            byte type = os.readByte();
            // Read action content.
            AbstractAction action = V4ActionRegistry.createAction(type);
            action.readFrom(os);

            this.frameActions.add(action);
        }
    }

}
