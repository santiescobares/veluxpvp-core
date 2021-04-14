package club.veluxpvp.core.command.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class ChatClearCommand extends ChatCommand {

	@Command(name = "chat.clear", permission = "core.command.chatclear")
	public void execute(CommandArgs cmd) {
		CommandSender sender = cmd.getSender();

		Bukkit.getOnlinePlayers().stream()
		.filter(p -> !p.hasPermission("core.bypass.chat.clear"))
		.forEach(p -> {
			for(int i = 0; i <= 250; i++) {
				p.sendMessage(" ");
			}
		});
		
		Bukkit.broadcastMessage(ChatUtil.TRANSLATE("&cGlobal chat has been cleared by " + sender.getName() + "!"));
	}
}
