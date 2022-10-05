package net.juicy.api.listener;

import net.juicy.api.listener.listeners.PlayerJoinListener;
import net.juicy.api.listener.listeners.menu.*;
import net.juicy.api.utils.load.ILoadable;
import net.juicy.api.utils.load.list.LoadableList;

import java.util.ArrayList;
import java.util.List;

public class ListenerManager extends LoadableList {

    public ListenerManager() {

        List<ILoadable> list = new ArrayList<>();

        list.add(new PlayerJoinListener());

        list.add(new ClickInMenuListener());
        list.add(new ClickJoinItemListener());

        addAllLoadable(list);

    }
}