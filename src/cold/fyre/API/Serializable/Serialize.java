package cold.fyre.API.Serializable;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * This creates and handles custom serialization for certain
 * bukkit objects. While these objects already have a custom
 * serialization using the YamlConfiguration object, it doesn't
 * in itself serialize the objects but acts as the medium for
 * the storing of serialized objects. This turns the Bukkit objects
 * into a serialized object that can be stored in any file type.<br>
 * <br>
 * Currently, this supports serializing / de-serializing the following
 * Bukkit Objects:<br>
 * <p>
 * ItemStack, Inventory<br>Block, Chunk<br>Location, Vector<br>Entity
 * </p>
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.2
 *
 */
public class Serialize {
	
	public AASerialize serialize(Object ser) {
		
		return null;
	}
	
	public Object deserialize(Object des) {
		
		return null;
	}
	
	protected Object serializeItemStack(ItemStack item) {
		return new ItemStackSerialize().serialize(item);
	}
	
	protected Object serializeInventory(Inventory inventory) {
		
		return null;
	}
	
	protected Object serializeBlock(Block block) {
		
		return null;
	}
	
	protected Object serializeChunk(Chunk chunk) {
		
		return null;
	}
	
	protected Object serializeEntity(Entity entity) {
		
		return null;
	}
	
	protected Object serializeLocation(Location location) {
		
		return null;
	}
	
	protected Object serializeVector(Vector vector) {
		
		return null;
	}

}
