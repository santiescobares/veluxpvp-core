package club.veluxpvp.core.command;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.profile.ProfileManager;
import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.rank.RankManager;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.commandframework.Command;
import club.veluxpvp.core.utilities.commandframework.CommandArgs;

public class ListCommand {

	@Command(name = "list")
	public void execute(CommandArgs cmd) {
		CommandSender sender = cmd.getSender();
		RankManager rm = Core.getInstance().getRankManager();
		ProfileManager pm = Core.getInstance().getProfileManager();
		List<Rank> sortedRanks = rm.getSortedRanks();
		String finalMessage = "";
		
		for(int i = 0; i < sortedRanks.size(); i++) {
			finalMessage += sortedRanks.get(i).colorName() + (i != (sortedRanks.size() - 1) ? "&7, " : "");
		}
		
		finalMessage += " &f(" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + "): ";
		
		List<Player> sortedPlayers = Bukkit.getOnlinePlayers().stream()
				.sorted((p1, p2) -> {
					Rank r1 = rm.getByName(pm.getProfile(p1.getUniqueId()).getRank());
					Rank r2 = rm.getByName(pm.getProfile(p2.getUniqueId()).getRank());
					
					if(r1 == null && r2 != null) return r2.getPriority();
					if(r2 == null && r1 != null) return r1.getPriority();
					if(r1 == null && r2 == null) return 0;
					
					return r2.getPriority() - r1.getPriority();
				})
				.collect(Collectors.toList());
		
		for(int i = 0; i < sortedPlayers.size(); i++) {
			finalMessage += pm.getProfile(sortedPlayers.get(i).getUniqueId()).colorName() + (i != (sortedPlayers.size() - 1) ? "&7, " : "");
		}
		
		sender.sendMessage(ChatUtil.TRANSLATE(finalMessage));
	}
}
