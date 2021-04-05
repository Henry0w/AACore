package cold.fyre.API.Serializable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import cold.fyre.API.Packets.Converter;
import cold.fyre.API.Packets.minecraft.support.NBTTagCompound;

public class ItemStackSerialize extends AASerialize {
	
	/*
	 * 0 - Name (String)
	 * 1 - Type (Material)
	 * 2 - Amount (int)
	 * 3 - Damage (short)
	 * 4 - Enchants List (String:int)
	 * 5 - ItemFlags List (String)
	 * 6 - Lore List (String)
	 * 7 - NBTTags (NBTTagCompound)
	 */
	private List<Object> values = new ArrayList<>(8);

	private static final long serialVersionUID = 2654564154165412512L;

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	protected Object deserializeObject() {
		if(values == null || values.size() == 0)
			return null;
		
		// Item Creation
		ItemStack des = new ItemStack(Material.getMaterial((String) values.get(1)));
		des.setAmount((int) values.get(2));
		
		// Sets durability if item has one
		if((short) values.get(3) > 0)
			des.setDurability((short) values.get(3));
		
		ItemMeta meta = des.getItemMeta();
		
		// Set Name
		if(((String) values.get(0)).length() > 0)
			meta.setDisplayName(((String) values.get(0)).replace('&','§'));
		
		// Sets Enchants
		if(((List<String>) values.get(4)).size() > 0) {
			for(String enchantLvl : (List<String>) values.get(4)) {
				Enchantment e = Enchantment.getByName(enchantLvl.split(":")[0]);
				meta.addEnchant(e, Integer.parseInt(enchantLvl.split(":")[1]), Integer.parseInt(enchantLvl.split(":")[1]) >= e.getMaxLevel() ? true : false);
			}
		}
		
		// Sets ItemFlags
		if(((List<String>) values.get(5)).size() > 0) {
			for(String flag : (List<String>) values.get(5)) {
				ItemFlag toAdd = ItemFlag.valueOf(flag);
				meta.addItemFlags(toAdd);
			}
		}
		
		// Sets Lore
		if(((List<String>) values.get(6)).size() > 0) {
			meta.setLore((List<String>) values.get(6));
		}
		
		// Set NBTTags
		if(values.get(7) != null)
			applyNBTTags(des);
		
		return des;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected AASerialize serializeObject(Object o) {
		ItemStack item = (ItemStack) o;
		
		String name = item.getItemMeta().getDisplayName();
		String type = item.getType().name();
		int amount = item.getAmount();
		short damage = item.getDurability();
		List<String> lore = item.getItemMeta().getLore();
		List<String> enchants = new ArrayList<String>();
		List<String> flags = new ArrayList<String>();
		NBTTagCompound save;
		
		for(Enchantment key: item.getItemMeta().getEnchants().keySet())
			enchants.add(key.getName() + ":" + item.getEnchantments().get(key));

		for(ItemFlag i : item.getItemMeta().getItemFlags())
			flags.add(i.name());
		
		try {
			Class<?> craftItemClass = Class.forName("org.bukkit.craftbukkit.inventory.CraftItemStack");
			Object nmsItem = craftItemClass.getMethod("asNMSCopy", item.getClass()).invoke(null, item);
			save = new NBTTagCompound(nmsItem.getClass().getMethod("getTag").invoke(nmsItem));
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			save = new NBTTagCompound();
		}
		
		values.add(name);
		values.add(type);
		values.add(amount);
		values.add(damage);
		values.add(enchants);
		values.add(flags);
		values.add(lore);
		values.add(save);
		
		return this;
	}
	
	public ItemStackSerialize serialize(ItemStack item) { return (ItemStackSerialize) serializeObject(item); }
	
	public ItemStack deserialize() { return (ItemStack) deserializeObject(); }
	
	private void applyNBTTags(ItemStack item) {
		try {
			Class<?> craftItemClass = Class.forName("org.bukkit.craftbukkit.inventory.CraftItemStack");
			Object nmsItem = craftItemClass.getMethod("asNMSCopy", item.getClass()).invoke(null, item);
			NBTTagCompound tag = (NBTTagCompound) values.get(7);
			Object nbtTag = Converter.convertNBT(tag);
			
			nmsItem.getClass().getMethod("setTag", nbtTag.getClass()).invoke(nmsItem, nbtTag);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
