package club.veluxpvp.core.profile;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Sets;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.Settings;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.PlayerUtil;
import club.veluxpvp.core.utilities.TimeUtil;
import lombok.Getter;

public class ProfileManager {

	@Getter private Set<Profile> profiles;
	private final Connection connection;
	
	public ProfileManager() {
		this.profiles = Sets.newHashSet();
		this.connection = Core.getInstance().getMySQLManager().getConnection();
	}
	
	public Profile getProfile(UUID uuid) {
		return this.profiles.stream().filter(p -> p.getUuid().equals(uuid)).findFirst().orElse(this.getProfileFromDB(uuid));
	}
	
	public boolean isLoaded(Profile p) {
		return this.profiles.contains(p);
	}
	
	public Profile loadProfile(UUID uuid) {
		Profile profile = this.getProfileFromDB(uuid);
		
		if(profile == null) {
			profile = new Profile(uuid);
		}
		
		this.profiles.add(profile);
		return profile;
	}
	
	public Profile getProfileFromDB(UUID uuid) {
		try {
			PreparedStatement s = connection.prepareStatement("SELECT * FROM " + Settings.PROFILES_TABLE + " WHERE UUID=?");
			s.setString(1, uuid.toString());
			ResultSet r = s.executeQuery();
			
			if(r.next()) {
				Profile p = new Profile(uuid);
				
				if(!r.getString("FirstAddress").equals("")) p.setFirstAddress(InetAddress.getByName(r.getString("FirstAddress")));
				if(!r.getString("LastAddress").equals("")) p.setLastAddress(InetAddress.getByName(r.getString("LastAddress")));
				p.setFirstJoin(r.getString("FirstJoin"));
				p.setLastSeen(r.getString("LastSeen"));
				p.setLastServer(r.getString("LastServer"));
				p.setPrivateMessages(r.getBoolean("PrivateMessages"));
				p.setPrivateMessagesSounds(r.getBoolean("PrivateMessagesSounds"));
				p.setVerified(r.getBoolean("Verified"));
				p.setRank(r.getString("Rank"));
				p.setTag(r.getString("Tag"));
				
				r.close();
				s.close();
				return p;
			}
		} catch(SQLException | UnknownHostException e) {
			Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&b[Core] &cAn error has ocurred while getting " + PlayerUtil.name(uuid) + "'s profile from the database!"));
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void saveProfile(Profile p) {
		if(p == null) return;
		
		try {
			if(!profileExists(p)) {
				PreparedStatement s = connection.prepareStatement("INSERT INTO " + Settings.PROFILES_TABLE + " VALUE (?,?,?,?,?,?,?,?,?,?,?,?)");
				
				s.setString(1, p.getUuid().toString());
				s.setString(2, PlayerUtil.name(p.getUuid()));
				s.setString(3, p.getFirstAddress() == null ? "" : p.getFirstAddress().toString().replaceAll("/", ""));
				s.setString(4, p.getLastAddress() == null ? "" : p.getLastAddress().toString().replaceAll("/", ""));
				s.setString(5, p.getFirstJoin());
				s.setString(6, p.getLastSeen());
				s.setString(7, p.getLastServer());
				s.setBoolean(8, p.isPrivateMessages());
				s.setBoolean(9, p.isPrivateMessagesSounds());
				s.setBoolean(10, p.isVerified());
				s.setString(11, p.getRank());
				s.setString(12, p.getTag());
				
				s.executeUpdate();
				s.close();
			} else {
				PreparedStatement s = connection.prepareStatement("UPDATE " + Settings.PROFILES_TABLE + " SET Name=?, LastAddress=?, LastSeen=?, LastServer=?, PrivateMessages=?, PrivateMessagesSounds=?, Verified=?, Rank=?, Tag=? WHERE UUID=?");
			
				s.setString(1, PlayerUtil.name(p.getUuid()));
				s.setString(2, p.getLastAddress() == null ? "" : p.getLastAddress().toString().replaceAll("/", ""));
				s.setString(3, p.getLastSeen());
				s.setString(4, p.getLastServer());
				s.setBoolean(5, p.isPrivateMessages());
				s.setBoolean(6, p.isPrivateMessagesSounds());
				s.setBoolean(7, p.isVerified());
				s.setString(8, p.getRank());
				s.setString(9, p.getTag());
				s.setString(10, p.getUuid().toString());
				
				s.executeUpdate();
				s.close();
			}
		} catch(SQLException e) {
			Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&b[Core] &cAn error has ocurred while saving " + PlayerUtil.name(p.getUuid()) + "'s profile into the database!"));
			e.printStackTrace();
		}
		
		if(Bukkit.getPlayer(p.getUuid()) == null) this.profiles.remove(p);
	}
	
	public boolean profileExists(Profile p) {
		try {
			PreparedStatement s = connection.prepareStatement("SELECT * FROM " + Settings.PROFILES_TABLE + " WHERE UUID=?");
			s.setString(1, p.getUuid().toString());
			ResultSet r = s.executeQuery();
			
			if(r.next()) {
				r.close();
				s.close();
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void saveAsync(Profile p) {
		Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> this.saveProfile(p));
	}
	
	public void onDisable() {
		try {
			for(Profile p : this.profiles) {
				Player player = Bukkit.getPlayer(p.getUuid());
				if(player == null) continue;
				
				p.setLastAddress(player.getAddress().getAddress());
				p.setLastSeen(TimeUtil.formatDate(new Date()));
				p.setLastServer(Bukkit.getServer().getServerName());
				
				this.saveProfile(p);
			}
		} catch(ConcurrentModificationException ignored) {}
	}
}
