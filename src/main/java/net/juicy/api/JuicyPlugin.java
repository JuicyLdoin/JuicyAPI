package net.juicy.api;

import net.juicy.api.utils.util.ColorUtil;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class JuicyPlugin extends JavaPlugin {
    
    public abstract String getPrefix();
    
    public String replace(String string) {

        if (getPrefix() != null)
            string = string.replace("%prefix%", getPrefix());

        return ColorUtil.makeColor(string);

    }
    
    public abstract void reload();

}