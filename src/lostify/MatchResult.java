package lostify;

public class MatchResult {
    private final String lostId, foundId, explanation;
    private final int score;

    public MatchResult(String lostId, String foundId, int score, String explanation) {
        this.lostId = lostId;
        this.foundId = foundId;
        this.score = score;
        this.explanation = explanation;
    }

    public void display() {
        System.out.println("\n--- POTENTIAL MATCH ---");
        System.out.println("Lost ID: " + lostId + " | Found ID: " + foundId);
        System.out.println("Matching score: " + score + "%");
        System.out.println("Strength: " + (score >= 80 ? "STRONG" : "POSSIBLE"));
        System.out.println("Reason: " + explanation);
        System.out.println("Note: This is a suggestion, not proof of ownership.");
    }

    public String toStorageLine() { return lostId + "|" + foundId + "|" + score + "|" + explanation.replace("|", "/"); }
    public String getLostId() { return lostId; }
    public String getFoundId() { return foundId; }
    public int getScore() { return score; }
}
