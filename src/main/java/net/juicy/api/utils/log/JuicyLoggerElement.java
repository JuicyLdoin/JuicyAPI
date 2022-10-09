package net.juicy.api.utils.log;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import net.juicy.api.utils.interfaces.ISavable;

@Value
public class JuicyLoggerElement implements ISavable {

    String name;
    JuicyLogger juicyLogger;
    List<JuicyLoggerMessage> messages;

    public JuicyLoggerElement(String name, JuicyLogger juicyLogger) {

        this.name = name;
        this.juicyLogger = juicyLogger;

        messages = new ArrayList<>();

        try {

            File file = getFile();

            if (!file.exists())
                file.createNewFile();

        } catch (IOException exception) {

            exception.printStackTrace();

        }
    }

    public Path getPath() {

        return Paths.get(juicyLogger.getPath().toString() + "/" + name + ".log");

    }

    public void addMessage(String message) {

        messages.add(new JuicyLoggerMessage(this, message));

    }

    public void save() {

        if (messages != null && !messages.isEmpty()) {

            messages.forEach(JuicyLoggerMessage::save);
            messages.clear();

        }
    }
}