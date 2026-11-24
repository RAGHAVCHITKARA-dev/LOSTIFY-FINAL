package lostify;

public class MatchingService {
    private final SimilarityCalculator calculator = new SimilarityCalculator();

    public MatchResult[] findMatches(ItemManager manager) {
        ItemReport[] lost = manager.getLostItems();
        ItemReport[] found = manager.getFoundItems();
        MatchResult[] temp = new MatchResult[lost.length * found.length];
        int count = 0;

        for (ItemReport l : lost) {
            for (ItemReport f : found) {
                int score = calculator.calculate(l, f);
                if (score >= 50) temp[count++] = new MatchResult(l.getId(), f.getId(), score,
                        calculator.explain(l, f));
            }
        }
        MatchResult[] results = new MatchResult[count];
        System.arraycopy(temp, 0, results, 0, count);
        return results;
    }
}
