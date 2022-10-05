package net.juicy.api.utils.placeholder.placeholders;

import net.juicy.api.JuicyAPIPlugin;
import org.bukkit.entity.Player;
import net.juicy.api.utils.placeholder.IPlaceholder;

public class APIPlaceholder implements IPlaceholder {

    private static final APIPlaceholder apiPlaceholder = new APIPlaceholder();

    public static APIPlaceholder getApiPlaceholder() {

        return apiPlaceholder;

    }

    protected String replace(String string) {

        return replace(string, null);

    }

    public String replace(String string, Player player) {

        return JuicyAPIPlugin.getPlugin().replace(string);

    }
}