package club.veluxpvp.core.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lunarclient.bukkitapi.LunarClientAPI;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class LunarClientCommand {
	
	@Command(name = "lunarclient", aliases = {"lc"})
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(!Core.isLunarAPIEnabled()) {
			sender.sendMessage(ChatUtil.TRANSLATE("&cLunarClientAPI not found!"));
			return;
		}
		
		if(args.length >= 1) {
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[0] + "\" not found!"));
				return;
			}
			
			if(LunarClientAPI.getInstance().isRunningLunarClient(target)) {
				sender.sendMessage(ChatUtil.TRANSLATE("&a" + target.getName() + " is currently running LunarClient."));
			} else {
				sender.sendMessage(ChatUtil.TRANSLATE("&c" + target.getName() + " is currently not running LunarClient."));
			}
		} else {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /lunarclient <player>"));
				return;
			}
			
			Player player = (Player) sender;
			
			if(LunarClientAPI.getInstance().isRunningLunarClient(player)) {
				player.sendMessage(ChatUtil.TRANSLATE("&aYou are currently running LunarClient."));
			} else {
				player.sendMessage(ChatUtil.TRANSLATE("&cYou are currently not running LunarClient."));
			}
		}
	}
}
