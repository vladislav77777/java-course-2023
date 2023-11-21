package edu.hw3;

import java.util.Comparator;

final class Task7 {
    private Task7() {
    }

    static class NullableStringComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            if (s1 == null && s2 == null) {
                return 0;
            }
            if (s1 == null) {
                return -1;
            }
            if (s2 == null) {
                return 1;
            }
            return s1.compareTo(s2);
        }
    }
}

