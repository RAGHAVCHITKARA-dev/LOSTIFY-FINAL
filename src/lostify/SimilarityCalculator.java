package lostify;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SimilarityCalculator {
    public int calculate(ItemReport lost, ItemReport found) {
        int score = 0;
        if (same(lost.getName(), found.getName())) score += 30;
        else if (containsEither(lost.getName(), found.getName())) score += 15;
        if (same(lost.getCategory(), found.getCategory())) score += 20;
        if (same(lost.getColour(), found.getColour())) score += 15;
        if (same(lost.getLocation(), found.getLocation())) score += 20;
        if (dateClose(lost.getDate(), found.getDate())) score += 10;
        if (descriptionRelated(lost.getDescription(), found.getDescription())) score += 5;
        return score;
    }

    private boolean same(String a, String b) { return a.trim().equalsIgnoreCase(b.trim()); }

    private boolean containsEither(String a, String b) {
        String x = a.toLowerCase(), y = b.toLowerCase();
        return x.contains(y) || y.contains(x);
    }

    private boolean dateClose(String a, String b) {
        try {
            long days = Math.abs(ChronoUnit.DAYS.between(LocalDate.parse(a), LocalDate.parse(b)));
            return days <= 2;
        } catch (RuntimeException ex) { return false; }
    }

    private boolean descriptionRelated(String a, String b) {
        String[] words = a.toLowerCase().split("\\W+");
        String other = " " + b.toLowerCase() + " ";
        for (String word : words) if (word.length() > 3 && other.contains(" " + word + " ")) return true;
        return false;
    }

    public String explain(ItemReport lost, ItemReport found) {
        StringBuilder text = new StringBuilder();
        if (same(lost.getName(), found.getName())) text.append("Name matches; ");
        if (same(lost.getCategory(), found.getCategory())) text.append("category matches; ");
        if (same(lost.getColour(), found.getColour())) text.append("colour matches; ");
        if (same(lost.getLocation(), found.getLocation())) text.append("location matches; ");
        if (dateClose(lost.getDate(), found.getDate())) text.append("dates are close; ");
        if (descriptionRelated(lost.getDescription(), found.getDescription())) text.append("description has common keywords; ");
        return text.length() == 0 ? "Some attributes are similar." : text.toString();
    }
}
