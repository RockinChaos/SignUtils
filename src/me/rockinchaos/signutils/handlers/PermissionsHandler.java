package me.rockinchaos.signutils.handlers;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PermissionsHandler {
	
	public static boolean isAuthorized(CommandSender sender, String permission) {
		if (sender.hasPermission(permission) || sender.hasPermission("signutils.*") || isDeveloper(sender) || (sender instanceof ConsoleCommandSender)) {
			return true;
		} else if (!ConfigHandler.getOPCommandPermissions() && sender.isOp()) {
			if (permission.equalsIgnoreCase("signutils.use") || permission.equalsIgnoreCase("signutils.reload") || permission.equalsIgnoreCase("signutils.updates")
					|| permission.equalsIgnoreCase("signutils.autoupdate") || permission.equalsIgnoreCase("signutils.create") || permission.equalsIgnoreCase("signutils.rank") 
					|| permission.equalsIgnoreCase("signutils.rank.others") || isDeveloper(sender)) {
				return true;
			}
		}
		return false;
	}
	
    /**
     * If Debugging Mode is enabled, the plugin developer will be allowed to execute ONLY this plugins commands for help and support purposes.
     */
	private static boolean isDeveloper(CommandSender sender) {
		if (MemoryHandler.isDebugging()) {
			if (sender instanceof Player) {
				try { if (((Player)sender).getUniqueId().toString().equalsIgnoreCase("ad6e8c0e-6c47-4e7a-a23d-8a2266d7baee")) { return true; }
				} catch (Exception e) { if (sender.getName().equalsIgnoreCase("RockinChaos")) { return true; } }
			}
		}
		return false;
	}
}