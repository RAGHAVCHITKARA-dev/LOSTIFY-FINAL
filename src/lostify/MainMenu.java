package lostify;

public class MainMenu {
    private final InputHandler input;
    private final ItemManager items;
    private final MatchHistoryManager history;
    private final FileManager files;
    private final MatchingService matching = new MatchingService();
    private final AnalyticsService analytics = new AnalyticsService();

    public MainMenu(InputHandler input, ItemManager items, MatchHistoryManager history, FileManager files) {
        this.input = input;
        this.items = items;
        this.history = history;
        this.files = files;
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n========== LOSTIFY ==========");
            System.out.println("1. Report lost item");
            System.out.println("2. Report found item");
            System.out.println("3. View all reports");
            System.out.println("4. Search reports");
            System.out.println("5. Find potential matches");
            System.out.println("6. View match history");
            System.out.println("7. View analytics");
            System.out.println("8. Exit");
            int choice = input.readInt("Choose: ", 1, 8);

            try {
                switch (choice) {
                    case 1 -> addItem(true);
                    case 2 -> addItem(false);
                    case 3 -> items.displayAll();
                    case 4 -> search();
                    case 5 -> findMatches();
                    case 6 -> history.displayAll();
                    case 7 -> analytics.display(items);
                    case 8 -> {
                        files.saveItems(items);
                        files.saveMatches(history);
                        System.out.println("Data saved. Goodbye!");
                        running = false;
                    }
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (RuntimeException ex) {
                System.out.println("Something went wrong: " + ex.getMessage());
            }
        }
    }

    private void addItem(boolean lost) {
        String name = input.readText("Item name: ");
        String category = input.readText("Category: ");
        String colour = input.readText("Colour: ");
        String location = input.readText("Location: ");
        String date = input.readText("Date (YYYY-MM-DD): ");
        String description = input.readText("Description: ");
        InputValidator.validateDate(date);
        String id = ItemManager.generateId(lost ? "L" : "F");
        ItemReport report = lost
                ? new LostItem(id, name, category, colour, location, date, description)
                : new FoundItem(id, name, category, colour, location, date, description);
        items.add(report);
        files.saveItems(items);
        System.out.println("Report saved successfully. Generated ID: " + id);
    }

    private void search() {
        String keyword = input.readText("Enter keyword (name/category/colour/location/description): ");
        ItemReport[] results = items.search(keyword);
        if (results.length == 0) System.out.println("No matching reports found.");
        else for (ItemReport item : results) item.displayDetails();
    }

    private void findMatches() {
        MatchResult[] results = matching.findMatches(items);
        if (results.length == 0) {
            System.out.println("No potential matches found yet.");
            return;
        }
        for (MatchResult result : results) {
            history.add(result);
            result.display();
        }
        files.saveMatches(history);
    }
}
