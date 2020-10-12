package cold.fyre.API;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachmentInfo;

/**
 * <b>This does not add / remove permissions from a player</b><br>
 * Contains methods that allow checking multiple permissions of a player
 * at the same time. This removes the necessity of handling each checking
 * of the permissions one a time. This also contains the ability to check
 * any numbers attached to a permission for hierarchy purposes.
 * 
 * @author Armeriness
 * @author Sommod
 * @version 2.0
 *
 */
public class PermissionChecker {
	
	public static boolean hasAllPermissions(Player player, String... permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(String p : permissions)
			if(!player.hasPermission(p)) return false;
		
		return true;
	}
	
	public static boolean hasAllPermissions(Player player, Permission... permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(Permission p : permissions)
			if(!player.hasPermission(p)) return false;
		
		return true;
	}
	
	public static boolean hasAllPermissions(Player player, Enum<?>... permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(Enum<?> e : permissions)
			if(!player.hasPermission(e.toString())) return false;
		
		return true;
	}
	
	public static boolean hasAllPermissions(Player player, List<String> permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(String s : permissions)
			if(!player.hasPermission(s)) return false;
		
		return true;
	}
	
	public static boolean hasOneOfPermisions(Player player, String... permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(String s : permissions)
			if(player.hasPermission(s)) return true;
		
		return false;
	}
	
	public static boolean hasOneOfPermissions(Player player, Permission... permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(Permission p : permissions)
			if(player.hasPermission(p)) return true;
		
		return false;
	}
	
	public static boolean hasOneOfPermissions(Player player, Enum<?>... permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(Enum<?> e : permissions)
			if(player.hasPermission(e.toString())) return true;
		
		return false;
	}
	
	public static boolean hasOneOfPermissions(Player player, List<String> permissions) {
		if(player == null || !player.isOnline() || permissions == null) return false;
		
		for(String s : permissions)
			if(player.hasPermission(s)) return true;
		
		return false;
	}
	
	public List<String> getEffectivePermissions(Player player) {
		List<String> toReturn = new ArrayList<String>();
		
		for(PermissionAttachmentInfo pai : player.getEffectivePermissions())
			toReturn.add(pai.getPermission());
		
		return toReturn;
	}
	
	public int getNumberFromPermission(Player player, String permission) {
		for(String p : getEffectivePermissions(player)) {
			if(p.contains(permission)) {
				String[] split = p.split(".");
				String number = split[split.length - 1];
				
				try {
					return Integer.parseInt(number);
				} catch (NumberFormatException e) {
					return 0;
				}
			}
		}
		
		return 0;
	}

}
