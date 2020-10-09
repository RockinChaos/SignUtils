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

import me.RockinChaos.signutils.handlers.ServerHandler;

public class DependAPI {
	private boolean placeHolderAPI = false;
	private boolean betterNick = false;
	
	private static DependAPI depends;
	
   /**
    * Creates a new DependAPI instance.
    * 
    */
	public DependAPI() {
		this.setPlaceHolderStatus(Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null);
		this.setNickStatus(Bukkit.getServer().getPluginManager().getPlugin("BetterNick") != null);
		VaultAPI.getVault(true);
	}
    
   /**
    * Checks if PlaceHolderAPI is Enabled.
    * 
    * @return If PlaceHolderAPI is Enabled.
    */
    public boolean placeHolderEnabled() {
    	return this.placeHolderAPI;
    }
    
   /**
    * Checks if BetterNick is Enabled.
    * 
    * @return If BetterNick is Enabled.
    */
    public boolean nickEnabled() {
    	return this.betterNick;
    }
    
   /**
    * Sets the status of PlaceHolderAPI.
    * 
    * @param bool - If PlaceHolderAPI is enabled.
    */
    public void setPlaceHolderStatus(final boolean bool) {
    	this.placeHolderAPI = bool;
    }
    
   /**
    * Sets the status of BetterNick.
    * 
    * @param bool - If BetterNick is enabled.
    */
    public void setNickStatus(final boolean bool) {
    	this.betterNick = bool;
    }
	
   /**
    * Gets the VaultAPI instance.
    * 
    * @return The current VaultAPI instance.
    */
	public VaultAPI getVault() {
		return VaultAPI.getVault(false);
	}
	
   /**
    * Sends a logging message of the found and enabled soft dependencies.
    * 
    */
	public void sendUtilityDepends() {
		String enabledPlugins = (this.nickEnabled() ? "BetterNick, " : "") + (this.placeHolderEnabled() ? "PlaceholderAPI, " : "") + (this.getVault().vaultEnabled() ? "Vault " : "");
		if (!enabledPlugins.isEmpty()) { ServerHandler.getServer().logInfo("Hooked into { " + enabledPlugins + "}"); }
	}
	
   /**
    * Gets the instance of the DependAPI.
    * 
    * @param regen - If the DependAPI should have a new instance created.
    * @return The DependAPI instance.
    */
    public static DependAPI getDepends(final boolean regen) { 
        if (depends == null || regen) { depends = new DependAPI(); }
        return depends; 
    } 
}
