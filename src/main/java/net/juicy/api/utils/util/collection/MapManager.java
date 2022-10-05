package net.juicy.api.utils.util.collection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapManager<K, V> {

    public Map<K, V> getMapFromString(String string) {

        Map<K, V> map = new HashMap<>();

        if (string.length() == 0)
            return map;

        Arrays.stream(string.split(";"))
                .filter(s -> !s.equals(""))
                .forEach(s -> map.put((K) s.split("-")[0], (V) s.split("-")[1]));

        return map;

    }

    public String getStringFromMap(Map<K, V> map) {

        if (map.isEmpty())
            return "";

        StringBuilder stringBuilder = new StringBuilder();

        map.entrySet()
                .forEach(entry -> stringBuilder.append(entry.getKey()).append("-").append(entry.getValue()).append(";"));

        return stringBuilder.toString();

    }
}