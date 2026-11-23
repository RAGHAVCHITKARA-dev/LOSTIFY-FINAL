package lostify;

import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner = new Scanner(System.in);

    public String readText(String prompt) {
        System.out.print(prompt);
        String value = scanner.nextLine().trim();
        if (value.isEmpty()) throw new IllegalArgumentException("This field cannot be empty.");
        return value;
    }

    public int readInt(String prompt, int min, int max) {
        System.out.print(prompt);
        String value = scanner.nextLine().trim();
        try {
            int number = Integer.parseInt(value);
            if (number < min || number > max) throw new IllegalArgumentException("Choose a number from " + min + " to " + max + ".");
            return number;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Please enter a valid whole number.");
        }
    }
}
