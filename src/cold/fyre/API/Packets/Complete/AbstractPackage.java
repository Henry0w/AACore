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
	
	private Server server = Bukkit.getServer();
	private Class<?> packetClass;
	private String packetName;
	private String version;
	
	private P manager;
	private JavaPlugin plugin;
	
	protected Class<?> getNMSClass() {
		try { return Class.forName("net.minecraft.server." + version + "." + packetName); }
		catch (ClassNotFoundException e) {
			FileManager.logExceptionToFile(manager != null ? manager.getPlugin().getName() : plugin.getName(), e);
			return null;
		}
	}
	
	protected P getPluginManager() { return manager; }
	protected JavaPlugin getBasePlugin() { return plugin; }
	protected String getMCVersion() { return version; }
	protected String getPacketName() { return packetName; }
	protected Class<?> getPacketAsClass() { return packetClass; }
	protected Server getServer() { return server; }

}
