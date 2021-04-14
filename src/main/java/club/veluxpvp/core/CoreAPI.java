package club.veluxpvp.core;

import java.util.UUID;

import org.bukkit.ChatColor;

import club.veluxpvp.core.profile.Profile;
import club.veluxpvp.core.profile.ProfileManager;
import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.rank.RankManager;

public final class CoreAPI {

	private final static ProfileManager profileManager;
	private final static RankManager rankManager;
	
	static {
		profileManager = Core.getInstance().getProfileManager();
		rankManager = Core.getInstance().getRankManager();
	}
	
	public final static String getPlayerRank(UUID uuid) {
		Profile profile = profileManager.getProfile(uuid);
		if(profile == null) return "";
		Rank rank = rankManager.getByName(profile.getRank());
		return rank == null ? "" : rank.getName();
	}
	
	public final static String getColor(UUID uuid) {
		Profile profile = profileManager.getProfile(uuid);
		return profile == null ? ChatColor.GREEN.toString() : profile.color();
	}
	
	public final static int getRankPriority(String rankName) {
		Rank rank = rankManager.getByName(rankName);
		return rank == null ? 0 : rank.getPriority();
	}
	
	public final static String getColoredRank(String rankName) {
		Rank rank = rankManager.getByName(rankName);
		return rank == null ? "" : rank.colorName();
	}
}
