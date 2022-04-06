package com.tictactoerest.tictactoegame.utils;

import java.io.FilenameFilter;

public class FileFilters {
    public static FilenameFilter xmlFilter = (dir, name) -> {
        if (name.lastIndexOf('.') > 0) {
            int lastIndex = name.lastIndexOf('.');
            String str = name.substring(lastIndex);
            return str.equals(".xml");
        }
        return false;
    };

    public static FilenameFilter jsonFilter = (dir, name) -> {
        if (name.lastIndexOf('.') > 0) {
            int lastIndex = name.lastIndexOf('.');
            String str = name.substring(lastIndex);
            return str.equals(".json");
        }
        return false;
    };

    public static boolean json;
}
