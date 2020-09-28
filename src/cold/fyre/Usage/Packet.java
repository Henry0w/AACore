package cold.fyre.Usage;

import cold.fyre.API.Packets.AAPacket;

/**
 * Class to extend AAPacket
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 */
public class Packet extends AAPacket<Manager> {

	public Packet(String packetName, Manager pluginManager) {
		super(packetName, pluginManager);
	}

}
