package cold.fyre.CMD;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cold.fyre.API.Managers.CommandManager;
import cold.fyre.Usage.Manager;

/**
 * Used to extend the CommandManager class.
 * 
 * @author Armeriness
 * @author Sommod
 * @version 2.0
 *
 */
public class CommandManagerHandler extends CommandManager<Manager> {

	protected CommandManagerHandler(CommandSender sender, Command command, String[] args, Manager pluginManager) {
		super(sender, command, args, pluginManager);
	}

}
