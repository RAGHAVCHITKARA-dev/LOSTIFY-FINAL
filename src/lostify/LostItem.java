package lostify;

public class LostItem extends ItemReport {
    public LostItem(String id, String name, String category, String colour,
                    String location, String date, String description) {
        super(id, name, category, colour, location, date, description);
    }

    @Override
    public String getReportType() { return "LOST"; }
}
