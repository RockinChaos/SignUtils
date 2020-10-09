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
import org.bukkit.entity.Player;

/**
 * Welcome to the magical land of make-believe.
 * These are Deprecated Legacy Methods and/or non-functioning methods
 * that exist to support legacy versions of Minecraft.
 * 
 */
public class LegacyAPI {
	
	private static LegacyAPI legacy;
	
   /**
    * Gets the Bukkit Player from their String name.
    * 
    * @param playerName - The String name of the Bukkit Player.
    * @return The found Player.
    */
	public Player getPlayer(final String playerName) {
		return Bukkit.getPlayer(playerName);
	}
    
   /**
    * Gets the instance of the LegacyAPI.
    * 
    * @return The LegacyAPI instance.
    */
    public static LegacyAPI getLegacy() { 
        if (legacy == null) { legacy = new LegacyAPI(); }
        return legacy; 
    }
}