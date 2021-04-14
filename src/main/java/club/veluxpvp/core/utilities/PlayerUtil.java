package club.veluxpvp.core.utilities;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class PlayerUtil {

	public static String name(UUID uuid) {
		return Bukkit.getOfflinePlayer(uuid).getName();
	}
	
	public static boolean hasEmptySlots(Player player, int slots) {
		int emptySlots = 0;
		
		for(int i = 0; i < player.getInventory().getSize(); i++) {
			if(player.getInventory().getItem(i) == null) {
				emptySlots++;
			}
		}
		
		if(emptySlots >= slots) {
			return true;
		}
		
		return false;
	}
}
