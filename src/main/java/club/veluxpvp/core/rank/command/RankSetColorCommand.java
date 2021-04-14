package club.veluxpvp.core.rank.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RankSetColorCommand extends RankCommand {

	@Command(name = "rank.setcolor", permission = "core.command.rank")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 2) {
			Rank rank = rankManager.getByName(args[0]);
			ChatColor color = null;
			
			if(rank == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cRank \"" + args[0] + "\" not found!"));
				return;
			}
			
			for(int i = 0; i < ChatColor.values().length; i++) {
				ChatColor c = ChatColor.values()[i];
				if(c.name().equalsIgnoreCase(args[1])) {
					color = c;
					break;
				}
			}
			
			if(color == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cColor \"" + args[1] + "\" not found!"));
				return;
			}
			
			rank.setColor(color);
			sender.sendMessage(ChatUtil.TRANSLATE("&b" + rank.getName() + "&f's color &aset &fto &b" + color.name() + "&f!"));
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /rank setcolor <rankName> <color>"));
		}
	}
}
