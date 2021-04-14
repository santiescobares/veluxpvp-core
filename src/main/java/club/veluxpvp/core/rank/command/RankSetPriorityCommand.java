package club.veluxpvp.core.rank.command;

import org.bukkit.command.CommandSender;

import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RankSetPriorityCommand extends RankCommand {

	@Command(name = "rank.setpriority", permission = "core.command.rank")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 2) {
			Rank rank = rankManager.getByName(args[0]);
			int priority = 0;
			
			if(rank == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cRank \"" + args[0] + "\" not found!"));
				return;
			}
			
			try {
				priority = Integer.valueOf(args[1]);
				rank.setPriority(priority);
				sender.sendMessage(ChatUtil.TRANSLATE("&b" + rank.getName() + "&f's priority &aset &fto &b" + priority + "&f!"));
			} catch(NumberFormatException e) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cYou must enter a valid number!"));
			}
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /rank setpriority <rankName> <priority>"));
		}
	}
}
