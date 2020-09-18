package cold.fyre.API;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * A collection of misfit methods that reduce the amount of code needed to perform the actions
 * of the names of the methods found within this class.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 */
public class Utilities {
	
	public enum Direction { LEFT, RIGHT, FORWARD, BACKWARDS };
	
	public enum Chat {
		/** '§' */CHAT("§"), /** '\\u00A7' */MOTD("\\u00A7");
		
		private String replace;
		private Chat(String name) { replace = name; }
		
		/**
		 * Return the String object of the format for the given message output
		 */
		@Override
		public String toString() { return replace; }
	}
	
	public enum Currency {
		/** Format: $1234.56 */ CURRENCY, /** Format: $1,234.56 */ STANDARD;
	}
	
	/**
	 * Formats the number given into two standards methods of number format.
	 * @param number - Number to format
	 * @param format - Format type
	 * @return String of number
	 */
	public static String formatNumber(double number, Currency format) {
		DecimalFormat stand = new DecimalFormat("#,###.##");
		DecimalFormat money = new DecimalFormat("#0.00");
		String toReturn = "";
		
		stand.setRoundingMode(RoundingMode.HALF_UP);
		money.setRoundingMode(RoundingMode.CEILING);
		
		if(format == Currency.STANDARD)
			toReturn = stand.format(number);
		else {
			toReturn = money.format(number);
			
			if(toReturn.endsWith(".00"))
				toReturn = toReturn.substring(0, toReturn.length() - 3);
		}
		
		return toReturn;
	}
	
	/**
	 * Wraps the string into an array based on the amount of words per line.
	 * @param toWarp - String to word-wrap
	 * @param words - amount of words per line
	 * @return Array of string
	 */
	public static String[] wordWrapper(String toWarp, int words) {
		String[] toReturn = new String[(int) Math.ceil(toWarp.split(" ").length / (double) words)];
		String[] indiv = toWarp.split(" ");
		
		for(int line = 0; line < toReturn.length; line++) {
			String lineSetter = "";
			
			for(int perWord = 0 + (line * words), i = 0; i < words && perWord < indiv.length; perWord++, i++)
				lineSetter += indiv[perWord] + " ";
			
			lineSetter = lineSetter.trim();
			toReturn[line] = lineSetter;
		}
		
		return toReturn;
	}
	
	/**
	 * Replaces the string color code symbol with the correct code depending on the 
	 * use of the text.
	 * @param format - Format type of string
	 * @param toReturn - String to format.
	 * @return Formated string
	 */
	public static String replace(Chat format, String toReturn) {
		if(format == Chat.CHAT)
			return toReturn.replace('&', '§');
		else
			return toReturn.replaceAll("&", format.toString());
	}
	
	/**
	 * Replaces the string color code symbol with the correct code depending on the use of the text.
	 * Each line is checked and replaced.
	 * @param format - Format type
	 * @param toReturn - Array to format
	 * @return Formated string array
	 */
	public static String[] replace(Chat format, String[] toReturn) {
		for(int line = 0; line < toReturn.length; line++)
			toReturn[line] = (format == Chat.CHAT) ? toReturn[line].replace('&', '§') : toReturn[line].replaceAll("&", format.toString());
		
		return toReturn;
	}
	
	/**
	 * Gets the location of the direction from the starting location. The direction is based on the entity
	 * or direction the location given. The offset gets the amount of blocks in the direction.
	 * @param start - Location to start from
	 * @param offset - Amount of blocks into the direction
	 * @param direction - Where to get the new location of which way
	 * @return Location of in the new direction
	 */
	public static Location getOffSetLocation(Location start, double offset, Direction direction) {
		if(offset < 0.01D || start == null)
			return start;
		
		Vector dir = start.getDirection().normalize();
		
		if(direction == Direction.RIGHT)
			dir = new Vector(-dir.getZ(), 0.0, dir.getX()).normalize();
		else if(direction == Direction.LEFT)
			dir = new Vector(dir.getZ(), 0.0, -dir.getX()).normalize();
		else if(direction == Direction.FORWARD)
			dir = new Vector(-dir.getX(), 0.0, dir.getZ()).normalize();
		else
			dir = new Vector(dir.getX(), 0.0, -dir.getZ());
		
		return start.add(dir.multiply(offset));
	}
	
	/**
	 * Gets the block the player is looking at or within the distance given. If the player
	 * is not looking at a block within the distance, then this will return null.
	 * @param player - Player to get block of.
	 * @param distance - Number of blocks to check
	 * @return Block of player looking at.
	 */
	public Block getTargetBlock(Player player, int distance) { return player.getTargetBlock((Set<Material>) null, distance); }

}
