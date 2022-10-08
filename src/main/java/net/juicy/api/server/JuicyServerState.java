package net.juicy.api.server;

import lombok.AllArgsConstructor;
import net.juicy.api.utils.interfaces.IDisplayable;

@AllArgsConstructor
public enum JuicyServerState implements IDisplayable {

    WAITING("§aОжидание"),
    INGAME("§eВ игре"),
    DEVELOPMENT("§6В разработке"),
    UNKNOWN("");

    private final String displayName;

    public String getDisplayName() {

        return displayName;

    }
}