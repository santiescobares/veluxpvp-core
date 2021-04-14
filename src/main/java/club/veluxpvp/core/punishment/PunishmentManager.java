package club.veluxpvp.core.punishment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;

import com.google.common.collect.Sets;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.Settings;
import club.veluxpvp.core.utilities.ChatUtil;

public class PunishmentManager {

	private final Connection connection;
	
	public PunishmentManager() {
		this.connection = Core.getInstance().getMySQLManager().getConnection();
	}
	
	public List<Punishment> getAllActivePunishments(UUID uuid) {
		return this.getAllPunishments(uuid).stream().filter(p -> p.isActive()).collect(Collectors.toList());
	}
	
	public List<Punishment> getActivePunishments(UUID uuid, PunishmentType type) {
		return this.getAllPunishments(uuid).stream().filter(p -> p.isActive() && p.getType() == type).collect(Collectors.toList());
	}
	
	public Set<Punishment> getAllPunishments(UUID uuid) {
		Set<Punishment> punishments = Sets.newHashSet();
		
		try {
			PreparedStatement s = this.connection.prepareStatement("SELECT * FROM " + Settings.PUNISHMENTS_TABLE + " WHERE PunishedUUID=?");
			s.setString(1, uuid.toString());
			ResultSet r = s.executeQuery();
			
			while(r.next()) {
				Punishment p = new Punishment(uuid);
				
				p.setId(r.getString("ID"));
				p.setPunisher(r.getString("Punisher"));
				p.setType(PunishmentType.valueOf(r.getString("Type").toUpperCase()));
				p.setReason(r.getString("Reason"));
				p.setMadeOn(r.getString("MadeOn"));
				p.setExpiresOn(r.getString("ExpiresOn"));
				p.setDuration(r.getString("Duration"));
				p.setPunishedIP(r.getString("PunishedIP"));
				p.setIp(r.getBoolean("IP"));
				p.setSilent(r.getBoolean("Silent"));
				p.setActive(r.getBoolean("Active"));
				
				punishments.add(p);
			}
			
			r.close();
			s.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return punishments;
	}
	
	public void savePunishment(Punishment p) {
		try {
			if(!this.punishmentExists(p)) {
				PreparedStatement s = this.connection.prepareStatement("INSERT INTO " + Settings.PUNISHMENTS_TABLE + " VALUE (?,?,?,?,?,?,?,?,?,?,?,?)");
				
				s.setString(1, p.getId());
				s.setString(2, p.getPunishedUUID().toString());
				s.setString(3, p.getPunisher());
				s.setString(4, p.getType().name());
				s.setString(5, p.getReason());
				s.setString(6, p.getMadeOn());
				s.setString(7, p.getExpiresOn());
				s.setString(8, p.getDuration());
				s.setString(9, p.getPunishedIP());
				s.setBoolean(10, p.isIp());
				s.setBoolean(11, p.isSilent());
				s.setBoolean(12, p.isActive());
				
				s.executeQuery();
				s.close();
			} else {
				PreparedStatement s = this.connection.prepareStatement("UPDATE " + Settings.PUNISHMENTS_TABLE + " SET Active=? WHERE ID=?");
				
				s.setBoolean(1, p.isActive());
				s.setString(2, p.getId());
				
				s.executeUpdate();
				s.close();
			}
		} catch(SQLException e) {
			Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&b[Core] &cAn error has ocurred while saving a punishment into the database!"));
			e.printStackTrace();
		}
	}
	
	public void saveAsync(Punishment p) {
		Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> this.savePunishment(p));
	}
	
	public boolean punishmentExists(Punishment p) {
		try {
			PreparedStatement s = this.connection.prepareStatement("SELECT * FROM " + Settings.PUNISHMENTS_TABLE + " WHERE ID=?");
			s.setString(1, p.getId());
			
			if(s.executeQuery().next()) {
				s.close();
				return true;
			} else {
				s.close();
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
