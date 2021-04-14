package club.veluxpvp.core.rank.command;

import org.bukkit.command.CommandSender;

import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RankSetPrefixCommand extends RankCommand {

	@Command(name = "rank.setprefix", permission = "core.command.rank")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 2) {
			Rank rank = rankManager.getByName(args[0]);
			String prefix = "";
			
			if(rank == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cRank \"" + args[0] + "\" not found!"));
				return;
			}
			
			for(int i = 1; i < args.length; i++) {
				prefix += args[i] + (i != (args.length - 1) ? " " : "");
			}
			
			rank.setPrefix(prefix);
			sender.sendMessage(ChatUtil.TRANSLATE("&b" + rank.getName() + "&f's prefix &aset &fto &b" + prefix + "&r!"));
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /rank setprefix <rankName> <prefix>"));
		}
	}
}
