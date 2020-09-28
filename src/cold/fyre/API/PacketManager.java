package cold.fyre.API;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import com.mojang.authlib.GameProfile;

import cold.fyre.API.Packets.AbstractPacketManager;
import cold.fyre.Usage.Manager;
import cold.fyre.Usage.Packet;

public class PacketManager extends AbstractPacketManager {

	public PacketManager(Server server, ServerVersion version, Manager coldfyre) {
		super(server, version, coldfyre);
	}

	/**
	 * 
	 * 
	 * @param player
	 * @param message
	 * @param fadeIn
	 * @param showTime
	 * @param fadeOut
	 */
	@Override
	public void sendTitle(Player player, String message, int fadeIn, int showTime, int fadeOut) {
		Packet title = new Packet("PacketPlayOutTitle", getManager());
		try {
			title.createPacket(new Class<?>[]{title.getPacketClass("EnumTitleAction"), title.getPacketClass("IChatBaseComponent"), int.class, int.class, int.class},
					new Object[]{title.getEnumValues(title.getPacketClass("EnumTitleAction"), "TITLE"), title.getPacketClass("ChatSerializer").getMethod("a", String.class).invoke(title.getPacketClass("ChatSerializer"), "{\"text\":\"" + message + "\"}"), fadeIn, showTime, fadeOut});
			title.sendPacket(player);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendSubtitle(Player player, String title, String subtitle, int fadeIn, int showTime, int fadeOut) {
	}

	@Override
	public void sendActionbar(Player player, String message) {
	}

	@Override
	public GameProfile createNPCPlayer(Location location, UUID uuid, String name) {
		return null;
	}

	@Override
	public Entity createNPC(EntityType type, Location location, String name) {
		return null;
	}

	@Override
	public Entity editNBTTag(Entity entity, String tag, Object value) {
		return null;
	}

	@Override
	public ItemStack editNBTTag(ItemStack itemStack, String tag, Object value) {
		return null;
	}

	@Override
	public Block editNBTTag(Block block, String tag, Object value) {
		return null;
	}

	@Override
	public BukkitTask createTablist(Plugin plugin, String header, String footer) {
		return null;
	}

	@Override
	public BukkitTask createAnimatedTablist(Plugin plugin, String[] header, String[] footer, int seconds) {
		return null;
	}

	@Override
	public void sendKickMessage(Player player, String message) {
	}

	@Override
	public void setPlayersWorldBorder(Player player, double xCenter, double zCenter, double size, double damage,
			double damageBuffer, int warnDistance, int warnTime) {
	}

	@Override
	public void sendSign(Player player) {
	}

	@Override
	public void r(Player player) {
	}

	@Override
	protected void i(Player player) {
		
	}

}
