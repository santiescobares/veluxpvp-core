package club.veluxpvp.core.grant.command;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.grant.Grant;
import club.veluxpvp.core.profile.Profile;
import club.veluxpvp.core.profile.ProfileManager;
import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.rank.RankManager;
import club.veluxpvp.core.sync.SyncType;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.Preconditions;
import club.veluxpvp.core.utilities.TimeUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class GrantCommand {

	@SuppressWarnings("deprecation")
	@Command(name = "grant", permission = "core.command.grant")
	public void execute(CommandArgs cmd) {
		String[] args = cmd.getArgs();
		CommandSender sender = cmd.getSender();
		ProfileManager profileManager = Core.getInstance().getProfileManager();
		RankManager rankManager = Core.getInstance().getRankManager();

		if(args.length >= 4) {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			Rank rank = rankManager.getByName(args[1]);
			String duration = args[2];
			String reason = "";
			
			if(rank == null) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cRank \"" + args[1] + "\" not found!"));
				return;
			}
			
			if(!Preconditions.checkIfValidDuration(duration)) {
				sender.sendMessage(ChatUtil.TRANSLATE("&cYou must enter a valid duration!"));
				return;
			}
			
			for(int i = 3; i < args.length; i++) {
				reason += args[i] + (i != (args.length - 1) ? " " : "");
			}
			
			Profile targetProfile = profileManager.getProfile(target.getUniqueId());
			if(targetProfile == null) targetProfile = new Profile(target.getUniqueId());
			
			Grant g = new Grant(target.getUniqueId(), sender.getName());
			
			g.setGrantedRank(rank.getName());
			if(duration.equalsIgnoreCase("Permanent")) {
				g.setDuration("Permanent");
			} else {
				Date expiresOnDate = TimeUtil.addTimeToDate(duration);
				
				if(expiresOnDate == null) {
					sender.sendMessage(ChatUtil.TRANSLATE("&cYou must enter a valid duration!"));
					return;
				}
				
				g.setDuration(duration);
			}
			g.setReason(reason);
			g.grant();
			Core.getInstance().getGrantManager().saveAsync(g);
			
			sender.sendMessage(ChatUtil.TRANSLATE("You have &agranted &b" + rank.getName() + " &frank to &b" + target.getName() + " &ffor &b" + g.duration() + " &ffor &b" + reason + "&f!"));
		
			if(Core.isBungee()) {
				Core.getInstance().getSyncManager().sendSynchronization(sender.getName(), SyncType.GRANT, g);
			}
		} else {
			sender.sendMessage(ChatUtil.TRANSLATE("&cUsage: /grant <player> <rankName> <duration> <reason...>"));
		}
	}
}
