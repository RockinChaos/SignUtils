/*
 * SignUtils
 * Copyright (C) CraftationGaming <https://www.craftationgaming.com/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.RockinChaos.signutils.handlers;

import java.io.File;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.RockinChaos.signutils.ChatExecutor;
import me.RockinChaos.signutils.SignUtils;
import me.RockinChaos.signutils.ChatTab;
import me.RockinChaos.signutils.listeners.SignInteract;
import me.RockinChaos.signutils.utils.SchedulerUtils;
import me.RockinChaos.signutils.utils.ServerUtils;
import me.RockinChaos.signutils.utils.StringUtils;
import me.RockinChaos.signutils.utils.api.DependAPI;
import me.RockinChaos.signutils.utils.api.LanguageAPI;
import me.RockinChaos.signutils.utils.api.MetricsAPI;

public class ConfigHandler {
	
	private HashMap < String, Boolean > noSource = new HashMap < String, Boolean > ();

	private YamlConfiguration configFile;
	private YamlConfiguration langFile;
	
	private static ConfigHandler config;
	
   /**
    * Registers the command executors and events.
    * 
    */
	public void registerEvents() {
	    SignUtils.getInstance().getCommand("signutils").setExecutor(new ChatExecutor()); 
	    SignUtils.getInstance().getCommand("signutils").setTabCompleter(new ChatTab());
	    SignUtils.getInstance().getServer().getPluginManager().registerEvents(new SignInteract(), SignUtils.getInstance());
	}
	
   /**
    * Copies files into memory.
    * 
    */
	public void copyFiles() {
		this.copyFile("config.yml", "config-Version", 1);
		this.copyFile(LanguageAPI.getLang(true).getFile(), LanguageAPI.getLang(false).getFile().split("-")[0] + "-Version", 1);
	}

   /**
    * Registers new instances of the plugin classes.
    * 
    */
	public void registerClasses(final boolean silent) {
		ServerUtils.clearErrorStatements();
		this.copyFiles();
		DependAPI.getDepends(true);
		if (!silent) { 
			DependAPI.getDepends(false).sendUtilityDepends();
		}
		SchedulerUtils.runLater(100L, () -> {
			new MetricsAPI(SignUtils.getInstance(), 4278);
			ServerUtils.sendErrorStatements(null);
		});
	}
	
   /**
    * Gets the file from the specified path.
    * 
    * @param path - The File to be fetched.
    * @return The file.
    */
	public FileConfiguration getFile(final String path) {
		final File file = new File(SignUtils.getInstance().getDataFolder(), path);
		if (this.configFile == null) { this.getSource(path); }
		try {
			return this.getLoadedConfig(file, false);
		} catch (Exception e) {
			ServerUtils.sendSevereTrace(e);
			ServerUtils.logSevere("Cannot load " + file.getName() + " from disk!");
		}
		return null;
	}
	
   /**
    * Gets the source file from the specified path.
    * 
    * @param path - The File to be loaded.
    * @return The source file.
    */
	public FileConfiguration getSource(final String path) {
		final File file = new File(SignUtils.getInstance().getDataFolder(), path);
		if (!(file).exists()) {
			try {
				InputStream source;
				final File dataDir = SignUtils.getInstance().getDataFolder();
				if (!dataDir.exists()) { dataDir.mkdir(); }
				if (!path.contains("lang.yml")) { source = SignUtils.getInstance().getResource("files/configs/" + path); } 
				else { source = SignUtils.getInstance().getResource("files/locales/" + path); }
        		if (!file.exists()) { Files.copy(source, file.toPath(), new CopyOption[0]); }
			} catch (Exception e) {
				ServerUtils.sendSevereTrace(e);
				ServerUtils.logWarn("Cannot save " + path + " to disk!");
				this.noSource.put(path, true);
				return null;
			}
		}
		try {
			YamlConfiguration config = this.getLoadedConfig(file, true);
			this.noSource.put(path, false);
			return config;
		} catch (Exception e) {
			ServerUtils.sendSevereTrace(e);
			ServerUtils.logSevere("Cannot load " + file.getName() + " from disk!");
			this.noSource.put(file.getName(), true);
		}
		return null;
	}

   /**
    * Gets the file and loads it into memory if specified.
    * 
    * @param file - The file to be loaded.
    * @param commit - If the File should be committed to memory.
    * @return The Memory loaded config file.
    */
	public YamlConfiguration getLoadedConfig(final File file, final boolean commit) throws Exception {
		YamlConfiguration config = new YamlConfiguration();
		if (commit) { config.load(file); }
		if (file.getName().contains("config.yml")) {
			if (commit) { this.configFile = config; }
			return this.configFile;
		} else if (file.getName().contains("lang.yml")) {
			if (commit) { this.langFile = config; }
			return this.langFile;
		}
		return null;
	}

   /**
    * Copies the specified config file to the data folder.
    * 
    * @param configFile - The name and extension of the config file to be copied.
    * @param version - The version String to be checked in the config file.
    * @param id - The expected version id to be found in the config file.
    */
	private void copyFile(final String configFile, final String version, final int id) {
		this.getSource(configFile);
		File File = new File(SignUtils.getInstance().getDataFolder(), configFile);
		if (File.exists() && !this.noSource.get(configFile) && this.getFile(configFile).getInt(version) != id) {
			InputStream source;
			if (!configFile.contains("lang.yml")) { source = SignUtils.getInstance().getResource("files/configs/" + configFile); } 
			else { source = SignUtils.getInstance().getResource("files/locales/" + configFile); }
			if (source != null) {
				String[] namePart = configFile.split("\\.");
				String renameFile = namePart[0] + "-old-" + StringUtils.getRandom(1, 50000) + namePart[1];
				File renamedFile = new File(SignUtils.getInstance().getDataFolder(), renameFile);
				if (!renamedFile.exists()) {
					File.renameTo(renamedFile);
					File copyFile = new File(SignUtils.getInstance().getDataFolder(), configFile);
					copyFile.delete();
					this.getSource(configFile);
					ServerUtils.logWarn("Your " + configFile + " is out of date and new options are available, generating a new one!");
				}
			}
		} else if (this.noSource.get(configFile)) {
			ServerUtils.logSevere("Your " + configFile + " is not using proper YAML Syntax and will not be loaded!");
			ServerUtils.logSevere("Check your YAML formatting by using a YAML-PARSER such as http://yaml-online-parser.appspot.com/");
		}
		if (!this.noSource.get(configFile)) { 
			this.getFile(configFile).options().copyDefaults(false);
			if (configFile.contains("lang.yml")) { LanguageAPI.getLang(false).setPrefix(); }
		}
	}
	
   /**
    * Softly reloads the configuration files.
    * Usefully when editing booleans.
    * 
    */
	public void softReload() {
		this.copyFiles();
	}
	
   /**
    * Properly reloads the configuration files.
    * 
    */
	public void reloadConfigs(final boolean silent) {
		config = new ConfigHandler(); 
        config.registerClasses(silent);
	}
	
   /**
    * Checks if Debugging is enabled.
    * 
    * @return If Debugging is enabled.
    */
	public boolean debugEnabled() {
		return this.getFile("config.yml").getBoolean("General.Debugging");
	}
	
   /**
    * Gets the instance of the ConfigHandler.
    * 
    * @return The ConfigHandler instance.
    */
    public static ConfigHandler getConfig() { 
        if (config == null) {
        	config = new ConfigHandler(); 
        	config.registerClasses(false);
        }
        return config; 
    } 
}