package club.veluxpvp.core.command.chat;

import org.bukkit.command.CommandSender;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class ChatCommand {

	public ChatCommand() {
		Core.getInstance().getCommandFramework().registerCommands(this);
	}
	
	@Command(name = "chat", permission = "core.command.chat")
	public void execute(CommandArgs cmd) {
		sendHelpMessage(cmd.getSender());
	}
	
	private void sendHelpMessage(CommandSender sender) {
		sender.sendMessage(ChatUtil.SHORTER_LINE());
		sender.sendMessage(ChatUtil.TRANSLATE("&b&lChat Commands"));
		sender.sendMessage(ChatUtil.SHORTER_LINE());
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/chat mute"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/chat unmute"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/chat clear"));
		sender.sendMessage(ChatUtil.TRANSLATE(" &7* &f/chat delay <time>"));
		sender.sendMessage(ChatUtil.SHORTER_LINE());
	}
}
