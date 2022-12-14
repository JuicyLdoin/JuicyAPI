package net.juicy.api.utils.util;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class FileUtil {

    public static boolean deleteDirectory(File directoryToBeDeleted) {

        File[] allContents = directoryToBeDeleted.listFiles();

        if (allContents != null)
            for (File file : allContents)
                deleteDirectory(file);

        return directoryToBeDeleted.delete();

    }

    public static void copy(File source, File destination, List<String> ignore) throws IOException {

        if (source.isDirectory())
            copyDirectory(source, destination, ignore);
        else
            copyFile(source, destination);

    }

    public static void copy(File source, File destination) throws IOException {

        copy(source, destination, new ArrayList<>());

    }

    private static void copyDirectory(File sourceDirectory, File destinationDirectory, List<String> ignore) throws IOException, NullPointerException {

        if (!destinationDirectory.exists())
            destinationDirectory.mkdir();

        for (String file : sourceDirectory.list())
            if (!ignore.contains(file))
                copy(new File(sourceDirectory, file), new File(destinationDirectory, file));

    }

    private static void copyFile(File sourceFile, File destinationFile) throws IOException {

        try (InputStream in = Files.newInputStream(sourceFile.toPath()); OutputStream out = Files.newOutputStream(destinationFile.toPath())) {

            byte[] buf = new byte[1024];
            int length;

            while ((length = in.read(buf)) > 0)
                out.write(buf, 0, length);

        }
    }
}