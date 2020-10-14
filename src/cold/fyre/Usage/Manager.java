package cold.fyre.Usage;

import java.io.File;

import org.bukkit.event.HandlerList;

import cold.fyre.IcyHot;
import cold.fyre.API.PluginManager;
import cold.fyre.CMD.CommandHandler;
import cold.fyre.Events.DeveloperMessage;

public class Manager extends PluginManager<IcyHot> {

	public Manager(IcyHot plugin) {
		super(plugin);
	}
	
	private void checkPlugins() { }
	
	@Override
	public void onStartup() {
		logMessage(getHeaderMessage());
		//logMessage("Running pre-check...");
		checkPlugins();
		
		//logMessage("Pre-check complete");
		logMessage("Registering Config files...");
		registerConfigs();
		
		logMessage("Config files loaded");
		logMessage("Registering Commands...");
		new CommandHandler(this);
		
		logMessage("Commands Registered");
		logMessage("Registering Events...");
		new DeveloperMessage(this);
		
		logMessage("Events registered");
		logMessage(getFooterMessage());
	}
	
	@Override
	public void onShutdown() {
		logMessage(getHeaderMessage());
		logMessage("Shutting down...");
		HandlerList.unregisterAll(getPlugin());
		logMessage("Shutdown complete");
		logMessage(getFooterMessage());
	}
	
	private void registerConfigs() {
		if(!getPlugin().getDataFolder().exists())
			getPlugin().getDataFolder().mkdir();
		
		File config = new File(getPlugin().getDataFolder(), "config.yml");
		
		if(!config.exists())
			createFile(getPlugin().getClass().getResourceAsStream("/Resources/config.yml"), config);
	}

}
