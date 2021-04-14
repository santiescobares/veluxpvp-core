package club.veluxpvp.core.grant;

import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.google.common.collect.Maps;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.grant.menu.GrantsButton;
import club.veluxpvp.core.grant.menu.GrantsMenu;
import club.veluxpvp.core.menu.Button;
import club.veluxpvp.core.menu.Menu;
import club.veluxpvp.core.menu.MenuManager;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.PlayerUtil;

public class GrantsListener implements Listener {

	private Map<UUID, Grant> ungranting;
	
	public GrantsListener() {
		this.ungranting = Maps.newConcurrentMap();
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Menu openedMenu = MenuManager.getOpenedMenu(player);
		
		if(openedMenu != null && openedMenu instanceof GrantsMenu) {
			event.setCancelled(true);
			
			GrantsMenu menu = (GrantsMenu) openedMenu;
			Button clickedButton = menu.getClickedButton(event.getSlot());
			
			if(clickedButton != null && clickedButton instanceof GrantsButton) {
				GrantsButton button = (GrantsButton) clickedButton;
				Grant g = button.getGrant();
				
				if(event.getClick() != ClickType.RIGHT && event.getClick() != ClickType.SHIFT_RIGHT) return;
				if(g == null || !g.isActive() || !player.hasPermission("core.command.ungrant")) return;
				
				this.ungranting.put(player.getUniqueId(), g);
				player.closeInventory();
				player.sendMessage(ChatUtil.TRANSLATE("&aPlease enter in the chat the reason for the ungrant. Type &cCancel &ato cancel this process."));
			}
		}
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		Grant g = this.ungranting.get(player.getUniqueId());
		
		if(g == null) return;
		
		event.setCancelled(true);
		
		if(message.equalsIgnoreCase("Cancel")) {
			this.ungranting.remove(player.getUniqueId());
			player.sendMessage(ChatUtil.TRANSLATE("&cProcess cancelled!"));
			return;
		}
		
		Core.getInstance().getGrantManager().ungrant(player.getName(), g, message);
		this.ungranting.remove(player.getUniqueId());
		player.sendMessage(ChatUtil.TRANSLATE("You have &cungranted &b" + PlayerUtil.name(g.getGrantedUUID()) + "&f!"));
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) { this.ungranting.remove(event.getPlayer().getUniqueId()); }
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) { this.ungranting.remove(event.getPlayer().getUniqueId()); }
}
