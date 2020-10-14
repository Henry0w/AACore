package cold.fyre.CMD;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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

			default:
				break;
			}
		}
		
		return false;
	}

}
