package cold.fyre.API.Packets.Complete;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import cold.fyre.API.FileManager;
import cold.fyre.API.PluginManager;

/**
 * Abstract class that handles the action of sending Packets out the players.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 * @param <P> - PluginManager class type
 */
public abstract class PacketPlayOut<P extends PluginManager<?>> extends AbstractPacket<P> {
	
	/**
	 * Stores the basic information given into the Super class allowing use of most
	 * of the functions found within this class or the super class. The name of the
	 * packet is essential and is required to be able to send to the player. The name
	 * of the packet <b>MUST</b> be the same as found in the NMS package.
	 * @param packetName - Name of the packet being created
	 * @param pluginManager - PluginManager of this plugin, if one exists. (can be null)
	 */
	public PacketPlayOut(String packetName, P pluginManager) {
		super(packetName, pluginManager);
	}
	
	/**
	 * Stores the basic information given into the Super class allowing use of most
	 * of the functions found within this class or the super class. The name of the
	 * packet is essential and is required to be able to send to the player. The name
	 * of the packet <b>MUST</b> be the same as found in the NMS package.
	 * @param packetName - Name of the packet being created
	 * @param plugin - This plugin. (can be null)
	 */
	public PacketPlayOut(String packetName, JavaPlugin plugin) {
		super(packetName, plugin);
	}
	
	/**
	 * Sends this packet to the player. Note that the packet, if not done correctly or given null values
	 * in incorrect places will cause this to throw an exception error and log the exception either in
	 * the plugin folder of the plugin that tried to run this file, or in the generic folder found
	 * within the main Plugins folder called <b>Error Logs</b>.
	 * @param toSend
	 */
	public void sendPacket(Player toSend) {
		try {
			Object handle = toSend.getClass().getMethod("getHandle").invoke(toSend);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			
			playerConnection.getClass().getMethod("sendPacket", getBasePacketClass()).invoke(playerConnection, getPacket());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException | NullPointerException e) {
			FileManager.logExceptionToFile(getBasePlugin().getName(), e);
			return;
		}
	}

}
