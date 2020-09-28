package cold.fyre.Usage;

import cold.fyre.IcyHot;
import cold.fyre.API.PluginManager;

public class Manager extends PluginManager<IcyHot> {
	
	//PacketManager packetManager;

	public Manager(IcyHot plugin) {
		super(plugin);
	}
	
	@Override
	public void onStartup() {
		super.onStartup();
	}
	
	@Override
	public void onShutdown() {
		super.onShutdown();
	}
	
	@Override
	public boolean onReload() {
		return super.onReload();
	}

}
