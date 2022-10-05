package net.juicy.api.utils.load.list;

import java.util.ArrayList;
import java.util.List;

import net.juicy.api.utils.load.IUnLoadable;

public abstract class UnLoadableList extends LoadableList implements IUnLoadable {

    public List<IUnLoadable> unLoadableList;
    
    protected UnLoadableList() {

        unLoadableList = new ArrayList<>();

    }
    
    public void addAllUnLoadable(List<IUnLoadable> unLoadable) {

        unLoadableList.addAll(unLoadable);

    }

    public void unload() {

        for (IUnLoadable unLoadable : unLoadableList)
            try {

                unLoadable.unload();

            } catch (Exception ex) {

                ex.printStackTrace();

            }
    }
}