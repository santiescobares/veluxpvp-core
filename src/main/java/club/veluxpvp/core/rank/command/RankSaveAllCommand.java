package club.veluxpvp.core.rank.command;

import org.bukkit.command.CommandSender;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RankSaveAllCommand extends RankCommand {

	@Command(name = "rank.saveall", permission = "core.command.rank")
	public void execute(CommandArgs cmd) {
		CommandSender sender = cmd.getSender();
		rankManager.saveAsync();
		sender.sendMessage(ChatUtil.TRANSLATE("All ranks have been &asaved &fin &branks.yml&f!"));
	}
}
