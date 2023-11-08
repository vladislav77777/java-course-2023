package edu.hw5;

public final class Task5 {
    private Task5() {
    }

    private static final String CAR_NUMBER_REGEX = "^[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{3}$";

    public static boolean isValidCarNumber(String carNumber) {
        return carNumber.matches(CAR_NUMBER_REGEX);
    }

}

