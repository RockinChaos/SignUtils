package me.RockinChaos.signutils.utils;

import org.bukkit.Bukkit;

public class DependAPI {
	private boolean placeHolderAPI = false;
	private boolean betterNick = false;
	private VaultAPI vault;
	
	public DependAPI() {
		this.setPlaceHolderStatus(Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null);
		this.setNickStatus(Bukkit.getServer().getPluginManager().getPlugin("BetterNick") != null);
		this.setVault();
	}
    
    public boolean placeHolderEnabled() {
    	return this.placeHolderAPI;
    }
    
    public boolean nickEnabled() {
    	return this.betterNick;
    }
    
    public void setPlaceHolderStatus(boolean bool) {
    	this.placeHolderAPI = bool;
    }
    
    public void setNickStatus(boolean bool) {
    	this.betterNick = bool;
    }
	
	public VaultAPI getVault() {
		return this.vault;
	}
	
	private void setVault() {
		this.vault = new VaultAPI();
	}
}
