package edu.hw5;

import java.util.regex.Pattern;

public final class Task6 {

    private Task6() {
    }

    public static boolean isSubsequence(String s, String t) {
        String regex = ".*" + s + ".*";
        return Pattern.compile(regex).matcher(t).matches();
    }
}
