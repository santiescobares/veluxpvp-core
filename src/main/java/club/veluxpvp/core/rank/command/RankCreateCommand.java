package club.veluxpvp.core.rank.command;

import org.bukkit.command.CommandSender;

import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RankCreateCommand extends RankCommand {

	@Command(name = "rank.create", permission = "core.command.rank")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 1) {
			Rank rank = rankManager.getByName(args[0]);
			
			if(rank != null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cRank \"" + rank.getName() + "\" is already exists!"));
				return;
			}
			
			rank = new Rank(args[0]);
			rankManager.getRanks().add(rank);
			
			sender.sendMessage(ChatUtil.TRANSLATE("Rank &b" + rank.getName() + " &acreated&f!"));
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /rank create <rankName>"));
		}
	}
}
