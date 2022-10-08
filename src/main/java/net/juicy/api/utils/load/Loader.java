package net.juicy.api.utils.load;

import net.juicy.api.JuicyAPIPlugin;
import net.juicy.api.utils.Hologram;

import net.juicy.api.utils.load.list.UnLoadableList;

public class Loader extends UnLoadableList {

    public void addLoadable(ILoadable loadable) {

        loadableList.add(loadable);

    }

    public void addUnLoadable(IUnLoadable unLoadable) {

        unLoadableList.add(unLoadable);

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
