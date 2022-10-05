package net.juicy.api.utils.interfaces;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.io.File;

public interface ISavable {
    default File getFile() {

        return new File(getPath().toUri());

    }
    
    Path getPath();
    
    void save() throws FileNotFoundException;

}