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
package eu.matejkormuth.rpgdavid.starving;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.jar.Manifest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import eu.matejkormuth.rpgdavid.starving.tasks.RepeatingTask;

public class StatusServer extends RepeatingTask {

    HttpServer server;
    int maxPlayers;
    int currentPlayers;
    String mcVersion;
    String starvingVersionTitle;
    String starvingVersionVersion;
    String starvingVersionBuildNumber;
    String starvingVersionSCMRevision;
    Collection<? extends Player> players;

    @Override
    public void run() {
        maxPlayers = Bukkit.getMaxPlayers();
        players = Bukkit.getOnlinePlayers();
        currentPlayers = players.size();
        mcVersion = Bukkit.getVersion();
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(9063), 0);
        server.createContext("/status", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        this.findStarvingVersion();
        this.run();
        this.schedule(20L);
    }

    private void findStarvingVersion() {
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
            starvingVersionTitle = title;
            starvingVersionBuildNumber = buildnumber;
            starvingVersionSCMRevision = scmRevision;
            starvingVersionVersion = version;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        this.cancel();
        server.stop(1);
    }

    class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            JsonObject json = new JsonObject();
            json.addProperty("maxPlayers", maxPlayers);
            json.addProperty("currentPlayers", currentPlayers);
            JsonObject version = new JsonObject();
            version.addProperty("minecraft", mcVersion);
            JsonObject starvingVersion = new JsonObject();
            starvingVersion.addProperty("title", starvingVersionTitle);
            starvingVersion.addProperty("version", starvingVersionVersion);
            starvingVersion.addProperty("build", starvingVersionBuildNumber);
            starvingVersion.addProperty("revision", starvingVersionSCMRevision);
            version.add("starving", starvingVersion);
            json.add("version", version);
            JsonArray playersArray = new JsonArray();
            for (Player p : players) {
                playersArray.add(new JsonPrimitive(p.getName()));
            }
            json.add("players", playersArray);
            String response = json.toString();
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
