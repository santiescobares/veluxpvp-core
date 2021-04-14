package club.veluxpvp.core.command;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class MoreCommand {

	@Command(name = "more", aliases = {"stack"}, permission = "core.command.more", playersOnly = true)
	public void execute(CommandArgs cmd) {
		Player player = cmd.getPlayer();
		
		if(player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
			player.sendMessage(ChatUtil.TRANSLATE("&cYou must hold an item!"));
			return;
		}
		
		player.getItemInHand().setAmount(64);
		player.updateInventory();
		player.sendMessage(ChatUtil.TRANSLATE("You have &aset &fyour item in hand's amount to &b64&f!"));
	}
}
