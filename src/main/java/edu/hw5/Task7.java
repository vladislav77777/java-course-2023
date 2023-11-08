package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Task7 {

    private Task7() {
    }

    public static boolean validateString(String input) {
        String regex = "^.{2}0.*|([01]).*\\1|[01]{1,3}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }

}

