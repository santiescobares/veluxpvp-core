package club.veluxpvp.core.rank.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RankAddPermCommand extends RankCommand {

	@Command(name = "rank.addperm", aliases = {"rank.addpermission"}, permission = "core.command.rank")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 2) {
			Rank rank = rankManager.getByName(args[0]);
			String permission = args[1];
			
			if(rank == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cRank \"" + args[0] + "\" not found!"));
				return;
			}
			
			if(!rank.addPermission(permission)) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cThis permission is already added!"));
			} else {
				sender.sendMessage(ChatUtil.TRANSLATE("Permission &b" + permission + " &aadded &fto &b" + rank.getName() + " &frank!"));
			}
			
			for(Player p : rankManager.getPlayersWithRank(rank)) {
				rankManager.updatePermissions(p);
			}
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /rank addperm <rankName> <permission>"));
		}
	}
}
