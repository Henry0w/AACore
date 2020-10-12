package cold.fyre.Usage;

import cold.fyre.IcyHot;
import cold.fyre.API.PluginManager;

public class Manager extends PluginManager<IcyHot> {

	public Manager(IcyHot plugin) {
		super(plugin);
	}
	
	private void checkPlugins() { }
	
	@Override
	public void onStartup() {
		logMessage(getHeaderMessage());
		logMessage("Running pre-check...");
		checkPlugins();
		
		logMessage(getFooterMessage());
	}
	
	@Override
	public void onShutdown() {
		logMessage(getHeaderMessage());
		logMessage(getFooterMessage());
	}
	
	@Override
	public boolean onReload() {
		return super.onReload();
	}

}
