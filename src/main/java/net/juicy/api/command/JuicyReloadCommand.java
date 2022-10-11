package net.juicy.api.command;

import net.juicy.api.JuicyAPIPlugin;
import net.juicy.api.JuicyPlugin;
import net.juicy.api.utils.command.Command;
import net.juicy.api.utils.command.CommandArgument;
import net.juicy.api.utils.command.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

@Command(aliases = { "juicyreload", "jr" }, permissions = "juicy.reload")
public class JuicyReloadCommand implements ICommand {

    private final JuicyAPIPlugin plugin = JuicyAPIPlugin.getPlugin();

    @CommandArgument(allArgs = true)
    public void onCommand(CommandSender commandSender, String[] args) {

        if (args.length != 1) {

            commandSender.sendMessage(this.plugin.replace("%prefix%Используйте: &e/juicyreload [имя плагина]!"));
            return;

        }

        String pluginName = args[0];
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);

        if (plugin == null) {

            commandSender.sendMessage(this.plugin.replace("%prefix%Плагин &e" + pluginName + " &fне найден!"));
            return;

        }

        try {

            ((JuicyPlugin) plugin).reload();
            commandSender.sendMessage(this.plugin.replace("%prefix%Плагин &e" + pluginName + " &fперезагружён!"));

        } catch (Exception ignored) {

            commandSender.sendMessage(this.plugin.replace("%prefix%Плагин &e" + pluginName + " &fне является частью плагинов проекта!"));

        }
    }
}