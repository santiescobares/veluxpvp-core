package club.veluxpvp.core.sync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.Settings;
import club.veluxpvp.core.grant.Grant;
import club.veluxpvp.core.grant.GrantManager;
import club.veluxpvp.core.utilities.ChatUtil;

public class SyncReaderRunnable extends BukkitRunnable {

	@Override
	public void run() {
		Connection connection = Core.getInstance().getMySQLManager().getConnection();
		
		try {
			PreparedStatement s = connection.prepareStatement("SELECT * FROM " + Settings.SYNC_TABLE + " WHERE Readed=?");
			s.setBoolean(1, false);
			ResultSet r = s.executeQuery();
			
			while(r.next()) {
				SyncType type = SyncType.valueOf(r.getString("Type").toUpperCase());
				final String id = r.getString("ID");
				boolean sameServer = false;
				
				switch(type) {
				case GRANT:
					if(r.getString("ServerSender").equals(Bukkit.getServer().getServerName())) {
						sameServer = true;
						break;
					}
					
					GrantManager grantManager1 = Core.getInstance().getGrantManager();
					Grant g1 = grantManager1.deserialize(r.getString("Message"));
					Player player = Bukkit.getPlayer(g1.getGrantedUUID());
					
					if(player != null) {
						g1.grant();
					}
					
					Core.getInstance().getProfileManager().saveAsync(Core.getInstance().getProfileManager().getProfile(g1.getGrantedUUID()));
					System.out.println("[Sync] " + type.name() + " sync type received from " + r.getString("ServerSender") + " with data: " + r.getString("Message"));
					
					Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), () -> {
						try {
							PreparedStatement updateS = connection.prepareStatement("UPDATE " + Settings.SYNC_TABLE + " SET Readed=? WHERE ID=?");
							
							updateS.setBoolean(1, true);
							updateS.setString(2, id);
							updateS.executeUpdate();
							updateS.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}, 10L);
					break;
				case UNGRANT:
					if(r.getString("ServerSender").equals(Bukkit.getServer().getServerName())) {
						sameServer = true;
						break;
					}
					
					GrantManager grantManager2 = Core.getInstance().getGrantManager();
					Grant g2 = grantManager2.deserialize(r.getString("Message"));
					
					g2.remove();
					
					Core.getInstance().getProfileManager().saveAsync(Core.getInstance().getProfileManager().getProfile(g2.getGrantedUUID()));
					System.out.println("[Sync] " + type.name() + " sync type received from " + r.getString("ServerSender") + " with data: " + r.getString("Message"));
					
					Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), () -> {
						try {
							PreparedStatement updateS = connection.prepareStatement("UPDATE " + Settings.SYNC_TABLE + " SET Readed=? WHERE ID=?");
							
							updateS.setBoolean(1, true);
							updateS.setString(2, id);
							updateS.executeUpdate();
							updateS.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}, 10L);
					break;
				case STAFF_MESSAGE:
					String staffMessage = r.getString("Message");
					
					if(ChatColor.stripColor(ChatUtil.TRANSLATE(staffMessage)).startsWith("[Staff-Chat]")) {
						if(r.getString("ServerSender").equals(Bukkit.getServer().getServerName())) {
							sameServer = true;
							break;
						}
					}
					
					Bukkit.getOnlinePlayers().stream()
					.filter(p -> p.hasPermission("core.staff"))
					.forEach(p -> p.sendMessage(ChatUtil.TRANSLATE(staffMessage)));
					
					Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE(staffMessage));
					Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), () -> {
						try {
							PreparedStatement updateS = connection.prepareStatement("UPDATE " + Settings.SYNC_TABLE + " SET Readed=? WHERE ID=?");
							
							updateS.setBoolean(1, true);
							updateS.setString(2, id);
							updateS.executeUpdate();
							updateS.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}, 10L);
					
					Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), () -> {
						try {
							PreparedStatement updateS = connection.prepareStatement("DELETE FROM " + Settings.SYNC_TABLE + " WHERE ID=?");
							
							updateS.setString(1, id);
							updateS.executeUpdate();
							updateS.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}, 25L);
					break;
				default:
					break;
				}
				
				if(sameServer) continue;
			}
			
			r.close();
			s.close();
		} catch(SQLException e) {
			Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&b[Core] &cAn error has ocurred while reading database syncs!"));
			e.printStackTrace();
		}
	}
}
