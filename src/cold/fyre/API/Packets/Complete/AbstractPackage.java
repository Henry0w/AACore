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
 * <p>
 * 
 * </p>
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 * 
 */
public abstract class AbstractPackage<P extends PluginManager<?>> {
	
	// Reflection Objects
	private Server server = Bukkit.getServer();
	private Class<?> packetClass;
	private Object packet;
	private String packetName;
	private final String version = Bukkit.getServerId().getClass().getPackage().getName().split("\\.")[3];
	
	// AACore Items
	private P manager;
	private JavaPlugin plugin;
	
	protected AbstractPackage(final String packetName, final Object packet, P pluginManager) {
		this.packetName = packetName;
		packetClass = getNMSClass();
		manager = pluginManager;
		plugin = pluginManager.getPlugin();
	}
	
	protected AbstractPackage(final String packetName, final Object packet, JavaPlugin plugin) {
		this.packetName = packetName;
		this.plugin = plugin;
		this.packet = packet;
		manager = null;
		packetClass = getNMSClass();
	}
	
	private Class<?> getNMSClass() {
		try { return Class.forName("net.minecraft.server." + version + "." + packetName); }
		catch (ClassNotFoundException e) {
			FileManager.logExceptionToFile(manager != null ? manager.getPlugin().getName() : plugin.getName(), e);
			return null;
		}
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
	
	protected P getPluginManager() { return manager; }
	protected JavaPlugin getBasePlugin() { return plugin; }
	protected String getMCVersion() { return version; }
	protected String getPacketName() { return packetName; }
	protected Class<?> getPacketAsClass() { return packetClass; }
	protected Server getServer() { return server; }

}
