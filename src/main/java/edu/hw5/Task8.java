package edu.hw5;

public final class Task8 {
    private Task8() {
    }

    public static boolean isOddLength(String input) {
        return input.matches("^(00|01|10|11)*[01]$");
    }

    public static boolean hasSpecificLengthAndStartsWith(String input) {
        return input.matches("^(0|10|11)(00|01|10|11)*$");
    }

    public static boolean hasMultipleOfThreeZeros(String input) {
        return input.matches("^((101*01*0)|(01*01*0))*1*$");
    }

    public static boolean isNotElevenOrTripleOne(String input) {
        return input.matches("^(?!11$)(?!111$)[01]*$");
    }

    public static boolean hasOddOnOddIndices(String input) {
        return input.matches("^1(01|11)*$");
    }

    public static boolean hasAtLeastTwoZerosAtMostOneOne(String input) {
        return input.matches("^0*(001|010|100)0*$");
    }

    public static boolean hasNoConsecutiveOnes(String input) {
        return input.matches("^(?!.*11)[01]*$");
    }

    public static boolean validateInput(String input, int conditionIndex) {
        return switch (conditionIndex) {
            case 1 -> isOddLength(input);
            case 2 -> hasSpecificLengthAndStartsWith(input);
            case 2 + 1 -> hasMultipleOfThreeZeros(input);
            case 2 + 2 -> isNotElevenOrTripleOne(input);
            case 2 * 2 + 1 -> hasOddOnOddIndices(input);
            case 2 * 2 + 2 -> hasAtLeastTwoZerosAtMostOneOne(input);
            case 2 * 2 * 2 - 1 -> hasNoConsecutiveOnes(input);
            default -> false;
        };
    }
}
