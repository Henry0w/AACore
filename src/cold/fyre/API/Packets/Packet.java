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
 * without having to update / have version control. Please note that the
 * Interface and Abstract classes cannot be created with this. You must use
 * the subclass of the given anonymous class. When inputting the class name,
 * the path is defaulted to {@code net.minecraft.server.VERSION.CLASSNAME, this means
 * you only need to input the actual class name (and proper nested classing).
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
			if(packetName.contains("$")) {
				Class<?> mainClass = Class.forName("net.minecraft.server." + serverVersion + "." + packetName.split("$")[0]);
				Class<?> subClass = Class.forName("net.minecraft.server." + serverVersion + "." + packetName);
				Object mainObject = mainClass.newInstance();
				packet = subClass.getDeclaredConstructor(getAllParameters(mainClass, (Class<?>[]) parameters)).newInstance(mainObject, parameters);
			} else {
				Class<?> packetClass = Class.forName("net.minecraft.server." + serverVersion + "." + packetName);
				packet = packetClass.getConstructor((Class<?>[]) parameters).newInstance(parameters);
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			FileManager.logExceptionToFile("", e);
			packet = null;
			exception = e;
		}
	}
	
	/**
	 * Quick and dirty constructor in the event, when creating new packets
	 * with other packets are forgotten to call the {@link #getPacket()} method.
	 * @param packetName - name of packet
	 * @param packet - object representing this class
	 */
	public Packet(String packetName, Packet packet) {
		this.packetName = packetName;
		this.packet = packet.getPacket();
	}
	
	/**
	 * Stores the given packet into this Packet to use
	 * the methods that handle reflection.
	 * @param packet - packet already from reflection
	 * @param packetName - name of class (not location)
	 */
	public Packet(Object packet, String packetName) {
		this.packet = packet;
		this.packetName = packetName;
	}
	
	// Adds the MainClass to the array
	private Class<?>[] getAllParameters(Class<?> mainClass, Class<?>[] parameters) {
		Class<?>[] toReturn = new Class<?>[parameters.length + 1];
		toReturn[0] = mainClass;
		
		for(int i = 1; i < toReturn.length; i++)
			toReturn[i] = parameters[i - 1];
		
		return toReturn;
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
				toExecute.setAccessible(true);
				return toExecute.invoke(packet, parameters);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				FileManager.logExceptionToFile("", e);
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
				FileManager.logExceptionToFile("", e);
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
				FileManager.logExceptionToFile("", e);
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
			FileManager.logExceptionToFile("", e);
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
			FileManager.logExceptionToFile("", e);
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
			FileManager.logExceptionToFile("", e);
			return null;
		}
	}

}
