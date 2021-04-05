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
package me.RockinChaos.signutils.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.RockinChaos.signutils.handlers.PermissionsHandler;
import me.RockinChaos.signutils.signs.Ranks;
import me.RockinChaos.signutils.utils.StringUtils;
import me.RockinChaos.signutils.utils.api.DependAPI;
import me.RockinChaos.signutils.utils.api.EffectAPI;
import me.RockinChaos.signutils.utils.api.LanguageAPI;

public class SignInteract implements Listener {

   /**
	* Checks for the players associated groups when clicking a sign.
	* 
	* @param event - PlayerInteractEvent
	*/
	@EventHandler
	private void onSignClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && StringUtils.containsIgnoreCase(event.getClickedBlock().getType().name(), "SIGN")) {
			Sign sign = (Sign) event.getClickedBlock().getState();
			if (ChatColor.stripColor(sign.getLine(0)).equalsIgnoreCase(ChatColor.stripColor(StringUtils.translateLayout(LanguageAPI.getLang(false).getLangMessage("signs.rank.signLine"), player)))) {
				Ranks.signRank(player, null);
			}
		}
	}
	
   /**
	* Sets the sign information when creating a utility sign.
	* 
	* @param event - SignChangeEvent
	*/
	@EventHandler
	private void onSignPlace(SignChangeEvent event) {
		Player player = event.getPlayer();
		if (ChatColor.stripColor(event.getLine(0)).equalsIgnoreCase(ChatColor.stripColor(StringUtils.translateLayout(LanguageAPI.getLang(false).getLangMessage("signs.rank.signLine"), player)))) {
			if (PermissionsHandler.hasPermission(player, "signutils.create") && DependAPI.getDepends(false).getVault().vaultError(player, true)) {
				if (this.setDefault(event.getLines())) { for (int i = 0; i < 4; i++) { event.setLine(i, this.defaultSignLines(player, event.getLines(), i)); } }
				else { for (int i = 0; i < 4; i++) { event.setLine(i, StringUtils.translateLayout(event.getLine(i), player)); } } 
				EffectAPI.playEffect(event.getBlock(), "VILLAGER_HAPPY", "ENTITY_EXPERIENCE_ORB_PICKUP");
				LanguageAPI.getLang(false).sendLangMessage("signs.default.signCreated", player);
			} else if (!DependAPI.getDepends(false).getVault().vaultError(player, false)) {
				EffectAPI.playEffect(event.getBlock(), "VILLAGER_ANGRY", "ENTITY_VILLAGER_HURT");
				event.setCancelled(true);
			} else {
				EffectAPI.playEffect(event.getBlock(), "VILLAGER_ANGRY", "ENTITY_VILLAGER_HURT");
				LanguageAPI.getLang(false).sendLangMessage("signs.default.noPermission", player);
				event.setCancelled(true);
			}
		}
	}
	
   /**
    * Checks if the default sign lines should be used.
    * 
    * @param lines - The defined sign lines.
    * @return If the default sign lines should be used.
    */
	private boolean setDefault(String[] lines) {
		for (int i = 1; i < 4; i++) {
			String signLine = lines[i];
			if (!signLine.isEmpty()) { return false; }
		}
		return true;
	}
	
   /**
    * Attempts to get the specified sign line.
    * 
    * @param player - The Player that interacted with the sign.
    * @param lines - The defined sign lines.
    * @param i - The sign line to be fetched.
    * @return The located sign line.
    */
	private String defaultSignLines(Player player, String[] lines, int i) {
		switch(i) {
		   case 0 : return StringUtils.translateLayout(lines[0], player);
		   case 2 : return StringUtils.translateLayout("Click to Display", player);
		   case 3 : return StringUtils.translateLayout("Your Groups", player);
		   default : return "";
		}
	}
}