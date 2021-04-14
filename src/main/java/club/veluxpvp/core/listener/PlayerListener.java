package club.veluxpvp.core.listener;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.profile.Profile;
import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.rank.RankManager;
import club.veluxpvp.core.sync.SyncType;
import club.veluxpvp.core.utilities.ChatUtil;

public class PlayerListener implements Listener {

	private final RankManager rankManager;
	
	public PlayerListener() {
		this.rankManager = Core.getInstance().getRankManager();
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		Profile profile = Core.getInstance().getProfileManager().getProfile(player.getUniqueId());
		Rank rank = rankManager.getByName(profile.getRank());
		
		if(rank == null) {
			Rank defaultRank = rankManager.getDefaultRank();
			
			if(defaultRank != null) profile.setRank(defaultRank.getName());
		}
		
		rankManager.updatePermissions(player);
		
		if(player.hasPermission("core.staff")) {
			String staffMessage = ChatUtil.TRANSLATE("&9[S] &b" + player.getName() + " has &ajoined &bto &7" + Bukkit.getServer().getServerName() + "&b.");
			Core.getInstance().getSyncManager().sendSynchronization(player.getName(), SyncType.STAFF_MESSAGE, staffMessage);
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		rankManager.clearPermissions(player);
		
		if(player.hasPermission("core.staff")) {
			String staffMessage = ChatUtil.TRANSLATE("&9[S] &b" + player.getName() + " has &cleft &bfrom &7" + Bukkit.getServer().getServerName() + "&b.");
			Core.getInstance().getSyncManager().sendSynchronization(player.getName(), SyncType.STAFF_MESSAGE, staffMessage);
		}
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		
		rankManager.clearPermissions(player);
		
		if(player.hasPermission("core.staff")) {
			String staffMessage = ChatUtil.TRANSLATE("&9[S] &b" + player.getName() + " has &cleft &bfrom &7" + Bukkit.getServer().getServerName() + "&b.");
			Core.getInstance().getSyncManager().sendSynchronization(player.getName(), SyncType.STAFF_MESSAGE, staffMessage);
		}
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		List<String> blacklistedCommands = Arrays.asList(
				"plugins", "pl", "me", "version", "ver", "about", "icanhasbukkit", "execute", "executeforall", "/calc", "/solve", 
				"/eval", "/calculate", "/evaluate"
				);
		List<String> restrictedCommands = Arrays.asList(
				"holographicdisplays", "hd", "hologram", "multiversecore", "mv", "permissionsex", "pex", "nametagedit", "nte", "act"
				);
		
		for(String cmd : blacklistedCommands) {
			if(message.toLowerCase().startsWith("/" + cmd)) {
				event.setCancelled(true);
				player.sendMessage(ChatUtil.NO_PERMISSION());
				return;
			}
		}
		
		if(!player.isOp()) {
			for(String cmd : restrictedCommands) {
				if(message.toLowerCase().startsWith("/" + cmd)) {
					event.setCancelled(true);
					player.sendMessage(ChatUtil.NO_PERMISSION());
					return;
				}
			}
		}
		
		if(!player.isOp()) {
			if(message.split(" ")[0].contains(":")) {
				event.setCancelled(true);
				player.sendMessage(ChatUtil.TRANSLATE("&cYou can't use &b: &cin your commands!"));
				return;
			}
		}
	}
}
