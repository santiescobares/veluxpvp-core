package club.veluxpvp.core.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class BroadcastCommand {

	@Command(name = "broadcast", aliases = {"bc"}, permission = "core.command.broadcast")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 2) {
			boolean raw = args[0].equalsIgnoreCase("-r");
			String message = "";
			
			if(!raw) {
				for(int i = 0; i < args.length; i++) {
					message += args[i] + (i != (args.length - 1) ? " " : "");
				}
			} else {
				for(int i = 1; i < args.length; i++) {
					message += args[i] + (i != (args.length - 1) ? " " : "");
				}
			}
			
			if(!raw) {
				Bukkit.broadcastMessage(ChatUtil.TRANSLATE("&7[&cAlert&7] &f" + message));
			} else {
				Bukkit.broadcastMessage(ChatUtil.TRANSLATE(message));
			}
		} else {
			if(args.length >= 1) {
				if(args[0].equalsIgnoreCase("-r")) {
					sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /broadcast [-r] <message...>"));
					return;
				}
				
				String message = "";
				
				for(int i = 0; i < args.length; i++) {
					message += args[i] + (i != (args.length - 1) ? " " : "");
				}
				
				Bukkit.broadcastMessage(ChatUtil.TRANSLATE("&7[&cAlert&7] &f" + message));
			} else {
				sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /broadcast [-r] <message...>"));
				return;
			}
		}
	}
}
