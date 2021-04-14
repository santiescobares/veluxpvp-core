package club.veluxpvp.core.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class MenuListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onInventoryClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		this.removeMenu(player);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		this.removeMenu(player);
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		this.removeMenu(player);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Menu openedMenu = MenuManager.getOpenedMenu(player);
		
		if(openedMenu != null) {
			if(openedMenu.isUpdateOnClick()) {
				Button button = openedMenu.getClickedButton(event.getSlot());
				
				if(button == null) return;
				
				openedMenu.getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
				openedMenu.getInventory().setItem(event.getSlot(), button.build());
			}
		}
	}
	
	public void removeMenu(Player player) {
		Menu openedMenu = MenuManager.getOpenedMenu(player);
		
		if(openedMenu != null) {
			if(openedMenu.getUpdateTask() != null) openedMenu.getUpdateTask().cancel();
			
			MenuManager.openedMenu.remove(player.getUniqueId(), openedMenu);
		}
	}
}
