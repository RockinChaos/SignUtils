package me.RockinChaos.signutils.handlers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.domedd.betternick.api.nickedplayer.NickedPlayer;
import me.RockinChaos.signutils.utils.Legacy;

public class PlayerHandler {

	public static Player getPlayerString(String playerName) {
		Player args = null;
		try { args = Bukkit.getPlayer(UUID.fromString(playerName)); } catch (Exception e) {}
		if (playerName != null && MemoryHandler.isBetterNick()) {
			NickedPlayer np = new NickedPlayer(Legacy.getLegacyPlayer(playerName));
			if (np.isNicked()) {
			return Legacy.getLegacyPlayer(np.getRealName());
			} else {
				return Legacy.getLegacyPlayer(playerName);
			}
		} else if (args == null) { return Legacy.getLegacyPlayer(playerName); }
		return args;
	}
}
