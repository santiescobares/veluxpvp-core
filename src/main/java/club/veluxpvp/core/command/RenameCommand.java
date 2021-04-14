package club.veluxpvp.core.command;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class RenameCommand {

	@Command(name = "rename", permission = "core.command.rename", playersOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		Player player = cmd.getPlayer();
		
		if(args.length >= 1) {
			ItemStack item = player.getItemInHand();
			String name = "";
			
			if(item == null || item.getType() == Material.AIR) {
				player.sendMessage(ChatUtil.TRANSLATE("&cYou must hold an item!"));
				return;
			}
			
			for(int i = 0; i < args.length; i++) {
				name += args[i] + (i != (args.length - 1) ? " " : "");
			}
			
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatUtil.TRANSLATE(name));
			item.setItemMeta(meta);
			
			player.setItemInHand(item);
			player.updateInventory();
			player.sendMessage(ChatUtil.TRANSLATE("&fYou &arenamed &fthe item in your hand to &b" + name + "&r!"));
		} else {
			player.sendMessage(ChatUtil.TRANSLATE("&cUsage: /rename <name...>"));
		}
	}
}
