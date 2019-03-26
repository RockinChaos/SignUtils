package me.RockinChaos.signutils.handlers;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.RockinChaos.signutils.SignUtils;
import me.RockinChaos.signutils.utils.Utils;

public class ConfigHandler {
	private static YamlConfiguration loadConfig;
	private static YamlConfiguration loadEnLang;
	private static boolean opCommandPermissions = false;
	
	public static void loadConfigs() {
		configFile();
		enLangFile();
	}
	
	public static FileConfiguration loadConfig(String path) {
		File file = new File(SignUtils.getInstance().getDataFolder(), path);
		if (!(file).exists()) {
			try {
				SignUtils.getInstance().saveResource(path, false);
			} catch (Exception e) {
				ServerHandler.sendDebugTrace(e);
				SignUtils.getInstance().getLogger().warning("Cannot save " + path + " to disk!");
				return null;
			}
		}
		return getPath(path, 1, file);
	}

	public static FileConfiguration getConfig(String path) {
		File file = new File(SignUtils.getInstance().getDataFolder(), path);
		if (loadConfig == null) {
			loadConfig(path);
		}
		return getPath(path, 2, file);
	}

	public static YamlConfiguration getPath(String path, int integer, File file) {
		if (path.contains("config.yml")) {
			if (integer == 1) {
				loadConfig = YamlConfiguration.loadConfiguration(file);
			}
			return loadConfig;
		} else if (path.contains("en-lang.yml")) {
			if (integer == 1) {
				loadEnLang = YamlConfiguration.loadConfiguration(file);
			}
			return loadEnLang;
		}
		return null;
	}

	public static void configFile() {
		loadConfig("config.yml");
		File File = new File(SignUtils.getInstance().getDataFolder(), "config.yml");
		if (File.exists() && getConfig("config.yml").getInt("config-Version") != 0) {
			if (SignUtils.getInstance().getResource("config.yml") != null) {
				String newGen = "config" + Utils.getRandom(1, 50000) + ".yml";
				File newFile = new File(SignUtils.getInstance().getDataFolder(), newGen);
				if (!newFile.exists()) {
					File.renameTo(newFile);
					File configFile = new File(SignUtils.getInstance().getDataFolder(), "config.yml");
					configFile.delete();
					loadConfig("config.yml");
					ServerHandler.sendConsoleMessage("&4Your config.yml is out of date and new options are available, generating a new one!");
				}
			}
		}
		getConfig("config.yml").options().copyDefaults(false);
	}
	
	public static void enLangFile() {
	      loadConfig("en-lang.yml");
	      File enLang = new File(SignUtils.getInstance().getDataFolder(), "en-lang.yml");
	      if (enLang.exists() && SignUtils.getInstance().getConfig().getString("Language").equalsIgnoreCase("English") && getConfig("en-lang.yml").getInt("en-Version") != 0) {
	      if (SignUtils.getInstance().getResource("en-lang.yml") != null) {
	        String newGen = "en-lang" + Utils.getRandom(1, 50000) + ".yml";
	        File newFile = new File(SignUtils.getInstance().getDataFolder(), newGen);
	           if (!newFile.exists()) {
	    	      enLang.renameTo(newFile);
	              File configFile = new File(SignUtils.getInstance().getDataFolder(), "en-lang.yml");
	              configFile.delete();
				  loadConfig("en-lang.yml");
				  ServerHandler.sendConsoleMessage("&4Your en-lang.yml is out of date and new options are available, generating a new one!");
	           }
	        }
	      }
		  if (SignUtils.getInstance().getConfig().getString("Language").equalsIgnoreCase("English")) {
			  getConfig("en-lang.yml").options().copyDefaults(false);
		  }
	}
	
	public static boolean getOPCommandPermissions() {
		return opCommandPermissions;
	}
	
	public static void loadOPCommandPermissions() {
		opCommandPermissions = ConfigHandler.getConfig("config.yml").getBoolean("Permissions.Commands-OP");
	}
}