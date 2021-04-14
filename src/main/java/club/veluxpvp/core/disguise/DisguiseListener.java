package club.veluxpvp.core.disguise;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import club.veluxpvp.core.Core;

public class DisguiseListener implements Listener {

	private final DisguiseManager disguiseManager;
	
	public DisguiseListener() {
		this.disguiseManager = Core.getInstance().getDisguiseManager();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		DisguisedPlayer disguisedPlayer = disguiseManager.getDisguisedPlayers().stream().filter(dp -> dp.getFakeName().equalsIgnoreCase(player.getName())).findFirst().orElse(null);
		
		if(disguisedPlayer != null) {
			this.disguiseManager.getDisguisedPlayers().remove(disguisedPlayer);
			Bukkit.getPlayer(disguisedPlayer.getRealUUID()).kickPlayer(ChatColor.RED + "A player with your same nickname has joined the server while you were disguised.");
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		DisguisedPlayer dp = this.disguiseManager.getDisguisedPlayer(player);
		
		if(dp != null) {
			this.disguiseManager.getDisguisedPlayers().remove(dp);
		}
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		DisguisedPlayer dp = this.disguiseManager.getDisguisedPlayer(player);
		
		if(dp != null) {
			this.disguiseManager.getDisguisedPlayers().remove(dp);
		}
	}
}
