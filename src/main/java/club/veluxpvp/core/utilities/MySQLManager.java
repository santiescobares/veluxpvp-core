package club.veluxpvp.core.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;

import club.veluxpvp.core.Settings;
import lombok.Getter;

public class MySQLManager {

	@Getter private Connection connection;
	
	public MySQLManager() {
		try {
			synchronized(this) {
				if(this.connection != null && !this.connection.isClosed()) {
					return;
				}
				
				Class.forName("com.mysql.jdbc.Driver");
				this.connection = DriverManager.getConnection("jdbc:mysql://" + Settings.DATABASE_HOST + ":" + Settings.DATABASE_PORT + "/" + Settings.DATABASE_NAME, Settings.DATABASE_USERNAME, Settings.DATABASE_PASSWORD);
			
				createProfilesTable();
				createGrantsTable();
				createPunishmentsTable();
				createSyncTable();
				
				Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&aDatabase successfully connected!"));
			}
		} catch(SQLException | ClassNotFoundException e) {
			Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&cAn error has ocurred while connecting to the database!"));
			e.printStackTrace();
		}
	}
	
	private void createProfilesTable() {
		List<String> profilesTable = 
				Arrays.asList(
						"CREATE TABLE IF NOT EXISTS " + Settings.PROFILES_TABLE + "(UUID VARCHAR(40),",
						"Name VARCHAR(20),",
						"FirstAddress VARCHAR(20),",
						"LastAddress VARCHAR(20),",
						"FirstJoin VARCHAR(20),",
						"LastSeen VARCHAR(20),",
						"LastServer VARCHAR(25),",
						"PrivateMessages BOOLEAN,",
						"PrivateMessagesSounds BOOLEAN,",
						"Verified BOOLEAN,",
						"Rank VARCHAR(30),",
						"Tag VARCHAR(30)) ",
						"ENGINE = InnoDB CHARACTER SET utf8;"
						);
		
		try {
			String statement = "";
			
			for(int i = 0; i < profilesTable.size(); i++) {
				statement += profilesTable.get(i) + (i != (profilesTable.size() - 1) ? " " : "");
			}

			PreparedStatement s = this.connection.prepareStatement(statement);			
			s.executeUpdate();
			s.close();
		} catch(SQLException e) {
			Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&cAn error has ocurred while creating profiles table!"));
			e.printStackTrace();
		}
	}
	
	private void createGrantsTable() {
		List<String> grantsTable = 
				Arrays.asList(
						"CREATE TABLE IF NOT EXISTS " + Settings.GRANTS_TABLE + "(ID VARCHAR(40),",
						"GrantedUUID VARCHAR(40),",
						"Granter VARCHAR(20),",
						"GrantedRank VARCHAR(30),",
						"Reason VARCHAR(500),",
						"MadeOn VARCHAR(20),",
						"ExpiresOn VARCHAR(20),",
						"Duration VARCHAR(30),",
						"RemovedBy VARCHAR(20),",
						"RemovedOn VARCHAR(20),",
						"RemoveReason VARCHAR(500)) ",
						"ENGINE = InnoDB CHARACTER SET utf8;"
						);
		
		try {
			String statement = "";
			
			for(int i = 0; i < grantsTable.size(); i++) {
				statement += grantsTable.get(i) + (i != (grantsTable.size() - 1) ? " " : "");
			}

			PreparedStatement s = this.connection.prepareStatement(statement);
			s.executeUpdate();
			s.close();
		} catch(SQLException e) {
			Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&cAn error has ocurred while creating grants table!"));
			e.printStackTrace();
		}
	}
	
	private void createPunishmentsTable() {
		List<String> punishmentsTable = 
				Arrays.asList(
						"CREATE TABLE IF NOT EXISTS " + Settings.PUNISHMENTS_TABLE + "(ID VARCHAR(40),",
						"PunishedUUID VARCHAR(40),",
						"Punisher VARCHAR(20),",
						"Type VARCHAR(20),",
						"Reason VARCHAR(500),",
						"MadeOn VARCHAR(30),",
						"ExpiresOn VARCHAR(30),",
						"Duration VARCHAR(30),",
						"PunishedIP VARCHAR(30),",
						"IP BOOLEAN,",
						"Silent BOOLEAN,",
						"Active BOOLEAN) ",
						"ENGINE = InnoDB CHARACTER SET utf8;"
						);
		
		try {
			String statement = "";
			
			for(int i = 0; i < punishmentsTable.size(); i++) {
				statement += punishmentsTable.get(i) + (i != (punishmentsTable.size() - 1) ? " " : "");
			}

			PreparedStatement s = this.connection.prepareStatement(statement);
			s.executeUpdate();
			s.close();
		} catch(SQLException e) {
			Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&cAn error has ocurred while creating punishments table!"));
			e.printStackTrace();
		}
	}
	
	private void createSyncTable() {
		List<String> syncTable = 
				Arrays.asList(
						"CREATE TABLE IF NOT EXISTS " + Settings.SYNC_TABLE + "(ID VARCHAR(40),",
						"Sender VARCHAR(20),",
						"Type VARCHAR(20),",
						"Message VARCHAR(500),",
						"ServerSender VARCHAR(30),",
						"Readed BOOLEAN) ",
						"ENGINE = InnoDB CHARACTER SET utf8;"
						);
		
		try {
			String statement = "";
			
			for(int i = 0; i < syncTable.size(); i++) {
				statement += syncTable.get(i) + (i != (syncTable.size() - 1) ? " " : "");
			}

			PreparedStatement s = this.connection.prepareStatement(statement);
			s.executeUpdate();
			s.close();
		} catch(SQLException e) {
			Bukkit.getConsoleSender().sendMessage(ChatUtil.TRANSLATE("&cAn error has ocurred while creating sync table!"));
			e.printStackTrace();
		}
	}
}
