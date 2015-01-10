package eu.matejkormuth.rpgdavid.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerHeadCommandExecutor implements CommandExecutor {

	public boolean onCommand(CommandSender sender,
			Command command, String label,
			String[] args) {
		if(command.getName().equals("playerhead")) {
			if(sender.hasPermission("playerhead")) {
				if(args.length == 1) {
					if(args[0].isEmpty()) {
						sender.sendMessage(ChatColor.RED + "Name can't be empty!");
					}
					
					ItemStack head = new ItemStack(Material.SKULL_ITEM);
					SkullMeta sm = (SkullMeta)head.getItemMeta();
					sm.setOwner(args[0]);
					head.setItemMeta(sm);
					
					sender.sendMessage(ChatColor.GREEN + "Giving you one skull of " + args[0]);
					
					if(sender instanceof Player) {
						((Player)sender).getInventory().addItem(head);
					}
					
				} else {
					sender.sendMessage(ChatColor.RED + "Invalid usage! Usage: /playerhead <name>");
				}
				return true;
			}
		} 
		return false;
	}

}
