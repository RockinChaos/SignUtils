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
package me.RockinChaos.signutils.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;

import me.RockinChaos.signutils.SignUtils;
import me.RockinChaos.signutils.handlers.ServerHandler;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultAPI {
    private Economy econ = null;
    private Permission permission;
    private boolean isEnabled = false;
    
    private static VaultAPI vault;
    
   /**
	* Creates a new VaultAPI instance.
	* 
	*/
    public VaultAPI() {
    	this.setVaultStatus(Bukkit.getServer().getPluginManager().getPlugin("Vault") != null);
    }
    
   /**
	* Attempts to enable the economy.
	* 
	*/
	private void enableEconomy() { 
		if (SignUtils.getInstance().getServer().getPluginManager().getPlugin("Vault") != null) {
			if (!this.setupEconomy()) {
				ServerHandler.getServer().logDebug("{VaultAPI} An error has occured while setting up enabling Vault-ItemJoin support!");
			}
			if (!this.setupPermissions()) {
				ServerHandler.getServer().logSevere("{VaultAPI} There was an issue setting up Vault Permissions!");
				ServerHandler.getServer().logSevere("{VaultAPI} If this continues, please contact the plugin developer!");
				return;
			}
		}
	}

   /**
	* Sets the Economy instance.
	* 
	* @return If the Economy instance was successfully enabled.
	*/
    private boolean setupEconomy() {
        if (SignUtils.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) { return false; }
        RegisteredServiceProvider<Economy> rsp = SignUtils.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) { return false; }
        this.econ = rsp.getProvider();
        return this.econ != null;
    }
    
   /**
	* Sets the Permissions instance.
	* 
	* @return If the Permissions instance was successfully enabled.
	*/
    private boolean setupPermissions() {
    	if (SignUtils.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) { return false; }
        RegisteredServiceProvider<Permission> permissionProvider = SignUtils.getInstance().getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) { 
        	this.permission = ((Permission)permissionProvider.getProvider());
        	try { this.permission.getGroups(); } catch (Exception e) { permissionProvider = null;}
        }
        return this.permission != null;
    }
    
   /**
	* Gets the Vault Economy instance.
	* 
	* @return Gets the current economy instance.
	*/
    public Economy getEconomy() {
        return this.econ;
    }
    
   /**
	* Gets the Vault Permissions instance.
	* 
	* @return Gets the current permission instance.
	*/
    public Permission getPermissions() {
    	return this.permission;
    }
    
   /**
	* Sets the Economy instance.
	* 
	* @param sender - The CommandSender.
	* @param sendMessage - If the message should be sent or not.
	* @return If the Economy instance was successfully enabled.
	*/
    public boolean vaultError(CommandSender sender, boolean sendMessage) {
    	if (this.isEnabled) { return true; }
    	else if (sendMessage) { 
    		String[] placeHolders = LanguageAPI.getLang(false).newString(); placeHolders[5] = "Vault";
			LanguageAPI.getLang(false).sendLangMessage("Signs.Default.missingDependency", sender, placeHolders);
    	}
    	return false;
    }
    
   /**
	* Checks if Vault is enabled.
	* 
	* @return If Vault is enabled.
	*/
    public boolean vaultEnabled() {
    	return this.isEnabled;
    }
    
   /**
	* Sets the status of the VaultAPI.
	* 
	* @param bool - If Vault is enabled.
	*/
    private void setVaultStatus(final boolean bool) {
    	if (bool) { this.enableEconomy(); }
    	this.isEnabled = bool;
    }
	
   /**
    * Gets the instance of the VaultAPI.
    * 
    * @param regen - If the VaultAPI should have a new instance created.
    * @return The VaultAPI instance.
    */
    public static VaultAPI getVault(final boolean regen) { 
        if (vault == null || regen) { vault = new VaultAPI(); }
        return vault; 
    } 
}