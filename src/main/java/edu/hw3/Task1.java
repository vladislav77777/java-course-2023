package edu.hw3;

final class Task1 {
    private Task1() {
    }

    public static final int ALPHABET_SIZE = 26;

    public static String atbash(String input) {
        StringBuilder result = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                char mirroredChar = (char) (2 * base - ch + ALPHABET_SIZE - 1);
                result.append(mirroredChar);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
}

