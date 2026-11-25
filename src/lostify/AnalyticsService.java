package lostify;

public class AnalyticsService {
    public void display(ItemManager manager) {
        int lost = manager.getLostItems().length;
        int found = manager.getFoundItems().length;
        System.out.println("\n========== LOSTIFY ANALYTICS ==========");
        System.out.println("Total reports: " + manager.size());
        System.out.println("Lost reports: " + lost);
        System.out.println("Found reports: " + found);
        System.out.println("Reports still needing a potential match: " + Math.max(0, lost - found));
        System.out.println("Note: this is a basic count, not a verified recovery statistic.");
    }
}
