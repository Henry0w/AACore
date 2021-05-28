package com.soulcraftserver.aacore.Usage;

import java.io.File;

import com.soulcraftserver.aacore.API.Managers.PacketManager;
import com.soulcraftserver.aacore.API.Managers.PluginManager;
import com.soulcraftserver.aacore.API.Packets.AbstractPacketManager;
import com.soulcraftserver.aacore.CMD.CommandHandler;
import com.soulcraftserver.aacore.Events.DeveloperMessage;
import com.soulcraftserver.aacore.IcyHot;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.ServicePriority;


public class IcyHotManager extends PluginManager<IcyHot> {
	
	private PacketManager pm = null;

	public IcyHotManager(IcyHot plugin) {
		super(plugin);
	}
	
	@Override
	public void onStartup() {
		logMessage(getHeaderMessage());
		
		logMessage("Registering Config files...");
		registerConfigs();
		
		logMessage("Config files loaded");
		logMessage("Registering Commands...");
		new CommandHandler(this);
		
		logMessage("Commands Registered");
		logMessage("Registering Events...");
		new DeveloperMessage(this);
		
		logMessage("Events Registered");
		logMessage("Registering PacketManager...");
		if(pm == null) {
			pm = new PacketManager(getPlugin().getServer(), getVersion(), this);
			registerClass(PacketManager.class, pm, ServicePriority.Normal);
		}
		
		logMessage("Plugin enabled!");
		logMessage(getFooterMessage());
	}
	
	@Override
	public void onShutdown() {
		logMessage(getHeaderMessage());
		logMessage("Shutting down...");
		HandlerList.unregisterAll(getPlugin());
		logMessage("Shutdown complete");
		logMessage(getFooterMessage());
	}
	
	@Override
	public boolean onReload() {
		registerConfigs();
		return true;
	}
	
	private void registerConfigs() {
		if(!getPlugin().getDataFolder().exists())
			getPlugin().getDataFolder().mkdir();
		
		File config = new File(getPlugin().getDataFolder(), "config.yml");
		
		if(!config.exists())
			createFile(getPlugin().getClass().getResourceAsStream("/Resources/config.yml"), config);
	}
	
	private AbstractPacketManager.ServerVersion getVersion() {
		String version = getPlugin().getServer().getClass().getPackage().getName().split("\\.")[3];
		
		for(AbstractPacketManager.ServerVersion sv : AbstractPacketManager.ServerVersion.values()) {
			if(sv.toPackageString().equalsIgnoreCase(version))
				return sv;
		}
		
		return AbstractPacketManager.ServerVersion.ERROR;
	}

}
