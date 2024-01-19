import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Schedule {
    private ArrayList<Event> eventList;

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
        for (int startElem = 0; startElem < temp.length - 1; startElem++) {
            smallest = startElem;
            for (int currentElem = startElem + 1; currentElem < temp.length; currentElem++) {
                if (temp[currentElem].compareTo(temp[smallest]) == 1) {
                    smallest = currentElem;
                }
            }
            emptyGlass = temp[startElem];
            temp[startElem] = temp[smallest];
            temp[smallest] = emptyGlass;
        }
        return temp;
    }

    private static Event[] dateSort(Event[] e) {
        Event[] temp = e;
        int smallest = 0;
        Event emptyGlass = new Event(); // reference to BroCode tutorial just 'cause

        // for loop for
        for (int startElem = 0; startElem < temp.length - 1; startElem++) {
            smallest = startElem;
            for (int currentElem = startElem + 1; currentElem < temp.length; currentElem++) {
                if (temp[currentElem].compareTo(temp[smallest]) == 1) {
                    smallest = currentElem;
                }
            }
            emptyGlass = temp[startElem];
            temp[startElem] = temp[smallest];
            temp[smallest] = emptyGlass;
        }
        return temp;
    }


    public Event[] getEventList() {
        return eventList.toArray(new Event[eventList.size()]);
    }

    private int getNextEventIndex() {
        int currentInterval = eventList.size() - 1;
        Event rn = new Event();
        System.out.println(eventList);
        while (currentInterval > 0) {
            if (rn.compareTo(eventList.get(currentInterval)) == -1) {
                currentInterval = currentInterval + (eventList.size() - 1 - currentInterval)/2;
            } else if (rn.compareTo(eventList.get(currentInterval)) == 1 && rn.compareTo(eventList.get(currentInterval - 1)) == 1) {
                currentInterval /= 2;
            } else {
                return currentInterval;
            }
            System.out.println();
        }
        return -1;
    }

    public Event getNextEvent() {
        int temp = getNextEventIndex();
        if (temp > -1) {
            return eventList.get(temp);
        } else {
            try {
                throw new Exception("You just finished your last task.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}