package com.soulcraftserver.aacore.CMD.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import com.soulcraftserver.aacore.API.PermissionChecker;
import com.soulcraftserver.aacore.CMD.CommandManagerHandler;
import com.soulcraftserver.aacore.Usage.IcyHotManager;
import com.soulcraftserver.aacore.Usage.Perms;

public class CommandList extends CommandManagerHandler {

	public CommandList(CommandSender sender, Command command, String[] args, IcyHotManager pluginManager) {
		super(sender, command, args, pluginManager);
	}
	
	@Override
	public void execute() {
		if(isPlayer() && (!getPlayer().isOp() || PermissionChecker.hasOneOfPermissions(getPlayer(), Perms.values()))) {
			getPlayer().sendMessage("�c�lAACore �8�l>> �7Sorry, but you do not have permission to perform this command.");
			return;
		}
		
		getBaseSender().sendMessage("�c�m-----�f�l Plugins �c�m-----");
		for(Plugin p : getServer().getPluginManager().getPlugins()) {
			if(!p.getDescription().getAuthors().contains("Sommod") || !p.getDescription().getName().contains("AA"))
				continue;
			getBaseSender().sendMessage("�6" + p.getName() + " �7[�a" + p.getDescription().getVersion() + "�7]");
		}
		getBaseSender().sendMessage("�c�m-------------------");
	}

}
