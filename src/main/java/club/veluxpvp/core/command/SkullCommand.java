package club.veluxpvp.core.command;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.ItemBuilder;
import club.veluxpvp.core.utilities.PlayerUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class SkullCommand {

	@Command(name = "skull", permission = "core.command.skull", playersOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		Player player = cmd.getPlayer();
		
		if(args.length >= 1) {
			String headName = args[0];
			ItemStack head = new ItemBuilder()
					.of(Material.SKULL_ITEM)
					.dataValue((byte) 3)
					.skull(headName)
					.name("&b" + headName + "'s Head")
					.build();
			
			if(head == null) {
				player.sendMessage(ChatUtil.TRANSLATE("&cThe head is invalid!"));
				return;
			}
			
			if(!PlayerUtil.hasEmptySlots(player, 1)) {
				player.sendMessage(ChatUtil.TRANSLATE("&cYour inventory is full!"));
				return;
			}
			
			player.getInventory().addItem(head);
			player.sendMessage(ChatUtil.TRANSLATE("You have &areceived &b" + headName + "&f's head!"));
		} else {
			ItemStack head = new ItemBuilder()
					.of(Material.SKULL_ITEM)
					.dataValue((byte) 3)
					.skull(player.getName())
					.name("&b" + player.getName() + "'s Head")
					.build();

			if(head == null) {
				player.sendMessage(ChatUtil.TRANSLATE("&cThe head is invalid!"));
				return;
			}
			
			if(!PlayerUtil.hasEmptySlots(player, 1)) {
				player.sendMessage(ChatUtil.TRANSLATE("&cYour inventory is full!"));
				return;
			}
			
			player.getInventory().addItem(head);
			player.sendMessage(ChatUtil.TRANSLATE("You have &areceived &b" + player.getName() + "&f's head!"));
		}
	}
}
