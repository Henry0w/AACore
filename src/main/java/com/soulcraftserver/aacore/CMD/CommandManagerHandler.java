package com.soulcraftserver.aacore.CMD;

import com.soulcraftserver.aacore.API.Managers.CommandManager;
import com.soulcraftserver.aacore.Usage.IcyHotManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;



/**
 * Used to extend the CommandManager class.
 * 
 * @author Armeriness
 * @author Sommod
 * @version 2.0
 *
 */
public class CommandManagerHandler extends CommandManager<IcyHotManager> {

	protected CommandManagerHandler(CommandSender sender, Command command, String[] args, IcyHotManager pluginManager) {
		super(sender, command, args, pluginManager);
	}

}
