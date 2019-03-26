package me.RockinChaos.signutils.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;

import me.RockinChaos.signutils.SignUtils;
import me.RockinChaos.signutils.handlers.ServerHandler;
import net.milkbowl.vault.permission.Permission;

public class VaultAPI {
    private static boolean isEnabled = false;
    private static Permission permission;
    
	public static void enableEconomy() { 
		if (SignUtils.getInstance().getServer().getPluginManager().getPlugin("Vault") != null) {
			if (!setupPermissions()) {
		      ServerHandler.sendErrorMessage("There was an issue setting up Vault Permissions!");
		      ServerHandler.sendErrorMessage("If this continues, please contact the plugin developer!");
		      return;
			}
		}
	}
    
    private static boolean setupPermissions() {
      if (SignUtils.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) {  return false; }
      RegisteredServiceProvider<Permission> permissionProvider = SignUtils.getInstance().getServer().getServicesManager().getRegistration(Permission.class);
      if (permissionProvider != null) { permission = ((Permission)permissionProvider.getProvider()); }
      return permission != null;
    }
    
    public static Permission getGroups() {
    	return permission;
    }
    
    public static boolean vaultError(CommandSender sender, boolean sendMessage) {
    	if (vaultEnabled()) { return true; }
    	else if (sendMessage) { 
    		String[] placeHolders = Language.newString(); placeHolders[5] = "Vault";
			Language.sendLangMessage("Signs.Default.missingDependency", sender, placeHolders);
    	}
    	return false;
    }
    
    public static boolean vaultEnabled() {
    	return isEnabled;
    }
    
    public static void setVaultStatus(boolean bool) {
    	isEnabled = bool;
    }
}