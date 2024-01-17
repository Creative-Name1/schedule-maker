import java.util.ArrayList;
import java.util.Calendar;

public class Schedule {
    private ArrayList<Event> eventList = new ArrayList<Event>();

    public Schedule(ArrayList<Event> eList) {
        eventList = eList;
    }

    private static Event[] dateSort(ArrayList<Event> e) {
        Event[] temp = new Event[e.size()];
        temp = e.toArray(temp);
        return new Event[1];
    }
}