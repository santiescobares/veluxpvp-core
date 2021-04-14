package club.veluxpvp.core.disguise.command;

import club.veluxpvp.core.disguise.DisguiseManager;

import org.bukkit.entity.Player;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class UnDisguiseCommand {

	@Command(name = "undisguise", aliases = {"ud"}, permission = "core.command.disguise", playersOnly = true)
	public void execute(CommandArgs cmd) {
		Player player = cmd.getPlayer();
		DisguiseManager disguiseManager = Core.getInstance().getDisguiseManager();
		
		if(!disguiseManager.isDisguised(player)) {
			player.sendMessage(ChatUtil.TRANSLATE("&cYou are not disguised!"));
			return;
		}
		
		disguiseManager.undisguise(player);
		player.sendMessage(ChatUtil.TRANSLATE("&aYou have undisguised!"));
	}
}
