package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class AnimalTest {

    @ParameterizedTest
    @MethodSource("provideDataForSortByHeight")
    @DisplayName("test testSortByHeight method")
    public void testSortByHeight(List<Animal> input, List<Animal> expectedOutput) {
        List<Animal> sortedAnimals = Animal.sortByHeight(input);
        assertThat(sortedAnimals).isEqualTo(expectedOutput);
    }

    private static Stream<Arguments> provideDataForSortByHeight() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 30, 5, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 40, 8, true)
                ),
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 30, 5, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 40, 8, true)
                )
            ),
            Arguments.of(
                Arrays.asList(
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 20, 3, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 15, 2, false)
                ),
                Arrays.asList(
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 15, 2, false),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 20, 3, false)
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForSortByWeightAndGetTopK")
    @DisplayName("test testSortByWeightAndGetTopK method")
    public void testSortByWeightAndGetTopK(List<Animal> input, int k, List<Animal> expectedOutput) {
        List<Animal> sortedAnimals = Animal.sortByWeightAndGetTopK(input, k);
        assertThat(sortedAnimals).isEqualTo(expectedOutput);
    }

    private static Stream<Arguments> provideDataForSortByWeightAndGetTopK() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 30, 5, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 40, 8, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 20, 3, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 15, 2, false)
                ),
                2,
                Arrays.asList(
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 40, 8, true),
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 30, 5, false)
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForCountAnimalsByType")
    @DisplayName("test testCountAnimalsByType method")
    public void testCountAnimalsByType(List<Animal> input, Map<Animal.Type, Integer> expectedOutput) {
        Map<Animal.Type, Integer> animalCounts = Animal.countAnimalsByType(input);
        assertThat(animalCounts).isEqualTo(expectedOutput);
    }

    private static Stream<Arguments> provideDataForCountAnimalsByType() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 30, 5, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 40, 8, true),
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 4, 35, 6, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 6, 45, 7, true)
                ),
                Map.of(
                    Animal.Type.CAT, 2,
                    Animal.Type.DOG, 2
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForFindAnimalWithLongestName")
    @DisplayName("test testFindAnimalWithLongestName method")
    public void testFindAnimalWithLongestName(List<Animal> animals, Animal expectedAnimal) {
        Animal animalWithLongestName = Animal.findAnimalWithLongestName(animals);
        assertThat(animalWithLongestName).isEqualTo(expectedAnimal);
    }

    private static Stream<Arguments> provideDataForFindAnimalWithLongestName() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 30, 5, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 40, 8, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 10, 300, 6000, false)
                ),
                new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 10, 300, 6000, false)
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForFindDominantSex")
    @DisplayName("test testFindDominantSex method")
    public void testFindDominantSex(List<Animal> animals, Animal.Sex expectedDominantSex) {
        Animal.Sex dominantSex = Animal.findDominantSex(animals);
        assertThat(dominantSex).isEqualTo(expectedDominantSex);
    }

    private static Stream<Arguments> provideDataForFindDominantSex() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 30, 5, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 5, 40, 8, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 20, 3, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 15, 2, false)
                ),
                Animal.Sex.M
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForFindHeaviestAnimalOfEachType")
    @DisplayName("test FindHeaviestAnimalOfEachType method")
    public void testFindHeaviestAnimalOfEachType(List<Animal> animals, Map<Animal.Type, Animal> expectedAnimals) {
        Map<Animal.Type, Animal> heaviestAnimals = Animal.findHeaviestAnimalOfEachType(animals);
        assertThat(heaviestAnimals).isEqualTo(expectedAnimals);
    }

    private static Stream<Arguments> provideDataForFindHeaviestAnimalOfEachType() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 30, 5, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 40, 8, true),
                    new Animal("Dog2", Animal.Type.DOG, Animal.Sex.F, 7, 35, 6, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 20, 3, false),
                    new Animal("Bird2", Animal.Type.BIRD, Animal.Sex.F, 1, 10, 3, false)
                ),
                Map.of(
                    Animal.Type.CAT, new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 30, 5, false),
                    Animal.Type.DOG, new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 40, 8, true),
                    Animal.Type.BIRD, new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 20, 3, false)
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForFindKthOldestAnimal")
    @DisplayName("test FindKthOldestAnimal method")

    public void testFindKthOldestAnimal(List<Animal> animals, int k, Animal expectedAnimal) {
        Animal kthOldestAnimal = Animal.findKthOldestAnimal(animals, k);
        assertThat(kthOldestAnimal).isEqualTo(expectedAnimal);
    }

    private static Stream<Arguments> provideDataForFindKthOldestAnimal() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 30, 5, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 40, 8, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 20, 3, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 15, 2, false)
                ),
                2,
                new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 30, 5, false)
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForFindHeaviestAnimalBelowHeight")
    @DisplayName("test FindHeaviestAnimalBelowHeight method")
    public void testFindHeaviestAnimalBelowHeight(List<Animal> animals, int height, Optional<Animal> expectedAnimal) {
        Optional<Animal> heaviestAnimal = Animal.findHeaviestAnimalBelowHeight(animals, height);
        assertThat(heaviestAnimal).isEqualTo(expectedAnimal);
    }

    private static Stream<Arguments> provideDataForFindHeaviestAnimalBelowHeight() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 24, 5, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 40, 8, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 20, 3, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 15, 2, false)
                ),
                25,
                Optional.of(new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 24, 5, false))
            ),
            Arguments.of(
                Arrays.asList(
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 35, 6, false),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 20, 3, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 15, 2, false)
                ),
                10,
                Optional.empty()
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForGetTotalNumberOfPaws")
    @DisplayName("test testGetTotalNumberOfPaws method")
    public void testGetTotalNumberOfPaws(List<Animal> animals, int expectedTotalPaws) {
        int totalPaws = Animal.getTotalNumberOfPaws(animals);
        assertThat(totalPaws).isEqualTo(expectedTotalPaws);
    }

    private static Stream<Arguments> provideDataForGetTotalNumberOfPaws() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 30, 5, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 40, 8, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 20, 3, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 15, 2, false)
                ),
                10
            ),
            Arguments.of(
                Arrays.asList(
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 35, 6, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 15, 2, false)
                ),
                4
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForFindAnimalsWithMismatchedAgeAndPaws")
    @DisplayName("test testFindAnimalsWithMismatchedAgeAndPaws method")
    public void testFindAnimalsWithMismatchedAgeAndPaws(List<Animal> animals, List<Animal> expectedAnimals) {
        List<Animal> mismatchedAnimals = Animal.findAnimalsWithMismatchedAgeAndPaws(animals);
        assertThat(mismatchedAnimals).isEqualTo(expectedAnimals);
    }

    private static Stream<Arguments> provideDataForFindAnimalsWithMismatchedAgeAndPaws() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 4, 5, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 4, 4, 8, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 2, 3, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 0, 2, false)
                ),
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 4, 5, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 0, 2, false)
                )
            ),
            Arguments.of(
                Arrays.asList(
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 5, 5, 6, false),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 3, 2, 3, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 1, 2, false)
                ),
                Arrays.asList(
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 5, 5, 6, false),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 3, 2, 3, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 1, 2, false)
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForFindBitingAnimalsWithHeightGreaterThan")
    @DisplayName("test testFindBitingAnimalsWithHeightGreaterThan method")
    public void testFindBitingAnimalsWithHeightGreaterThan(
        List<Animal> animals,
        int height,
        List<Animal> expectedAnimals
    ) {
        List<Animal> bitingAnimals = Animal.findBitingAnimalsWithHeightGreaterThan(animals, height);
        assertThat(bitingAnimals).isEqualTo(expectedAnimals);
    }

    private static Stream<Arguments> provideDataForFindBitingAnimalsWithHeightGreaterThan() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 4, 5, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 6, 8, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 2, 3, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 4, 2, true)
                ),
                4,
                List.of(
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 6, 8, true)
                )
            ),
            Arguments.of(
                Arrays.asList(
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 5, 6, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 2, 3, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 4, 2, true)
                ),
                3,
                Arrays.asList(
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 5, 6, true),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 4, 2, true)
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForCountAnimalsWithWeightExceedingHeight")
    @DisplayName("test testCountAnimalsWithWeightExceedingHeight method")
    public void testCountAnimalsWithWeightExceedingHeight(List<Animal> animals, long expectedCount) {
        long count = Animal.countAnimalsWithWeightExceedingHeight(animals);
        assertThat(count).isEqualTo(expectedCount);
    }

    private static Stream<Arguments> provideDataForCountAnimalsWithWeightExceedingHeight() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, 3, 6, 5, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 6, 8, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 2, 3, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 3, 2, false)
                ),
                2
            ),
            Arguments.of(
                Arrays.asList(
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 5, 6, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 4, 3, false),
                    new Animal("Fish", Animal.Type.FISH, Animal.Sex.F, 1, 2, 2, false)
                ),
                1
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForFindAnimalsWithNamesContainingMoreThanTwoWords")
    @DisplayName("test testFindAnimalsWithNamesContainingMoreThanTwoWords method")
    public void testFindAnimalsWithNamesContainingMoreThanTwoWords(List<Animal> animals, List<Animal> expectedAnimals) {
        List<Animal> animalsWithLongNames = Animal.findAnimalsWithNamesContainingMoreThanTwoWords(animals);
        assertThat(animalsWithLongNames).isEqualTo(expectedAnimals);
    }

    private static Stream<Arguments> provideDataForFindAnimalsWithNamesContainingMoreThanTwoWords() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Big Cat John", Animal.Type.CAT, Animal.Sex.M, 3, 4, 5, false),
                    new Animal("Brown Dog Jack", Animal.Type.DOG, Animal.Sex.F, 5, 6, 8, true),
                    new Animal("Red Fox", Animal.Type.BIRD, Animal.Sex.M, 2, 2, 3, false),
                    new Animal("Yellow Fish Merry", Animal.Type.FISH, Animal.Sex.F, 1, 3, 2, false)
                ),
                Arrays.asList(
                    new Animal("Big Cat John", Animal.Type.CAT, Animal.Sex.M, 3, 4, 5, false),
                    new Animal("Brown Dog Jack", Animal.Type.DOG, Animal.Sex.F, 5, 6, 8, true),
                    new Animal("Yellow Fish Merry", Animal.Type.FISH, Animal.Sex.F, 1, 3, 2, false)
                )
            ),
            Arguments.of(
                Arrays.asList(
                    new Animal("White Dog", Animal.Type.DOG, Animal.Sex.M, 4, 5, 6, true),
                    new Animal("Blue Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 2, 3, false),
                    new Animal("Green Fish", Animal.Type.FISH, Animal.Sex.F, 1, 1, 2, false)
                ),
                List.of()
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForHasTallDog")
    @DisplayName("test testHasTallDog method")
    public void testHasTallDog(List<Animal> animals, int height, boolean expectedResult) {
        boolean hasTallDog = Animal.hasTallDog(animals, height);
        assertThat(hasTallDog).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> provideDataForHasTallDog() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Big Dog", Animal.Type.DOG, Animal.Sex.M, 3, 40, 5, false),
                    new Animal("Small Dog", Animal.Type.DOG, Animal.Sex.F, 5, 30, 8, true),
                    new Animal("Brown Cat", Animal.Type.CAT, Animal.Sex.M, 2, 20, 3, false)
                ),
                35,
                true
            ),
            Arguments.of(
                Arrays.asList(
                    new Animal("Dog1", Animal.Type.DOG, Animal.Sex.M, 4, 35, 6, false),
                    new Animal("Dog2", Animal.Type.DOG, Animal.Sex.M, 3, 33, 5, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 25, 3, false)
                ),
                40,
                false
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForCalculateTotalWeightForAnimalsAgedBetween")
    @DisplayName("test testCalculateTotalWeightForAnimalsAgedBetween method")
    public void testCalculateTotalWeightForAnimalsAgedBetween(
        List<Animal> animals, int k, int l, Map<Animal.Type, Integer> expectedMap
    ) {
        Map<Animal.Type, Integer> totalWeights = Animal.calculateTotalWeightForAnimalsAgedBetween(animals, k, l);
        assertThat(totalWeights).isEqualTo(expectedMap);
    }

    private static Stream<Arguments> provideDataForCalculateTotalWeightForAnimalsAgedBetween() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 3, 40, 5, false),
                    new Animal("Dog2", Animal.Type.DOG, Animal.Sex.F, 4, 40, 7, false),
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 5, 30, 8, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 20, 3, false)
                ),
                2,
                4,
                Map.of(Animal.Type.DOG, 12, Animal.Type.BIRD, 3)
            ),
            Arguments.of(
                Arrays.asList(
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 35, 6, false),
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 33, 5, true),
                    new Animal("Cat2", Animal.Type.CAT, Animal.Sex.M, 2, 33, 5, false),
                    new Animal("Cat3", Animal.Type.CAT, Animal.Sex.F, 4, 33, 4, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 25, 3, false)
                ),
                3,
                5,
                Map.of(Animal.Type.DOG, 6, Animal.Type.CAT, 9)
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForSortAnimalsByTypeSexAndName")
    @DisplayName("test testSortAnimalsByTypeSexAndName method")
    public void testSortAnimalsByTypeSexAndName(List<Animal> animals, List<Animal> expectedAnimals) {
        List<Animal> sortedAnimals = Animal.sortAnimalsByTypeSexAndName(animals);
        assertThat(sortedAnimals).isEqualTo(expectedAnimals);
    }

    private static Stream<Arguments> provideDataForSortAnimalsByTypeSexAndName() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Dog1", Animal.Type.DOG, Animal.Sex.M, 3, 40, 5, false),
                    new Animal("Dog2", Animal.Type.DOG, Animal.Sex.F, 5, 30, 8, true),
                    new Animal("Cat Beta", Animal.Type.CAT, Animal.Sex.F, 2, 20, 3, false),
                    new Animal("Cat Alex", Animal.Type.CAT, Animal.Sex.F, 4, 25, 4, true)
                ),
                Arrays.asList(
                    new Animal("Cat Alex", Animal.Type.CAT, Animal.Sex.F, 4, 25, 4, true),
                    new Animal("Cat Beta", Animal.Type.CAT, Animal.Sex.F, 2, 20, 3, false),
                    new Animal("Dog2", Animal.Type.DOG, Animal.Sex.F, 5, 30, 8, true),
                    new Animal("Dog1", Animal.Type.DOG, Animal.Sex.M, 3, 40, 5, false)
                )
            ),
            Arguments.of(
                Arrays.asList(
                    new Animal("Spider", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 1, true),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, true),
                    new Animal("Spider2", Animal.Type.SPIDER, Animal.Sex.F, 3, 3, 3, false),
                    new Animal("Spider3", Animal.Type.SPIDER, Animal.Sex.F, 3, 3, 3, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 4, 4, 4, false)
                ),
                Arrays.asList(
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 4, 4, 4, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, true),
                    new Animal("Spider2", Animal.Type.SPIDER, Animal.Sex.F, 3, 3, 3, false),
                    new Animal("Spider3", Animal.Type.SPIDER, Animal.Sex.F, 3, 3, 3, false),
                    new Animal("Spider", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 1, true)
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForDoSpidersBiteMoreThanDogs")
    @DisplayName("test testDoSpidersBiteMoreThanDogs method")
    public void testDoSpidersBiteMoreThanDogs(List<Animal> animals, boolean expectedResult) {
        boolean result = Animal.doSpidersBiteMoreThanDogs(animals);
        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> provideDataForDoSpidersBiteMoreThanDogs() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Spider1", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 1, true),
                    new Animal("Dog1", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, true),
                    new Animal("Spider2", Animal.Type.SPIDER, Animal.Sex.F, 3, 3, 3, true),
                    new Animal("Dog2", Animal.Type.DOG, Animal.Sex.F, 4, 4, 4, false)
                ),
                true
            ),
            Arguments.of(
                Arrays.asList(
                    new Animal("Spider1", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 1, true),
                    new Animal("Dog1", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, true),
                    new Animal("Spider2", Animal.Type.SPIDER, Animal.Sex.F, 3, 3, 3, true),
                    new Animal("Dog2", Animal.Type.DOG, Animal.Sex.F, 4, 4, 4, true)
                ),
                false
            ),
            Arguments.of(
                Arrays.asList(
                    new Animal("Spider1", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 1, false),
                    new Animal("Dog1", Animal.Type.DOG, Animal.Sex.M, 2, 2, 2, false),
                    new Animal("Spider2", Animal.Type.SPIDER, Animal.Sex.F, 3, 3, 3, true),
                    new Animal("Dog2", Animal.Type.DOG, Animal.Sex.F, 4, 4, 4, true)
                ),
                false
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForFindHeaviestFishInMultipleLists")
    @DisplayName("test testFindHeaviestFishInMultipleLists method")
    public void testFindHeaviestFishInMultipleLists(List<Animal>[] lists, Animal expectedFish) {
        Animal heaviestFish = Animal.findHeaviestFishInMultipleLists(lists);
        assertThat(heaviestFish).isEqualTo(expectedFish);
    }

    private static Stream<Arguments> provideDataForFindHeaviestFishInMultipleLists() {
        return Stream.of(
            Arguments.of(
                new List[] {
                    Arrays.asList(
                        new Animal("Fish1", Animal.Type.FISH, Animal.Sex.M, 1, 1, 1, true),
                        new Animal("Fish2", Animal.Type.FISH, Animal.Sex.F, 2, 2, 2, true)
                    ),
                    Arrays.asList(
                        new Animal("Fish3", Animal.Type.FISH, Animal.Sex.M, 3, 3, 3, true),
                        new Animal("Fish4", Animal.Type.FISH, Animal.Sex.F, 4, 4, 4, true)
                    )
                },
                new Animal("Fish4", Animal.Type.FISH, Animal.Sex.F, 4, 4, 4, true)
            ),
            Arguments.of(
                new List[] {
                    List.of(
                        new Animal("Dog Bars", Animal.Type.DOG, Animal.Sex.M, 1, 1, 1, true)
                    ),
                    List.of(
                        new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 2, 2, 2, true)
                    ),
                    List.of(
                        new Animal("Spider Robin", Animal.Type.SPIDER, Animal.Sex.M, 3, 3, 3, true)
                    )
                },
                null
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForValidateAnimals")
    @DisplayName("test testValidateAnimals method")
    public void testValidateAnimals(List<Animal> animals, Map<String, Set<ValidationError>> expectedErrors) {
        Map<String, Set<ValidationError>> errors = Animal.validateAnimals(animals);
        assertThat(errors).isEqualTo(expectedErrors);
    }

    private static Stream<Arguments> provideDataForValidateAnimals() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, -1, 10, 5, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, 5, 12, 0, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, -1, 1, false)
                ),
                Map.of(
                    "Cat", Set.of(new ValidationError("Age can't be negative")),
                    "Dog", Set.of(new ValidationError("Weight should be positive")),
                    "Bird", Set.of(new ValidationError("Height should be positive"))
                )
            ),
            Arguments.of(
                Arrays.asList(
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 35, 6, false),
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 33, 5, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 25, 3, false)
                ),
                Map.of()
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForValidateAnimalsStrings")
    @DisplayName("test testValidateAnimalsStrings method")
    public void testValidateAnimalsStrings(List<Animal> animals, Map<String, String> expectedErrors) {
        Map<String, String> errors = Animal.validateAnimalsStrings(animals);
        assertThat(errors).isEqualTo(expectedErrors);
    }

    private static Stream<Arguments> provideDataForValidateAnimalsStrings() {
        return Stream.of(
            Arguments.of(
                Arrays.asList(
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.M, -1, 10, 5, false),
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.F, -5, 10, 0, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, -20, -3, false)
                ),
                Map.of(
                    "Cat", "Age cannot be negative",
                    "Dog", "Age cannot be negative, Weight should be only positive",
                    "Bird", "Weight should be only positive, Height should be only positive"
                )
            ),
            Arguments.of(
                Arrays.asList(
                    new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 35, 6, false),
                    new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 33, 5, true),
                    new Animal("Bird", Animal.Type.BIRD, Animal.Sex.M, 2, 25, 3, false)
                ),
                Map.of()
            )
        );
    }
}
