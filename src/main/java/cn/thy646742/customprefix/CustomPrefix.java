package cn.thy646742.customprefix;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Map;

public final class CustomPrefix extends JavaPlugin{
    String msgSyntaxIncorrect, msgTargetOffline, msgPluginEnabled, msgPluginDisabled, msgReloadComplete;
    String msgInfoUndefined, msgGetInfo, msgSetInfo, msgResetInfo, msgPapiNotFound, msgListLength, msgGetList;
    ConfigAccessor datayml = new ConfigAccessor(this, "data.yml");
    public void onEnable(){
        this.saveDefaultConfig();
        datayml.saveDefaultConfig();
        reloadMsg();
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new PrefixExpansion(this).register();
        }
        else getLogger().info(msgPapiNotFound);
        getLogger().info(msgPluginEnabled);
    }
    public void onDisable(){
        getLogger().info(msgPluginDisabled);
    }
    public boolean onCommand(CommandSender sender, Command command, String aliases, String[] arguments){
        if(command.getName().equalsIgnoreCase("customprefix")){
            if(arguments.length != 1)return false;
            if(arguments[0].equalsIgnoreCase("reload")){
                this.reloadConfig();
                datayml.reloadConfig();
                reloadMsg();
                sender.sendMessage(msgReloadComplete);
                return true;
            }
            return false;
        }
        if(command.getName().equalsIgnoreCase("prefix")){
            if(arguments.length == 0 || arguments[1] == null){
                sender.sendMessage(msgSyntaxIncorrect);
                return true;
            }
            Player target = Bukkit.getServer().getPlayer(arguments[1]);
            if(target == null){
                sender.sendMessage(msgTargetOffline);
                return true;
            }
            if(arguments[0].equalsIgnoreCase("info")){// /prefix info <target> <slot>
                if(arguments.length != 3){
                    sender.sendMessage(msgSyntaxIncorrect);
                    return true;
                }
                String info = datayml.getConfig().getString(target.getName()+"."+arguments[2]);
                if(info == null || info.equals("")) {
                    sender.sendMessage(msgInfoUndefined);
                }
                else{
                    sender.sendMessage(replaceMsg(msgGetInfo, arguments[2], target.getName(), info, ""));
                }
                return true;
            }
            else if(arguments[0].equalsIgnoreCase("set")){// /prefix set <target> <slot> <prefix>
                if(arguments.length != 4){
                    sender.sendMessage(msgSyntaxIncorrect);
                    return true;
                }
                arguments[3]=replaceColorCode(arguments[3]);
                datayml.getConfig().set(target.getName()+"."+arguments[2], arguments[3]);
                datayml.saveConfig();
                datayml.reloadConfig();
                sender.sendMessage(replaceMsg(msgSetInfo, arguments[2], target.getName(), arguments[3], ""));
                return true;
            }
            else if(arguments[0].equalsIgnoreCase("reset")){// /prefix reset <target> <slot>
                if(arguments.length != 3){
                    sender.sendMessage(msgSyntaxIncorrect);
                    return true;
                }
                datayml.getConfig().set(target.getName()+"."+arguments[2], null);
                datayml.saveConfig();
                datayml.reloadConfig();
                sender.sendMessage(replaceMsg(msgResetInfo, arguments[2], target.getName(), "", ""));
                return true;
            }
            else if(arguments[0].equalsIgnoreCase("list")){// /prefix list <target>
                if(arguments.length != 2){
                    sender.sendMessage(msgSyntaxIncorrect);
                    return true;
                }
                Map<String, Object> list = datayml.getConfig().getConfigurationSection(target.getName()).getValues(false);
                Object[] keys = list.keySet().toArray();
                Object[] values = list.values().toArray();
                int prefixNum = keys.length;
                sender.sendMessage(replaceMsg(msgListLength, "", target.getName(), "", prefixNum+""));
                String listMsg = msgGetList;
                if(prefixNum == 0)return true;
                for(int i = 0; i < prefixNum - 1; i++){
                    listMsg += "&a" + keys[i].toString() + "&6 : &r" + values[i].toString() + "&6, ";
                }
                listMsg += "&a" + keys[prefixNum - 1].toString() + "&6 : &r" + values[prefixNum - 1].toString() + "&6.";
                sender.sendMessage(replaceColorCode(listMsg));
                return true;
            }
            else{
                sender.sendMessage(msgSyntaxIncorrect);
                return true;
            }
        }
        sender.sendMessage(msgSyntaxIncorrect);
        return true;
    }
    protected void reloadMsg(){
        msgSyntaxIncorrect = loadFromConfig("messages.syntax-incorrect");
        msgTargetOffline = loadFromConfig("messages.target-offline");
        msgPluginEnabled = loadFromConfig("messages.plugin-enabled");
        msgPluginDisabled = loadFromConfig("messages.plugin-disabled");
        msgReloadComplete = loadFromConfig("messages.reload-complete");
        msgInfoUndefined = loadFromConfig("messages.info-undefined");
        msgGetInfo = loadFromConfig("messages.get-info");
        msgSetInfo = loadFromConfig("messages.set-info");
        msgResetInfo = loadFromConfig("messages.reset-info");
        msgPapiNotFound = loadFromConfig("messages.papi-not-found");
        msgListLength = loadFromConfig("messages.list-length");
        msgGetList = loadFromConfig("messages.get-list");
    }
    private String loadFromConfig(String str){
        return replaceColorCode(this.getConfig().getString(str));
    }
    protected String replaceMsg(String str, String slot, String target, String prefix, String number){
        String message;
        message = str.replaceAll("\\{slot}", slot);
        message = message.replaceAll("\\{target}", target);
        message = message.replaceAll("\\{prefix}", prefix);
        message = message.replaceAll("\\{number}", number);
        return message;
    }
    protected String replaceColorCode(String str){
        str = str.replaceAll("&", "§");
        return str;
    }
}