package club.veluxpvp.core.rank.command;

import org.bukkit.command.CommandSender;

import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RankSetDefaultCommand extends RankCommand {

	@Command(name = "rank.setdefault", permission = "core.command.rank")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 2) {
			Rank rank = rankManager.getByName(args[0]);
			boolean Default = false;
			
			if(rank == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cRank \"" + args[0] + "\" not found!"));
				return;
			}
			
			if(args[1].equalsIgnoreCase("true")) {
				Default = true;
			} else if(args[1].equalsIgnoreCase("false")) {
				Default = false;
			} else {
				sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /rank setdefault <rankName> <true|false>"));
				return;
			}
			
			rank.setDefault(Default);
			sender.sendMessage(ChatUtil.TRANSLATE("&b" + rank.getName() + " &fis " + (Default ? "&anow" : "&cno longer") + " &fthe default rank for new players!"));
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /rank setdefault <rankName> <true|false>"));
		}
	}
}
