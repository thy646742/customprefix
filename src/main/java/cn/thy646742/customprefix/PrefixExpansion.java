package cn.thy646742.customprefix;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.entity.Player;

public class PrefixExpansion extends PlaceholderExpansion {
    private CustomPrefix plugin;
    public PrefixExpansion(CustomPrefix plugin){
        this.plugin = plugin;
    }
    public boolean canRegister(){
        return true;
    }
    public boolean persist(){
        return true;
    }
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }
    public String getIdentifier(){
        return "customprefix";
    }
    public String getVersion(){
        return plugin.getDescription().getVersion().toString();
    }
    public String onPlaceholderRequest(Player player, String identifier){
        if(plugin.datayml.getConfig().getString(player.getName()+"."+identifier)!=null){
            return plugin.datayml.getConfig().getString(player.getName()+"."+identifier);
        }
        return "";
    }
}