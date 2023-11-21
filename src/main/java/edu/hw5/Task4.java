package edu.hw5;

public final class Task4 {
    private Task4() {
    }

    private static final String SPECIAL_CHARACTERS_REGEX = ".*[~!@#$%^&*|].*";

    public static boolean containsSpecialCharacter(String password) {
        return password.matches(SPECIAL_CHARACTERS_REGEX);
    }
}
