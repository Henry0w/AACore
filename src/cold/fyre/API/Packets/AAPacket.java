package cold.fyre.API.Packets;

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
	
	String getName();
	Object getPacket();
	Object runMethod(String methodName, Object... parameters);
	Object getField(String fieldName);
	void setField(String fieldName, Object value);
	static Enum<?> getEnum(String packetClass) {
		Enum<?> toReturn = null;
		
		try {
			Class<?> packetClazz = Class.forName(packetClass);
			
			if(packetClazz.isEnum()) {
				toReturn = (Enum<?>) packetClazz.cast(Enum.class);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			FileManager.logExceptionToFile(null, e);
		}
		
		return toReturn;
	}
	
	Exception getError();
	boolean hasError();

}
