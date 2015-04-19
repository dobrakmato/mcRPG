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
package eu.matejkormuth.rpgdavid.starving.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_8_R2.CraftServer;

import eu.matejkormuth.rpgdavid.starving.Starving;
import eu.matejkormuth.rpgdavid.starving.annotations.NMSHooks;

/**
 * Class that provides dynamic access to registered commands.
 */
public class CommandManager {

    private CommandMap map;
    private String fallbackPrefix;

    public CommandManager() {
        this.map = aquireCommandMap();
        this.fallbackPrefix = Starving.getInstance().getPlugin().getName();
    }

    @NMSHooks(version = "v1_8_R2")
    private static SimpleCommandMap aquireCommandMap() {
        CraftServer server = (CraftServer) Bukkit.getServer();
        return server.getCommandMap();
    }

    public void register(String name, CommandExecutor executor) {
        map.register(fallbackPrefix, new DynamicCommand(name, executor));
    }

    public class DynamicCommand extends Command {

        private CommandExecutor executor;

        protected DynamicCommand(String name, CommandExecutor executor) {
            super(name);
            this.executor = executor;
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            return this.executor.onCommand(sender, this, label, args);
        }

    }
}
