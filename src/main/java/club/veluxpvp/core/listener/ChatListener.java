package club.veluxpvp.core.listener;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.google.common.collect.Maps;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.command.StaffChatCommand;
import club.veluxpvp.core.disguise.DisguiseManager;
import club.veluxpvp.core.disguise.DisguisedPlayer;
import club.veluxpvp.core.profile.Profile;
import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.sync.SyncType;
import club.veluxpvp.core.utilities.ChatUtil;

public class ChatListener implements Listener {

	private Map<UUID, Long> chatCooldown;
	
	public ChatListener() {
		this.chatCooldown = Maps.newConcurrentMap();
		Bukkit.getPluginManager().registerEvents(this, Core.getInstance());
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		DisguiseManager disguiseManager = Core.getInstance().getDisguiseManager();
		
		if(StaffChatCommand.playersInStaffChat.contains(player) && player.hasPermission("core.staff")) {
			String staffMessage = ChatUtil.TRANSLATE("&9[Staff-Chat] &7(" + player.getServer().getServerName() + ") &b" + player.getName() + "&7: " + message);
			Core.getInstance().getSyncManager().sendSynchronization(player.getName(), SyncType.STAFF_MESSAGE, staffMessage);
			
			Bukkit.getOnlinePlayers().stream()
			.filter(p -> p.hasPermission("core.staff"))
			.forEach(p -> p.sendMessage(staffMessage));
			
			Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE(staffMessage));
			event.setCancelled(true);
			return;
		}
		
		if(Bukkit.getPluginManager().getPlugin("Lazarus") != null) {
			return;
		}
		
		if(Core.getInstance().getConfig().getBoolean("CHAT.MUTED")) {
			if(!player.hasPermission("core.bypass.chat.mute")) {
				event.setCancelled(true);
				player.sendMessage(ChatUtil.TRANSLATE("&cThe chat is currently muted!"));
				return;
			}
		}
		
		if(this.chatCooldown.containsKey(player.getUniqueId()) && !player.hasPermission("core.chat.bypass.delay")) {
			if(System.currentTimeMillis() < this.chatCooldown.get(player.getUniqueId())) {
				event.setCancelled(true);
				player.sendMessage(ChatUtil.TRANSLATE("&cThe chat is currently delayed! You can bypass this restriction by purchasing a rank."));
				return;
			}
		}
		
		if(player.hasPermission("core.chat.color")) message = ChatUtil.TRANSLATE(message);
		
		if(!disguiseManager.isDisguised(player)) {
			Profile p = Core.getInstance().getProfileManager().getProfile(player.getUniqueId());
			Rank rank = Core.getInstance().getRankManager().getByName(p.getRank());
			//Tag t = Core.getInstance().getTagManager().getByName(p.getTag());
			
			String prefix = rank != null ? rank.getPrefix() : "";
			String suffix = rank != null ? rank.getSuffix() : "";
			//String tag = t == null ? "" : t.getDisplayName() + " ";
			
			event.setFormat(ChatUtil.TRANSLATE(prefix + player.getName() + suffix + "&7: &f") + message);
		} else {
			DisguisedPlayer dp = disguiseManager.getDisguisedPlayer(player);
			
			event.setFormat(ChatUtil.TRANSLATE(dp.getFakeRank().getPrefix() + dp.getFakeName() + dp.getFakeRank().getSuffix() + "&7: &f") + message);
		}

		int DELAY = Core.getInstance().getConfig().getInt("CHAT.DELAY") * 1000;
		if(DELAY > 0) this.chatCooldown.put(player.getUniqueId(), System.currentTimeMillis() + ((long) DELAY));
	}
}
