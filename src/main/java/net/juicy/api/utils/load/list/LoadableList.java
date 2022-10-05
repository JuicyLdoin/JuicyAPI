package net.juicy.api.utils.load.list;

import java.util.ArrayList;
import java.util.List;

import net.juicy.api.utils.load.ILoadable;

public abstract class LoadableList implements ILoadable {

    public List<ILoadable> loadableList;
    
    protected LoadableList() {

        loadableList = new ArrayList<>();

    }
    
    public void addAllLoadable(List<ILoadable> loadable) {

        loadableList.addAll(loadable);

    }

    public void load() {

        for (ILoadable loadable : loadableList)
            try {

                loadable.load();

            } catch (Exception ex) {

                ex.printStackTrace();

            }
    }
}