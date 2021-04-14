package club.veluxpvp.core.command.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class ChatDelayCommand extends ChatCommand {

	@Command(name = "chat.delay", permission = "core.command.chatdelay")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 1) {
			int delay = 0;
			
			try {
				delay = Integer.valueOf(args[0]);
				
				if(delay < 0) {
					sender.sendMessage(ChatUtil.TRANSLATE("&cThe time must be positive!"));
					return;
				}
				
				Core.getInstance().getConfig().set("CHAT.DELAY", delay);
				Core.getInstance().saveConfig();
				
				if(delay == 0) {
					Bukkit.broadcastMessage(ChatUtil.TRANSLATE("&cGlobal chat is no longer delayed!"));
				} else {
					Bukkit.broadcastMessage(ChatUtil.TRANSLATE("Global chat has been &adelayed &fby &b" + sender.getName() + " &fto &b" + delay + " &fseconds!"));
				}
			} catch(NumberFormatException e) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cYou must enter a valid time!"));
			}
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /chat delay <time>"));
		}
	}
}
