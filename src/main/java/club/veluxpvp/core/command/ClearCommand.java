package club.veluxpvp.core.command;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class ClearCommand {

	@Command(name = "clear", aliases = {"clearinventory", "ci"}, permission = "core.command.clear")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 1) {
			if(!sender.hasPermission("core.command.clear.others")) {
				sender.sendMessage(ChatUtil.NO_PERMISSION());
				return;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[0] + "\" not found!"));
				return;
			}
			
			target.getInventory().clear();
			target.getInventory().setArmorContents(null);
			target.updateInventory();
			
			target.sendMessage(ChatUtil.TRANSLATE("Your inventory has been &ccleared&f!"));
			sender.sendMessage(ChatUtil.TRANSLATE("&b" + target.getName() + "&f's inventory has been &ccleared&f!"));
		} else {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /clear <player>"));
				return;
			}
			
			Player player = (Player) sender;
			
			player.getInventory().clear();
			player.getInventory().setArmorContents(null);
			player.updateInventory();
			
			player.sendMessage(ChatUtil.TRANSLATE("Your inventory has been &ccleared&f!"));
		}
	}
}
