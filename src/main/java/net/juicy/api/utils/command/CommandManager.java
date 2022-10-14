package net.juicy.api.utils.command;

import lombok.NonNull;
import lombok.Value;
import net.juicy.api.JuicyAPIPlugin;
import net.juicy.api.JuicyPlugin;
import net.juicy.api.utils.load.ILoadable;
import net.juicy.api.utils.util.collection.ArrayManager;
import net.juicy.player.JuicyPlayerPlugin;
import net.juicy.player.player.auth.AuthPlayerStatus;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Value
public class CommandManager implements CommandExecutor, ILoadable {

    JuicyPlugin plugin;
    JuicyAPIPlugin juicyAPIPlugin;

    String command;

    Map<String, List<Class<? extends ICommand>>> commandClasses;
    Map<Class<? extends ICommand>, List<CommandMethod>> classMethods;

    public CommandManager(JuicyPlugin plugin, String command, List<String> commandAliases, List<Class<? extends ICommand>> classes) {

        this.plugin = plugin;
        juicyAPIPlugin = JuicyAPIPlugin.getPlugin();

        this.command = command;

        commandClasses = new HashMap<>();
        classMethods = new HashMap<>();

        List<String> clonedAliases = new ArrayList<>(commandAliases);

        if (!clonedAliases.contains(command))
            clonedAliases.add(command);

        clonedAliases.forEach(alias -> {

            List<Class<? extends ICommand>> registeredClasses = new ArrayList<>();

            classes.forEach(clazz -> {

                Command commandAnnotation = clazz.getDeclaredAnnotation(Command.class);

                if (commandAnnotation != null)
                    if (Arrays.stream(commandAnnotation.aliases()).collect(Collectors.toList()).contains(alias)) {

                        registeredClasses.add(clazz);

                        List<CommandMethod> commandMethods = new ArrayList<>();

                        Arrays.stream(clazz.getMethods())
                                .forEach(method -> {

                                    CommandArgument commandArgument = method.getDeclaredAnnotation(CommandArgument.class);

                                    if (commandArgument != null)
                                        commandMethods.add(new CommandMethod(Arrays.asList(commandArgument.aliases()), method));

                                });

                        classMethods.put(clazz, commandMethods);

                    }
            });

            if (!registeredClasses.isEmpty())
                commandClasses.put(alias, registeredClasses);

        });
    }

    public CommandManager(JuicyPlugin plugin, String command, List<Class<? extends ICommand>> classes) {

        this(plugin, command, Collections.singletonList(command), classes);

    }

    public void load() {

        Objects.requireNonNull(plugin.getCommand(command)).setExecutor(this);

    }

    public boolean onCommand(@NonNull CommandSender commandSender, @NonNull org.bukkit.command.Command command, @NonNull String label, @NonNull String[] args) {

        if (commandSender instanceof Player && !JuicyPlayerPlugin.getPlugin().getAuthPlayerManager().getPlayer((Player) commandSender).getStatus().equals(AuthPlayerStatus.AUTHORIZED))
            return false;

        List<Class<? extends ICommand>> classes = commandClasses.get(label);

        if (classes == null)
            return false;

        AtomicBoolean called = new AtomicBoolean(false);

        classes.forEach(clazz -> {

            Command commandAnnotation = clazz.getDeclaredAnnotation(Command.class);

            if (Arrays.stream(commandAnnotation.aliases()).collect(Collectors.toList()).contains(label)) {

                if (called.get())
                    return;

                if (commandAnnotation.onlyPlayers() && !(commandSender instanceof Player)) {

                    commandSender.sendMessage(juicyAPIPlugin.replace("%prefix%&fКоманда должна выполняться от имени игрока!"));
                    return;

                }

                AtomicBoolean hasPermissionCommand = new AtomicBoolean(commandAnnotation.permissions().length == 0);

                if (!hasPermissionCommand.get())
                    for (String permission : commandAnnotation.permissions())
                        if (!hasPermissionCommand.get())
                            if (commandSender.hasPermission(permission))
                                hasPermissionCommand.set(true);

                if (!hasPermissionCommand.get()) {

                    commandSender.sendMessage(juicyAPIPlugin.replace("%prefix%&fУ вас нет прав для выполнения этой команды!"));
                    return;

                }

                classMethods.get(clazz).forEach(method -> {

                    if (called.get())
                        return;

                    CommandArgument commandArgument = method.getMethod().getDeclaredAnnotation(CommandArgument.class);

                    if (commandArgument.onlyPlayers() && !(commandSender instanceof Player)) {

                        commandSender.sendMessage(juicyAPIPlugin.replace("%prefix%&fКоманда должна выполняться от имени игрока!"));
                        return;

                    }

                    AtomicBoolean hasPermission = new AtomicBoolean(commandArgument.permissions().length == 0);

                    if (!hasPermission.get())
                        for (String permission : commandArgument.permissions())
                            if (!hasPermission.get())
                                hasPermission.set(commandSender.hasPermission(permission));

                    if (!hasPermission.get()) {

                        commandSender.sendMessage(juicyAPIPlugin.replace("%prefix%&fУ вас нет прав для выполнения этой команды!"));
                        return;

                    }

                    try {

                        if (commandArgument.allArgs() && args.length >= 1) {

                            callCommand(commandSender, args, clazz, method.getMethod());
                            called.set(true);

                        } else if (method.getArguments().isEmpty() && args.length < 1) {

                            callCommand(commandSender, new String[0], clazz, method.getMethod());
                            called.set(true);

                        } else if (method.getArguments().contains(args[0])) {

                            callCommand(commandSender, new ArrayManager<>(args).removeElement(0), clazz, method.getMethod());
                            called.set(true);

                        }
                    } catch (Exception ignored) {
                    }
                });
            }
        });

        return false;

    }

    public void callCommand(@NonNull CommandSender commandSender, String[] args, Class<? extends ICommand> clazz, Method method) throws Exception {

        method.invoke(clazz.getConstructor().newInstance(), commandSender, args);

    }
}