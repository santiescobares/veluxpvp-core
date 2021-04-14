package club.veluxpvp.core.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.profile.Profile;
import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class UserInfoCommand {

	@SuppressWarnings("deprecation")
	@Command(name = "userinfo", permission = "core.command.userinfo")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		
		if(args.length >= 1) {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			Profile targetProfile = Core.getInstance().getProfileManager().getProfile(target.getUniqueId());
			
			if(targetProfile == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&c" + target.getName() + " is not registered in the database!"));
				return;
			}
			
			Rank rank = Core.getInstance().getRankManager().getByName(targetProfile.getRank());
			
			sender.sendMessage(ChatUtil.SHORTER_LINE());
			sender.sendMessage(ChatUtil.TRANSLATE("&b&l" + target.getName() + "'s Information"));
			sender.sendMessage(ChatUtil.SHORTER_LINE());
			sender.sendMessage(ChatUtil.TRANSLATE(" &7* &fUUID&7: &b" + targetProfile.getUuid().toString()));
			sender.sendMessage(ChatUtil.TRANSLATE(" &7* &fRank&7: &b" + (rank == null ? "None" : rank.colorName())));
			sender.sendMessage(ChatUtil.TRANSLATE(" &7* &fLast Seen&7: &b" + (target.isOnline() ? "Now on your server" : targetProfile.getLastSeen() + "&7(" + targetProfile.getLastServer() + ")")));
			sender.sendMessage(ChatUtil.TRANSLATE(" &7* &fTag&7: &b" + (targetProfile.getTag().equals("") ? "None" : targetProfile.getTag())));
			sender.sendMessage(ChatUtil.TRANSLATE(" &7* &fPermissions &7(0):"));
			sender.sendMessage(ChatUtil.SHORTER_LINE());
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /userinfo <player>"));
		}
	}
}
