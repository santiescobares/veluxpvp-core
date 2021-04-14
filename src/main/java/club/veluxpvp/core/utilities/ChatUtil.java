package club.veluxpvp.core.utilities;

import java.util.List;

import com.google.common.collect.Lists;

import net.md_5.bungee.api.ChatColor;

public final class ChatUtil {

	public static String TRANSLATE(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static List<String> TRANSLATE(List<String> s) {
		List<String> toReturn = Lists.newArrayList();
		s.forEach(string -> toReturn.add(ChatColor.translateAlternateColorCodes('&', string)));
		return toReturn;
	}
	
	public static String LINE() {
		return ChatColor.translateAlternateColorCodes('&', "&7&m-----------------------------------------------------");
	}
	
	public static String SHORTER_LINE() {
		return ChatColor.translateAlternateColorCodes('&', "&7&m---------------------------------------");
	}
	
	public static String MENU_LINE() {
		return ChatColor.translateAlternateColorCodes('&', "&7&m------------------------------");
	}
	
	public static String SB_LINE() {
		return ChatColor.translateAlternateColorCodes('&', "&7&m---------------------");
	}
	
	public static String NO_PERMISSION() {
		return ChatColor.translateAlternateColorCodes('&', "Unknown command.");
	}
	
	public static String NO_CONSOLE() {
		return ChatColor.translateAlternateColorCodes('&', "&cThis action only can be performed by players!");
	}
	
	public static String NO_PLAYERS() {
		return ChatColor.translateAlternateColorCodes('&', "&cThis action only can be performed by console!");
	}
}
