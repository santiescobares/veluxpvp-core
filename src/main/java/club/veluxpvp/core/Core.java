package club.veluxpvp.core;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import club.veluxpvp.core.command.*;
import club.veluxpvp.core.command.chat.*;
import club.veluxpvp.core.command.message.*;
import club.veluxpvp.core.command.teleport.*;
import club.veluxpvp.core.command.toggle.*;
import club.veluxpvp.core.disguise.*;
import club.veluxpvp.core.disguise.command.*;
import club.veluxpvp.core.grant.*;
import club.veluxpvp.core.grant.command.GrantCommand;
import club.veluxpvp.core.grant.command.GrantsCommand;
import club.veluxpvp.core.listener.ChatListener;
import club.veluxpvp.core.listener.PlayerListener;
import club.veluxpvp.core.menu.MenuListener;
import club.veluxpvp.core.profile.*;
import club.veluxpvp.core.rank.RankManager;
import club.veluxpvp.core.rank.command.*;
import club.veluxpvp.core.sync.SyncManager;
import club.veluxpvp.core.sync.SyncReaderRunnable;
import club.veluxpvp.core.utilities.*;
import club.veluxpvp.core.utilities.commandframework.CommandFramework;
import lombok.Getter;

@Getter
public class Core extends JavaPlugin {

	@Getter private static Core instance;

	private CommandFramework commandFramework;
	
	private ConfigurationManager configurationManager;
	private MySQLManager mySQLManager;
	private RankManager rankManager;
	private ProfileManager profileManager;
	private GrantManager grantManager;
	private SyncManager syncManager;
	private DisguiseManager disguiseManager;
	
	@Override
	public void onEnable() {
		instance = this;
		registerManagers();
		registerListeners();
		registerCommands();
		registerTasks();
	}
	
	@Override
	public void onDisable() {
		this.rankManager.saveRanks();
		this.profileManager.onDisable();
		
		try {
			this.mySQLManager.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Managers
	private void registerManagers() {
		this.configurationManager = new ConfigurationManager();
		this.mySQLManager = new MySQLManager();
		this.rankManager = new RankManager();
		this.profileManager = new ProfileManager();
		this.grantManager = new GrantManager();
		this.syncManager = new SyncManager();
		this.disguiseManager = new DisguiseManager();
	}
	
	// Listeners
	private void registerListeners() {
		List<Listener> listeners = Arrays.asList(
				new MenuListener(),
				new ProfileListener(),
				new PlayerListener(),
				new ChatListener(),
				new GrantsListener(),
				new DisguiseListener()
				);
		
		listeners.forEach(l -> Bukkit.getPluginManager().registerEvents(l, this));
	}
	
	// Commands
	private void registerCommands() {
		this.commandFramework = new CommandFramework(this);
		
		List<Object> commands = Arrays.asList(
				new GrantCommand(),
				new GrantsCommand(),
				
				new DisguiseCommand(),
				new UnDisguiseCommand(),

				new GameModeCommand(),
				new FlyCommand(),
				new ListCommand(),
				new BroadcastCommand(),
				new HealCommand(),
				new FeedCommand(),
				new MoreCommand(),
				new SpeedCommand(),
				new SkullCommand(),
				new UserInfoCommand(),
				new ReportCommand(),
				new RequestCommand(),
				new StaffChatCommand(),
				new RenameCommand(),
				new ClearCommand(),
				new LunarClientCommand(),
				new LunarClientUsersCommand(),

				new MessageCommand(),
				new ReplyCommand(),
				
				new TeleportCommand(),
				new TeleportHereCommand(),
				new TeleportAllCommand(),
				new TeleportPositionCommand(),
				
				new TogglePrivateMessagesCommand(),
				new TogglePrivateMessagesSoundsCommand()
				);
		
		commands.forEach(c -> this.commandFramework.registerCommands(c));
		
		new ChatCommand();
		new ChatMuteCommand();
		new ChatUnMuteCommand();
		new ChatClearCommand();
		new ChatDelayCommand();
		
		new RankCommand();
		new RankCreateCommand();
		new RankDeleteCommand();
		new RankSetPrefixCommand();
		new RankSetSuffixCommand();
		new RankSetPriorityCommand();
		new RankSetColorCommand();
		new RankSetDefaultCommand();
		new RankSetItalianCommand();
		new RankAddPermCommand();
		new RankRemovePermCommand();
		new RankInheritCommand();
		new RankUnInheritCommand();
		new RankInfoCommand();
		new RankListCommand();
		new RankSaveAllCommand();
	}
	
	// Tasks
	private void registerTasks() {
		new GrantCheckRunnable().runTaskTimerAsynchronously(this, 20L, 20L);
		new SyncReaderRunnable().runTaskTimerAsynchronously(this, 15L, 15L);
	}
	
	// Other
	public static boolean isBungee() {
		return true;
	}
	
	public static boolean isLunarAPIEnabled() {
		Plugin lunarAPI = Bukkit.getPluginManager().getPlugin("LunarClient-API");
		return lunarAPI != null && lunarAPI.isEnabled();
	}
}
