package club.veluxpvp.core.profile;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.utilities.TimeUtil;

public class ProfileListener implements Listener {

	private final ProfileManager profileManager;
	
	public ProfileListener() {
		this.profileManager = Core.getInstance().getProfileManager();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Profile p = this.profileManager.loadProfile(player.getUniqueId());
		
		if(p.getFirstAddress() == null) p.setFirstAddress(player.getAddress().getAddress());
		if(p.getLastAddress() == null) p.setLastAddress(player.getAddress().getAddress());
		p.setFirstJoin(TimeUtil.formatDate(new Date()));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Profile p = this.profileManager.getProfile(player.getUniqueId());
		
		p.setLastAddress(player.getAddress().getAddress());
		p.setLastSeen(TimeUtil.formatDate(new Date()));
		p.setLastServer(Bukkit.getServer().getServerName());
		
		this.profileManager.saveAsync(p);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		Profile p = this.profileManager.getProfile(player.getUniqueId());
		
		p.setLastAddress(player.getAddress().getAddress());
		p.setLastSeen(TimeUtil.formatDate(new Date()));
		p.setLastServer(Bukkit.getServer().getServerName());
		
		this.profileManager.saveAsync(p);
	}
}
