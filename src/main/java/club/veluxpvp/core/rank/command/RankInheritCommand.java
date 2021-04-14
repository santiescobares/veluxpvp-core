package club.veluxpvp.core.rank.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RankInheritCommand extends RankCommand {

	@Command(name = "rank.inherit", permission = "core.command.rank")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 2) {
			Rank rank = rankManager.getByName(args[0]);
			Rank inheritRank = rankManager.getByName(args[1]);
			
			if(rank == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cRank \"" + args[0] + "\" not found!"));
				return;
			}
			
			if(inheritRank == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cRank \"" + args[1] + "\" not found!"));
				return;
			}
			
			if(!rank.addInheritance(inheritRank.getName())) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cThis rank is already inheriting permissions from " + inheritRank.getName() + "!"));
			} else {
				sender.sendMessage(ChatUtil.TRANSLATE("&b" + rank.getName() + " &fis &anow &finheriting permissions from &b" + inheritRank.getName() + "&f!"));
			}
			
			for(Player p : rankManager.getPlayersWithRank(rank)) {
				rankManager.updatePermissions(p);
			}
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /rank inherit <rankName> <inheritRankName>"));
		}
	}
}
