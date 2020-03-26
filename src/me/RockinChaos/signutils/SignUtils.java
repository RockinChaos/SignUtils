package me.RockinChaos.signutils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.RockinChaos.signutils.handlers.ConfigHandler;
import me.RockinChaos.signutils.handlers.ServerHandler;

public class SignUtils extends JavaPlugin {
	private static SignUtils instance;
  
	@Override
	public void onEnable() {
		instance = this;
		ConfigHandler.generateData(getFile());
		ConfigHandler.registerEvents();
		ServerHandler.sendConsoleMessage("&ahas been Enabled.");
	}
	
	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		ServerHandler.sendConsoleMessage("&4has been Disabled.");
	}
  
    public static SignUtils getInstance() {
	    return instance;
    }
}