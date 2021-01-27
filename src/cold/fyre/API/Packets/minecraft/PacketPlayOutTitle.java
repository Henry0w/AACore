package cold.fyre.API.Packets.minecraft;

import cold.fyre.API.Packets.Converter;
import cold.fyre.API.Packets.PacketHandler;
import cold.fyre.API.Packets.minecraft.support.ChatMessage;

public class PacketPlayOutTitle extends Packet {

	private EnumTitleAction action;
	private ChatMessage message;
	private int fadeIn, showTime, fadeOut;
	
	public PacketPlayOutTitle() { super("PacketPlayOutTitle"); }
	
	public PacketPlayOutTitle(EnumTitleAction action, ChatMessage message) {
		this(action, message, -1, -1, -1);
	}
	
	public PacketPlayOutTitle(EnumTitleAction action, ChatMessage message, int fadeIn, int showTime, int fadeOut) {
		super("PacketPlayOutTitle");
		this.action = action;
		this.fadeIn = fadeIn;
		this.showTime = showTime;
		this.fadeOut = fadeOut;
		this.message = message;
	}
	
	public void setAction(EnumTitleAction action) { this.action = action; }
	
	public void setFadeIn(int fadeIn) { this.fadeIn = fadeIn; }
	
	public void setShowTime(int showTime) { this.showTime = showTime; }
	
	public void setFadeOut(int fadeOut) { this.fadeOut = fadeOut; }
	
	@Override
	public Object getPacket() {
		PacketHandler ppot = new PacketHandler(getPacketName(), Converter.convertChatMessage(message), PacketHandler.getEnum("PacketPlayOutTitle", action.name()), fadeIn, showTime, fadeOut);
		return ppot.getPacket();
	}
	
	public enum EnumTitleAction {
		TITLE, SUBTITLE, ACTIONBAR, TIMES, CLEAR, RESET;
	}

}
