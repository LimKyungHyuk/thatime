package net.khim.thatime.values;

import java.util.ArrayList;

public class ValueSrchPlaceHead{

    private String lastBuildDate;
    private String total;
    private String start;
    private String display;
    private ArrayList<ValueSrchPlaceItem> items = new ArrayList<ValueSrchPlaceItem>();

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public ArrayList<ValueSrchPlaceItem> getItems() {
        return items;
    }

    public void addItem(ValueSrchPlaceItem item) {
        this.items.add(item);
    }

    public ValueSrchPlaceItem getItem(int index) {
        return this.items.get(index);
    }

}
