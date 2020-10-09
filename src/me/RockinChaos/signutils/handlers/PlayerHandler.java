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
package me.RockinChaos.signutils.handlers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.domedd.betternick.BetterNick;
import me.RockinChaos.signutils.utils.DependAPI;
import me.RockinChaos.signutils.utils.LegacyAPI;

public class PlayerHandler {
	
	private static PlayerHandler player;

   /**
    * Gets the Player instance from their String name.
    * 
    * @param playerName - The player name to be transformed.
    * @return The fetched Player instance.
    */
	public Player getPlayerString(final String playerName) {
		Player args = null;
		try { args = Bukkit.getPlayer(UUID.fromString(playerName)); } catch (Exception e) {}
		if (playerName != null && DependAPI.getDepends(false).nickEnabled()) {
			try { 
				de.domedd.betternick.api.nickedplayer.NickedPlayer np = new de.domedd.betternick.api.nickedplayer.NickedPlayer(LegacyAPI.getLegacy().getPlayer(playerName));
				if (np.isNicked()) { return LegacyAPI.getLegacy().getPlayer(np.getRealName()); }
				else { return LegacyAPI.getLegacy().getPlayer(playerName); }
			} catch (NoClassDefFoundError e) {
				if (BetterNick.getApi().isPlayerNicked(LegacyAPI.getLegacy().getPlayer(playerName))) { return LegacyAPI.getLegacy().getPlayer(BetterNick.getApi().getRealName(LegacyAPI.getLegacy().getPlayer(playerName))); }
				else { return LegacyAPI.getLegacy().getPlayer(playerName); }
			}
		} else if (args == null) { return LegacyAPI.getLegacy().getPlayer(playerName); }
		return args;
	}
	
   /**
    * Gets the instance of the PlayerHandler.
    * 
    * @return The PlayerHandler instance.
    */
    public static PlayerHandler getPlayer() { 
        if (player == null) { player = new PlayerHandler(); }
        return player; 
    } 
}