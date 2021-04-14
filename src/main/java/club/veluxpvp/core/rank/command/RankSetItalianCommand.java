package club.veluxpvp.core.rank.command;

import org.bukkit.command.CommandSender;

import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RankSetItalianCommand extends RankCommand {

	@Command(name = "rank.setitalian", permission = "core.command.rank")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 2) {
			Rank rank = rankManager.getByName(args[0]);
			boolean italian = false;
			
			if(rank == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cRank \"" + args[0] + "\" not found!"));
				return;
			}
			
			if(args[1].equalsIgnoreCase("true")) {
				italian = true;
			} else if(args[1].equalsIgnoreCase("false")) {
				italian = false;
			} else {
				sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /rank setitalian <rankName> <true|false>"));
				return;
			}
			
			rank.setItalian(italian);
			sender.sendMessage(ChatUtil.TRANSLATE("&b" + rank.getName() + "&f's italian style " + (italian ? "&aenabled" : "&cdisabled") + "&f!"));
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /rank setitalian <rankName> <true|false>"));
		}
	}
}
