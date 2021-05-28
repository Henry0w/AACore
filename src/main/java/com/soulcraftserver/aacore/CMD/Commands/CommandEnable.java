package com.soulcraftserver.aacore.CMD.Commands;

import com.soulcraftserver.aacore.API.PermissionChecker;
import com.soulcraftserver.aacore.Events.AAEnable;
import com.soulcraftserver.aacore.Usage.IcyHotManager;
import com.soulcraftserver.aacore.Usage.Perms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;


import com.soulcraftserver.aacore.CMD.CommandManagerHandler;


public class CommandEnable extends CommandManagerHandler {

	public CommandEnable(CommandSender sender, Command command, String[] args, IcyHotManager pluginManager) {
		super(sender, command, args, pluginManager);
	}
	
	@Override
	public void execute() {
		if(isPlayer() && (!getPlayer().isOp() || !PermissionChecker.hasOneOfPermissions(getPlayer(), Perms.OP, Perms.ENABLE))) {
			getPlayer().sendMessage("�c�lAACore �8�l>> �7Sorry, but you do not have permission to perform this command.");
			return;
		}
		
		Plugin target = getServer().getPluginManager().getPlugin(getArg(1));
		
		if(target == null) {
			getBaseSender().sendMessage("�c�lAACore �8�l>> �7Error, plugin �b" + getArg(1) + "�7 does not exist.");
			return;
		} else if(!target.getDescription().getName().contains("AA") || !target.getDescription().getAuthors().contains("Sommod")) {
			getBaseSender().sendMessage("�c�lAACore �8�l>> �7Error, plugin �b" + getArg(1) + "�7 is not an AA-plugin.");
			return;
		} else if(target.isEnabled()) {
			getBaseSender().sendMessage("�c�lAACore �8�l>> �7Error, plugin is already enabled.");
			return;
		}
		
		AAEnable event = new AAEnable(getPluginManager(), target);
		getServer().getPluginManager().callEvent(event);
		
		if(event.isCancelled()) {
			getBaseSender().sendMessage("�c�lAACore �8�l>> �7Notice, �b" + getArg(1) + "�7 was cancelled from being enabled.");
			return;
		}
		
		getBaseSender().sendMessage("�c�lAACore �8�l>> �7Enabling plugin...");
		getServer().getPluginManager().enablePlugin(target);
		
		if(target.isEnabled())
			getBaseSender().sendMessage("�c�lAACore �8�l>> �aPlugin Enabled");
		else
			getBaseSender().sendMessage("�c�lAACore �8�l>> �cError, a problem occurred when attempting to enable plugin.");
		
	}

}
