package lostify;

public class FoundItem extends ItemReport {
    public FoundItem(String id, String name, String category, String colour,
                     String location, String date, String description) {
        super(id, name, category, colour, location, date, description);
    }

    @Override
    public String getReportType() { return "FOUND"; }
}
