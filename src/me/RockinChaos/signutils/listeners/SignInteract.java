package me.RockinChaos.signutils.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.RockinChaos.signutils.handlers.ConfigHandler;
import me.RockinChaos.signutils.handlers.PermissionsHandler;
import me.RockinChaos.signutils.utils.EffectsAPI;
import me.RockinChaos.signutils.utils.Language;
import me.RockinChaos.signutils.utils.Utility;
import me.RockinChaos.signutils.utils.Utils;

public class SignInteract implements Listener {

	@EventHandler
	private void onSignClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && Utils.containsIgnoreCase(event.getClickedBlock().getType().name(), "SIGN")) {
			Sign sign = (Sign) event.getClickedBlock().getState();
			if (ChatColor.stripColor(sign.getLine(0)).equalsIgnoreCase(ChatColor.stripColor(Language.returnLangMessage("Signs.Rank.signLine", player, false)))) {
				Utility.signRank(player, null);
			}
		}
	}
	
	@EventHandler
	private void onSignPlace(SignChangeEvent event) {
		Player player = event.getPlayer();
		if (ChatColor.stripColor(event.getLine(0)).equalsIgnoreCase(ChatColor.stripColor(Language.returnLangMessage("Signs.Rank.signLine", player, false)))) {
			if (PermissionsHandler.isAuthorized(player, "signutils.create") && ConfigHandler.getDepends().getVault().vaultError(player, true)) {
				if (this.setDefault(event.getLines())) { for (int i = 0; i < 4; i++) { event.setLine(i, this.defaultSignLines(player, event.getLines(), i)); } }
				else { for (int i = 0; i < 4; i++) { event.setLine(i, Utils.translateLayout(event.getLine(i), player)); } } 
				EffectsAPI.playEffect(event.getBlock(), "VILLAGER_HAPPY", "ENTITY_EXPERIENCE_ORB_PICKUP");
				Language.sendLangMessage("Signs.Default.signCreated", player);
			} else if (!ConfigHandler.getDepends().getVault().vaultError(player, false)) {
				EffectsAPI.playEffect(event.getBlock(), "VILLAGER_ANGRY", "ENTITY_VILLAGER_HURT");
				event.setCancelled(true);
			} else {
				EffectsAPI.playEffect(event.getBlock(), "VILLAGER_ANGRY", "ENTITY_VILLAGER_HURT");
				Language.sendLangMessage("Signs.Default.noPermission", player);
				event.setCancelled(true);
			}
		}
	}
	
	private boolean setDefault(String[] lines) {
		for (int i = 1; i < 4; i++) {
			String signLine = lines[i];
			if (!signLine.isEmpty()) { return false; }
		}
		return true;
	}
	
	private String defaultSignLines(Player player, String[] lines, int i) {
		switch(i) {
		   case 0 : return Utils.translateLayout(lines[0], player);
		   case 2 : return Utils.translateLayout("Click to Display", player);
		   case 3 : return Utils.translateLayout("Your Groups", player);
		   default : return "";
		}
	}
}