package me.RockinChaos.signutils.handlers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.RockinChaos.signutils.SignUtils;

public class ServerHandler {

	public static boolean hasCombatUpdate() {
		String pkgname = SignUtils.getInstance().getServer().getClass().getPackage().getName();
		String combatVersion = "v1_9_R0".replace("_", "").replace("R0", "").replace("R1", "").replace("R2", "").replace("R3", "").replace("R4", "").replace("R5", "").replaceAll("[a-z]", "");
		String version = pkgname.substring(pkgname.lastIndexOf('.') + 1).replace("_", "").replace("R0", "").replace("R1", "").replace("R2", "").replace("R3", "").replace("R4", "").replace("R5", "").replaceAll("[a-z]", "");
		if (Integer.parseInt(version) >= Integer.parseInt(combatVersion)) {
			return true;
		}
		return false;
	}
	
	public static void sendConsoleMessage(String message) {
		String prefix = "&7[&5SignUtils&7] ";
		message = prefix + message;
		message = ChatColor.translateAlternateColorCodes('&', message).toString();
		if (!MemoryHandler.isLogColor()) {
			message = ChatColor.stripColor(message);
		}
		if (message.equalsIgnoreCase("") || message.isEmpty()) {
			message = "";
		}
		SignUtils.getInstance().getServer().getConsoleSender().sendMessage(message);
	}
	
	public static void sendErrorMessage(String message) {
		String prefix = "&e[&4SIGNUTILS_ERROR&e]&c ";
		message = prefix + message;
		message = ChatColor.translateAlternateColorCodes('&', message).toString();
		if (!MemoryHandler.isLogColor()) {
			message = ChatColor.stripColor(message);
		}
		if (message.equalsIgnoreCase("") || message.isEmpty()) {
			message = "";
	}
		SignUtils.getInstance().getServer().getConsoleSender().sendMessage(message);
	}
	
	public static void sendPlayerMessage(Player player, String message) {
		String prefix = "&7[&5SignUtils&7] ";
		message = prefix + message;
		message = ChatColor.translateAlternateColorCodes('&', message).toString();
			if (message.contains("blankmessage")) {
				message = "";
		}
		player.sendMessage(message);
	}
	
	public static void sendMessage(CommandSender sender, String message) {
		String prefix = "&7[&5SignUtils&7] ";
		message = prefix + message;
		message = ChatColor.translateAlternateColorCodes('&', message).toString();
		if	(!MemoryHandler.isLogColor()) {
			message = ChatColor.stripColor(message);
		}
		sender.sendMessage(message);
	}
	
	public static void sendDebugTrace(Exception e) {
		if (MemoryHandler.isDebugging()) { e.printStackTrace(); }
	}

	public static void sendDebugMessage(String message) {
		if (MemoryHandler.isDebugging()) {
			String prefix = "[SIGNUTILS_DEBUG] &c";
			message = ChatColor.translateAlternateColorCodes('&', message).toString();
			message = ChatColor.stripColor(message);
			message = prefix + message;
			message = ChatColor.translateAlternateColorCodes('&', message).toString();
			SignUtils.getInstance().getServer().getConsoleSender().sendMessage(message);
		}
	}
}