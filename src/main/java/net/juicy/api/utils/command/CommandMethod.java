package net.juicy.api.utils.command;

import lombok.Value;

import java.lang.reflect.Method;
import java.util.List;

@Value
public class CommandMethod {

    List<String> arguments;
    Method method;

}