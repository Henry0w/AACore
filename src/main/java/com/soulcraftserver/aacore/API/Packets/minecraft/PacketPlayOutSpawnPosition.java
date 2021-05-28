package com.soulcraftserver.aacore.API.Packets.minecraft;

import com.soulcraftserver.aacore.API.Packets.PacketHandler;
import com.soulcraftserver.aacore.API.Packets.minecraft.support.BlockPosition;

public class PacketPlayOutSpawnPosition extends Packet {
	
	private BlockPosition position;
	
	public PacketPlayOutSpawnPosition() { super("PacketPlayOutSpawnPosition"); }
	
	public PacketPlayOutSpawnPosition(BlockPosition position) {
		super("PacketPlayOutSpawnPosition");
		this.position = position;
	}
	
	public void setPosition(BlockPosition position) { this.position = position; }

	@Override
	public Object getPacket() {
		PacketHandler BlockPosition = new PacketHandler("BlockPosition", position.getX(), position.getY(), position.getZ());
		PacketHandler pposp = new PacketHandler(getPacketName(), BlockPosition.getPacket());
		return pposp.getPacket();
	}

}
