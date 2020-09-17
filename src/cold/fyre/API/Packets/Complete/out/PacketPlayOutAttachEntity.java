package cold.fyre.API.Packets.Complete.out;

import org.bukkit.entity.Entity;

import cold.fyre.API.PluginManager;
import cold.fyre.API.Packets.Complete.PacketPlayOut;

public class PacketPlayOutAttachEntity<P extends PluginManager<?>> extends PacketPlayOut<P> {
	
	private Entity ent1;
	private Entity ent2;
	private int value;
	
	public PacketPlayOutAttachEntity(int value, Entity ent1, Entity ent2) {
		this(null, value, ent1, ent2);
	}
	
	public PacketPlayOutAttachEntity(P pluginManager, int value, Entity ent1, Entity ent2) {
		super("PacketPlayOutAttachEntity", pluginManager);
		this.value = value;
		this.ent1 = ent1;
		this.ent2 = ent2;
	}

}
