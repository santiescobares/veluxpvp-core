package club.veluxpvp.core.sync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;

import club.veluxpvp.core.Core;
import club.veluxpvp.core.Settings;
import club.veluxpvp.core.grant.Grant;
import club.veluxpvp.core.utilities.ChatUtil;

public class SyncManager {

	private final Connection connection;
	
	public SyncManager() {
		this.connection = Core.getInstance().getMySQLManager().getConnection();
	}
	
	public void sendSynchronization(String sender, SyncType type, Object obj) {
		switch(type) {
		case GRANT:
			try {
				Grant g = (Grant) obj;
				PreparedStatement s = connection.prepareStatement("INSERT INTO " + Settings.SYNC_TABLE + " VALUE (?,?,?,?,?,?)");
				
				s.setString(1, UUID.randomUUID().toString().replaceAll("-", ""));
				s.setString(2, sender);
				s.setString(3, type.name());
				s.setString(4, g.serialize());
				s.setString(5, Bukkit.getServer().getServerName());
				s.setBoolean(6, false);
				
				s.executeUpdate();
				s.close();
				
				System.out.println("[Sync] Successfully sent a sync with data: Sender: " + sender + " - Type: " + type.name() + " - Message: " + g.serialize());
			} catch(SQLException e) {
				Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&b[Core] &cAn error has ocurred while sending a grant sync!"));
				e.printStackTrace();
			}
			
			break;
		case UNGRANT:
			try {
				Grant g = (Grant) obj;
				PreparedStatement s = connection.prepareStatement("INSERT INTO " + Settings.SYNC_TABLE + " VALUE (?,?,?,?,?,?)");
				
				s.setString(1, UUID.randomUUID().toString().replaceAll("-", ""));
				s.setString(2, sender);
				s.setString(3, type.name());
				s.setString(4, g.serialize());
				s.setString(5, Bukkit.getServer().getServerName());
				s.setBoolean(6, false);
				
				s.executeUpdate();
				s.close();
			} catch(SQLException e) {
				Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&b[Core] &cAn error has ocurred while sending an ungrant sync!"));
				e.printStackTrace();
			}
		case STAFF_MESSAGE:
			try {
				String staffMessage = (String) obj;
				PreparedStatement s = connection.prepareStatement("INSERT INTO " + Settings.SYNC_TABLE + " VALUE (?,?,?,?,?,?)");
				
				s.setString(1, UUID.randomUUID().toString().replaceAll("-", ""));
				s.setString(2, sender);
				s.setString(3, type.name());
				s.setString(4, staffMessage);
				s.setString(5, Bukkit.getServer().getServerName());
				s.setBoolean(6, false);
				
				s.executeUpdate();
				s.close();
			} catch(SQLException e) {
				Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&b[Core] &cAn error has ocurred while sending a staff message sync!"));
				e.printStackTrace();
			}
		default:
			break;
		}
	}
}
