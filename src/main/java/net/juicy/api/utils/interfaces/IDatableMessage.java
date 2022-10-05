package net.juicy.api.utils.interfaces;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public interface IDatableMessage {

    LocalDateTime getDate();
    
    String getMessage();
    
    default String build() {

        return ((getDate() != null) ? ("[" + getDate().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] ") : "") + getMessage() + "\n";

    }
}