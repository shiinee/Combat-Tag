package com.trc202.CombatTagListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;


import com.trc202.CombatTag.CombatTag;

public class CombatTagCommandPrevention implements Listener{
	
	CombatTag plugin;
	
	public CombatTagCommandPrevention(CombatTag plugin){
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
    {
		Player player = event.getPlayer();
		if(plugin.hasDataContainer(player.getName()) && !plugin.getPlayerData(player.getName()).hasPVPtagExpired()){
			for(String disabledCommand : plugin.settings.getDisabledCommands()){
				if(event.getMessage().equalsIgnoreCase(disabledCommand)){
					if(plugin.isDebugEnabled()){plugin.log.info("[CombatTag] Combat tag has blocked the command: " + disabledCommand + " .");}
					player.sendMessage("This command is disabled while in combat");
					event.setCancelled(true);
					return;
				}
			}
		}else if(plugin.hasDataContainer(player.getName()) && plugin.getPlayerData(player.getName()).hasPVPtagExpired()){
			// do nothing
		}
	}
}
