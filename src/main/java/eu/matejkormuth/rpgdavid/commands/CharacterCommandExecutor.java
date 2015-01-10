package eu.matejkormuth.rpgdavid.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.RpgPlugin;

public class CharacterCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command comamnd, String label,
			String[] args) {
		if(sender instanceof Player) {
			// TODO: Kill player.
			((Player) sender).sendMessage("You has been killed by server!");
			RpgPlugin.getInstance().getCharacterChoser().showTo((Player) sender);
		}
		return true;
	}

}
