package club.veluxpvp.core.rank.command;

import org.bukkit.command.CommandSender;

import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RankListCommand extends RankCommand {

	@Command(name = "rank.list", permission = "core.command.rank")
	public void execute(CommandArgs cmd) {
		CommandSender sender = cmd.getSender();
		
		sender.sendMessage(ChatUtil.SHORTER_LINE());
		sender.sendMessage(ChatUtil.TRANSLATE("&b&lRanks List"));
		sender.sendMessage(ChatUtil.SHORTER_LINE());
		
		if(rankManager.getRanks().size() > 0) {
			for(Rank r : rankManager.getSortedRanks()) {
				sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f" + r.color() + r.getName() + " &7- &bPriority: " + r.getPriority()));
			}
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE(" &7* &cThere are no created ranks yet!"));
		}
		
		sender.sendMessage(ChatUtil.SHORTER_LINE());
	}
}
