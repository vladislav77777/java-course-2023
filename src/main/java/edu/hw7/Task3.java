package edu.hw7;

import java.util.HashMap;
import java.util.Map;

public class Task3 {

    public static class CachingPersonDatabase implements PersonDatabase {

        private final Map<Integer, Person> idToPersonMap = new HashMap<>();
        private final Map<String, Person> nameIndex = new HashMap<>();
        private final Map<String, Person> addressIndex = new HashMap<>();
        private final Map<String, Person> phoneIndex = new HashMap<>();

        @Override
        public synchronized void add(Person person) {
            idToPersonMap.put(person.id(), person);

            addToIndex(nameIndex, person.name(), person);
            addToIndex(addressIndex, person.address(), person);
            addToIndex(phoneIndex, person.phoneNumber(), person);
        }

        @Override
        public synchronized void delete(int id) {
            Person person = idToPersonMap.remove(id);

            removeFromIndex(nameIndex, person.name());
            removeFromIndex(addressIndex, person.address());
            removeFromIndex(phoneIndex, person.phoneNumber());
        }

        @Override
        public synchronized Person findByName(String name) {
            return nameIndex.getOrDefault(name, null);
        }

        @Override
        public synchronized Person findByAddress(String address) {
            return addressIndex.getOrDefault(address, null);
        }

        @Override
        public synchronized Person findByPhone(String phone) {
            return phoneIndex.getOrDefault(phone, null);
        }

        private void addToIndex(Map<String, Person> index, String key, Person person) {
            index.putIfAbsent(key, person);
        }

        private void removeFromIndex(Map<String, Person> index, String key) {
            index.remove(key);
        }
    }
}
