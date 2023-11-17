package edu.hw6;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public interface AbstractFilter extends DirectoryStream.Filter<Path> {

    boolean accept(Path path) throws IOException;

    default AbstractFilter and(AbstractFilter other) {
        return (path) -> this.accept(path) && other.accept(path);
    }

    static AbstractFilter readable() {
        return Files::isReadable;
    }

    static AbstractFilter writable() {
        return Files::isWritable;
    }

    static AbstractFilter regularFile() {
        return Files::isRegularFile;
    }

    static AbstractFilter largerThan(long size) {
        return (path) -> Files.size(path) > size;
    }

    static AbstractFilter globMatches(String extension) {
        return (path) -> path.toString().toLowerCase().endsWith("." + extension.toLowerCase());
    }

    static AbstractFilter regexContains(String regex) {
        return (path) -> path.getFileName().toString().matches(regex);
    }

    static AbstractFilter magicNumber(int... magicBytes) {
        return (path) -> {
            try {
                byte[] fileBytes = Files.readAllBytes(path);
                for (int i = 0; i < magicBytes.length; i++) {
                    if (fileBytes[i] != (byte) magicBytes[i]) {
                        return false;
                    }
                }
                return true;
            } catch (IOException e) {
                return false;
            }
        };
    }
}

