package edu.hw3;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

final class Task2 {

    private Task2() {

    }

    public static List<String> clusterize(String s) {
        Stack<Integer> stack = new Stack<>();
        List<String> result = new ArrayList<>();
        Integer openValue;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);

            } else if (c == ')' && s.charAt(stack.peek()) == '(') {
                openValue = stack.pop();
                if (stack.isEmpty()) {
                    result.add("\"" + s.substring(openValue, i + 1) + "\"");
                }
            } else {
                return null;
            }
        }

        return result;
    }
}
