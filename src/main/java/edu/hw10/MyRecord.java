package edu.hw10;

record MyRecord(@Min(1) @Max(13) int intValue, @NotNull String stringValue) {

}
