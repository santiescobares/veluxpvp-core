package club.veluxpvp.core.command.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class ChatUnMuteCommand extends ChatCommand {

	@Command(name = "chat.unmute", permission = "core.command.chatunmute")
	public void execute(CommandArgs cmd) {
		CommandSender sender = cmd.getSender();
		FileConfiguration config = Core.getInstance().getConfig();
		
		if(!config.getBoolean("CHAT.MUTED")) {
			sender.sendMessage(ChatUtil.TRANSLATE("&cThe chat isn't muted!"));
			return;
		}
		
		config.set("CHAT.MUTED", false);
		Core.getInstance().saveConfig();
		
		Bukkit.broadcastMessage(ChatUtil.TRANSLATE("&aGlobal chat has been unmuted by " + sender.getName() + "!"));
	}
}
