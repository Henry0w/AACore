package cold.fyre.API.Packets;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import cold.fyre.API.Managers.FileManager;

public class Packet implements AAPacket {
	
	// Used to get details of the server
	private Server server = Bukkit.getServer();
	private final String serverVersion = server.getClass().getPackage().getName().split("\\.")[3];
	
	private String packetName;
	private Object packet;
	private Exception exception;
	
	public Packet(String packetName, Object... parameters) {
		try {
			Class<?> packetClass = Class.forName("net.minecraft.server." + serverVersion + "." + packetName);
			packet = packetClass.getConstructor((Class<?>[]) parameters).newInstance(parameters);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			FileManager.logExceptionToFile(null, e);
			packet = null;
			exception = e;
		}
	}

	@Override
	public String getName() { return packetName; }

	@Override
	public Object getPacket() { return packet; }

	@Override
	public Object runMethod(String methodName, Object... parameters) {
		if(packet != null) {
			try {
				Method toExecute = packet.getClass().getDeclaredMethod(methodName, (Class<?>[]) parameters);
				return toExecute.invoke(packet, parameters);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				FileManager.logExceptionToFile(null, e);
			}
		}
		
		return null;
	}

	@Override
	public Object getFieldValue(String fieldName) {
		if(packet != null) {
			try {
				return packet.getClass().getDeclaredField(fieldName).get(packet);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				FileManager.logExceptionToFile(null, e);
			}
		}
		
		return null;
	}

	@Override
	public void setFieldValue(String fieldName, Object value) {
		if(packet != null) {
			try {
				Field field = packet.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(packet, value);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				FileManager.logExceptionToFile(null, e);
			}
		}
	}

	@Override
	public Exception getError() { return exception; }

	@Override
	public boolean hasError() { return exception != null; }

	@Override
	public void sendPacket(Player player) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			
			playerConnection.getClass().getMethod("sendPacket", AAPacket.getClass("Packet")).invoke(playerConnection, packet);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
			FileManager.logExceptionToFile(null, e);
		}
	}

}
