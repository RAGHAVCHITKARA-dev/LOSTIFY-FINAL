package lostify;

public class MatchHistoryManager {
    private final MatchResult[] matches = new MatchResult[1000];
    private int count = 0;

    public void add(MatchResult result) {
        for (int i = 0; i < count; i++) {
            if (matches[i].getLostId().equals(result.getLostId()) &&
                matches[i].getFoundId().equals(result.getFoundId())) return;
        }
        if (count < matches.length) matches[count++] = result;
    }

    public MatchResult[] getAll() {
        MatchResult[] result = new MatchResult[count];
        System.arraycopy(matches, 0, result, 0, count);
        return result;
    }

    public void displayAll() {
        if (count == 0) { System.out.println("No match history available."); return; }
        for (int i = 0; i < count; i++) matches[i].display();
    }

    public void load(MatchResult result) { add(result); }
}
