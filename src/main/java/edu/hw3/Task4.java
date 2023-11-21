package edu.hw3;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("checkstyle:MagicNumber")
final class Task4 {
    private Task4() {
    }

    private static final Map<Integer, String> ROMAN_NUMERALS = new HashMap<>();

    static {
        ROMAN_NUMERALS.put(1, "I");
        ROMAN_NUMERALS.put(4, "IV");
        ROMAN_NUMERALS.put(5, "V");
        ROMAN_NUMERALS.put(9, "IX");
        ROMAN_NUMERALS.put(10, "X");
        ROMAN_NUMERALS.put(40, "XL");
        ROMAN_NUMERALS.put(50, "L");
        ROMAN_NUMERALS.put(90, "XC");
        ROMAN_NUMERALS.put(100, "C");
        ROMAN_NUMERALS.put(400, "CD");
        ROMAN_NUMERALS.put(500, "D");
        ROMAN_NUMERALS.put(900, "CM");
        ROMAN_NUMERALS.put(1000, "M");
    }

    public static String convertToRoman(int n) {
        StringBuilder romanNumeral = new StringBuilder();
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        int num = n;
        for (int value : values) {
            while (num >= value) {
                romanNumeral.append(ROMAN_NUMERALS.get(value));
                num -= value;
            }
        }

        return romanNumeral.toString();
    }
}
