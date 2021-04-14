package club.veluxpvp.core.command.message;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.profile.Profile;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class MessageCommand {

	public static Map<UUID, UUID> reply = Maps.newConcurrentMap();
	
	@Command(name = "message", aliases = {"msg", "tell", "whisper"}, playersOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		Player player = cmd.getPlayer();
		
		if(args.length >= 2) {
			Player target = Bukkit.getPlayer(args[0]);
			String message = "";
			
			if(target == null) {
				player.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[0] + "\" not found!"));
				return;
			}
			
			if(target.getName().equalsIgnoreCase(player.getName())) {
				player.sendMessage(ChatUtil.TRANSLATE("&cYou can't message yourself!"));
				return;
			}
			
			for(int i = 1; i < args.length; i++) {
				message += args[i] + (i != (args.length - 1) ? " " : "");
			}
			
			Profile p = Core.getInstance().getProfileManager().getProfile(player.getUniqueId());
			Profile t = Core.getInstance().getProfileManager().getProfile(target.getUniqueId());
			
			if(!p.isPrivateMessages()) {
				player.sendMessage(ChatUtil.TRANSLATE("&cYou can't message people while you have your private messages disabled!"));
				return;
			}
			
			if(!t.isPrivateMessages()) {
				player.sendMessage(ChatUtil.TRANSLATE("&c" + target.getName() + " has their private messages disabled!"));
				return;
			}
			
			if(t.isPrivateMessagesSounds()) target.playSound(target.getLocation(), Sound.NOTE_PLING, 6F, 1.0F);
			target.sendMessage(ChatUtil.TRANSLATE("&7(From " + p.colorName() + "&7) ") + message);
			player.sendMessage(ChatUtil.TRANSLATE("&7(To " + t.colorName() + "&7) ") + message);
			
			reply.put(player.getUniqueId(), target.getUniqueId());
			reply.put(target.getUniqueId(), player.getUniqueId());
		} else {
			player.sendMessage(ChatUtil.TRANSLATE("&cUsage: /message <player> <message...>"));
		}
	}
}
