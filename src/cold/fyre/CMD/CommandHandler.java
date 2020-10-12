package cold.fyre.CMD;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import cold.fyre.API.CommandManager;
import cold.fyre.Usage.Manager;

public class CommandHandler extends CommandManager<Manager> {

	protected CommandHandler(CommandSender sender, Command command, String[] args, Manager pluginManager) {
		super(sender, command, args, pluginManager);
	}

}
