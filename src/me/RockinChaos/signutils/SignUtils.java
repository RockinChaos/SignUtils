package me.RockinChaos.signutils;

import org.bukkit.plugin.java.JavaPlugin;

import me.RockinChaos.signutils.handlers.ConfigHandler;
import me.RockinChaos.signutils.handlers.MemoryHandler;
import me.RockinChaos.signutils.handlers.ServerHandler;
import me.RockinChaos.signutils.handlers.UpdateHandler;

public class SignUtils extends JavaPlugin {
  private static SignUtils instance;
  
	@Override
	public void onEnable() {
      instance = this;
      ConfigHandler.loadConfigs();
      MemoryHandler.setUpdater(new UpdateHandler(getFile()));
      MemoryHandler.generateData();
      MemoryHandler.registerEvents();
      ServerHandler.sendConsoleMessage("&ahas been Enabled.");
	}
	
	@Override
	public void onDisable() {
		ServerHandler.sendConsoleMessage("&4has been Disabled.");
	}
  
  public static SignUtils getInstance() {
	  return instance;
  }
}