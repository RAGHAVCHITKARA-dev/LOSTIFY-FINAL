package lostify;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InputValidator {
    public static void validateDate(String date) {
        try { LocalDate.parse(date); }
        catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Date must use YYYY-MM-DD format, for example 2026-11-18.");
        }
    }
}
