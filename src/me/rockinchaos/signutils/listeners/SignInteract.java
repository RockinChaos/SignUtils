package me.rockinchaos.signutils.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.rockinchaos.signutils.handlers.PermissionsHandler;
import me.rockinchaos.signutils.utils.EffectsAPI;
import me.rockinchaos.signutils.utils.Language;
import me.rockinchaos.signutils.utils.Utils;
import me.rockinchaos.signutils.utils.VaultAPI;

public class SignInteract implements Listener {

	@EventHandler
	private void onSignClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && Utils.containsIgnoreCase(event.getClickedBlock().getType().name(), "SIGN")) {
			Sign sign = (Sign) event.getClickedBlock().getState();
			String signType = ChatColor.stripColor(sign.getLine(0));
			if (signType.equalsIgnoreCase(Language.returnLangMessage("Signs.Rank.signLine", player, false))) {
				String[] placeHolders = Language.newString(); placeHolders[2] = Integer.toString(VaultAPI.getGroups().getPlayerGroups(player).length);
				Language.sendLangMessage("Signs.Rank.playerLine", player, placeHolders);
				Language.sendLangMessage("Signs.Rank.groupSizeLine", player, placeHolders);
				for (String groupName : VaultAPI.getGroups().getPlayerGroups(player)) {
					placeHolders[3] = groupName;
					Language.sendLangMessage("Signs.Rank.groupListLine", player, placeHolders);
				}
			}
		}
	}
	
	@EventHandler
	private void onSignPlace(SignChangeEvent event) {
		Player player = event.getPlayer();
		if (event.getLine(0).replaceAll("(&([a-f0-9]))", "").equalsIgnoreCase(Language.returnLangMessage("Signs.Rank.signLine", player, false))) {
			if (PermissionsHandler.isAuthorized(player, "signutils.create")) {
				if (this.setDefault(event.getLines())) { for (int i = 0; i < 4; i++) { event.setLine(i, defaultSignLines(player, event.getLines(), i)); } }
				else { for (int i = 0; i < 4; i++) { event.setLine(i, Utils.translateLayout(event.getLine(i), player)); } } 
				EffectsAPI.playEffect(event.getBlock(), Particle.VILLAGER_HAPPY, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
				Language.sendLangMessage("Signs.Default.signCreated", player);
			} else {
				EffectsAPI.playEffect(event.getBlock(), Particle.VILLAGER_ANGRY, Sound.ENTITY_VILLAGER_HURT);
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