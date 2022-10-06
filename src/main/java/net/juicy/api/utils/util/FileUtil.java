package net.juicy.api.utils.util;

import java.io.*;
import java.nio.file.Files;

public class FileUtil {

    public static void copy(File source, File destination) throws IOException {

        if (source.isDirectory())
            copyDirectory(source, destination);
        else
            copyFile(source, destination);

    }

    private static void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {

        if (!destinationDirectory.exists())
            destinationDirectory.mkdir();

        for (String f : sourceDirectory.list())
            copy(new File(sourceDirectory, f), new File(destinationDirectory, f));

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