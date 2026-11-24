package lostify;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Base64;

public class FileManager {
    private final Path folder;
    public FileManager(String folderName) {
        folder = Paths.get(folderName);
        try { Files.createDirectories(folder); }
        catch (IOException ex) { throw new IllegalStateException("Cannot create data folder.", ex); }
    }

    public void saveItems(ItemManager manager) {
        try (BufferedWriter writer = Files.newBufferedWriter(folder.resolve("items.txt"), StandardCharsets.UTF_8)) {
            for (ItemReport r : manager.getAll()) {
                String[] fields = {r.getReportType(), r.getId(), r.getName(), r.getCategory(), r.getColour(),
                        r.getLocation(), r.getDate(), r.getDescription()};
                for (int i = 0; i < fields.length; i++) {
                    if (i > 0) writer.write("|");
                    writer.write(encode(fields[i]));
                }
                writer.newLine();
            }
        } catch (IOException ex) { System.out.println("Could not save items: " + ex.getMessage()); }
    }

    public void loadItems(ItemManager manager) {
        Path file = folder.resolve("items.txt");
        if (!Files.exists(file)) return;
        try {
            for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
                String[] f = line.split("\\\\|", -1);
                if (f.length != 8) continue;
                String type = decode(f[0]);
                String id = decode(f[1]);
                ItemReport item = type.equals("LOST")
                        ? new LostItem(id, decode(f[2]), decode(f[3]), decode(f[4]), decode(f[5]), decode(f[6]), decode(f[7]))
                        : new FoundItem(id, decode(f[2]), decode(f[3]), decode(f[4]), decode(f[5]), decode(f[6]), decode(f[7]));
                manager.load(item);
            }
        } catch (IOException | IllegalArgumentException ex) { System.out.println("Could not load items: " + ex.getMessage()); }
    }

    public void saveMatches(MatchHistoryManager history) {
        try (BufferedWriter writer = Files.newBufferedWriter(folder.resolve("matches.txt"), StandardCharsets.UTF_8)) {
            for (MatchResult m : history.getAll()) {
                String line = m.getLostId() + "|" + m.getFoundId() + "|" + m.getScore() + "|" + encode(m.toStorageLine());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) { System.out.println("Could not save matches: " + ex.getMessage()); }
    }

    public void loadMatches(MatchHistoryManager history) {
        Path file = folder.resolve("matches.txt");
        if (!Files.exists(file)) return;
        try {
            for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
                String[] f = line.split("\\\\|", 4);
                if (f.length < 4) continue;
                String[] details = decode(f[3]).split("\\\\|", 4);
                if (details.length == 4) history.load(new MatchResult(details[0], details[1],
                        Integer.parseInt(details[2]), details[3]));
            }
        } catch (IOException | RuntimeException ex) { System.out.println("Could not load matches: " + ex.getMessage()); }
    }

    private String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }
    private String decode(String value) {
        return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
    }
}
