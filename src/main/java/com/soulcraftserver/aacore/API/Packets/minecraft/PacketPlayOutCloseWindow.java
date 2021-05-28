package com.soulcraftserver.aacore.API.Packets.minecraft;

import com.soulcraftserver.aacore.API.Packets.PacketHandler;

public class PacketPlayOutCloseWindow extends Packet {
	
	private int value;
	
	public PacketPlayOutCloseWindow() { }
	
	public PacketPlayOutCloseWindow(int value) { this.value = value; }
	
	public void setValue(int value) { this.value = value; }
	
	public int getValue() { return value; }

	@Override
	public Object getPacket() {
		PacketHandler ppocw = new PacketHandler("PacketPlayOutCloseWindow", value);
		return ppocw.getPacket();
	}

}
