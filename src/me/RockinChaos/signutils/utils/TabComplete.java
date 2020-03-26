package me.RockinChaos.signutils.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import me.RockinChaos.signutils.handlers.PermissionsHandler;
import me.RockinChaos.signutils.handlers.ServerHandler;

public class TabComplete implements TabCompleter {
	
	@Override
    public List < String > onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    	final List < String > completions = new ArrayList < > ();
    	final List < String > commands = new ArrayList < > ();
    	Collection < ? > playersOnlineNew = null;
    	Player[] playersOnlineOld;
    	if (args.length == 2 && args[0].equalsIgnoreCase("help") && PermissionsHandler.isAuthorized(sender, "singutils.use")) {
    		commands.add("2");
    	} else if (args.length == 2 && args[0].equalsIgnoreCase("rank") && PermissionsHandler.isAuthorized(sender, "signutils.rank.others")) {
    		try {
    			if (Bukkit.class.getMethod("getOnlinePlayers", new Class < ? > [0]).getReturnType() == Collection.class) {
    				if (Bukkit.class.getMethod("getOnlinePlayers", new Class < ? > [0]).getReturnType() == Collection.class) {
    					playersOnlineNew = ((Collection < ? > ) Bukkit.class.getMethod("getOnlinePlayers", new Class < ? > [0]).invoke(null, new Object[0]));
    					for (Object objPlayer: playersOnlineNew) {
    						commands.add(((Player) objPlayer).getName());
    					}
    				}
    			} else {
    				playersOnlineOld = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers", new Class < ? > [0]).invoke(null, new Object[0]));
    				for (Player player: playersOnlineOld) {
    					commands.add(player.getName());
    				}
    			}
    		} catch (Exception e) {
    			ServerHandler.sendDebugTrace(e);
    		}
    	} else if (args.length == 1) {
    		if (PermissionsHandler.isAuthorized(sender, "signutils.use")) { commands.add("help"); }
    		if (PermissionsHandler.isAuthorized(sender, "signutils.permissions")) { commands.add("permissions"); }
    		if (PermissionsHandler.isAuthorized(sender, "signutils.rank")) { commands.add("rank"); }
    		if (PermissionsHandler.isAuthorized(sender, "signutils.reload")) { commands.add("reload"); }
    		if (PermissionsHandler.isAuthorized(sender, "signutils.updates")) { commands.add("updates"); }
    		if (PermissionsHandler.isAuthorized(sender, "signutils.autoupdate")) { commands.add("autoupdate"); }
    	}
    	StringUtil.copyPartialMatches(args[(args.length - 1)], commands, completions);
    	Collections.sort(completions);
    	return completions;
    }
}