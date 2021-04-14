package club.veluxpvp.core.grant.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import club.veluxpvp.core.grant.menu.GrantsMenu;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class GrantsCommand {

	@SuppressWarnings("deprecation")
	@Command(name = "grants", permission = "core.command.grants", playersOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		Player player = cmd.getPlayer();
		
		if(args.length >= 1) {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			new GrantsMenu(player, target.getUniqueId()).openMenu(player);
		} else {
			player.sendMessage(ChatUtil.TRANSLATE("&cUsage: /grants <player>"));
		}
	}
}
