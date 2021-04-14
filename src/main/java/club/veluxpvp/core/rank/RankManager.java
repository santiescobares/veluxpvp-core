package club.veluxpvp.core.rank;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import club.veluxpvp.core.Core;
import lombok.Getter;

public class RankManager {

	@Getter private Set<Rank> ranks;
	@Getter private Map<UUID, PermissionAttachment> perms;
	@Getter private Map<UUID, List<String>> playerPermissions;
	
	public RankManager() {
		this.ranks = Sets.newHashSet();
		this.perms = Maps.newConcurrentMap();
		this.playerPermissions = Maps.newConcurrentMap();
		
		loadRanks();
	}
	
	public Rank getByName(String name) {
		return this.ranks.stream().filter(r -> r.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}
	
	public Rank getDefaultRank() {
		return this.ranks.stream().filter(r -> r.isDefault()).findFirst().orElse(null);
	}
	
	public List<Rank> getSortedRanks() {
		return this.ranks.stream()
				.sorted((r1, r2) -> {
					int priority1 = r1.getPriority();
					int priority2 = r2.getPriority();
					
					return priority2 - priority1;
				})
				.collect(Collectors.toList());
	}
	
	private void addPermissions(Player player, List<String> permissions) {
		PermissionAttachment pa = player.addAttachment(Core.getInstance());
		
		for(String perm : permissions) {
			if(!pa.getPermissions().containsKey(perm)) pa.setPermission(perm, true);
		}
		
		this.perms.put(player.getUniqueId(), pa);
	}
	
	public void clearPermissions(Player player) {
		this.perms.getOrDefault(player.getUniqueId(), player.addAttachment(Core.getInstance())).getPermissions().clear();
		this.perms.getOrDefault(player.getUniqueId(), player.addAttachment(Core.getInstance())).remove();
		this.perms.remove(player.getUniqueId());
	}
	
	public boolean addPlayerPermission(OfflinePlayer player, String permission) {
		List<String> permissions = this.playerPermissions.getOrDefault(player.getUniqueId(), Lists.newArrayList());
		
		if(!permissions.contains(permission)) {
			permissions.add(permission);
			this.playerPermissions.put(player.getUniqueId(), permissions);
			return true;
		}
		
		return false;
	}
	
	public boolean removePlayerPermission(OfflinePlayer player, String permission) {
		List<String> permissions = this.playerPermissions.getOrDefault(player.getUniqueId(), Lists.newArrayList());
		
		if(permissions.contains(permission)) {
			permissions.remove(permission);
			this.playerPermissions.put(player.getUniqueId(), permissions);
			return true;
		}
		
		return false;
	}
	
	public Set<Player> getPlayersWithRank(Rank rank) {
		return Bukkit.getOnlinePlayers().stream()
				.filter(p -> Core.getInstance().getProfileManager().getProfile(p.getUniqueId()).getRank().equals(rank.getName()))
				.collect(Collectors.toSet());
	}
	
	public void updatePermissions(Player player) {
		this.clearPermissions(player);
		
		Rank rank = this.getByName(Core.getInstance().getProfileManager().getProfile(player.getUniqueId()).getRank());
		List<String> allPermissions = Lists.newArrayList(this.playerPermissions.getOrDefault(player.getUniqueId(), Lists.newArrayList()));
		
		if(rank != null) {
			rank.getAllPermissions().stream().forEach(p -> allPermissions.add(p));
		} else {
			Rank defaultRank = this.getDefaultRank();
			
			if(defaultRank != null) {
				Core.getInstance().getProfileManager().getProfile(player.getUniqueId()).setRank(defaultRank.getName());
				defaultRank.getAllPermissions().stream().forEach(p -> allPermissions.add(p));
			}
		}
		
		this.addPermissions(player, allPermissions);
	}
	
	public void saveAsync() {
		Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> this.saveRanks());
	}
	
	public void loadRanks() {
		FileConfiguration ranks = Core.getInstance().getConfigurationManager().getRanks();

		if(ranks.contains("RANKS")) {
			for(String key : ranks.getConfigurationSection("RANKS").getKeys(false)) {
				Rank r = new Rank(key);
				
				r.setPrefix(ranks.getString("RANKS." + key + ".PREFIX"));
				r.setSuffix(ranks.getString("RANKS." + key + ".SUFFIX"));
				r.setPriority(ranks.getInt("RANKS." + key + ".PRIORITY"));
				r.setDefault(ranks.getBoolean("RANKS." + key + ".DEFAULT"));
				r.setColor(ChatColor.valueOf(ranks.getString("RANKS." + key + ".COLOR").toUpperCase()));
				r.setItalian(ranks.getBoolean("RANKS." + key + ".ITALIAN"));
				if(ranks.contains("RANKS." + key + ".PERMISSIONS")) r.setPermissions(ranks.getStringList("RANKS." + key + ".PERMISSIONS"));
				if(ranks.contains("RANKS." + key + ".INHERITANCES")) r.setInheritances(ranks.getStringList("RANKS." + key + ".INHERITANCES"));
			
				this.ranks.add(r);
			}
		}
	}
	
	public void saveRanks() {
		FileConfiguration ranks = Core.getInstance().getConfigurationManager().getRanks();

		ranks.set("RANKS", null);

		for(Rank r : this.ranks) {
			this.updateInheritances(r);
			
			ranks.set("RANKS." + r.getName() + ".PREFIX", r.getPrefix());
			ranks.set("RANKS." + r.getName() + ".SUFFIX", r.getSuffix());
			ranks.set("RANKS." + r.getName() + ".PRIORITY", r.getPriority());
			ranks.set("RANKS." + r.getName() + ".DEFAULT", r.isDefault());
			ranks.set("RANKS." + r.getName() + ".COLOR", r.getColor().name());
			ranks.set("RANKS." + r.getName() + ".ITALIAN", r.isItalian());
			if(r.getPermissions().size() > 0) ranks.set("RANKS." + r.getName() + ".PERMISSIONS", r.getPermissions());
			if(r.getInheritances().size() > 0) ranks.set("RANKS." + r.getName() + ".INHERITANCES", r.getInheritances());
		}
		
		Core.getInstance().getConfigurationManager().saveRanks();
	}
	
	public void updateInheritances(Rank rank) {
		int index = -1;
		
		for(String inheritance : rank.getInheritances()) {
			index++;
			Rank r = this.getByName(inheritance);
			
			if(r == null) rank.getInheritances().remove(index);
		}
	}
}
