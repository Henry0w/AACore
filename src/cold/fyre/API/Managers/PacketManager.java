package cold.fyre.API.Managers;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import cold.fyre.API.Events.SignInputEvent;
import cold.fyre.API.Packets.AbstractPacketManager;
import cold.fyre.API.Packets.Packet;
import cold.fyre.Usage.Manager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;

/**
 * This contains default methods that are commonly used for handling certain
 * server or player attributes. 
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 */
public class PacketManager extends AbstractPacketManager {

	public PacketManager(Server server, ServerVersion version, Manager coldfyre) {
		super(server, version, coldfyre);
	}

	@Override
	public void sendTitle(Player player, String message, int fadeIn, int showTime, int fadeOut) {
		try {
			Enum<?> enumTitleAction = Packet.getEnum("PacketPlayOutTitle$EnumTitleAction", "TITLE");
			String chatComponent = (String) Packet.getClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, formatJSON(message.replace('&', '§')));
			Packet title = new Packet("PacketPlayOutTitle", enumTitleAction, chatComponent, fadeIn * 4, showTime * 4, fadeOut * 4);

			title.sendPacket(player);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendSubtitle(Player player, String title, String subtitle, int fadeIn, int showTime, int fadeOut) {
		try {
			Enum<?> enumTitleAction = Packet.getEnum("PacketPlayOutTitle$EnumTitleAction", "TITLE");
			Enum<?> enumSubtitleAction = Packet.getEnum("PacketPlayOutTitle$EnumTitleAction", "SUBTITLE");
			String chatComponentTitle = (String) Packet.getClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, formatJSON(title.replace('&', '§')));
			String chatComponentSub = (String) Packet.getClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, formatJSON(subtitle.replace('&', '§')));
			Packet main = new Packet("PacketPlayOutTitle", enumTitleAction, chatComponentTitle, fadeIn * 4, showTime * 4, fadeOut * 4);
			Packet sub = new Packet("PacketPlayOutTitle", enumSubtitleAction, chatComponentSub, fadeIn * 4, showTime * 4, fadeOut * 4);

			main.sendPacket(player);
			sub.sendPacket(player);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendActionbar(Player player, String message) {
		try {
			Enum<?> enumTitleAction = Packet.getEnum("PacketPlayOutTitle$EnumTitleAction", "ACTIONBAR");
			String chatComponent = (String) Packet.getClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, formatJSON(message.replace('&', '§')));
			Packet bar = new Packet("PacketPlayOutTitle", enumTitleAction, chatComponent, 8, 2, 8);

			bar.sendPacket(player);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Entity editNBTTag(Entity entity, String tag, Object value) {
		try {
			Object craftEntity = entity.getClass().getMethod("getHandle").invoke(entity);
			Packet ent = new Packet(craftEntity, "Entity");
			ent.runMethod("c", setTag(new Packet("NBTTagCompound").getPacket(), tag, value));
			return (Entity) craftEntity;
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			return entity;
		}
	}

	@Override
	public ItemStack editNBTTag(ItemStack itemStack, String tag, Object value) {
		try {
			Object craftItem = itemStack.getClass().getMethod("getHandle").invoke(itemStack);
			Packet craftItemPacket = new Packet(craftItem, "CraftItem");
			Object nbt = craftItemPacket.runMethod("getTag") != null ? setTag(craftItemPacket.runMethod("getTag"), tag, value) : setTag(new Packet("NBTTagCompund").getPacket(), tag, value);
			
			craftItemPacket.runMethod("setTag", nbt);
			return (ItemStack) craftItemPacket.runMethod("asBukkitCopy", craftItemPacket.getPacket());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			return itemStack;
		}
	}

	@Override
	public Block editNBTTag(Block block, String tag, Object value) {
		try {
			Object tileEntity = block.getClass().cast(new Packet("TileEntity").getPacket());
			tileEntity.getClass().getMethod("save", new Packet("NBTTagCompound").getPacket().getClass()).invoke(tileEntity, setTag(new Packet("NBTTagCompound").getPacket(), tag, value));
			return (Block) tileEntity;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Object setTag(Object nbtPacket, String tag, Object value) {
		Packet enclose = new Packet(nbtPacket, "NBTTagCompund");
		
		if (value instanceof Integer)
			enclose.runMethod("setInt", tag, (int) value);
        else if (value instanceof Double)
        	enclose.runMethod("setDouble", tag, (double) value);
        else if (value instanceof Float)
        	enclose.runMethod("setFloat", tag, (float) value);
        else if (value instanceof Long)
        	enclose.runMethod("setLong", tag, (long) value);
        else if (value instanceof String)
        	enclose.runMethod("setString", tag, String.valueOf(value));
        else if (value instanceof Short)
        	enclose.runMethod("setShort", tag, (short) value);
        else if (value instanceof Boolean)
        	enclose.runMethod("setBoolean", tag, (boolean) value);
        else if (value instanceof Byte)
        	enclose.runMethod("setByte", tag, (byte) value);
        else if (value instanceof byte[])
        	enclose.runMethod("setByteArray", tag, (byte[]) value);
        else if (value instanceof int[])
        	enclose.runMethod("setIntArray", tag, (int[]) value);
        else if (value.getClass().isInstance(new Packet("NBTBase").getPacket()))
        	enclose.runMethod("set", tag, value.getClass().cast(new Packet("NBTBase").getPacket()));
		
		return enclose.getPacket();
	}

	@Override
	public BukkitTask createTablist(Plugin plugin, String header, String footer) {
		return new BukkitRunnable() {
			
			@Override
			public void run() {
				Packet list = new Packet("PacketPlayOutListHeaderFooter");
				
				list.setFieldValue("header", new Packet("ChatComponentText", header).getPacket());
				list.setFieldValue("footer", new Packet("ChatComponentText", footer).getPacket());
				
				if(getServer().getOnlinePlayers().size() == 0)
					return;
				
				for(Player player : getServer().getOnlinePlayers())
					list.sendPacket(player);
			}
			
		}.runTaskTimer(plugin, 0L, 20L);
	}

	@Override
	public BukkitTask createAnimatedTablist(Plugin plugin, String[] header, String[] footer, int seconds) {
		return new BukkitRunnable() {
			private int hCounter = 0;
			private int fCounter = 0;
			private String[] storeHeader = header;
			private String[] storeFooter = footer;
			
			@Override
			public void run() {
				Packet list = new Packet("PacketPlayOutListHeaderFooter");
				
				if(hCounter == storeHeader.length) {
					hCounter = 0;
					list.setFieldValue("header", new Packet("ChatComponentText", storeHeader[0]).getPacket());
				} else {
					list.setFieldValue("header", new Packet("ChatComponentText", storeHeader[hCounter]).getPacket());
					hCounter++;
				}
				
				if(fCounter == storeFooter.length) {
					fCounter = 0;
					list.setFieldValue("header", new Packet("ChatComponentText", storeFooter[0]).getPacket());
				} else {
					list.setFieldValue("header", new Packet("ChatComponentText", storeFooter[fCounter]).getPacket());
					fCounter++;
				}
				
				if(getServer().getOnlinePlayers().size() == 0)
					return;
				
				for(Player player : getServer().getOnlinePlayers())
					list.sendPacket(player);
			}
			
		}.runTaskTimer(plugin, 0L, (long)(seconds * 20));
	}

	@Override
	public void sendKickMessage(Player player, String message) {
		try {
			Object mutable = Packet.getClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, formatJSON(message));
			Packet kick = new Packet("PacketPlayOutKickDisconnect", mutable);
			kick.sendPacket(player);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setPlayersWorldBorder(Player player, double xCenter, double zCenter, double size, double damage, double damageBuffer, int warnDistance, int warnTime) {
		Packet worldBorder = new Packet("WorldBorder");
		worldBorder.runMethod("setCenter", xCenter, zCenter);
		worldBorder.runMethod("setDamage", damage);
		worldBorder.runMethod("setDamageBuffer", damageBuffer);
		worldBorder.runMethod("setSize", size);
		worldBorder.runMethod("setWarningDistance", warnDistance);
		worldBorder.runMethod("setWarningTime", warnTime);
		
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			worldBorder.setFieldValue("world", handle.getClass().getMethod("getWorldServer").invoke(handle));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		Enum<?> init = Packet.getEnum("PacketPlayOutWorldBorder$EnumWorldBorderAction", "INITIALIZE");
		Packet world = new Packet("PacketPlayOutWorldBorder", worldBorder.getPacket(), init);
		
		world.sendPacket(player);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void sendSign(Player player, Sign sign) {
		i(player, sign);
		
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Block signBlock;
			
			if(sign == null) {
				signBlock = player.getLocation().getChunk().getBlock(0, 0, 0);
				
				if(signBlock.getType() != Material.LEGACY_SIGN_POST) {
					signBlock.setType(Material.LEGACY_SIGN_POST);
					signBlock.getState().update();
				}
				
			} else
				signBlock = sign.getBlock();
			
			Packet blockPosition = new Packet("BlockPosition", signBlock.getX(), signBlock.getY(), signBlock.getZ());
			Object craftWorld = handle.getClass().getField("world").get(handle);
			Object tesWorld = craftWorld.getClass().getMethod("getTileEntity", blockPosition.getPacket().getClass()).invoke(craftWorld, blockPosition.getPacket());
			Packet tesCast = new Packet("tileEntitySign");
			Object tes = tesWorld.getClass().cast(tesCast.getPacket());
			
			Packet tileEntitySign = new Packet(tes, "TileEntitySign");
			tileEntitySign.setFieldValue("isEditable", true);
			tileEntitySign.runMethod("a", handle);
			
			Packet toSend = new Packet("PacketPlayOpenSignEditor", blockPosition.getPacket());
			toSend.sendPacket(player);
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void r(Player player) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			Object networkManager = playerConnection.getClass().getField("networkManager").get(playerConnection);
			Channel channel = (Channel) networkManager.getClass().getField("channel").get(networkManager);
			
			channel.eventLoop().submit(() -> {
				channel.pipeline().remove(player.getName());
				return null;
			});
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void i(Player player, Sign sign) {
		try {
			Object craftPlayer = player.getClass().getMethod("getHandle").invoke(player);
			Object packetObject = new Packet("PacketPlayInUpdateSign").getPacket();
			PacketManager pm = this;
			
			ChannelDuplexHandler cdh = new ChannelDuplexHandler() {
				
				public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
					if(msg.getClass().isInstance(packetObject)) {
						String[] lines = (String[]) msg.getClass().getMethod("c").invoke(msg);
						SignInputEvent sie = new SignInputEvent(pm, lines, player, sign);
						getServer().getPluginManager().callEvent(sie);
						return;
					}
					super.channelRead(ctx, msg);
				}
				
				public void write(ChannelHandlerContext chc, Object packet, ChannelPromise cp) throws Exception {
					super.write(chc, packet, cp);
				}
			};
			
			Object playerConnection = craftPlayer.getClass().getField("playerConnection").get(craftPlayer);
			Object networkManager = playerConnection.getClass().getField("networkManager").get(playerConnection);
			Object channel = networkManager.getClass().getField("channel").get(networkManager);
			ChannelPipeline line = (ChannelPipeline) channel.getClass().getMethod("pipeline").invoke(channel);
			line.addBefore("packet_handler", player.getName(), cdh);
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

}
