import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Schedule {
    private ArrayList<Event> eventList = new ArrayList<Event>();

    public Schedule(ArrayList<Event> eList) {
        eventList = new ArrayList<>(Arrays.asList(dateSort(eList)));
    }
    public Schedule(Event[] eList) {
        eventList = new ArrayList<>(Arrays.asList(dateSort(eList)));
    }

    private static Event[] dateSort(ArrayList<Event> e) {
        Event[] temp = new Event[e.size()];
        temp = e.toArray(temp);
        int smallest = 0;
        Event emptyGlass = new Event(); // reference to BroCode tutorial just 'cause

        // for loop for
        for (int startElem = 0; startElem < temp.length; startElem++) {
            for (int currentElem = startElem + 1; currentElem < temp.length; currentElem++) {
                if (temp[currentElem].compareTo(temp[smallest]) == 1) {
                    smallest = currentElem;
                }
            }
            emptyGlass = temp[startElem];
            temp[startElem] = temp[smallest];
            temp[smallest] = temp[startElem];
        }
        return temp;
    }

    private static Event[] dateSort(Event[] e) {
        Event[] temp = e;
        int smallest = 0;
        Event emptyGlass = new Event(); // reference to BroCode tutorial just 'cause

        // for loop for
        for (int startElem = 0; startElem < temp.length; startElem++) {
            for (int currentElem = startElem + 1; currentElem < temp.length; currentElem++) {
                if (temp[currentElem].compareTo(temp[smallest]) == 1) {
                    smallest = currentElem;
                }
            }
            emptyGlass = temp[startElem];
            temp[startElem] = temp[smallest];
            temp[smallest] = temp[startElem];
            smallest++;
        }
        return temp;
    }


    public Event[] getEventList() {
        return eventList.toArray(new Event[eventList.size()]);
    }
}