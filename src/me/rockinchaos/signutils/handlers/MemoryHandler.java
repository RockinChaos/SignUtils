package me.RockinChaos.signutils.handlers;

import org.bukkit.Bukkit;

import me.RockinChaos.signutils.Commands;
import me.RockinChaos.signutils.SignUtils;
import me.RockinChaos.signutils.listeners.SignInteract;
import me.RockinChaos.signutils.utils.Language;
import me.RockinChaos.signutils.utils.Metrics;
import me.RockinChaos.signutils.utils.VaultAPI;


public class MemoryHandler {
	private static boolean placeHolderAPI = false;
	private static boolean betterNick = false;
	private static boolean logColoration = true;
	private static boolean metricsLogging = true;
	private static UpdateHandler updater;
	private static Metrics metrics;
	private static boolean Debugging = false;

	public static void generateData() {
		setLogColor();
		setDebugging();
		newPlaceholderAPI();
		newBetterNick();
		newVault();
		setMetricsLogging();
		ConfigHandler.loadOPCommandPermissions();
	}

	public static void registerEvents() {
		setupMetrics();
	    SignUtils.getInstance().getCommand("signutils").setExecutor(new Commands());
	    SignUtils.getInstance().getServer().getPluginManager().registerEvents(new SignInteract(), SignUtils.getInstance());
	}
	
	public static boolean isPlaceholderAPI() {
		return placeHolderAPI;
	}
	
	public static boolean isBetterNick() {
		return betterNick;
	}
	
	public static boolean isLogColor() {
		return logColoration;
	}
	
	public static boolean isDebugging() {
		return Debugging;
	}
	
	public static boolean isMetricsLogging() {
		return metricsLogging;
	}
	
	public static Metrics getMetrics() {
		return metrics;
	}
	
	public static UpdateHandler getUpdater() {
		return updater;
	}
	
	public static void setMetricsLogging() {
		metricsLogging = ConfigHandler.getConfig("config.yml").getBoolean("General.Metrics-Logging");
	}
	
	public static void setUpdater(UpdateHandler update) {
		updater = update;
	}
	
	public static void setLogColor() {
		logColoration = ConfigHandler.getConfig("config.yml").getBoolean("General.Log-Coloration");
	}
	
	public static void setDebugging() {
		Debugging = ConfigHandler.getConfig("config.yml").getBoolean("General.Debugging");
	}

	private static void setupMetrics() {
		if (isMetricsLogging()) { 
			metrics = new Metrics();
		    metrics.addCustomChart(new Metrics.SimplePie("language", Language.getLanguage()));
		    metrics.addCustomChart(new Metrics.SimplePie("softDepend", MemoryHandler.isPlaceholderAPI() ? "PlaceholderAPI" : ""));
			metrics.addCustomChart(new Metrics.SimplePie("softDepend", MemoryHandler.isBetterNick() ? "BetterNick" : ""));
			metrics.addCustomChart(new Metrics.SimplePie("softDepend", VaultAPI.vaultEnabled() ? "Vault" : ""));
		}
	}

	public static void newPlaceholderAPI() {
		if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null && ConfigHandler.getConfig("config.yml").getBoolean("softDepend.PlaceholderAPI") == true) {
			ServerHandler.sendConsoleMessage("&aHooked into PlaceholderAPI!");
			placeHolderAPI = true;
		} else if (ConfigHandler.getConfig("config.yml").getBoolean("softDepend.PlaceholderAPI") == true) {
			ServerHandler.sendConsoleMessage("&4Could not find PlaceholderAPI.");
		}
	}
	
	public static void newBetterNick() {
		if (Bukkit.getServer().getPluginManager().getPlugin("BetterNick") != null && ConfigHandler.getConfig("config.yml").getBoolean("softDepend.BetterNick") == true) {
			ServerHandler.sendConsoleMessage("&aHooked into BetterNick!");
			betterNick = true;
		} else if (ConfigHandler.getConfig("config.yml").getBoolean("softDepend.BetterNick") == true) {
			ServerHandler.sendConsoleMessage("&4Could not find BetterNick.");
		}
	}
	
	public static void newVault() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null && ConfigHandler.getConfig("config.yml").getBoolean("softDepend.Vault") == true) {
			ServerHandler.sendConsoleMessage("&aHooked into Vault!");
			VaultAPI.enableEconomy();
			VaultAPI.setVaultStatus(true);
		} else if (ConfigHandler.getConfig("config.yml").getBoolean("softDepend.Vault") == true) {
			ServerHandler.sendConsoleMessage("&4Could not find Vault or no economy plugin is attached.");
			VaultAPI.setVaultStatus(false);
		}
	}
}