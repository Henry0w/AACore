package com.soulcraftserver.aacore.API.Packets.minecraft;


import org.bukkit.entity.Entity;

import com.soulcraftserver.aacore.API.Packets.PacketHandler;
import org.jetbrains.annotations.Nullable;

public class PacketPlayOutAttachEntity extends Packet {
	
	private int ent1;
	private int ent2;
	
	public PacketPlayOutAttachEntity() { }
	
	public PacketPlayOutAttachEntity(Entity ent1, @Nullable Entity ent2) {
		this.ent1 = ent1.getEntityId();
		this.ent2 = ((ent2 != null) ? ent2.getEntityId() : 0);
	}
	
	public void setMainEntity(Entity entity) { ent1 = entity.getEntityId(); }
	
	public void setAttachTo(Entity entity) { ent2 = ((entity != null) ? entity.getEntityId() : 0); }
	
	public int getMainEntityID() { return ent1; }
	
	public int getAttachEntityID() { return ent2; }

	@Override
	public Object getPacket() {
		PacketHandler ppoae = new PacketHandler("PacketPlayOutAttachEntity");
		ppoae.setFieldValue("a", ent1);
		ppoae.setFieldValue("b", ent2);
		
		return ppoae.getPacket();
	}

}
