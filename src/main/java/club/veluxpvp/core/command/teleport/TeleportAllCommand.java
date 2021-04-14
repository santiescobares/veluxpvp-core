package club.veluxpvp.core.command.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class TeleportAllCommand {

	@Command(name = "teleportall", aliases = {"tpall"}, permission = "core.command.teleportall")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 1) {
			if(sender instanceof Player && !sender.hasPermission("core.command.teleportall.others")) {
				sender.sendMessage(ChatUtil.NO_PERMISSION());
				return;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[0] + "\" not found!"));
				return;
			}
			
			for(Player all : Bukkit.getOnlinePlayers()) {
				if(all == target) {
					target.sendMessage(ChatUtil.TRANSLATE("All players have been &ateleported &fto you!"));
					continue;
				}
				
				all.teleport(target.getLocation());
				all.sendMessage(ChatUtil.TRANSLATE("All players have been &ateleported &fto &b" + target.getName() + "&f!"));
			}
		} else {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /teleportall <player>"));
				return;
			}
			
			Player player = (Player) sender;
			
			for(Player all : Bukkit.getOnlinePlayers()) {
				if(all == player) {
					player.sendMessage(ChatUtil.TRANSLATE("All players have been &ateleported &fto you!"));
					continue;
				}
				
				all.teleport(player.getLocation());
				all.sendMessage(ChatUtil.TRANSLATE("All players have been &ateleported &fto &b" + player.getName() + "&f!"));
			}
		}
	}
}
