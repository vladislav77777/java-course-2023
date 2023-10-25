package edu.hw3;

import java.util.Arrays;
import java.util.Comparator;

final class Task5 {
    private Task5() {
    }

    public static String[] parseContacts(String[] contacts, String order) {
        if (contacts == null || contacts.length == 0) {
            return new String[] {};
        }

        Comparator<String> nameComparator = (contact1, contact2) -> {
            String regex = "\\s+";
            String[] parts1 = contact1.split(regex);
            String[] parts2 = contact2.split(regex);
            String lastName1 = parts1.length > 1 ? parts1[parts1.length - 1] : parts1[0];
            String lastName2 = parts2.length > 1 ? parts2[parts2.length - 1] : parts2[0];
            return order.equals("ASC") ? lastName1.compareTo(lastName2) : lastName2.compareTo(lastName1);
        };

        Arrays.sort(contacts, nameComparator);
        return contacts;
    }
}
