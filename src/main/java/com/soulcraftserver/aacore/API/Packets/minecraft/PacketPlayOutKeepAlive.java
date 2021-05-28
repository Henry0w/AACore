package com.soulcraftserver.aacore.API.Packets.minecraft;

import com.soulcraftserver.aacore.API.Packets.PacketHandler;

public class PacketPlayOutKeepAlive extends Packet {
	
	private long value;
	
	public PacketPlayOutKeepAlive() { }
	
	public PacketPlayOutKeepAlive(long value) { this.value = value; }
	
	public void setValue(long value) { this.value = value; }
	
	public long getValue() { return value; }

	@Override
	public Object getPacket() {
		PacketHandler ppoka = new PacketHandler("PacketPlayOutKeepAlive", value);
		return ppoka.getPacket();
	}

}
