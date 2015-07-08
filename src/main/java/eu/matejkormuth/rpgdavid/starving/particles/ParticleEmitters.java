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
package eu.matejkormuth.rpgdavid.starving.particles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

import com.darkblade12.particleeffect.ParticleEffect;
import com.darkblade12.particleeffect.ParticleEffect.OrdinaryColor;
import com.darkblade12.particleeffect.ParticleEffect.ParticleData;
import com.google.common.base.Charsets;

import eu.matejkormuth.bukkit.Worlds;
import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.tasks.RepeatingTask;

public class ParticleEmitters extends RepeatingTask {

    private static final char S = ',';
    private List<ParticleEmitter> emitters;

    public ParticleEmitters() {
        this.emitters = new ArrayList<ParticleEmitter>();
        load();
    }

    private void tick() {
        Chunk chunk;
        for (ParticleEmitter pe : emitters) {
            if (pe.getLocation().getWorld() == null) {
                pe.getLocation().setWorld(Bukkit.getWorld("Beta"));
            }
            chunk = pe.getLocation().getChunk();
            if (chunk != null && chunk.isLoaded()) {
                pe.emit();
            }
        }
    }

    @Override
    public void run() {
        this.tick();
    }

    public void add(ParticleEmitter particleEmitter) {
        this.emitters.add(particleEmitter);
    }

    public void clear() {
        this.emitters.clear();
    }

    public void save() {
        StringBuilder content = new StringBuilder();
        for (ParticleEmitter pe : emitters) {

            content.append(pe.getLocation().getX() + S);
            content.append(pe.getLocation().getY() + S);
            content.append(pe.getLocation().getZ() + S);
            content.append(pe.getLocation().getPitch() + S);
            content.append(pe.getLocation().getYaw() + S);
            content.append(pe.getLocation().getWorld().getName() + S);

            content.append(pe.getAmount() + S);
            content.append(pe.getOffsetX() + S);
            content.append(pe.getOffsetY() + S);
            content.append(pe.getOffsetZ() + S);
            content.append(pe.getSpeed() + S);
            content.append(pe.getEffect().name() + S);

            if (pe.getDirection() != null) {
                content.append("DIRECTION|");
                content.append(pe.getDirection().getX() + "|");
                content.append(pe.getDirection().getY() + "|");
                content.append(pe.getDirection().getZ());
                content.append(S);
            }

            if (pe.getData() != null) {
                content.append("DATA|");
                content.append(pe.getData().getData() + "|");
                content.append(pe.getData().getMaterial().name());
                content.append(S);
            }

            if (pe.getColor() != null) {
                content.append("COLOR|");
                content.append(pe.getColor().getValueX() + "|");
                content.append(pe.getColor().getValueY() + "|");
                content.append(pe.getColor().getValueZ());
                content.append(S);
            }
        }

        try {
            Files.write(
                    Starving.getInstance().getFile("pemmiters.dat").toPath(),
                    content.toString().getBytes(Charsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            List<String> lines = Files.readAllLines(Starving.getInstance().getFile(
                    "pemmiters.dat").toPath());
            for (String line : lines) {
                String[] parts = line.split(String.valueOf(S));

                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                double z = Double.parseDouble(parts[2]);
                float pitch = Float.parseFloat(parts[3]);
                float yaw = Float.parseFloat(parts[4]);
                World world = Worlds.by(parts[5]);

                int amount = Integer.parseInt(parts[6]);
                double ox = Double.parseDouble(parts[7]);
                double oy = Double.parseDouble(parts[8]);
                double oz = Double.parseDouble(parts[9]);
                float speed = Float.parseFloat(parts[10]);
                ParticleEffect effect = ParticleEffect.valueOf(parts[11]);
                
                ParticleEmitter emitter = new ParticleEmitter(new Location(world, x, y, z, yaw, pitch), speed, amount, effect);
                emitter.setOffsets((float) ox, (float) oy, (float) oz);

                if (parts.length > 12) {
                    for (int i = 12; i < parts.length; i++) {
                        String superData = parts[i];

                        if (superData.startsWith("DIRECTION|")) {
                            String[] superParts = superData.split("|");

                            double dx = Double.parseDouble(superParts[1]);
                            double dy = Double.parseDouble(superParts[2]);
                            double dz = Double.parseDouble(superParts[3]);

                            Vector direction = new Vector(dx, dy, dz);
                            emitter.setDirection(direction);
                            
                        } else if (superData.startsWith("DATA|")) {
                            String[] superParts = superData.split("|");

                            byte data = Byte.parseByte(superParts[1]);
                            Material material = Material.valueOf(superParts[2]);

                            ParticleData pdata = new ParticleData(material, data);
                            emitter.setData(pdata);
                            
                        } else if (superData.startsWith("COLOR|")) {
                            String[] superParts = superData.split("|");

                            double cx = Double.parseDouble(superParts[1]);
                            double cy = Double.parseDouble(superParts[2]);
                            double cz = Double.parseDouble(superParts[3]);

                            OrdinaryColor color = new OrdinaryColor(
                                    (int) (cx * 255), (int) (cy * 255),
                                    (int) (cz * 255));
                            
                            emitter.setColor(color);
                        }
                    }
                }
                
                this.add(emitter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
