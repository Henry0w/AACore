package cold.fyre.Usage;

import cold.fyre.IcyHot;
import cold.fyre.API.PluginManager;

public class Manager extends PluginManager<IcyHot> {

	public Manager(IcyHot plugin) {
		super(plugin);
	}
	
	@Override
	public void onStartup() {
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
