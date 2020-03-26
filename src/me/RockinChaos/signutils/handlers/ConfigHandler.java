package me.RockinChaos.signutils.handlers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.RockinChaos.signutils.Commands;
import me.RockinChaos.signutils.SignUtils;
import me.RockinChaos.signutils.listeners.SignInteract;
import me.RockinChaos.signutils.utils.DependAPI;
import me.RockinChaos.signutils.utils.Language;
import me.RockinChaos.signutils.utils.Metrics;
import me.RockinChaos.signutils.utils.TabComplete;
import me.RockinChaos.signutils.utils.Utils;

public class ConfigHandler {
	private static YamlConfiguration configYAML;
	private static YamlConfiguration langYAML;
	
	private static UpdateHandler updater;
	private static Metrics metrics;
	private static DependAPI depends;
	
	public static void generateData(File file) {
		configFile(); affixLang("en");
		setDepends(new DependAPI());
		setMetrics(new Metrics());
	    if (file != null) { sendUtilityDepends(); setUpdater(new UpdateHandler(file)); }
	}

	public static void registerEvents() {
	    SignUtils.getInstance().getCommand("signutils").setExecutor(new Commands()); 
	    SignUtils.getInstance().getCommand("signutils").setTabCompleter(new TabComplete());
	    SignUtils.getInstance().getServer().getPluginManager().registerEvents(new SignInteract(), SignUtils.getInstance());
	}
	
	public static FileConfiguration getConfig(String path) {
		File file = new File(SignUtils.getInstance().getDataFolder(), path);
		if (configYAML == null) { getConfigData(path); }
		return getPath(path, file, false);
	}
	
    public static void saveDefaultData(String path) throws IOException {
        InputStream source;
        File dataDir = SignUtils.getInstance().getDataFolder();
        if (!dataDir.exists()) { dataDir.mkdir(); }
        if (!path.contains("lang.yml")) { source = SignUtils.getInstance().getResource("files/configs/" + path); } 
        else { source = SignUtils.getInstance().getResource("files/locales/" + path); }
        File file = new File(SignUtils.getInstance().getDataFolder(), path);
        if (!file.exists()) { Files.copy(source, file.toPath(), new CopyOption[0]); }
    }
	
	public static FileConfiguration getConfigData(String path) {
		File file = new File(SignUtils.getInstance().getDataFolder(), path);
		if (!(file).exists()) {
			try {
				saveDefaultData(path);
			} catch (Exception e) {
				ServerHandler.sendDebugTrace(e);
				SignUtils.getInstance().getLogger().warning("Cannot save " + path + " to disk!");
				return null;
			}
		}
		return getPath(path, file, true);
	}

	public static YamlConfiguration getPath(String path, File file, boolean saveData) {
		if (path.contains("config.yml")) {
			if (saveData) { configYAML = YamlConfiguration.loadConfiguration(file); }
			return configYAML;
		} else if (path.contains("lang.yml")) {
			if (saveData) { langYAML = YamlConfiguration.loadConfiguration(file); }
			return langYAML;
		}
		return null;
	}

	public static void configFile() {
		getConfigData("config.yml");
		File File = new File(SignUtils.getInstance().getDataFolder(), "config.yml");
		if (File.exists() && getConfig("config.yml").getInt("config-Version") != 1) {
			if (SignUtils.getInstance().getResource("config.yml") != null) {
				String newGen = "config" + Utils.getRandom(1, 50000) + ".yml";
				File newFile = new File(SignUtils.getInstance().getDataFolder(), newGen);
				if (!newFile.exists()) {
					File.renameTo(newFile);
					File configFile = new File(SignUtils.getInstance().getDataFolder(), "config.yml");
					configFile.delete();
					getConfigData("config.yml");
					ServerHandler.sendConsoleMessage("&aYour config.yml is out of date and new options are available, generating a new one!");
				}
			}
		}
		getConfig("config.yml").options().copyDefaults(false);
	}
	
	public static void affixLang(String affix) {
		getConfigData(affix + "-lang.yml");
		Language.setLanguage(affix);
		File affixLang = new File(SignUtils.getInstance().getDataFolder(), affix + "-lang.yml");
		if (affixLang.exists() && getConfig(affix + "-lang.yml").getInt(affix + "-Version") != 1) {
			if (SignUtils.getInstance().getResource(affix + "-lang.yml") != null) {
				String newGen = affix + "-lang" + Utils.getRandom(1, 50000) + ".yml";
				File newFile = new File(SignUtils.getInstance().getDataFolder(), newGen);
				if (!newFile.exists()) {
					affixLang.renameTo(newFile);
					File configFile = new File(SignUtils.getInstance().getDataFolder(), affix + "-lang.yml");
					configFile.delete();
					getConfigData(affix + "-lang.yml");
					ServerHandler.sendConsoleMessage("&4Your " + affix + "-lang.yml is out of date and new options are available, generating a new one!");
				}
			}
		}
		getConfig(affix + "-lang.yml").options().copyDefaults(false);
	}
	
	private static void sendUtilityDepends() {
		ServerHandler.sendConsoleMessage("&aFetched [{ &e" + (getDepends().nickEnabled() ? "BetterNick, " : "") + (getDepends().placeHolderEnabled() ? "PlaceholderAPI, " : "") + (getDepends().getVault().vaultEnabled() ? "Vault " : "") + "&a}]");
	}
	
	public Metrics getMetrics() {
		return metrics;
	}
	
	private static void setMetrics(Metrics metric) {
		metrics = metric;
	}
	
	public static UpdateHandler getUpdater() {
		return updater;
	}
	
	public static void setUpdater(UpdateHandler update) {
		updater = update;
	}
	
	public static DependAPI getDepends() {
		return depends;
	}
	
	private static void setDepends(DependAPI depend) {
		depends = depend;
	}
	
	public static boolean isDebugging() {
		return ConfigHandler.getConfig("config.yml").getBoolean("General.Debugging");
	}
	
	public static boolean isLoggable() {
		return ConfigHandler.getConfig("config.yml").getBoolean("General.Log-Commands");
	}
	
	public static boolean isLogColor() {
		return ConfigHandler.getConfig("config.yml").getBoolean("General.Log-Coloration");
	}
	
	public static boolean getCommandPermissions() {
		return ConfigHandler.getConfig("config.yml").getBoolean("Permissions.Commands-OP");
	}
}