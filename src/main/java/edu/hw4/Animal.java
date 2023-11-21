package edu.hw4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    boolean bites
) {
    public static final int FOUR = 4;
    public static final int EIGHT = 8;

    public static List<Animal> sortByHeight(List<Animal> animals) {
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::height))
            .collect(toList());
    }

    public static List<Animal> sortByWeightAndGetTopK(List<Animal> animals, int k) {
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::weight).reversed())
            .limit(k)
            .collect(toList());
    }

    public static Map<Type, Integer> countAnimalsByType(List<Animal> animals) {
        return animals.stream()
            .collect(groupingBy(Animal::type, summingInt(animal -> 1)));
    }

    public static Animal findAnimalWithLongestName(List<Animal> animals) {
        return animals.stream()
            .max(Comparator.comparingInt(animal -> animal.name().length()))
            .orElse(null);
    }

    public static Sex findDominantSex(List<Animal> animals) {
        Map<Sex, Long> sexCount = animals.stream()
            .collect(groupingBy(Animal::sex, counting()));

        return (sexCount.getOrDefault(Sex.M, 0L) > sexCount.getOrDefault(Sex.F, 0L)) ? Sex.M : Sex.F;
    }

    public static Map<Animal.Type, Animal> findHeaviestAnimalOfEachType(List<Animal> animals) {
        return animals.stream()
            .collect(groupingBy(
                Animal::type,
                collectingAndThen(maxBy(Comparator.comparingInt(Animal::weight)), op -> op.orElse(null))
            ));
    }

    public static Animal findKthOldestAnimal(List<Animal> animals, int k) {
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::age).reversed())
            .skip(k - 1).findFirst().orElse(null);

    }

    public static Optional<Animal> findHeaviestAnimalBelowHeight(List<Animal> animals, int k) {
        return animals.stream()
            .filter(animal -> animal.height() < k)
            .max(Comparator.comparingInt(Animal::weight));
    }

    public static int getTotalNumberOfPaws(List<Animal> animals) {
        return animals.stream()
            .mapToInt(Animal::paws)
            .sum();
    }

    public static List<Animal> findAnimalsWithMismatchedAgeAndPaws(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.age() != animal.paws())
            .collect(toList());
    }

    public static List<Animal> findBitingAnimalsWithHeightGreaterThan(List<Animal> animals, int height) {
        return animals.stream()
            .filter(animal -> animal.bites && animal.height > height)
            .collect(toList());
    }

    public static long countAnimalsWithWeightExceedingHeight(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.weight > animal.height)
            .count();
    }

    public static List<Animal> findAnimalsWithNamesContainingMoreThanTwoWords(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.name.split("\\s+").length > 2)
            .collect(toList());
    }

    public static boolean hasTallDog(List<Animal> animals, int height) {
        return animals.stream()
            .anyMatch(animal -> animal.type == Animal.Type.DOG && animal.height > height);
    }

    public static Map<Animal.Type, Integer> calculateTotalWeightForAnimalsAgedBetween(
        List<Animal> animals,
        int k,
        int l
    ) {
        return animals.stream()
            .filter(animal -> animal.age >= k && animal.age <= l)
            .collect(groupingBy(Animal::type, summingInt(Animal::weight)));
    }

    public static List<Animal> sortAnimalsByTypeSexAndName(List<Animal> animals) {
        return animals.stream()
            .sorted(Comparator.comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name))
            .collect(toList());
    }

    public static Boolean doSpidersBiteMoreThanDogs(List<Animal> animals) {
        long spiderCount = animals.stream()
            .filter(animal -> animal.type() == Animal.Type.SPIDER)
            .count();
        long dogCount = animals.stream()
            .filter(animal -> animal.type() == Animal.Type.DOG)
            .count();
        if (spiderCount == 0 || dogCount == 0) { //  недостаточно данных
            return false;
        }

        double spiderBiteRate = animals.stream()
            .filter(animal -> animal.type == Animal.Type.SPIDER && animal.bites)
            .count() / (double) animals.stream()
            .filter(animal -> animal.type == Animal.Type.SPIDER)
            .count();

        double dogBiteRate = animals.stream()
            .filter(animal -> animal.type == Animal.Type.DOG && animal.bites)
            .count() / (double) animals.stream()
            .filter(animal -> animal.type == Animal.Type.DOG)
            .count();

        return spiderBiteRate > dogBiteRate;
    }

    @SafeVarargs public static Animal findHeaviestFishInMultipleLists(List<Animal>... lists) {
        return Stream.of(lists)
            .flatMap(List::stream)
            .filter(animal -> animal.type() == Animal.Type.FISH)
            .max(Comparator.comparingInt(Animal::weight)).orElse(null);
    }

    public static Map<String, Set<ValidationError>> validateAnimals(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.age() < 0 || animal.weight() <= 0 || animal.height() <= 0)
            .collect(toMap(
                Animal::name,
                animal -> {
                    Set<ValidationError> errorSet = new HashSet<>();
                    if (animal.age() < 0) {
                        errorSet.add(new ValidationError("Age can't be negative"));
                    }
                    if (animal.weight() <= 0) {
                        errorSet.add(new ValidationError("Weight should be positive"));
                    }
                    if (animal.height() <= 0) {
                        errorSet.add(new ValidationError("Height should be positive"));
                    }
                    return errorSet;
                }
            ));
    }

    public static Map<String, String> validateAnimalsStrings(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.age() < 0 || animal.weight() <= 0 || animal.height() <= 0)
            .collect(toMap(
                Animal::name,
                animal -> {
                    List<String> errorMessages = new ArrayList<>();
                    if (animal.age() < 0) {
                        errorMessages.add("Age cannot be negative");
                    }
                    if (animal.weight() <= 0) {
                        errorMessages.add("Weight should be only positive");
                    }
                    if (animal.height() <= 0) {
                        errorMessages.add("Height should be only positive");
                    }
                    return String.join(", ", errorMessages);
                }
            ));
    }

    public enum Type {
        CAT, BIRD, DOG, FISH, SPIDER
    }

    public enum Sex {
        F, M
    }

    public int paws() {
        return switch (type) {
            case CAT, DOG -> FOUR;
            case BIRD -> 2;
            case FISH -> 0;
            case SPIDER -> EIGHT;
        };
    }
}
