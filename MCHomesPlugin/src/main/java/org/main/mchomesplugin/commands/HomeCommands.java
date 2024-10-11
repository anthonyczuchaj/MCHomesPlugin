package org.main.mchomesplugin.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;


public class HomeCommands implements CommandExecutor {
    private final FileConfiguration config;

    public HomeCommands(FileConfiguration config){
        this.config = config;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!((sender) instanceof Player)){return true;}
        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase("sethome")){
            if(args.length >=1){
                String homeName = args[0];
                double x = player.getLocation().getX();
                double y = player.getLocation().getY();
                double z = player.getLocation().getZ();

                String path = "homes."+ player.getUniqueId() + "." + player.getName() + "." +homeName;
                config.set(path + ".world",player.getLocation().getWorld().getName());
                config.set(path + ".x", x);
                config.set(path + ".y", y);
                config.set(path + ".z", z);

                player.getServer().getPluginManager().getPlugin("MCHomesPlugin").saveConfig();

                player.sendMessage(homeName+" has been created");
            }else{
                player.sendMessage("Missing argument /sethome <name>");
            }
            return true;
        }

        if(command.getName().equalsIgnoreCase("home")){
            if(args.length >=1){
                String homeName = args[0];
                String path = "homes."+ player.getUniqueId() + "." + player.getName() + "." +homeName;
                if(config.contains(path)){
                    double x = config.getDouble(path+".x");
                    double y = config.getDouble(path+".y");
                    double z = config.getDouble(path+".z");

                    World world = player.getServer().getWorld(Objects.requireNonNull(config.getString(path + ".world")));
                    Location location = new Location(world,x,y,z);

                    player.teleport(location);
                    System.out.println(homeName + " exists! at: "+x+", "+y+", "+z);
                }else{
                    player.sendMessage("<"+homeName+"> there are NO homes by that name");
                }
            }
            return true;
        }

        if(command.getName().equalsIgnoreCase("deletehome")){
            if(args.length>=1){
                String homeName = args[0];
                String path = "homes."+ player.getUniqueId() + "." + player.getName() + "." +homeName;
                if(config.contains(path)){
                    config.set(path,null);
                }

            }
            return true;
        }

        if(command.getName().equalsIgnoreCase("homes")){
            String path = "homes."+ player.getUniqueId() + "." + player.getName();
            Set<String> homeNames = config.getConfigurationSection(path).getKeys(false);
            player.sendMessage("Your Homes: "+ homeNames);
            return true;
        }


        return true;
    }
}
