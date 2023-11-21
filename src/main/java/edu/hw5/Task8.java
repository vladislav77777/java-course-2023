package edu.hw5;

@SuppressWarnings("checkstyle:ReturnCount")
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
        return input.matches("^((1*01*01*0)|(01*01*0))*1*$");
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

    public enum Condition {
        ODD_LENGTH,
        SPECIFIC_LENGTH_AND_STARTS_WITH,
        MULTIPLE_OF_THREE_ZEROS,
        NOT_ELEVEN_OR_TRIPLE_ONE,
        ODD_ON_ODD_INDICES,
        AT_LEAST_TWO_ZEROS_AT_MOST_ONE_ONE,
        NO_CONSECUTIVE_ONES
    }

    static boolean validateInput(String input, Condition condition) {
        return switch (condition) {
            case ODD_LENGTH -> isOddLength(input);
            case SPECIFIC_LENGTH_AND_STARTS_WITH -> hasSpecificLengthAndStartsWith(input);
            case MULTIPLE_OF_THREE_ZEROS -> hasMultipleOfThreeZeros(input);
            case NOT_ELEVEN_OR_TRIPLE_ONE -> isNotElevenOrTripleOne(input);
            case ODD_ON_ODD_INDICES -> hasOddOnOddIndices(input);
            case AT_LEAST_TWO_ZEROS_AT_MOST_ONE_ONE -> hasAtLeastTwoZerosAtMostOneOne(input);
            case NO_CONSECUTIVE_ONES -> hasNoConsecutiveOnes(input);
        };
    }
}
