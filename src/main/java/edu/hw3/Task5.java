package edu.hw3;

import java.util.Arrays;
import java.util.Comparator;

public final class Task5 {

    private Task5() {
    }

    public static Contact[] parseContacts(String[] names, String order) {
        if (names == null || names.length == 0) {
            return new Contact[0];
        }

        Contact[] contacts = Arrays.stream(names)
            .map(Contact::new)
            .toArray(Contact[]::new);

        Comparator<Contact> comparator = Comparator.comparing(Contact::getLastNameOrFirstName);
        if ("DESC".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        Arrays.sort(contacts, comparator);

        return contacts;
    }

    public static class Contact {
        private final String fullName;

        public Contact(String fullName) {
            this.fullName = fullName;
        }

        public String getFullName() {
            return fullName;
        }

        public String getLastNameOrFirstName() {
            String[] parts = fullName.split("\\s+");
            return parts.length > 1 ? parts[1] : parts[0];
        }

        @Override public String toString() {
            return fullName;
        }
    }
}
