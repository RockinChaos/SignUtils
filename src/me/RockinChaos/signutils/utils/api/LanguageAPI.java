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
package me.RockinChaos.signutils.utils.api;

import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.RockinChaos.signutils.ChatComponent;
import me.RockinChaos.signutils.ChatComponent.ClickAction;
import me.RockinChaos.signutils.ChatComponent.ClickEvent;
import me.RockinChaos.signutils.ChatComponent.TextSection;
import me.RockinChaos.signutils.handlers.ConfigHandler;
import me.RockinChaos.signutils.utils.ServerUtils;
import me.RockinChaos.signutils.utils.StringUtils;
	
public class LanguageAPI {
	private Lang langType = Lang.ENGLISH;
	private String langPrefix = "SignUtils";
	
    private static LanguageAPI lang;
	
   /**
    * Executes a Message to the Sender.
    * 
    * @param sender - The sender receiving the Message.
    * @param langMessage - The Message being sent.
    */
	public void dispatchMessage(final CommandSender sender, String langMessage) { 
		Player player = null; if (sender instanceof Player) { player = (Player) sender; }
		langMessage = StringUtils.translateLayout(langMessage, player);
		if (sender instanceof ConsoleCommandSender) { langMessage = ChatColor.stripColor(langMessage); } 
		sender.sendMessage(langMessage);
	}
	
   /**
    * Executes a Message to the Sender.
    * 
    * @param sender - The sender receiving the Message.
    * @param langMessage - The Message being sent.
    * @param hoverMessage - The Hoverable Message being attached to langMessage.
    * @param clickMessage - The Clicked Message being attached to langMessage.
    */
	public void dispatchMessage(final CommandSender sender, String langMessage, String hoverMessage, String clickMessage, final ClickAction action) { 
		Player player = null; if (sender instanceof Player) { player = (Player) sender; }
		langMessage = StringUtils.translateLayout(langMessage, player);
		hoverMessage = (hoverMessage != null ? StringUtils.translateLayout(hoverMessage, player) : null);
		clickMessage = (clickMessage != null ? StringUtils.translateLayout(clickMessage, player) : null);
		if (sender instanceof ConsoleCommandSender) { 
			langMessage = ChatColor.stripColor(langMessage); 
			sender.sendMessage(langMessage);
		} else if (ServerUtils.hasSpecificUpdate("1_8")) {
			TextSection textComponent = ChatComponent.of(langMessage);
			if (hoverMessage != null) {
				TextSection.HoverEvent hoverEvent = new TextSection.HoverEvent(ChatComponent.of(hoverMessage));
				textComponent.hoverEvent(hoverEvent);
			}
			if (clickMessage != null) {
				ClickEvent clickEvent = new ClickEvent();
				clickEvent.action(action);
				clickEvent.click(clickMessage);
				textComponent.clickEvent(clickEvent);
			}
			ChatComponent.sendTo(textComponent, player);
		} else {
			sender.sendMessage(langMessage);
		}
	}
	
   /**
    * Executes a Lang Message for the Sender.
    * 
    * @param nodeLocation - The String location of the Language Message. 
    * @param sender - The Sender who will receive the Message. 
    * @param placeHolder - Placeholders to be placed into the Langugage Message.
    */
	public void sendLangMessage(final String nodeLocation, final CommandSender sender, final String...placeHolder) {
		Player player = null; if (sender instanceof Player) { player = (Player) sender; }
		String langMessage = (sender.isPermissionSet("signutils.lang." + nodeLocation) ? sender.hasPermission("signutils.lang." + nodeLocation) ? this.getLangMessage(nodeLocation) : null : this.getLangMessage(nodeLocation));
		if (langMessage != null && !langMessage.isEmpty()) {
			langMessage = this.translateLangHolders(langMessage, this.initializeRows(placeHolder));
			langMessage = StringUtils.translateLayout(langMessage, player).replace(" \\n ", " \\n").replace(" /n ", " \\n").replace(" /n", " \\n");
			String[] langLines = langMessage.split(Pattern.quote(" \\" + "n"));
			for (String langLine : langLines) {
				String langStrip = langLine;
				if (sender instanceof ConsoleCommandSender) { langStrip = ChatColor.stripColor(langStrip); } 
				if (this.isConsoleMessage(nodeLocation)) { ServerUtils.logInfo(ChatColor.stripColor(langLine)); }
				else { sender.sendMessage(langStrip);	}
			}
		}
	}
	
   /**
    * Gets the lang message at the node location.
    * 
    * @param nodeLocation - The String location of the Language Message. 
    */
	public String getLangMessage(final String nodeLocation) {
		String message = ConfigHandler.getConfig().getFile(this.langType.nodeLocation()).getString(nodeLocation);
		return (message != null && message.isEmpty() ? null : message);
	}
	
   /**
    * Initializes the Placeholders for the Lang Message.
    * 
    * @param placeHolder - Placeholders to be placed into the Langugage Message.
    */
	private String[] initializeRows(final String...placeHolder) {
		if (placeHolder == null || placeHolder.length != this.newString().length) {
			String[] langHolder = this.newString();
			for (int i = 0; i < langHolder.length; i++) {
				langHolder[i] = "&lnull";
			}
			return langHolder;
		} else {
			String[] langHolder = placeHolder;
			for (int i = 0; i < langHolder.length; i++) {
				if (langHolder[i] == null) {
					langHolder[i] = "&lnull";
				}
			}
			return langHolder;
		}
	}
	
   /**
    * Translates the Language PlaceHolders into the actual Placeholders defined for the Message.
    * 
    * @param langMessage - The Message being sent.
    * @param langHolder - Placeholders to be placed into the Langugage Message.
    */
	private String translateLangHolders(final String langMessage, final String...langHolder) {
		return langMessage
				.replace("%name%", langHolder[0])
				.replace("%lore%", langHolder[1])
				.replace("%group_size%", langHolder[2])
				.replace("%group_list%", langHolder[3])
				.replace("%targetplayer%", langHolder[4])
				.replace("%dependency%", langHolder[5])
				.replace("%player%", langHolder[6])
				.replace("%prefix%", this.langPrefix);
	}
	
   /**
    * Checks if the Language Message is a Console Message.
    * 
    * @param nodeLocation - The String location of the Language Message.
    * @return If the Language Message is a Console Message.
    */
	private boolean isConsoleMessage(final String nodeLocation) {
		if (nodeLocation.equalsIgnoreCase("Commands.Updates.checking") 
				|| nodeLocation.equalsIgnoreCase("Commands.Updates.forcing")) {
			return true;
		}
		return false;
	}
	
   /**
    * Creates a new String Array for langHolders.
    * 
    * @return The new String Array.
    */
	public String[] newString() {
		return new String[18];
	}
	
   /**
    * Gets the current Language that is being translated.
    * 
    * @return The Language.
    */
	public String getLanguage() {
		return this.langType.name().substring(0, 1).toUpperCase() + this.langType.name().substring(1).toLowerCase();
	}
	
   /**
    * Gets the current Language File name.
    * 
    * @return The Language File name.
    */
	public String getFile() {
		return this.langType.nodeLocation();
	}
	
   /**
    * Sets the Language Prefix
    * 
    */
	public void setPrefix() {
		this.langPrefix = StringUtils.colorFormat(ConfigHandler.getConfig().getFile(this.langType.nodeLocation()).getString("Prefix"));
	}
	
   /**
    * Sets the current Language.
    * 
    * @param lang - The Language to be set.
    */
	public void setLanguage(final String lang) {
		if (lang.equalsIgnoreCase("tw")) {
			this.langType = Lang.TWCHINESE;
		} else if (lang.equalsIgnoreCase("cn")) {
			this.langType = Lang.CNCHINESE;
		} else if (lang.equalsIgnoreCase("en")) {
			this.langType = Lang.ENGLISH;
		} else if (lang.equalsIgnoreCase("es")) {
			this.langType = Lang.SPANISH;
		} else if (lang.equalsIgnoreCase("ru")) {
			this.langType = Lang.RUSSIAN;
		} else if (lang.equalsIgnoreCase("fr")) {
			this.langType = Lang.FRENCH;
		}
	}
	
   /**
    * Gets the current Language from the config and saves it to memory.
    * 
    */
	public void langFile() {
		String lang = ConfigHandler.getConfig().getFile("config.yml").getString("Language").replace(" ", "");
		if (lang.equalsIgnoreCase("TraditionalChinese") || lang.equalsIgnoreCase("TwChinese") || lang.equalsIgnoreCase("Chinese")) { this.setLanguage("tw"); } 
		else if (lang.equalsIgnoreCase("SimplifiedChinese") || lang.equalsIgnoreCase("CnChinese")) { this.setLanguage("cn"); } 
		else if (StringUtils.containsIgnoreCase(lang, "Chinese")) { this.setLanguage("tw"); } 
		else if (lang.equalsIgnoreCase("Spanish")) { this.setLanguage("es"); } 
		else if (lang.equalsIgnoreCase("Russian")) { this.setLanguage("ru"); } 
		else if (lang.equalsIgnoreCase("French")) { this.setLanguage("fr"); } 
		else { this.setLanguage("en"); }
	}
	
   /**
	* Defines the Lang type for the Language.
	* 
	*/
	private enum Lang {
		DEFAULT("en-lang.yml", 0), ENGLISH("en-lang.yml", 1), SPANISH("es-lang.yml", 2), TWCHINESE("tw-lang.yml", 3), CNCHINESE("cn-lang.yml", 4), RUSSIAN("ru-lang.yml", 5), FRENCH("fr-lang.yml", 6);
		private Lang(final String nodeLocation, final int i) { this.nodeLocation = nodeLocation; }
		private final String nodeLocation;
		private String nodeLocation() { return nodeLocation; }
	}
	
   /**
    * Gets the instance of the Language.
    * 
    * @param regen - If the instance should be regenerated.
    * @return The Language instance.
    */
    public static LanguageAPI getLang(final boolean regen) { 
        if (lang == null || regen) {
        	lang = new LanguageAPI();
        	lang.langFile();
        }
        return lang; 
    } 
}