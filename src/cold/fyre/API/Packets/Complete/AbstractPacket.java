package cold.fyre.API.Packets.Complete;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.constructor.ConstructorException;

import cold.fyre.API.FileManager;
import cold.fyre.API.PluginManager;

/**
 * Abstract methods used for version handling each method of the packets.
 * This uses reflection, which is not thread-safe, as it's method of handling
 * version-type packets. Note that creating a sub-class of this will not do
 * anything nor allow you to create a 'new' packet that can be sent to a player.
 * The client / server only support certain packets and data within such packets.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 * 
 */
public abstract class AbstractPacket<P extends PluginManager<?>> {
	
	// Reflection Objects
	private Server server = Bukkit.getServer();
	private Object packet;
	private String packetName;
	private final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	
	// AACore Items
	private P manager;
	private JavaPlugin plugin;
	
	/**
	 * Stores the default needed data into the Variables of this class.
	 * @param packetName - Name of packet being created
	 * @param pluginManager - this Plugin's PluginManager
	 */
	protected AbstractPacket(final String packetName, P pluginManager) {
		this.packetName = packetName;
		manager = pluginManager;
		plugin = pluginManager.getPlugin();
	}
	
	/**
	 * Sores the default needed data into the Variables of this class.
	 * @param packetName - Name of the packet being created
	 * @param plugin - This plugin
	 */
	protected AbstractPacket(final String packetName, JavaPlugin plugin) {
		this.packetName = packetName;
		this.plugin = plugin;
		manager = null;
	}
	
	// Used to get the basic Packet class for sending the packets to either the
	// server or the player
	protected Class<?> getBasePacketClass() {
		try { return Class.forName("net.minecraft.server." + getMCVersion() + ".Packet"); } 
		catch (ClassNotFoundException e) {
			FileManager.logExceptionToFile(getBasePlugin().getName(), e);
			return null;
		}
	}
	
	/**
	 * Attempts to obtain a Class of Object of the Packet given. If the packet does not
	 * Exist, or is spelled incorrectly, then this will return null. The name of the packet
	 * is <b>case sensitive</b>.
	 * @param packetName
	 * @return
	 */
	protected Class<?> getPacketClass(String packetName) {
		try { return Class.forName("net.minecraft.server." + getMCVersion() + "." + packetName); }
		catch (ClassNotFoundException e) {
			FileManager.logExceptionToFile(getBasePlugin().getName(), e);
			return null;
		}
	}
	
	/**
	 * Obtains the contstructor of a Packet Class.
	 * @param clazz - Class object of packet.
	 * @param parameterTypes - The parameterTypes to init in the constructor.
	 * @return Constructor
	 */
	protected Constructor<?> getPacketConstructor(Class<?> clazz, Class<?>... parameterTypes) {
		try { return clazz.getConstructor(parameterTypes); }
		catch (ConstructorException | NoSuchMethodException | SecurityException e) {
			FileManager.logExceptionToFile(getBasePlugin().getName(), e);
			return null;
		}
	}
	
	protected void createPacket(String packetName, List<Class<?>> parameterTypes, List<Object> parameters) {
		Class<?>[] types = new Class<?>[parameterTypes.size()];
		Object[] localParameters = new Object[parameters.size()];
		
		for(int i = 0; i < parameterTypes.size(); i++)
			types[i] = parameterTypes.get(i);
		
		for(int i = 0; i < parameters.size(); i++)
			localParameters[i] = parameters.get(i);
		
		try {
			Object localPacket = getPacketConstructor(getPacketClass(packetName), types).newInstance(localParameters);
			loadPacket(localPacket);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			FileManager.logExceptionToFile(getBasePlugin().getName(), e);
		}
	}
	
	/**
	 * Gets the PluginManager object. If no PluginManager was given in the Constructor, then
	 * this will return null.
	 * @return subclass of PluginManagr, otherwise null
	 */
	protected P getPluginManager() { return manager; }
	
	/**
	 * Returns the stored JavaPlugin is one was given. If the PluginManager class
	 * was given, then this will still return the plugin, otherwise this is null.
	 * @return JavaPlugin, otherwise null
	 */
	protected JavaPlugin getBasePlugin() { return plugin; }
	
	/**
	 * Returns the Current Minecraft Version. This is in the format of V#_##_R# as found
	 * on in the name of the packages.
	 * @return Minecraft version from the package
	 */
	public String getMCVersion() { return version; }
	
	/**
	 * Returns the name of this packet.
	 * @return Packet Name
	 */
	protected String getPacketName() { return packetName; }
	
	/**
	 * Returns the server this plugi is currently running on.
	 * @return Server
	 */
	public Server getServer() { return server; }
	
	/**
	 * Returns this packet as an Object to allow usage with reflection.
	 * @return This packet as an object.
	 */
	protected Object getPacket() { return packet; }
	
	/**
	 * Loads the packet into the Super class and allows it to be sent to
	 * the player or server. This does not need to be ran for each packet
	 * as some are created for y
	 * @param packet - initialized packet of the MC version.
	 */
	protected void loadPacket(Object packet) { this.packet = packet; }

}
