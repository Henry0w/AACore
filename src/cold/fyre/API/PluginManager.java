package cold.fyre.API;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 * @param J - Your Plugin Class
 *
 */
public abstract class PluginManager<J extends JavaPlugin> {
	
	private J plugin;
	private String[] headerMessage;
	private String[] footerMessage;
	//private PacketManager packetManager;
	
	/**
	 * Stores the main JavaPlugin class file and initializes any
	 * data that is used with such class.
	 * @param plugin - Main Class of Plugin.
	 */
	public PluginManager(final J plugin) {
		this.plugin = plugin;
		headerMessage = new String[]{};
		footerMessage = new String[]{};
		initMessages();
		
		//if(plugin.getServer().getServicesManager().isProvidedFor(PacketManager.class) && !(plugin instanceof AACore)) {
		//	packetManager = getRegisteredClass(PacketManager.class);
		//}
		
		onStartup();
	}
	
	// Creates the default messages that will appear if they are not changed.
	@SuppressWarnings("unused")
	private void initMessages() {
		headerMessage[0] = "";
		headerMessage[1] = "############";
		footerMessage[2] = "";
		footerMessage[1] = "############";
		for(char c : plugin.getName().toCharArray()) {
			headerMessage[1] += "#";
			footerMessage[1] += "#";
		}
		
		headerMessage[2] = "+---- " + plugin.getName() + " ----+";
		footerMessage[0] = "+---- " + plugin.getName() + " ----+";
	}
	
	/**
	 * This is ran when the constructor is called. This can be used
	 * for initializing any data and objects for the plugin.
	 */
	public abstract void onStartup();
	
	/**
	 * This function is NOT ran when the plugin disables. Can be used for when
	 * the plugin is being disabled, mainly for saving and data via file or
	 * server. This must be called in the onDisable() function within the main
	 * plugin class.
	 */
	public abstract void onShutdown();
	
	/**
	 * Can be used to reload any data within the plugin. By default if this function
	 * is not overridden, then it will return FALSE.
	 * @return false - if not overridden
	 */
	public boolean onReload() { return false; }
	
	/**
	 * Gets the stored plugin. This allows getting a wide selection of data and functions
	 * including the server, PluginDataFolder and Loggers.
	 * @return Stored JavaPlugin
	 */
	public J getPlugin() { return plugin; }
	
	/**
	 * Returns the default plugin logger.
	 * @return Logger
	 */
	public Logger getLogger() { return plugin.getLogger(); }
	
	public void logMessage(String message) { getLogger().log(Level.INFO, message); }
	
	public void logMessage(Level level, String message) { getLogger().log(level, message); }
	
	public YamlConfiguration getConfig() { return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml")); }
	
	public YamlConfiguration getCustomConfig(File file) { return YamlConfiguration.loadConfiguration(file); }
	
	public YamlConfiguration getCustomConfig(String path, String fileName) {
		if(path == null || fileName == null) return null;
		
		if(path.endsWith("/"))
			path = path.substring(0, path.length() - 1);
		
		if(!fileName.endsWith(".yml"))
			fileName += ".yml";
		
		File file = new File(path);
		YamlConfiguration config = new YamlConfiguration();
		
		try {
			config.load(file);
			return config;
		} catch (IOException | InvalidConfigurationException e) {
			logMessage(Level.WARNING, "WARNING: Could not load custom YML file (" + fileName + ").\n"
					+ "Exception has been logged to file in the Exception Log Folder.");
			logExceptionToFile(e);
			return null;
		}
	}
	
	public YamlConfiguration getCustomConfig(File parent, String fileName) {
		if(parent == null || fileName == null) return null;
		
		if(!fileName.endsWith(".yml"))
			fileName += ".yml";
		
		File toLoad = new File(parent, fileName);
		YamlConfiguration toReturn = new YamlConfiguration();
		
		try {
			toReturn.load(toLoad);
			return toReturn;
		} catch (IOException | InvalidConfigurationException e) {
			logMessage(Level.WARNING, "WARNING: Could not load custom YML file (" + fileName + ").\n"
					+ "Exception has been logged to file in the Exception Log Folder.");
			logExceptionToFile(e);
			return null;
		}
	}
	
	public void createFile(InputStream fis, File folder, String fileName) {
		createFile(fis, new File(folder, fileName));
	}
	
	public <T> void registerClass(Class<T> clazz, T instance, ServicePriority priority) {
		getPlugin().getServer().getServicesManager().register(clazz, instance, plugin, priority);
	}
	
	public <C> RegisteredServiceProvider<C> getServiceProvider(Class<C> clazz) {
		return plugin.getServer().getServicesManager().getRegistration(clazz);
	}
	
	public <C> C getRegisteredClass(Class<C> clazz) {
		return getServiceProvider(clazz).getProvider();
	}
	
	public static <C> RegisteredServiceProvider<C> getService(Class<C> clazz) {
		return Bukkit.getServer().getServicesManager().isProvidedFor(clazz) ?
				Bukkit.getServer().getServicesManager().getRegistration(clazz) : null;
	}
	
	public static <C> C getProvider(Class<C> clazz) {
		return getService(clazz).getProvider();
	}
	
	public String[] getHeaderMessage() { return headerMessage; }
	
	public String[] getFooterMessage() { return footerMessage; }
	
	public void setHeaderMessage(String[] message) { headerMessage = message; }
	
	public void setFooterMessage(String[] message) { footerMessage = message; }
	
	public void createFile(InputStream fis, File toLoad) {
		if(fis == null || toLoad == null) {
			logMessage(Level.WARNING, "ERROR: Could not create file (" + toLoad.getName() + ") as either the"
					+ "data, folder or file was incorrect.");
			return;
		}
		
		try {
			FileOutputStream fos = new FileOutputStream(toLoad);
			
			if(!toLoad.exists())
				toLoad.createNewFile();
			
			int i = 0;
			byte[] buffer = new byte[1024];
			
			while((i = fis.read(buffer)) != -1)
				fos.write(buffer, 0, i);
			
			fis.close();
			fos.close();
			
		} catch (IOException e) {
			logMessage(Level.WARNING, "WARNING: Could not write data into file (" + toLoad.getName() + ")."
					+ "Exception logged to Exception Log Folder.");
			logExceptionToFile(e);
		}
	}
	
	public void logException(Exception e) {
		logMessage(Level.WARNING, "======== Stacktrace ========");
		e.printStackTrace();
		logMessage(Level.WARNING, "============================");
	}
	
	public void logExceptionToFile(Exception e) {
		File logFolder = FileManager.getFolder(plugin.getDataFolder() + "/Logs");
		File toLog = new File(logFolder, getDate() + ".txt");
		toLog.setWritable(true);
		
		try {
			FileWriter fw = new FileWriter(toLog);
			for(StackTraceElement element : e.getStackTrace())
				fw.append(element.toString() + "\n");
			fw.close();
		} catch (IOException e1) {
			logException(e1);
			getConsole().sendMessage("&cError, could not write error data to file, another error occurred.");
		}
	}
	
	public ConsoleCommandSender getConsole() { return plugin.getServer().getConsoleSender(); }
	
	private String getDate() {
		String toReturn = "";
		toReturn += Calendar.YEAR + "-" + Calendar.MONTH + "-" + Calendar.DAY_OF_MONTH + "-" + Calendar.HOUR_OF_DAY + Calendar.MINUTE + Calendar.SECOND;
		return toReturn;
	}

}




