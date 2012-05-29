package com.sirdrakeheart.plotmanager;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PlotManagerPlugin extends JavaPlugin {
	
	public static Logger log;
	public static String filepath = "plugins" + File.separator + "SDHPlotManager" + File.separator;
	public static PlotManagerPlugin main;

	public void onEnable() {
		log = Logger.getLogger("Minecraft");
		
		PluginManager pm = getServer().getPluginManager();
	    
	    Listener events = new Events();
	    pm.registerEvents(events, this);
		
		log.info("SirDrakeHeart Plot Manager plugin enabled.");
	}
	
	public void onDisable() {
		log.info("SirDrakeHeart Plot Manager plugin enabled.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(cmd.getName().equalsIgnoreCase("makeprivate")){
			return CommandManager.run(sender, cmd, commandLabel, args);
		}
		else {
			return false;
		}
	}
	
}
