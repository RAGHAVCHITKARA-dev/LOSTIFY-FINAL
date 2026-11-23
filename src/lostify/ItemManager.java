package lostify;

public class ItemManager {
    private final ItemReport[] reports = new ItemReport[500];
    private int count = 0;
    private static int nextId = 101;

    public static String generateId(String prefix) {
        return prefix + nextId++;
    }

    public void add(ItemReport item) {
        if (count == reports.length) throw new IllegalStateException("Report storage is full.");
        if (findById(item.getId()) != null) throw new IllegalArgumentException("Duplicate item ID.");
        reports[count++] = item;
    }

    public ItemReport findById(String id) {
        for (int i = 0; i < count; i++) if (reports[i].getId().equalsIgnoreCase(id)) return reports[i];
        return null;
    }

    public ItemReport[] getLostItems() { return filterByType("LOST"); }
    public ItemReport[] getFoundItems() { return filterByType("FOUND"); }

    private ItemReport[] filterByType(String type) {
        int n = 0;
        for (int i = 0; i < count; i++) if (reports[i].getReportType().equals(type)) n++;
        ItemReport[] result = new ItemReport[n];
        int index = 0;
        for (int i = 0; i < count; i++) if (reports[i].getReportType().equals(type)) result[index++] = reports[i];
        return result;
    }

    public ItemReport[] search(String keyword) {
        String key = keyword.toLowerCase();
        ItemReport[] temp = new ItemReport[count];
        int n = 0;
        for (int i = 0; i < count; i++) {
            ItemReport r = reports[i];
            String searchable = (r.getName() + " " + r.getCategory() + " " + r.getColour() + " " +
                    r.getLocation() + " " + r.getDescription()).toLowerCase();
            if (searchable.contains(key)) temp[n++] = r;
        }
        ItemReport[] result = new ItemReport[n];
        System.arraycopy(temp, 0, result, 0, n);
        return result;
    }

    public ItemReport[] getAll() {
        ItemReport[] result = new ItemReport[count];
        System.arraycopy(reports, 0, result, 0, count);
        return result;
    }

    public int size() { return count; }

    public void displayAll() {
        if (count == 0) { System.out.println("No reports available."); return; }
        for (int i = 0; i < count; i++) reports[i].displayDetails();
    }

    public void load(ItemReport item) { add(item); }
}
