package club.veluxpvp.core.rank;

import java.util.List;

import org.bukkit.ChatColor;

import com.google.common.collect.Lists;

import club.veluxpvp.core.Core;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Rank {

	private String name, prefix, suffix;
	private int priority;
	private ChatColor color;
	private boolean Default, italian;
	private List<String> inheritances, permissions;
	
	public Rank(String name) {
		this.name = name;
		this.prefix = "";
		this.suffix = "";
		this.priority = 0;
		this.color = ChatColor.WHITE;
		this.Default = false;
		this.italian = false;
		this.inheritances = Lists.newArrayList();
		this.permissions = Lists.newArrayList();
	}
	
	public List<String> getAllPermissions() {
		List<String> permissions = Lists.newArrayList(this.permissions);
		
		for(String inheritance : this.inheritances) {
			Rank r = Core.getInstance().getRankManager().getByName(inheritance);
			
			if(r != null) {
				for(String perm : r.getAllPermissions()) {
					if(!permissions.contains(perm)) permissions.add(perm);
				}
			}
		}
		
		return permissions;
	}
	
	public boolean addPermission(String permission) {
		if(!this.permissions.contains(permission)) {
			this.permissions.add(permission);
			return true;
		}
		
		return false;
	}
	
	public boolean removePermission(String permission) {
		if(this.permissions.contains(permission)) {
			this.permissions.remove(permission);
			return true;
		}
		
		return false;
	}
	
	public boolean addInheritance(String inheritance) {
		if(!this.inheritances.contains(inheritance)) {
			this.inheritances.add(inheritance);
			return true;
		}
		
		return false;
	}
	
	public boolean removeInheritance(String inheritance) {
		if(this.inheritances.contains(inheritance)) {
			this.inheritances.remove(inheritance);
			return true;
		}
		
		return false;
	}
	
	public String color() {
		return this.color + (this.italian ? ChatColor.ITALIC.toString() : "");
	}
	
	public String colorName() {
		return this.color + (this.italian ? ChatColor.ITALIC.toString() : "") + this.name;
	}
	
	public byte getColorValue() {
		switch(this.color) {
		case BLACK:
			return 0;
		case DARK_BLUE:
			return 11;
		case DARK_GREEN:
			return 13;
		case DARK_AQUA:
			return 9;
		case DARK_RED:
			return 14;
		case DARK_PURPLE:
			return 10;
		case GOLD:
			return 1;
		case GRAY:
			return 8;
		case DARK_GRAY:
			return 7;
		case BLUE:
			return 9;
		case GREEN:
			return 5;
		case AQUA:
			return 3;
		case RED:
			return 14;
		case LIGHT_PURPLE:
			return 6;
		case YELLOW:
			return 4;
		default:
			return 0;
		}
	}
}
