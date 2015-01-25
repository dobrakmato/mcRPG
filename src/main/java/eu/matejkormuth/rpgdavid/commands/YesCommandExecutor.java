package eu.matejkormuth.rpgdavid.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.matejkormuth.rpgdavid.RpgPlugin;
import eu.matejkormuth.rpgdavid.quests.Quest;

public class YesCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;

        // If has offered quest.
        if (RpgPlugin.getInstance().getQuestManager().hasOffer(player)) {
            Quest quest = RpgPlugin.getInstance().getQuestManager()
                    .removeOffer(player);
            // Start quest
            RpgPlugin.getInstance().getProfile(player)
                    .setCurrentQuestId(quest.getId());
            sender.sendMessage(ChatColor.RED + "You accepted quest!");
        } else {
            sender.sendMessage(ChatColor.RED
                    + "You don't have any offered quest!");
        }
        return true;
    }

}
