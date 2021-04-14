package club.veluxpvp.core.command.teleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class TeleportPositionCommand {

	@Command(name = "teleportposition", aliases = {"tppos"}, permission = "core.command.teleportposition", playersOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		Player player = cmd.getPlayer();
		
		if(args.length >= 5) {
			try {
				double x = Double.valueOf(args[0]);
				double y = Double.valueOf(args[1]);
				double z = Double.valueOf(args[2]);
				float yaw = Float.valueOf(args[3]);
				float pitch = Float.valueOf(args[4]);
				
				player.teleport(new Location(player.getWorld(), x, y, z, yaw, pitch));
				player.sendMessage(ChatUtil.TRANSLATE("You have been &ateleported &fto &b" + x + ", " + y + ", " + z + " (" + player.getWorld().getName() + ")&f!"));
			} catch(NumberFormatException e) {
				player.sendMessage(ChatUtil.TRANSLATE("&cYou must enter a valid number!"));
			}
		} else if(args.length >= 3) {
			try {
				double x = Double.valueOf(args[0]);
				double y = Double.valueOf(args[1]);
				double z = Double.valueOf(args[2]);
				
				player.teleport(new Location(player.getWorld(), x, y, z));
				player.sendMessage(ChatUtil.TRANSLATE("You have been &ateleported &fto &b" + x + ", " + y + ", " + z + " (" + player.getWorld().getName() + ")&f!"));
			} catch(NumberFormatException e) {
				player.sendMessage(ChatUtil.TRANSLATE("&cYou must enter a valid number!"));
			}
		} else {
			player.sendMessage(ChatUtil.TRANSLATE("&cUsage: /teleportposition <x> <y> <z> [yaw] [pitch]"));
		}
	}
}
