package club.veluxpvp.core.command.message;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.profile.Profile;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class ReplyCommand {

	@Command(name = "reply", aliases = {"r"}, playersOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		Player player = cmd.getPlayer();
		
		if(args.length >= 1) {
			String message = "";
			Player target = Bukkit.getPlayer(MessageCommand.reply.get(player.getUniqueId()));
			
			if(target == null) {
				player.sendMessage(ChatUtil.TRANSLATE("&cYou don't have any conversation!"));
				return;
			}
			
			for(int i = 0; i < args.length; i++) {
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
			
			MessageCommand.reply.put(player.getUniqueId(), target.getUniqueId());
			MessageCommand.reply.put(target.getUniqueId(), player.getUniqueId());
		} else {
			player.sendMessage(ChatUtil.TRANSLATE("&cUsage: /reply <message...>"));
		}
	}
}
