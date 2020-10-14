package cold.fyre.CMD;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import cold.fyre.API.Utilities;
import cold.fyre.CMD.Commands.CommandDisable;
import cold.fyre.CMD.Commands.CommandEnable;
import cold.fyre.CMD.Commands.CommandHelp;
import cold.fyre.CMD.Commands.CommandVersion;
import cold.fyre.Usage.Manager;

public class CommandHandler implements CommandExecutor {
	
	private Manager manager;
	
	public CommandHandler(Manager manager) {
		this.manager = manager;
		manager.getPlugin().getCommand("AACore").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("AACore")) {
			switch (args.length) {
			case 0:
				new CommandVersion(sender, command, args, manager);
				return true;
			
			case 1:
				if(Utilities.isAny(args[0], "help", "h"))
					new CommandHelp(sender, command, args, manager);
				else if(Utilities.isAny(args[0], "list", "l"))
					new CommandEnable(sender, command, args, manager);
				else
					sender.sendMessage("§c§lAACore §8§l>> §7Error, invalid arguments given.");
				return true;
			
			case 2:
				if(Utilities.isAny(args[0], "help", "h"))
					new CommandHelp(sender, command, args, manager);
				else if(Utilities.isAny(args[0], "enable", "e"))
					new CommandEnable(sender, command, args, manager);
				else if(Utilities.isAny(args[0], "disable", "d"))
					new CommandDisable(sender, command, args, manager);
				else
					sender.sendMessage("§c§lAACore §8§l>> §7Error, invalid arguments given.");
				return true;

			default:
				sender.sendMessage("§c§lAACore §8§l>> §7Error, unknown command.");
				return false;
			}
		}
		
		return false;
	}

}
