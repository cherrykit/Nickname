package me.cherrykit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Nickname extends JavaPlugin implements Listener{

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("nickname") && sender instanceof Player) {
			
			Player p = (Player) sender;
			PlayerInventory inv = p.getInventory();
			String newname;
			
			try {
				newname = args[0];
			}
			catch (Exception e) {
				newname = null;
			}
			
			if (inv.contains(Material.NAME_TAG)) {
				if (newname != null && newname.length() <= 16) {
					
					ItemStack stack = inv.getItem(inv.first(Material.NAME_TAG));
					stack.setAmount(stack.getAmount()-1);
					
					p.setDisplayName("[" + args[0] + "] " + p.getName());
					p.setCustomName(args[0]);
					p.sendMessage(ChatColor.GOLD + "Your nickname has been changed to " + args[0] + ".");
				
				} else {
					
					p.sendMessage(ChatColor.RED + "Usage: /nickname <name> "
							+ "(please note the name cannot be longer than 16 characters)");
				
				}
			} else {
				p.sendMessage(ChatColor.RED + "You need a name tag to set a nickname!");
			}
			return true;
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		String playername = p.getCustomName();
		if (playername != null) {
			p.setDisplayName("[" + playername + "] " + p.getName());
			e.setJoinMessage(ChatColor.YELLOW + p.getCustomName() + " joined the server!");
		} else {
			e.setJoinMessage(ChatColor.YELLOW + p.getName() + " joined the server!");
		}	
	}
}
