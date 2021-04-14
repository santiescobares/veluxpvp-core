package club.veluxpvp.core.command.toggle;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.profile.Profile;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class TogglePrivateMessagesSoundsCommand {

	@Command(name = "toggleprivatemessagessounds", aliases = {"tpms", "sounds"}, playersOnly = true)
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		Player player = cmd.getPlayer();
		
		if(args.length >= 1) {
			Player target = Bukkit.getPlayer(args[0]);
			
			if(!player.hasPermission("core.command.toggleprivatemessagessounds.others")) {
				player.sendMessage(ChatUtil.NO_PERMISSION());
				return;
			}
			
			if(target == null) {
				player.sendMessage(ChatUtil.TRANSLATE("&cPlayer \"" + args[0] + "\" not found!"));
				return;
			}
			
			Profile targetProfile = Core.getInstance().getProfileManager().getProfile(target.getUniqueId());
			
			if(!targetProfile.isPrivateMessagesSounds()) {
				targetProfile.setPrivateMessagesSounds(true);
				target.sendMessage(ChatUtil.TRANSLATE("&aYou are now receiving sounds on private messages!"));
				player.sendMessage(ChatUtil.TRANSLATE("&a" + target.getName() + " is now receiving sounds on private messages!"));
			} else {
				targetProfile.setPrivateMessagesSounds(false);
				target.sendMessage(ChatUtil.TRANSLATE("&cYou are no longer receiving sounds on private messages!"));
				player.sendMessage(ChatUtil.TRANSLATE("&c" + target.getName() + " is no longer receiving sounds on private messages!"));
			}
		} else {
			Profile profile = Core.getInstance().getProfileManager().getProfile(player.getUniqueId());
			
			if(!profile.isPrivateMessagesSounds()) {
				profile.setPrivateMessagesSounds(true);
				player.sendMessage(ChatUtil.TRANSLATE("&aYou are now receiving sounds on private messages!"));
			} else {
				profile.setPrivateMessagesSounds(false);
				player.sendMessage(ChatUtil.TRANSLATE("&cYou are no longer receiving sounds on private messages!"));
			}
		}
	}
}
