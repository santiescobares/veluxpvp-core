package club.veluxpvp.core.rank.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RankUnInheritCommand extends RankCommand {

	@Command(name = "rank.uninherit", permission = "core.command.rank")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 2) {
			Rank rank = rankManager.getByName(args[0]);
			String inheritRank = args[1];
			
			if(rank == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cRank \"" + args[0] + "\" not found!"));
				return;
			}
			
			if(!rank.removeInheritance(inheritRank)) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cThis rank is not inheriting permissions from " + inheritRank + "!"));
			} else {
				sender.sendMessage(ChatUtil.TRANSLATE("&b" + rank.getName() + " &fis &cno longer &finheriting permissions from &b" + inheritRank + "&f!"));
			}
			
			for(Player p : rankManager.getPlayersWithRank(rank)) {
				rankManager.updatePermissions(p);
			}
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /rank uninherit <rankName> <uninheritRankName>"));
		}
	}
}
