package cold.fyre.Usage;

import org.bukkit.plugin.ServicePriority;

import cold.fyre.IcyHot;
import cold.fyre.API.PacketManager;
import cold.fyre.API.PluginManager;
import cold.fyre.API.Packets.AbstractPacketManager.ServerVersion;

public class Manager extends PluginManager<IcyHot> {
	
	private PacketManager packetManager;

	public Manager(IcyHot plugin) {
		super(plugin);
	}
	
	@Override
	public void onStartup() {
		initPacketManager();
	}
	
	@Override
	public void onShutdown() {
		super.onShutdown();
	}
	
	@Override
	public boolean onReload() {
		return super.onReload();
	}
	
	@Override
	public PacketManager getPacketManager() {
		return packetManager;
	}
	
	// Initializes the PacketManager class and registers it
	// within the RegisteredServiceProvider
	private void initPacketManager() {
		String version = getPlugin().getServer().getClass().getPackage().toString().split("\\.")[3];
		
		for(ServerVersion sv : ServerVersion.values()) {
			if(sv.toPackageString().equalsIgnoreCase(version)) {
				packetManager = new PacketManager(getPlugin().getServer(), sv, this);
				registerClass(PacketManager.class, packetManager, ServicePriority.Normal);
			}
		}
	}

}
