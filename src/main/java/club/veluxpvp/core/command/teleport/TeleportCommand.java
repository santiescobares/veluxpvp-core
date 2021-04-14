package club.veluxpvp.core.command.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class TeleportCommand {

	@Command(name = "teleport", aliases = {"tp"}, permission = "core.command.teleport")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 2) {
			Player player1 = Bukkit.getPlayer(args[0]);
			Player player2 = Bukkit.getPlayer(args[1]);
			
			if(sender instanceof Player && !sender.hasPermission("core.command.teleport.others")) {
				sender.sendMessage(ChatUtil.NO_PERMISSION());
				return;
			}
			
			if(player1 == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[0] + "\" not found!"));
				return;
			}
			
			if(player2 == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[1] + "\" not found!"));
				return;
			}
			
			player1.teleport(player2.getLocation());
			player1.sendMessage(ChatUtil.TRANSLATE("You have been &ateleported &fto &b" + player2.getName() + "&f!"));
			sender.sendMessage(ChatUtil.TRANSLATE("&b" + player1.getName() + " &fhas been &ateleported &fto &b" + player2.getName() + "&f!"));
		} else if(args.length >= 1) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /teleport <player> <to-player>"));
				return;
			}
			
			Player player = (Player) sender;
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[0] + "\" not found!"));
				return;
			}
			
			player.teleport(target.getLocation());
			player.sendMessage(ChatUtil.TRANSLATE("You have been &ateleported &fto &b" + target.getName() + "&f!"));
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /teleport <player> " + (sender instanceof Player ? "[to-player]" : "<to-player>")));
		}
	}
}
