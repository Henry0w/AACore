package cold.fyre.API.Packets;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import cold.fyre.API.Managers.FileManager;

/**
 * Used to create a packet or class found within the NMS package. Using this
 * allows the use of the class across all versions that contain the class
 * without having to update / have version control.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 */
public class Packet implements AAPacket {
	
	// Used to get details of the server
	private Server server = Bukkit.getServer();
	private final String serverVersion = server.getClass().getPackage().getName().split("\\.")[3];
	
	private String packetName;
	private Object packet;
	private Exception exception;
	
	/**
	 * Creates a new instance of the class
	 * @param packetName - name of NMS class
	 * @param parameters - parameters constructor takes (empty if necessary)
	 */
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
			
			playerConnection.getClass().getMethod("sendPacket", getClass("Packet")).invoke(playerConnection, packet);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
			FileManager.logExceptionToFile(null, e);
		}
	}
	
	/**
	 * This Gets the Enumeration Object of the given class. Note that the
	 * class has be of the Enum class, otherwise this will return null.
	 * <b>Note:</b> If the class you are trying to obtain is an inner class,
	 * you need to properly supply the class package end. ie.<br><br>
	 * <i>getEnum("outterClass$innerClass, "myEnum");</i>
	 * <p>
	 * public class outterClass {<br>
	 * &emsp;&emsp; public enum innerClass {<br>
	 * <br>
	 * &emsp;&emsp;}<br>
	 * }
	 * </p>
	 * 
	 * @param packetClass
	 * @return Enum constant
	 */
	public static Enum<?> getEnum(String packetClass, String enumName) {
		
		try {
			Class<?> packetClazz = Class.forName(packetClass);
			
			if(packetClazz.isEnum())
				return Enum.valueOf(packetClazz.asSubclass(Enum.class), enumName.toUpperCase(Locale.ROOT));
		} catch (ClassNotFoundException | ClassCastException e) {
			e.printStackTrace();
			FileManager.logExceptionToFile(null, e);
		}
		
		return null;
	}
	
	/**
	 * Obtains the class object of the given class name. This is used
	 * more commonly used for Abstract classes and Interfaces.
	 * @param packetClass - name of class to obtain
	 * @return Class
	 */
	public static Class<?> getClass(String packetClass) {
		try {
			return Class.forName(packetClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			FileManager.logExceptionToFile(null, e);
			return null;
		}
	}

}
