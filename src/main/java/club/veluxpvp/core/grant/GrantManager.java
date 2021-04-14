package club.veluxpvp.core.grant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;

import com.google.common.collect.Lists;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.Settings;
import club.veluxpvp.core.rank.Rank;
import club.veluxpvp.core.sync.SyncType;
import club.veluxpvp.core.utilities.ChatUtil;
import club.veluxpvp.core.utilities.TimeUtil;

public class GrantManager {

	private final Connection connection;
	
	public GrantManager() {
		this.connection = Core.getInstance().getMySQLManager().getConnection();
	}
	
	public List<Grant> getGrants(UUID uuid) {
		return this.getAllGrants().stream().filter(g -> g.getGrantedUUID().equals(uuid)).collect(Collectors.toList());
	}
	
	public List<Grant> getAllGrants() {
		List<Grant> grants = Lists.newArrayList();
		
		try {
			PreparedStatement s = connection.prepareStatement("SELECT * FROM " + Settings.GRANTS_TABLE);
			ResultSet r = s.executeQuery();
			
			while(r.next()) {
				Grant g = new Grant(UUID.fromString(r.getString("GrantedUUID")), r.getString("Granter"));
				
				g.setId(r.getString("ID"));
				g.setGrantedRank(r.getString("GrantedRank"));
				g.setReason(r.getString("Reason"));
				g.setMadeOn(r.getString("MadeOn"));
				g.setExpiresOn(r.getString("ExpiresOn"));
				g.setDuration(r.getString("Duration"));
				g.setRemovedBy(r.getString("RemovedBy"));
				g.setRemovedOn(r.getString("RemovedOn"));
				g.setRemoveReason(r.getString("RemoveReason"));
				
				grants.add(g);
			}
			
			r.close();
			s.close();
		} catch(SQLException e) {
			Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&b[Core] &cAn error has ocurred while getting all grants from the database!"));
			e.printStackTrace();
		}
		
		return grants;
	}
	
	public Grant getHighestActiveGrant(UUID uuid) {
		List<Grant> activeGrants = this.getGrants(uuid).stream().filter(g -> g.isActive()).collect(Collectors.toList());
		
		if(activeGrants.size() == 0) return null;
		if(activeGrants.size() == 1) return activeGrants.get(0);
		
		return activeGrants.stream()
				.sorted((g1, g2) -> {
					Rank rank1 = Core.getInstance().getRankManager().getByName(g1.getGrantedRank());
					Rank rank2 = Core.getInstance().getRankManager().getByName(g2.getGrantedRank());
					
					if(rank1 == null && rank2 != null) return rank2.getPriority();
					if(rank2 == null && rank1 != null) return rank1.getPriority();
					
					return rank2.getPriority() - rank1.getPriority();
				})
				.limit(1)
				.findFirst()
				.orElse(null);
	}
	
	public void saveGrant(Grant g) {
		try {
			if(!grantExists(g)) {
				PreparedStatement s = connection.prepareStatement("INSERT INTO " + Settings.GRANTS_TABLE + " VALUE (?,?,?,?,?,?,?,?,?,?,?)");
				
				s.setString(1, g.getId());
				s.setString(2, g.getGrantedUUID().toString());
				s.setString(3, g.getGranter());
				s.setString(4, g.getGrantedRank());
				s.setString(5, g.getReason());
				s.setString(6, g.getMadeOn());
				s.setString(7, g.getExpiresOn());
				s.setString(8, g.getDuration());
				s.setString(9, g.getRemovedBy());
				s.setString(10, g.getRemovedOn());
				s.setString(11, g.getRemoveReason());
				
				s.executeUpdate();
				s.close();
			} else {
				PreparedStatement s = connection.prepareStatement("UPDATE " + Settings.GRANTS_TABLE + " SET RemovedBy=?, RemovedOn=?, RemoveReason=? WHERE ID=?");
				
				s.setString(1, g.getRemovedBy());
				s.setString(2, g.getRemovedOn());
				s.setString(3, g.getRemoveReason());
				s.setString(4, g.getId());
				
				s.executeUpdate();
				s.close();
			}
		} catch(SQLException e) {
			Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&b[Core] &cAn error has ocurred while saving a grant into the database!"));
			e.printStackTrace();
		}
	}
	
	public boolean grantExists(Grant g) {
		try {
			PreparedStatement s = connection.prepareStatement("SELECT * FROM " + Settings.GRANTS_TABLE + " WHERE ID=?");
			s.setString(1, g.getId());
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
	
	public void saveAsync(Grant g) {
		Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> this.saveGrant(g));
	}
	
	public Grant deserialize(String data) {
		String[] splittedData = data.split(" ");
		
		UUID grantedUUID = UUID.fromString(splittedData[0].split("\\=")[1]);
		String granter = splittedData[1].split("\\=")[1];
		String grantedRank = splittedData[2].split("\\=")[1];
		String duration = splittedData[3].split("\\=")[1];
		
		Grant g = new Grant(grantedUUID, granter);
		g.setGrantedRank(grantedRank);
		g.setDuration(duration);
		
		return g;
	}
	
	public List<Grant> getSortedPlayerGrants(UUID uuid) {
		return this.getGrants(uuid).stream()
				.sorted((g1, g2) -> {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					
					try {
						Date d1 = sdf.parse(g1.getMadeOn());
						Date d2 = sdf.parse(g2.getMadeOn());
						
						return (int) (d2.getTime() - d1.getTime());
					} catch(ParseException e) {
						e.printStackTrace();
					}
					
					return 0;
				})
				.collect(Collectors.toList());
	}
	
	public void ungrant(String ungranter, Grant g, String reason) {
		if(g == null) return;
		
		g.setRemovedBy(ungranter);
		g.setRemovedOn(TimeUtil.formatDate(new Date()));
		g.setRemoveReason(reason);
		g.remove();
		
		if(Core.isBungee()) {
			Core.getInstance().getSyncManager().sendSynchronization(ungranter, SyncType.UNGRANT, g);
		}
		
		this.saveAsync(g);
	}
}
