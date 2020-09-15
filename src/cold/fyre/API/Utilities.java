package cold.fyre.API;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.bukkit.Location;
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
	
	public static String replace(Chat format, String toReturn) {
		if(format == Chat.CHAT)
			return toReturn.replace('&', '§');
		else
			return toReturn.replaceAll("&", format.toString());
	}
	
	public static String[] replace(Chat format, String[] toReturn) {
		for(int line = 0; line < toReturn.length; line++)
			toReturn[line] = (format == Chat.CHAT) ? toReturn[line].replace('&', '§') : toReturn[line].replaceAll("&", format.toString());
		
		return toReturn;
	}
	
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

}
