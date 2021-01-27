package cold.fyre.API.Packets.minecraft.support;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NBTTagCompound {
	
	protected final Map<String, Object> map;
	
	public NBTTagCompound() { map = new HashMap<>(); }
	
	public NBTTagCompound(NBTTagCompound clone) { map = clone.map; }
	
	public Set<String> getKeys() { return map.keySet(); }
	
	public Collection<Object> getValues() { return map.values(); }
	
	public byte getTypeID() { return 10; }
	
	public int getSize() { return map.size(); }
	
	public void setByte(final String name, final byte value) { map.put(name, value); }
	
	public void setShort(final String name, final short value) { map.put(name, value); }
	
	public void setInt(final String name, final int value) { map.put(name, value); }
	
	public void setLong(final String name, final long value) { map.put(name, value); }
	
	public void setFloat(final String name, final float value) { map.put(name, value); }
	
	public void setDouble(final String name, final double value) { map.put(name, value); }
	
	public void setString(final String name, final String value) { map.put(name, value); }
	
	public void setByteArray(final String name, final byte[] value) { map.put(name, value); }
	
	public void setIntArray(final String name, final int[] value) { map.put(name, value); }
	
	public void setBoolean(final String name, final boolean value) { map.put(name, value); }
	
	public void set(final String name, final NBTTagCompound value) { map.put(name, value); }
	
	public boolean hasKey(final String key) { return map.containsKey(key); }
	
	public byte getByte(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Byte))
			return 0;
		
		return (byte) map.get(key);
	}
	
	public int getInt(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Integer))
			return 0;
		
		return (int) map.get(key);
	}
	
	public short getShort(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Short))
			return 0;
		
		return (short) map.get(key);
	}
	
	public long getLong(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Long))
			return 0L;
		
		return (long) map.get(key);
	}
	
	public float getFloat(final String key) {
		if(map.get(key) == null ||  !(map.get(key) instanceof Float))
			return 0F;
		
		return (float) map.get(key);
	}
	
	public double getDouble(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Double))
			return 0D;
		
		return (double) map.get(key);
	}
	
	public String getString(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof String))
			return null;
		
		return (String) map.get(key); 
	}
	
	public byte[] getByteArray(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Byte[]))
			return new byte[0];
		
		return (byte[]) map.get(key);
	}
	
	public int[] getIntArray(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Integer[]))
			return new int[0];
		
		return (int[]) map.get(key);
	}
	
	public boolean getBoolean(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof Boolean))
			return false;
		
		return (boolean) map.get(key);
	}
	
	public NBTTagCompound getNBTTagCompound(final String key) {
		if(map.get(key) == null || !(map.get(key) instanceof NBTTagCompound))
			return null;
		
		return (NBTTagCompound) map.get(key);
	}
	
	public Object getObject(final String key) { return map.get(key); }

}
