package cold.fyre.API.Packets.Complete.out;

import cold.fyre.API.PluginManager;
import cold.fyre.API.Packets.Complete.PacketPlayOut;
import cold.fyre.API.Packets.Complete.PlayerAbilities;

public class PacketPlayOutAbilities<P extends PluginManager<?>> extends PacketPlayOut<P> {
	
	private PlayerAbilities abilities;
	
	public PacketPlayOutAbilities(PlayerAbilities abilities) {
		this(null, abilities);
	}
	
	public PacketPlayOutAbilities(P pluginManager, PlayerAbilities abilities) {
		super("PacketPlayOutAbilities", pluginManager);
		this.abilities = abilities;
		createPacket();
	}

	@Override
	protected void createPacket() {
		// Stops the rest of the code if the object is null. This will
		// Still throw and error if the sendPacket is invoked, but
		// This stops unnecessary code from being ran.
		if(abilities == null) { 
			loadPacket(null);
			return;
		}
		
		// TODO: Create an NBTTagCompound object
		// TODO: Create a PacketPlayerOutAbilities packet
		// TODO: Store packet in super class,
	}

}
