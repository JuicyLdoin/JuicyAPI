package net.juicy.api.utils.load;

import net.juicy.api.JuicyAPIPlugin;
import net.juicy.api.utils.Hologram;

import java.util.List;
import java.util.ArrayList;
import net.juicy.api.utils.load.list.UnLoadableList;

public class Loader extends UnLoadableList {

    public Loader() {

        List<ILoadable> loadable = new ArrayList<>();

        addAllLoadable(loadable);

        List<IUnLoadable> unLoadable = new ArrayList<>();

        addAllUnLoadable(unLoadable);

    }
    
    public void load(ILoadable loadable) {

        try {

            loadable.load();

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }
    
    public void unload(IUnLoadable unLoadable) {

        try {

            unLoadable.unload();

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }
    
    public void loadAll() {

        loadableList.forEach(this::load);

    }
    
    public void unLoadAll() {

        unLoadableList.forEach(this::unload);

        JuicyAPIPlugin.getPlugin().getJuicyLogger().save();

        Hologram.getHolograms().forEach(Hologram::clear);

    }
}
