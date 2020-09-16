package cold.fyre.API.Packets.Complete.out;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;

import cold.fyre.API.PluginManager;
import cold.fyre.API.Packets.Complete.PacketPlayOut;

/**
 * Loads the Entity and value into the PacketPlayOutAnimation to be used
 * to be sent to the player.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 * @version 1.8 -1.16.2
 *
 * @param <P> - PluginManager
 */
public class PacketPlayOutAnimation<P extends PluginManager<?>> extends PacketPlayOut<P> {
	
	// Stored Entity and int value data
	private Entity entity;
	private int value;
	
	/**
	 * Load an Entity and int value into the PacketPlayOutAnimation.
	 * @param entity - Entity of application
	 * @param value - int value to load
	 */
	public PacketPlayOutAnimation(Entity entity, int value) {
		this(null, entity, value);
	}
	
	/**
	 * Load an Entity and int value into the PacketPlayOutAnimation.
	 * @param entity - Entity of application
	 * @param value - int value to load
	 * @param pluginManager - PluginManager class of Plugin.
	 */
	public PacketPlayOutAnimation(P pluginManager, Entity entity, int value) {
		super("PacketPlayOutAnimation", pluginManager);
		this.entity = entity;
		this.value = value;
		loadObjects();
	}
	
	/**
	 * Load new data into the packet to be sent to a player.
	 * @param entity - Entity to apply
	 * @param value - Value to load
	 */
	public void load(Entity entity, int value) {
		this.entity = entity;
		this.value = value;
		loadObjects();
	}
	
	// Loads the Stored objects into the Super Class
	// to be sent to the player.
	private void loadObjects() {
		List<Class<?>> objectsToLoad = new ArrayList<Class<?>>();
		objectsToLoad.add(getPacketClass("Entity"));
		objectsToLoad.add(int.class);
		
		List<Object> parameters = new ArrayList<>();
		parameters.add(entity.getClass().cast(getPacketClass("Entity")));
		parameters.add(value);
		
		createPacket(getPacketName(), objectsToLoad, parameters);
	}

}
