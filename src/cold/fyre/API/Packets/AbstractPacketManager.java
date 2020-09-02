package cold.fyre.API.Packets;

import org.bukkit.Server;
import org.bukkit.entity.Player;

/**
 * Contains a list of different methods that use packets to which are sent to either
 * the server of player. The methods found here are SOME of the commonly used actions
 * from the NMS classes, but NOT all. Each method will have details as to what version
 * of Minecraft the method can be used with. Some methods may not be eligible on the
 * current version of Minecraft the server may run. Most of these methods do not allow you to
 * directly interact with the packets, but input data and have the code handle the rest.
 * Some methods do return manipulatable objects.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 */
public abstract class AbstractPacketManager {
	
	private Server server;
	private ServerVersion serverVersion;
	//private IcyHot manager;
	
	/**
	 * Contains the Server version of the server. Note that some of the
	 * versions may not be present, this is because many of the versions
	 * share the same class paths, i.e. the same NMS classes.
	 * 
	 * @author Armeriness
	 * @author Sommod
	 * @since 2.0
	 *
	 */
	public enum ServerVersion {
		V1_8("1.8", 1.8), V1_8_8("1.8.8", 1.88), V1_9("1.9", 1.9), V1_9_4("1.9.4", 1.94), V1_10_2("1.10.2", 1.102),
		V1_11_2("1.11.2", 1.112), V1_12_2("1.12.2", 1.122), V1_13("1.13", 1.13), V1_13_2("1.13.2", 1.132),
		V1_14_4("1.14.4", 1.144), V1_15_1("1.15.1", 1.151), VERROR("Error", 0.0);
		private String name;
		private double num;
		
		private ServerVersion(String name, double num) {
			this.name = name;
			this.num = num;
		}
		
		/**
		 * Returns the String representation of the server version. Note
		 * that the String version will have the decimal points, i.e.
		 * V1_15_1 will return "1.15.1".
		 */
		@Override
		public String toString() { return name; }
		
		/**
		 * The number will return a double of the version, i.e. V1_15_1
		 * will return "1.151".
		 * @return
		 */
		public double toNumber() { return num; }
	}
	
	public AbstractPacketManager(Server server, ServerVersion version/**, IcyHot coldfyre*/) {
		this.server = server;
		serverVersion = version;
		//manager = coldfyre;
	}
	
	/**
	 * Returns the Server as an Object.
	 * @return Server
	 */
	public Server getServer() { return server; }
	
	/**
	 * Gets the Minecraft Version the server is running.
	 * @return Minecraft Version
	 */
	public ServerVersion getVersion() { return serverVersion; }
	
	/**
	 * Formats the String into a standard TEXT JSON format.
	 * @param convert - String to change into JSON format
	 * @return String as JSON
	 */
	protected String toJSON(String convert) { return "{\"text\":" + convert + "\"}"; }
	
	/**
	 * Sends a player a Title Message with no Subtitle. This is the text that appears in the middle of the players
	 * screen. Depending on the Minecraft Version of the server, the Fade-In, Out and display time can be set. For
	 * the version that does not support it, it will run a standard time of (1) second fade in/out and a (2) second
	 * display time.
	 * @param toSend - Player to send message to
	 * @param message - Message to send
	 * @param fadeIn - Time (in seconds) for text to fade into view
	 * @param displayTime - Time (in seconds) for text to display on screen
	 * @param fadeOut - Time - (in seconds) for text to fade out of view
	 */
	public abstract void sendTitle(Player toSend, String message, int fadeIn, int displayTime, int fadeOut);
	
	public abstract void sendSubTitle(Player toSend, String message, int fadeIn, int displayTime, int fadeOut);

}
