package cold.fyre.API.Packets.Complete.out;

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
	
	// Creates the packet and loads it into the Super class.
	private void loadObjects() {
		
		// Checks if the PlayerAbilities is a null object. If
		// the object is null, then it will load a null object
		// As the packet and return to stop an error from occurring
		// now. This will still cause an error later when the packet
		// is to be sent.
		if(abilities == null) {
			loadPacket(null);
			return;
		}
		
		Class<?> nbtClass = getPacketClass("NBTTagCompound");
		// TODO: Create NBT object and loads the player abilities into it.
		// TODO: Create packet and load it into the super class.
	}

}
