package cold.fyre.API.Packets.Complete;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
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
	
	protected AbstractPacket() { }
	
	protected AbstractPacket(final String packetName, final Object packet, P pluginManager) {
		this.packetName = packetName;
		manager = pluginManager;
		plugin = pluginManager.getPlugin();
	}
	
	protected AbstractPacket(final String packetName, final Object packet, JavaPlugin plugin) {
		this.packetName = packetName;
		this.plugin = plugin;
		this.packet = packet;
		manager = null;
	}
	
	private Class<?> getBasePacketClass() {
		try { return Class.forName("net.minecraft.server." + version + ".Packet"); } 
		catch (ClassNotFoundException e) {
			FileManager.logExceptionToFile(plugin.getName(), e);
			return null;
		}
	}
	
	protected void sendPacket(Player toSend) {
		try {
			Object handle = toSend.getClass().getMethod("getHandle").invoke(toSend);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			
			playerConnection.getClass().getMethod("sendPacket", getBasePacketClass()).invoke(playerConnection, packet);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			FileManager.logExceptionToFile(plugin.getName(), e);
			return;
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
	 * Returns the Current Minecraft Version
	 * @return Minecraft version from the package
	 */
	protected String getMCVersion() { return version; }
	
	/**
	 * Returns the name of this packet.
	 * @return Packet Name
	 */
	protected String getPacketName() { return packetName; }
	
	/**
	 * Returns the server this plugi is currently running on.
	 * @return Server
	 */
	protected Server getServer() { return server; }
	
	/**
	 * Returns this packet as an Object to allow usage with reflection.
	 * @return This packet as an object.
	 */
	protected Object getPacket() { return packet; }

}
