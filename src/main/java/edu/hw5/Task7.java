package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Task7 {

    private Task7() {
    }

    public static boolean validateString(String input) {
        return validateStringFirstCondition(input)
            || validateStringSecondCondition(input)
            || validateStringThirdCondition(input);
    }

    public static boolean validateStringFirstCondition(String input) {
        String regex = "^.{2}0.*$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }

    public static boolean validateStringSecondCondition(String input) {
        String regex = "^([01]).*\\1$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }

    public static boolean validateStringThirdCondition(String input) {
        String regex = "^[01]{1,3}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }

}

