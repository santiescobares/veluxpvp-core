package club.veluxpvp.core.command.toggle;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.profile.Profile;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class TogglePrivateMessagesCommand {

	@Command(name = "toggleprivatemessages", playersOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		Player player = cmd.getPlayer();
		
		if(args.length >= 1) {
			Player target = Bukkit.getPlayer(args[0]);
			
			if(!player.hasPermission("core.command.toggleprivatemessages.others")) {
				player.sendMessage(ChatUtil.NO_PERMISSION());
				return;
			}
			
			if(target == null) {
				player.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[0] + "\" not found!"));
				return;
			}
			
			Profile targetProfile = Core.getInstance().getProfileManager().getProfile(target.getUniqueId());
			
			if(targetProfile.isPrivateMessages()) {
				targetProfile.setPrivateMessages(true);
				target.sendMessage(ChatUtil.TRANSLATE("&aYou are now receiving private messages!"));
				player.sendMessage(ChatUtil.TRANSLATE("&a" + target.getName() + " is now receiving private messages!"));
			} else {
				targetProfile.setPrivateMessages(false);
				target.sendMessage(ChatUtil.TRANSLATE("&cYou are no longer receiving private messages!"));
				player.sendMessage(ChatUtil.TRANSLATE("&c" + target.getName() + " is no longer receiving private messages!"));
			}
		} else {
			Profile profile = Core.getInstance().getProfileManager().getProfile(player.getUniqueId());
			
			if(profile.isPrivateMessages()) {
				profile.setPrivateMessages(true);
				player.sendMessage(ChatUtil.TRANSLATE("&aYou are now receiving private messages!"));
			} else {
				profile.setPrivateMessages(false);
				player.sendMessage(ChatUtil.TRANSLATE("&cYou are no longer receiving private messages!"));
			}
		}
	}
}
