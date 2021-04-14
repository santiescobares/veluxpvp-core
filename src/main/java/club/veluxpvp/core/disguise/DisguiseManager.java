package club.veluxpvp.core.disguise;

import java.util.List;

import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import club.veluxpvp.core.rank.Rank;
import lombok.Getter;


public class DisguiseManager {

	@Getter private List<DisguisedPlayer> disguisedPlayers;
	
	public DisguiseManager() {
		this.disguisedPlayers = Lists.newArrayList();
	}
	
	public void disguise(Player player, Rank fakeRank, String fakeName) {
		DisguisedPlayer dp = new DisguisedPlayer(player.getUniqueId(), player.getName(), fakeName, fakeRank);
		dp.disguise();
		
		this.disguisedPlayers.add(dp);
	}
	
	public void undisguise(Player player) {
		DisguisedPlayer dp = this.getDisguisedPlayer(player);
		if(dp == null) return;
		
		dp.undisguise();
		
		this.disguisedPlayers.remove(dp);
	}
	
	public DisguisedPlayer getDisguisedPlayer(Player player) {
		return this.disguisedPlayers.stream().filter(dp -> dp.getRealUUID().equals(player.getUniqueId())).findFirst().orElse(null);
	}
	
	public boolean isDisguised(Player player) {
		return this.getDisguisedPlayer(player) != null;
	}
}
