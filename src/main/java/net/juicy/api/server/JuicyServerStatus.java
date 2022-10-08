package net.juicy.api.server;

import lombok.AllArgsConstructor;
import net.juicy.api.utils.interfaces.IDisplayable;

@AllArgsConstructor
public enum JuicyServerStatus implements IDisplayable {

    ENABLED("§aВключен"),
    DISABLED("§cВыключен");

    private final String displayName;

    public String getDisplayName() {

        return displayName;

    }
}