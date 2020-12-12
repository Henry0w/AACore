package cold.fyre.API.Packets;

import java.util.Locale;

import org.bukkit.entity.Player;

import cold.fyre.API.Managers.FileManager;

/**
 * Class that is used to handle classes of NMS. This uses reflection
 * to access and use the packets. As such, the normal methods will not
 * appear. To use any of the methods found within the class, you need
 * to know what the method name is, including any capitalization, what
 * objects the method takes, and what type of objects the methods take.
 * Once all the information is given, you can grab the object from the
 * return method.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 */
public interface AAPacket {
	
	/**
	 * Gets the name of the packet intended to use.
	 * @return Name of the Packet Class
	 */
	String getName();
	
	/**
	 * Gets the packet in the form of an object.
	 * @return Packet
	 */
	Object getPacket();
	
	/**
	 * Runs the method of the given method name. This will return the
	 * object of the method. If the method is a void method, then this
	 * a NULL object.
	 * @param methodName - name of method to run
	 * @param parameters - any parameters needed by the method
	 * @return Object of method, or null
	 */
	Object runMethod(String methodName, Object... parameters);
	
	/**
	 * Gets the value of the field.
	 * @param fieldName - name of field
	 * @return Object of value
	 */
	Object getFieldValue(String fieldName);
	
	/**
	 * Sets the field to the given value. If the
	 * field is not compatible, then this method
	 * will thrown an error.
	 * @param fieldName
	 * @param value
	 */
	void setFieldValue(String fieldName, Object value);
	
	
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
	static Enum<?> getEnum(String packetClass, String enumName) {
		
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
	static Class<?> getClass(String packetClass) {
		try {
			return Class.forName(packetClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			FileManager.logExceptionToFile(null, e);
			return null;
		}
	}
	
	/**
	 * Gets the error that would occur if the packet object was
	 * to be called. If no Exception would be thrown, then this
	 * will return null.
	 * @return Exception that would be thrown
	 */
	Exception getError();
	
	/**
	 * Checks if the Packet has an error when trying to get it.
	 * This can be used to check if the packet is ready to get
	 * and if the method {@link #getError()} should be ran.
	 * @return
	 */
	boolean hasError();
	
	/**
	 * Sends the packet to the player.
	 * @param player - player to send packet to
	 */
	void sendPacket(Player player);

}
