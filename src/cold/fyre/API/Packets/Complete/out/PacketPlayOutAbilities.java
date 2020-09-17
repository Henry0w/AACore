package cold.fyre.API.Packets.Complete.out;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import cold.fyre.API.FileManager;
import cold.fyre.API.PluginManager;
import cold.fyre.API.Packets.Complete.PacketPlayOut;
import cold.fyre.API.Packets.Complete.util.PlayerAbilities;

public class PacketPlayOutAbilities<P extends PluginManager<?>> extends PacketPlayOut<P> {
	
	private PlayerAbilities abilities;
	
	public PacketPlayOutAbilities(PlayerAbilities abilities) {
		this(null, abilities);
	}
	
	public PacketPlayOutAbilities(P pluginManager, PlayerAbilities abilities) {
		super("PacketPlayOutAbilities", pluginManager);
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
