package cold.fyre.Events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import cold.fyre.Usage.Manager;

public class AADisable extends Event implements Cancellable {
	
	private boolean cancel = false;
	@SuppressWarnings("unused")
	private Manager Manager;
	private Plugin plugin;
	
	public AADisable(Manager manager, Plugin plugin) {
		this.plugin = plugin;
		this.Manager = manager;
	}
	
	/**
	 * Gets the plugin that is being disabled.
	 * @return Plugin
	 */
	public Plugin getPlugin() { return plugin; }

	@Override
	public HandlerList getHandlers() { return new HandlerList(); }

	@Override
	public boolean isCancelled() { return cancel; }

	@Override
	public void setCancelled(boolean cancel) { this.cancel = cancel;}

}
