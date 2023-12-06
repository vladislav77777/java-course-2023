package edu.hw7;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class Task35 {

    private Task35() {
    }

    public static class CachingPersonDatabase implements PersonDatabase {

        private final Map<Integer, Person> idToPersonMap = new HashMap<>();
        private final Map<String, Person> nameIndex = new HashMap<>();
        private final Map<String, Person> addressIndex = new HashMap<>();
        private final Map<String, Person> phoneIndex = new HashMap<>();
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        @Override
        public void add(Person person) {
            lock.writeLock().lock();
            try {
                idToPersonMap.put(person.id(), person);
                addToIndex(nameIndex, person.name(), person);
                addToIndex(addressIndex, person.address(), person);
                addToIndex(phoneIndex, person.phoneNumber(), person);
            } finally {
                lock.writeLock().unlock();
            }
        }

        @Override
        public void delete(int id) {
            lock.writeLock().lock();
            try {
                Person person = idToPersonMap.remove(id);
                removeFromIndex(nameIndex, person.name());
                removeFromIndex(addressIndex, person.address());
                removeFromIndex(phoneIndex, person.phoneNumber());
            } finally {
                lock.writeLock().unlock();
            }
        }

        @Override
        public Person findByName(String name) {
            lock.readLock().lock();
            try {
                return nameIndex.getOrDefault(name, null);
            } finally {
                lock.readLock().unlock();
            }
        }

        @Override
        public Person findByAddress(String address) {
            lock.readLock().lock();
            try {
                return addressIndex.getOrDefault(address, null);
            } finally {
                lock.readLock().unlock();
            }
        }

        @Override
        public Person findByPhone(String phone) {
            lock.readLock().lock();
            try {
                return phoneIndex.getOrDefault(phone, null);
            } finally {
                lock.readLock().unlock();
            }
        }

        private void addToIndex(Map<String, Person> index, String key, Person person) {
            lock.writeLock().lock();
            try {
                index.putIfAbsent(key, person);
            } finally {
                lock.writeLock().unlock();
            }
        }

        private void removeFromIndex(Map<String, Person> index, String key) {
            lock.writeLock().lock();
            try {
                index.remove(key);
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
}
