package club.veluxpvp.core.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.sync.SyncType;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class StaffChatCommand {

	public static List<Player> playersInStaffChat = Lists.newArrayList();
	
	@Command(name = "staffchat", aliases = {"sc"}, permission = "core.staff", playersOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		Player player = cmd.getPlayer();
		
		if(args.length >= 1) {
			String message = "";
			
			for(int i = 0; i < args.length; i++) {
				message += args[i] + (i != (args.length - 1) ? " " : "");
			}
			
			String staffMessage = ChatUtil.TRANSLATE("&9[Staff-Chat] &7(" + player.getServer().getServerName() + ") &b" + player.getName() + "&7: " + message);
			Core.getInstance().getSyncManager().sendSynchronization(player.getName(), SyncType.STAFF_MESSAGE, staffMessage);
			
			Bukkit.getOnlinePlayers().stream()
			.filter(p -> p.hasPermission("core.staff"))
			.forEach(p -> p.sendMessage(staffMessage));
			
			Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE(staffMessage));
		} else {
			if(!playersInStaffChat.contains(player)) {
				playersInStaffChat.add(player);
				player.sendMessage(ChatUtil.TRANSLATE("You are &anow &ftalking on Staff Chat!"));
			} else {
				playersInStaffChat.remove(player);
				player.sendMessage(ChatUtil.TRANSLATE("You are &cno longer &ftalking on Staff Chat!"));
			}
		}
	}
}
