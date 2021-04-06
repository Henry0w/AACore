package cold.fyre.API.Serializable;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;

/**
 * This creates and handles custom serialization for certain
 * bukkit objects. While these objects already have a custom
 * serialization using the ConfigurationSerialization object,
 * it doesn't serialize every object needed. This serializes
 * other objects that the current serialization does not do.
 * The current supported objects are:
 * <p>
 * Inventory, Chunk, Entity
 * </p>
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.2
 *
 */
public class Serialize {
	
	public static AASerialize serialize(Object ser) {
		
		if(ser instanceof Inventory)
			return serializeInventory((Inventory) ser);
		
		return null;
	}
	
	public static Object deserialize(AASerialize des) {
		
		if(des instanceof InventorySerialize)
			return deserializeInventory((InventorySerialize) des);
		return null;
	}
	
	protected static InventorySerialize serializeInventory(Inventory inventory) { return new InventorySerialize().serialize(inventory); }
	
	protected static Inventory deserializeInventory(InventorySerialize is) { return is.deserialize(); }
	
	protected static Object serializeChunk(Chunk chunk) {
		
		return null;
	}
	
	protected static Object serializeEntity(Entity entity) {
		
		return null;
	}

}
