package me.RockinChaos.signutils.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.RockinChaos.signutils.handlers.ConfigHandler;
import me.RockinChaos.signutils.handlers.PlayerHandler;
import net.milkbowl.vault.permission.Permission;

public class Utility {
	public static void signRank(CommandSender sender, String otherPlayer) {
		if (ConfigHandler.getDepends().getVault().vaultError(sender, true)) {
		if (otherPlayer != null && !otherPlayer.isEmpty() && PlayerHandler.getPlayerString(otherPlayer) == null) { 
			String[] placeHolders = Language.newString(); 
			placeHolders[4] = otherPlayer; 
			Language.sendLangMessage("Commands.Default.targetNotFound", sender, placeHolders);
		} else {
			Player player = (Player) sender;
			if (otherPlayer != null && !otherPlayer.isEmpty()) { player = PlayerHandler.getPlayerString(otherPlayer); }
			String[] placeHolders = Language.newString();
			int groups = 0;
			Permission permissionGroups = ConfigHandler.getDepends().getVault().getGroups();
			if (permissionGroups != null) { groups = ConfigHandler.getDepends().getVault().getGroups().getPlayerGroups(player).length; }
			placeHolders[2] = Integer.toString(groups);
			Language.sendLangMessage("Signs.Rank.playerLine", sender, placeHolders);
			Language.sendLangMessage("Signs.Rank.groupSizeLine", sender, placeHolders);
			if (permissionGroups != null) {
				for (String groupName : ConfigHandler.getDepends().getVault().getGroups().getPlayerGroups(player)) {
					placeHolders[3] = groupName;
					Language.sendLangMessage("Signs.Rank.groupListLine", sender, placeHolders);
				}
			}
		}
		}
	}
}
