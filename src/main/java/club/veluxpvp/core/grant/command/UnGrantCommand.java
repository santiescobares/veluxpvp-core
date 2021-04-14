package club.veluxpvp.core.grant.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.grant.Grant;
import club.veluxpvp.core.grant.GrantManager;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class UnGrantCommand {

	@SuppressWarnings("deprecation")
	@Command(name = "ungrant", consoleOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		GrantManager grantManager = Core.getInstance().getGrantManager();
		
		if(args.length >= 2) {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			String reason = "";
			
			for(int i = 1; i < args.length; i++) {
				reason += args[i] + (i != (args.length - 1) ? " " : "");
			}
			
			Grant g = grantManager.getHighestActiveGrant(target.getUniqueId());
			
			if(g == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&c" + target.getName() + "'s doesn't have any active grant!"));
				return;
			}
			
			grantManager.ungrant(sender.getName(), g, reason);
			sender.sendMessage(ChatUtil.TRANSLATE("You have &cungranted &b" + target.getName() + "&f!"));
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /ungrant <player> <reason...>"));
		}
	}
}
