package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.PacketHandler;
import cold.fyre.API.Packets.minecraft.support.WorldBorder;

public class PacketPlayOutWorldBorder extends Packet {
	
	private EnumWorldBorderAction action;
	private WorldBorder border;
	
	public PacketPlayOutWorldBorder(WorldBorder border, EnumWorldBorderAction action) {
		super("PacketPlayOutWorldBorder");
		this.border = border;
		this.action = action;
	}

	@Override
	public Object getPacket() {
		PacketHandler WorldBorder = new PacketHandler("WorldBorder");
		WorldBorder.runMethod("setCenter", border.getCenterX(), border.getCenterZ());
		WorldBorder.runMethod("setSize", border.getSize());
		WorldBorder.runMethod("setDamageAmount", border.getDamageAmount());
		WorldBorder.runMethod("setDamageBuffer", border.getDamageBuffer());
		WorldBorder.runMethod("setWarningTime", border.getWarningTime());
		WorldBorder.runMethod("setWarningDistance", border.getWarningDistance());
		
		PacketHandler ppowb = new PacketHandler(getPacketName(), WorldBorder.getPacket(), PacketHandler.getEnum(getPacketName() + "$EnumWorldBorderAction", action.name()));
		return ppowb.getPacket();
	}
	
	public enum EnumWorldBorderAction {
		SET_SIZE, LERP_SIZE, SET_CENTER, INITIALIZE, SET_WARNING_TIME, SET_WARNING_BLOCKS;
	}

}
