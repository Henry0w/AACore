package cold.fyre.API.Packets.Complete.out;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import cold.fyre.API.FileManager;
import cold.fyre.API.PluginManager;
import cold.fyre.API.Packets.Complete.PacketPlayOut;
import cold.fyre.API.Packets.Complete.util.PlayerAbilities;

/**
 * Loads the PlayerAbilities into the the packet in data form to allow
 * the data to be sent to the player's client. This supports across
 * multiple Minecraft Versions.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 * @version MC 1.8 - 1.16.2
 * 
 * @param <P> - PluginManager
 */
public class PacketPlayOutAbilities<P extends PluginManager<?>> extends PacketPlayOut<P> {
	
	// Object to hold the PlayerAbilities for reflection
	private PlayerAbilities abilities;
	
	/**
	 * Initializes a new PacketPlayOutPlayerAbilties object and loads it
	 * into the packet to be sent to a player. Please note that this
	 * takes the PlayerAbilities object; ensure that it's the correct
	 * object from the AACore API, NOT the version-specific NMS class.
	 * 
	 * @param abilities - {@link PlayerAbilities}
	 */
	public PacketPlayOutAbilities(PlayerAbilities abilities) {
		this(null, abilities);
	}
	
	/**
	 * Initializes a new PacketPlayOutPlayerAbilties object and loads it
	 * into the packet to be sent to a player. Please note that this
	 * takes the PlayerAbilities object; ensure that it's the correct
	 * object from the AACore API, NOT the version-specific NMS class.
	 * 
	 * @param abilities - {@link PlayerAbilities}
	 * @param pluginManager - PluginManager of plugin. Can be null.
	 */
	public PacketPlayOutAbilities(P pluginManager, PlayerAbilities abilities) {
		super("PacketPlayOutAbilities", pluginManager);
		this.abilities = abilities;
		loadObjects();
	}
	
	/**
	 * Load a new PlayerAbilities into the packet, allowing to send a
	 * different set of abilities to a player.
	 * @param abilities - new PlayerAbilities
	 */
	public void loadNewAbilities(PlayerAbilities abilities) {
		this.abilities = abilities;
		loadObjects();
	}
	
	public void loadAbilities(PlayerAbilities abilities) {
		this.abilities = abilities;
		loadObjects();
	}
	
	// Creates the packet and loads it into the Super class.
	private void loadObjects() {
		
		// Checks if the PlayerAbilities is a null object. If
		// the object is null, then it will load a null object
		// As the packet and return to stop an error from occurring
		// now. This will still cause an error later when the packet
		// is to be sent.
		if(abilities == null && getPacket() == null) {
			return;
		} else if(abilities == null) {
			loadPacket(null);
			return;
		} else if(abilities == null)
			return;
		
		Object nbt = applyAbilities(createNBTObject(getPacketClass("NBTTagCompound")));
		try {
			Object playerAbilities = getPacketClass("PlayerAbilities").getConstructor().newInstance();
			playerAbilities.getClass().getMethod("a", getPacketClass("NBTTagCompound")).invoke(playerAbilities, nbt);
			loadPacket(getPacketClass(getPacketName()).getConstructor(getPacketClass("PlayerAbilities")).newInstance(playerAbilities));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			FileManager.logExceptionToFile(getBasePlugin().getName(), e);
			loadPacket(null);
		}
	}
	
	// Create the NBT class of the version specific class.
	private Object createNBTObject(Object nbtClass) {
		try {
			Constructor<?> nbtConstructor = nbtClass.getClass().getConstructor();
			Object toReturnNBT = nbtConstructor.newInstance();
			return toReturnNBT;
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NullPointerException e) {
			FileManager.logExceptionToFile(getBasePlugin().getName(), e);
			return null;
		}
	}
	
	// Apply the PlayerAbilities data to the NBT class.
	private Object applyAbilities(Object nbt) {
		
		try {
			nbt.getClass().getMethod("setBoolean", String.class, boolean.class).invoke(nbt, "invulnerable", abilities.isInvulnerable());
			nbt.getClass().getMethod("setBoolean", String.class, boolean.class).invoke(nbt, "flying", abilities.isFlying());
			nbt.getClass().getMethod("setBoolean", String.class, boolean.class).invoke(nbt, "mayfly", abilities.canFly());
			nbt.getClass().getMethod("setBoolean", String.class, boolean.class).invoke(nbt, "instabuild", abilities.canInstantBuild());
			nbt.getClass().getMethod("setBoolean", String.class, boolean.class).invoke(nbt, "mayBuild", abilities.mayBuild());
			nbt.getClass().getMethod("setFloat", String.class, float.class).invoke(nbt, "flyspeed", abilities.flySpeed());
			nbt.getClass().getMethod("setFloat", String.class, float.class).invoke(nbt, "walkspeed", abilities.walkSpeed());
			return nbt;
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NullPointerException e) {
			FileManager.logExceptionToFile(getBasePlugin().getName(), e);
			return null;
		}
	}

}
