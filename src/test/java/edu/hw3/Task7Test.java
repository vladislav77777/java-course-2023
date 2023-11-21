package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.TreeMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task7Test {

    @ParameterizedTest
    @DisplayName("Поведение с кастомным компаратором")
    @MethodSource("provideTreeMapInstances")
    public void testTreeMapContainsNull(TreeMap<String, String> treeMap) {
        treeMap.put(null, "test");
        assertThat(treeMap.containsKey(null)).isTrue();
    }

    private static Stream<Arguments> provideTreeMapInstances() {
        return Stream.of(
            Arguments.of(new TreeMap<>(new Task7.NullableStringComparator()))
        );
    }

    @Test
    @DisplayName("Обычное поведение")
    public void testTreeMapContainsNullWithoutCustomComparator() {
        TreeMap<String, String> treeMap = new TreeMap<>();
        assertThrows(NullPointerException.class, () -> treeMap.put(null, "test"));
    }
}
