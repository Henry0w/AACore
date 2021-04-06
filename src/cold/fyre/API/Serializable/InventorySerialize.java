package cold.fyre.API.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import cold.fyre.API.Utilities;

public class InventorySerialize extends AASerialize {
	
	/*
	 * 0 - Name (String)
	 * 1 - ItemType (String)
	 * 2 - Size (int)
	 * 3 - Contents (Map<Integer, ItemStackSerialize>)
	 */
	private List<Object> data = new ArrayList<Object>(4);
	private static final long serialVersionUID = 7759805781719308478L;
	
	@SuppressWarnings("unchecked")
	@Override
	protected Object deserializeObject() {
		InventoryType type = InventoryType.valueOf((String) data.get(1));
		Inventory inv = type == InventoryType.CHEST ? Bukkit.createInventory(null, (int) data.get(2), (String) data.get(0)) : Bukkit.createInventory(null, type, (String) data.get(0));
		Map<Integer, Map<String, Object>> items = (Map<Integer, Map<String, Object>>) data.get(3);
		
		for(int i : items.keySet())
			inv.setItem(i, ItemStack.deserialize(items.get(i)));
		
		return inv;
	}

	@Override
	protected AASerialize serializeObject(Object o) {
		data.add(Utilities.getTitle((Inventory) o));
		data.add(((Inventory) o).getType().name());
		data.add(((Inventory) o).getSize());

		Map<Integer, Map<String, Object>> contents = new HashMap<Integer, Map<String, Object>>();
		
		for(int i = 0; i < ((Inventory) o).getSize(); i++) {
			if(((Inventory) o).getItem(i) != null)
				contents.put(i, ((Inventory) o).getItem(i).serialize());
		}
		
		data.add(contents);
		
		return this;
	}
	
	public InventorySerialize serialize(Inventory inv) { return (InventorySerialize) serializeObject(inv); }
	
	public Inventory deserialize() { return (Inventory) deserializeObject(); }

}
