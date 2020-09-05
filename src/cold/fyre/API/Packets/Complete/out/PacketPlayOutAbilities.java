package cold.fyre.API.Packets.Complete.out;

import cold.fyre.API.PluginManager;
import cold.fyre.API.Packets.Complete.AbstractPacket;

public class PacketPlayOutAbilities<P extends PluginManager<?>> extends AbstractPacket<P> {

	public PacketPlayOutAbilities(final P pluginManager) {
		String temp = "";
	}
	
	private PacketPlayOutAbilities(String packetName, Object packet, P pluginManager) {
		super(packetName, packet, pluginManager);
	}

}
