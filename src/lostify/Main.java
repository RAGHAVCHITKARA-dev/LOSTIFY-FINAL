package lostify;

public class Main {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager("data");
        ItemManager itemManager = new ItemManager();
        MatchHistoryManager historyManager = new MatchHistoryManager();
        fileManager.loadItems(itemManager);
        fileManager.loadMatches(historyManager);

        InputHandler input = new InputHandler();
        MainMenu menu = new MainMenu(input, itemManager, historyManager, fileManager);
        menu.run();
    }
}
