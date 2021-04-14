package club.veluxpvp.core.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class FlyCommand {

	@Command(name = "fly", permission = "core.command.fly", playersOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		Player player = cmd.getPlayer();
		
		if(args.length >= 1) {
			if(!player.hasPermission("core.command.fly.others")) {
				player.sendMessage(ChatUtil.NO_PERMISSION());
				return;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null) {
				player.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[0] + "\" not found!"));
				return;
			}
			
			if(!target.getAllowFlight()) {
				target.setAllowFlight(true);
				target.sendMessage(ChatUtil.TRANSLATE("Your fly mode has been &aenabled&f!"));
				player.sendMessage(ChatUtil.TRANSLATE("&b" + target.getName() + "&f's fly mode has been &aenabled&f!"));
			} else {
				target.setAllowFlight(false);
				target.setFlying(false);
				target.sendMessage(ChatUtil.TRANSLATE("Your fly mode has been &cdisabled&f!"));
				player.sendMessage(ChatUtil.TRANSLATE("&b" + target.getName() + "&f's fly mode has been &cdisabled&f!"));
			}
		} else {
			if(!player.getAllowFlight()) {
				player.setAllowFlight(true);
				player.sendMessage(ChatUtil.TRANSLATE("Your fly mode has been &aenabled&f!"));
			} else {
				player.setAllowFlight(false);
				player.setFlying(false);
				player.sendMessage(ChatUtil.TRANSLATE("Your fly mode has been &cdisabled&f!"));
			}
		}
	}
}
