import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Schedule {
    private ArrayList<Event> eventList;

    public Schedule(ArrayList<Event> eList) {
        eventList = new ArrayList<>(Arrays.asList(dateSort(eList)));
        removeCollisions();
    }

    public Schedule(Event[] eList) {
        eventList = new ArrayList<>(Arrays.asList(dateSort(eList)));
        removeCollisions();
        checkCollision(new Event(7, 3, 14, 8, 28, 50), eList);
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
  
    private void removeCollisions() {
      for (int i = 0; i < eventList.size() - 1; i++) {
        if (eventList.get(i).compareTo(eventList.get(i + 1), Event.END_DATE, Event.START_DATE) == -1) {
          eventList.get(i).setEndDate(eventList.get(i + 1).getStartDate());
        }
      }
    }

    public Event[] checkCollision(Event e, ArrayList<Event> eL) {
      ArrayList<Event> eList = new ArrayList<> (eL);
      ArrayList<Event> out = new ArrayList<Event>();
      while (true) {
        if (eList.get(getNextEventIndex(e)).compareTo(e, Event.START_DATE, Event.END_DATE) == 1) {
          out.add(eList.get(getNextEventIndex(e)));
          eList.remove(getNextEventIndex(e));
        } else {
          break;
        }
      }
      for (int i = 0; i < getNextEventIndex(e); i++) {
        if (eList.get(i).compareTo(e, Event.END_DATE, Event.START_DATE) == -1) {
          out.add(eList.get(i));
        }
      }
      System.out.println(out);
      return out.toArray(new Event[out.size()]);
    }

    public Event[] checkCollision(Event e, Event[] eL) {
      ArrayList<Event> eList = new ArrayList<>(Arrays.asList(eL));
      ArrayList<Event> out = new ArrayList<Event>();
      while (true) {
        System.out.print(eList.get(getNextEventIndex(e)));
        if (eList.get(getNextEventIndex(e)).compareTo(e, Event.START_DATE, Event.END_DATE) == 1) {
          out.add(eList.get(getNextEventIndex(e)));
          eList.remove(getNextEventIndex(e));
          System.out.println(", intersects");
        } else {
          System.out.println(", doesn't");
          break;
        }
      }
      for (int i = 0; i < getNextEventIndex(e); i++) {
        if (eList.get(i).compareTo(e, Event.END_DATE, Event.START_DATE) == -1) {
          out.add(eList.get(i));
        }
      }
      System.out.println(out);
      return out.toArray(new Event[out.size()]);
    }

    public Event[] getEventList() {
        return eventList.toArray(new Event[eventList.size()]);
    }

    private int getNextEventIndex() {
        int currentInterval = eventList.size() - 1;
        Event rn = new Event();
        while (currentInterval > 0) {
            if (rn.compareTo(eventList.get(currentInterval)) == -1) {
                currentInterval = currentInterval + (eventList.size() - 1 - currentInterval)/2;
            } else if (rn.compareTo(eventList.get(currentInterval)) == 1 && rn.compareTo(eventList.get(currentInterval - 1)) == 1) {
                currentInterval /= 2;
            } else {
                return currentInterval;
            }
        }
        return -1;
    }

    private int getNextEventIndex(Event rn) {
        int currentInterval = eventList.size() - 1;
        while (currentInterval > 0) {
            if (rn.compareTo(eventList.get(currentInterval)) == -1) {
                currentInterval = currentInterval + (eventList.size() - 1 - currentInterval)/2;
            } else if (rn.compareTo(eventList.get(currentInterval)) == 1 && rn.compareTo(eventList.get(currentInterval - 1)) == 1) {
                currentInterval /= 2;
            } else {
                return currentInterval;
            }
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

    public String toString() {
      String out = "Schedule:\n";

      for (int i = 0; i < eventList.size(); i++) {
        out += eventList.get(i) + "\n";
      }
      
      return out;
    }
}