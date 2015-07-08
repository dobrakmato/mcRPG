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
package eu.matejkormuth.rpgdavid.starving.listeners;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Manifest;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class VersionListener implements Listener {
    @EventHandler
    private void onVersionCommand(final PlayerCommandPreprocessEvent event) {
        if (event.getMessage().contains("/version")) {
            // Output self version.
            URLClassLoader cl = ((URLClassLoader) this.getClass()
                    .getClassLoader());
            try {
                URL url = cl.findResource("META-INF/MANIFEST.MF");
                Manifest manifest = new Manifest(url.openStream());
                // do stuff with it
                String title = manifest.getMainAttributes().getValue(
                        "Implementation-Title");
                String version = manifest.getMainAttributes().getValue(
                        "Implementation-Version");
                String buildnumber = manifest.getMainAttributes().getValue(
                        "Implementation-Build-Number");
                String scmRevision = manifest.getMainAttributes().getValue(
                        "Implementation-SCM-Revision");
                StringBuilder message = new StringBuilder();
                message.append(ChatColor.YELLOW).append("You are running: ")
                        .append(title).append(" ").append(version).append(" ")
                        .append(ChatColor.GREEN).append(buildnumber)
                        .append(" / ").append(scmRevision);
                event.getPlayer().sendMessage(message.toString());
            } catch (IOException e) {
                // handle
                event.getPlayer().sendMessage(ChatColor.RED + e.toString());
            }
        }
    }
}
