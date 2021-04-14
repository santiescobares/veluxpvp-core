package club.veluxpvp.core.rank.command;

import org.bukkit.command.CommandSender;

import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RankSetSuffixCommand extends RankCommand {

	@Command(name = "rank.setsuffix", permission = "core.command.rank")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 2) {
			Rank rank = rankManager.getByName(args[0]);
			String suffix = "";
			
			if(rank == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cRank \"" + args[0] + "\" not found!"));
				return;
			}
			
			for(int i = 1; i < args.length; i++) {
				suffix += args[i] + (i != (args.length - 1) ? " " : "");
			}
			
			rank.setSuffix(suffix);
			sender.sendMessage(ChatUtil.TRANSLATE("&b" + rank.getName() + "&f's suffix &aset &fto &b" + suffix + "&r!"));
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /rank setsuffix <rankName> <suffix>"));
		}
	}
}
