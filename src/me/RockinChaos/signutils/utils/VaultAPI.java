package me.RockinChaos.signutils.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;

import me.RockinChaos.signutils.SignUtils;
import me.RockinChaos.signutils.handlers.ConfigHandler;
import me.RockinChaos.signutils.handlers.ServerHandler;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultAPI {
    private Economy econ = null;
    private Permission permission;
    private boolean isEnabled = false;
    
    public VaultAPI() {
    	this.setVaultStatus(Bukkit.getServer().getPluginManager().getPlugin("Vault") != null);
    }
    
	private void enableEconomy() { 
		if (ConfigHandler.getConfig("config.yml").getBoolean("softDepend.Vault") && SignUtils.getInstance().getServer().getPluginManager().getPlugin("Vault") != null) {
			if (!this.setupEconomy()) {
				ServerHandler.sendErrorMessage("There was an issue setting up Vault Economy!");
				ServerHandler.sendErrorMessage("If this continues, please contact the plugin developer!");
			}
			if (!this.setupPermissions()) {
				ServerHandler.sendErrorMessage("There was an issue setting up Vault Permissions!");
				ServerHandler.sendErrorMessage("If this continues, please contact the plugin developer!");
				return;
			}
		}
	}

    private boolean setupEconomy() {
        if (SignUtils.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) { return false; }
        RegisteredServiceProvider<Economy> rsp = SignUtils.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) { return false; }
        this.econ = rsp.getProvider();
        return this.econ != null;
    }
    
    private boolean setupPermissions() {
    	if (SignUtils.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) { return false; }
        RegisteredServiceProvider<Permission> permissionProvider = SignUtils.getInstance().getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) { permission = ((Permission)permissionProvider.getProvider()); }
        return this.permission != null;
    }
      
    public Permission getGroups() {
    	return this.permission;
    }
    
    public Economy getEconomy() {
        return this.econ;
    }
    
    public boolean vaultError(CommandSender sender, boolean sendMessage) {
    	if (this.isEnabled) { return true; }
    	else if (sendMessage) { 
    		String[] placeHolders = Language.newString(); placeHolders[5] = "Vault";
			Language.sendLangMessage("Signs.Default.missingDependency", sender, placeHolders);
    	}
    	return false;
    }
    
    public boolean vaultEnabled() {
    	return this.isEnabled;
    }
    
    private void setVaultStatus(boolean bool) {
    	if (bool) { this.enableEconomy(); }
    	this.isEnabled = bool;
    }
}