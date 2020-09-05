package cold.fyre.API.Packets.Complete;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

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
	private final String version = Bukkit.getServerId().getClass().getPackage().getName().split("\\.")[3];
	
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
	 * the player or server.
	 * @param packet - initialized packet of the MC version.
	 */
	protected void loadPacket(Object packet) { this.packet = packet; }
	
	/**
	 * Used to create the packet for the version specific for the current
	 * Minecraft Version being ran on the server. Note that this does not
	 * store the packet, so the method {@link #loadPacket(Object)} still
	 * needs to be called after the creation of the packet.
	 */
	protected abstract void createPacket();

}
