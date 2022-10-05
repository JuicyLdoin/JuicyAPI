



package net.juicy.api.utils.log;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Value;
import net.juicy.api.utils.interfaces.IDatableMessage;
import net.juicy.api.utils.interfaces.ISavable;

@Value
@AllArgsConstructor
public class JuicyLoggerMessage implements ISavable, IDatableMessage {

    JuicyLoggerElement element;
    LocalDateTime date;
    String message;
    
    public JuicyLoggerMessage(JuicyLoggerElement element, String message) {

        this.element = element;
        date = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        this.message = message;

    }

    public Path getPath() {

        return element.getPath();

    }

    public void save() {

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(getFile()));

            writer.write(build());
            writer.close();

        } catch (IOException exception) {

            exception.printStackTrace();

        }
    }
}