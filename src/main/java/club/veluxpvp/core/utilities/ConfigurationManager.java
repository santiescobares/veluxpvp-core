package club.veluxpvp.core.utilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import club.veluxpvp.core.Core;

public class ConfigurationManager {

	private Core plugin;
	
	private File configFile = null;
	private File ranksFile = null;
	private FileConfiguration ranks = null;
	private File tagsFile = null;
	private FileConfiguration tags = null;
	
	public ConfigurationManager() {
		this.plugin = Core.getInstance();
		
		loadConfig();
		loadRanks();
		loadTags();
	}

	// Config File
	public void loadConfig() {
		configFile = new File(plugin.getDataFolder(), "config.yml");
		
		if(!configFile.exists()) {
			plugin.getConfig().options().copyDefaults(true);
			plugin.saveConfig();
		}
	}
	
	// Arenas File
	public void loadRanks() {
		ranksFile = new File(plugin.getDataFolder(), "ranks.yml");
		
		if(!ranksFile.exists()){
			getRanks().options().copyDefaults(true);
			saveRanks();
		}
	}
	
	public void saveRanks() {
		try {
			ranks.save(ranksFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   
    public FileConfiguration getRanks() {
    	if (ranks == null) {
    		reloadRanks();
    	}
    	
    	return ranks;
    }
   
    public void reloadRanks() {
    	if (ranks == null) {
    		ranksFile = new File(plugin.getDataFolder(), "ranks.yml");
    	}
    	
    	ranks = YamlConfiguration.loadConfiguration(ranksFile);
    	Reader defConfigStream;
    	
    	try {
    		defConfigStream = new InputStreamReader(plugin.getResource("ranks.yml"), "UTF8");
    		
    		if (defConfigStream != null) {
    			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
    			
    			ranks.setDefaults(defConfig);
    		}
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
    	}      
    }
    
	// Tags File
	public void loadTags() {
		tagsFile = new File(plugin.getDataFolder(), "tags.yml");
		
		if(!tagsFile.exists()){
			getTags().options().copyDefaults(true);
			saveTags();
		}
	}
	
	public void saveTags() {
		try {
			tags.save(tagsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
   
    public FileConfiguration getTags() {
    	if (tags == null) {
    		reloadTags();
    	}
    	
    	return tags;
    }
   
    public void reloadTags() {
    	if (tags == null) {
    		tagsFile = new File(plugin.getDataFolder(), "tags.yml");
    	}
    	
    	tags = YamlConfiguration.loadConfiguration(tagsFile);
    	Reader defConfigStream;
    	
    	try {
    		defConfigStream = new InputStreamReader(plugin.getResource("tags.yml"), "UTF8");
    		
    		if (defConfigStream != null) {
    			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
    			
    			tags.setDefaults(defConfig);
    		}
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
    	}      
    }
}
