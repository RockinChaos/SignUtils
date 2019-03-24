package me.rockinchaos.signutils;

import org.bukkit.plugin.java.JavaPlugin;

import me.rockinchaos.signutils.handlers.ConfigHandler;
import me.rockinchaos.signutils.handlers.MemoryHandler;
import me.rockinchaos.signutils.handlers.ServerHandler;
import me.rockinchaos.signutils.handlers.UpdateHandler;
import me.rockinchaos.signutils.utils.VaultAPI;

public class SignUtils extends JavaPlugin {
  private static SignUtils instance;
  
	@Override
	public void onEnable() {
      instance = this;
      ConfigHandler.loadConfigs();
      MemoryHandler.setUpdater(new UpdateHandler(getFile()));
      MemoryHandler.generateData();
      MemoryHandler.registerEvents();
      if (!VaultAPI.vaultEnabled()) {
    	  ServerHandler.sendConsoleMessage("&4disabling due to missing the required Vault dependency!");
    	  getServer().getPluginManager().disablePlugin(this);
    	  return;
      }
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