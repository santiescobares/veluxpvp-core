package club.veluxpvp.core.command.teleport;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class TeleportHereCommand {

	@Command(name = "teleporthere", aliases = {"tphere", "s"}, permission = "core.command.teleporthere", playersOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		Player player = cmd.getPlayer();
		
		if(args.length >= 1) {
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null) {
				player.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[0] + "\" not found!"));
				return;
			}
			
			target.teleport(player.getLocation());
			target.sendMessage(ChatUtil.TRANSLATE("You have been &ateleported &fto &b" + player.getName() + "&f!"));
			player.sendMessage(ChatUtil.TRANSLATE("&b" + target.getName() + " &fhas been &ateleported &fto you!"));
		} else {
			player.sendMessage(ChatUtil.TRANSLATE("&cUsage: /teleporthere <player>"));
		}
	}
}
