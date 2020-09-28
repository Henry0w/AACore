package cold.fyre.API.Packets;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import cold.fyre.API.FileManager;
import cold.fyre.API.PluginManager;

/**
 * Class that holds and handles NMS classes and objects. This class
 * is forced to be extended, otherwise the class would contain several
 * layers of Type parameters. The extended class does not need to possess
 * or override any methods, just forced to remove the constant need for
 * using Type parameters every time a packet is being created.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 * @param <P>
 */
public abstract class AAPacket<P extends PluginManager<?>> extends AbstractPacket<P> {

	/**
	 * Creates an object that holds the Class name of the NMS class. This can be used to create
	 * the NMS class of the specified version of the server via reflection. While using this is
	 * not thread-safe, it allows the removal of handling version-specific actions. The packet
	 * in which this object will hold and handle is given by the <b>packetName</b>. Please note
	 * that the string object is case-sensitive.
	 * @param packetName - NMS class name
	 * @param pluginManager - Your PluginManager
	 */
	public AAPacket(String packetName, P pluginManager) { super(packetName, pluginManager); }
	
	/**
	 * Creates an object that holds the Class name of the NMS class. This can be used to create
	 * the NMS class of the specified version of the server via reflection. While using this is
	 * not thread-safe, it allows the removal of handling version-specific actions. The packet
	 * in which this object will hold and handle is given by the <b>packetName</b>. Please note
	 * that the string object is case-sensitive.
	 * @param packetName - NMS class name
	 * @param plugin - Your Plugin or sub-class of JavaPlugin
	 */
	public AAPacket(String packetName, JavaPlugin plugin) { super(packetName, plugin); }
	
	/**
	 * Activates a method within the packet with the given parameters. The method is also case-sensitive.
	 * @param method - Method to run
	 * @param types - Parameter class types
	 * @param parameters - Object(s) to pass
	 */
	public void runFunction(String method, Class<?>[] types, Object[] parameters) {
		try {
			getPacket().getClass().getMethod(method, types).invoke(getPacket(), parameters);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the given field to the object given. Note that if you try to set the field
	 * to an incompatible object, (ie. setting a boolean to a numerical value), then this
	 * will cause an error. The field is case-sensitive.
	 * @param fieldName- Case-sensitive name of variable
	 * @param toSet - Object to set field to
	 */
	public void setField(String fieldName, Object toSet) {
		Field field;
		
		try {
			field = getPacket().getClass().getField(fieldName);
			field.setAccessible(true);
			field.set(getPacket(), toSet);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends this packet to the player. Note that the packet, if not done correctly or given null values
	 * in incorrect places will cause this to throw an exception error and log the exception either in
	 * the plugin folder of the plugin that tried to run this file, or in the generic folder found
	 * within the main Plugins folder called <b>Error Logs</b>.
	 * @param toSend - Player to send packet to.
	 */
	public void sendPacket(Player toSend) {
		try {
			Object handle = toSend.getClass().getMethod("getHandle").invoke(toSend);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			
			playerConnection.getClass().getMethod("sendPacket", getPacketClass("Packet")).invoke(playerConnection, getPacket());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException | NullPointerException e) {
			FileManager.logExceptionToFile(getBasePlugin().getName(), e);
			return;
		}
	}
	
	/**
	 * Checks if a packet for this object has been created yet or not.
	 * @return True - if packet has been created
	 */
	public boolean isCreated() { return getPacket() != null; }
	
	/**
	 * Returns the Name of this packet. This is the NMS class that
	 * is stored within this object. Note that the packet may not
	 * be initialized, but the name is still stored.
	 */
	public String toString() { return getPacketName(); }

}
