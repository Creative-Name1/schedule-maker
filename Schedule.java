import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Schedule {
    private ArrayList<Event> eventList;

    public Schedule(ArrayList<Event> eList) {
        eventList = new ArrayList<>(Arrays.asList(dateSort(eList)));
        removeCollisions();
    }

    public Schedule(Event[] eList) {
        eventList = new ArrayList<>(Arrays.asList(dateSort(eList)));
        removeCollisions();
    }

    private static Event[] dateSort(ArrayList<Event> e) {
        Event[] temp = new Event[e.size()];
        temp = e.toArray(temp);
        int smallest = 0;
        Event emptyGlass = new Event(); // reference to BroCode tutorial just 'cause

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
        Event emptyGlass = new Event();

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

    public void removeCollisions() {
        for (int i = 0; i < eventList.size() - 1; i++) {
            if (eventList.get(i).compareTo(eventList.get(i + 1), Event.END_DATE, Event.START_DATE) == -1) {
                eventList.get(i).setEndTime(eventList.get(i + 1).getStartTime());
            }
        }
    }

    public void applyPreset() {
        removeCollisions();
        Event e = new Event();
        ArrayList<Event> out = new ArrayList<Event>();
        for (int i = 0; i < eventList.size() - 1; i++) {
            if (! (eventList.get(i).getType().equals(eventList.get(i + 1).getType()))) {
                e = new Event(eventList.get(i + 1));
                e.setEndTime(e.getStartTime());
                if (e.getEndMinute() > 9) {
                    e.setStartMinute(e.getEndMinute() - 10);
                } else {
                    e.setStartHour(e.getEndHour() - 1);
                    e.setStartMinute(e.getStartMinute() + 50);
                }
                if (! checkCollision(e)) {
                    e.setType("Break_Special");
                    e.setName("Transition into next activity");
                    out.add(e);
                }
            }
        }
        eventList.addAll(out);
        eventList = new ArrayList<>(Arrays.asList(dateSort(eventList)));
    }

    private boolean checkCollision(Event e) {
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).collidesWith(e)) {
                return true;
            }
        }
        return false;
    }
    public Event checkCollisionSpecial(Event e) {
        ArrayList<Event> eList = new ArrayList<>(eventList);
        ArrayList<Event> out = new ArrayList<Event>();
        try {
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
            for (int i = 0; i < out.size(); i++) {
                if (out.get(i).getType().equals("Break_Special") && ! out.get(i).equals(e)) {
                    return out.get(i);
                }
            }
        } catch (Exception ex) {
            System.out.println("Out of bounds");
        }
        return new Event();
    }


    public Event[] getCollisions(Event e) {
        ArrayList<Event> out = new ArrayList<Event>();
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).collidesWith(e)) {
                out.add(eventList.get(i));
            }
        }
        return out.toArray(new Event[out.size()]);
    }

    public void writeToFile() {
        try {
            FileWriter fw = new FileWriter("Schedule.txt");
            PrintWriter pw = new PrintWriter(fw);
            pw.println("in-schedule\n");
        } catch (IOException e) {
            System.err.println(e);
        }
        for (int i = 0; i < eventList.size(); i++) {
            eventList.get(i).writeToFile();
        }
    }

    public void readFromFile() {
        String[] all = new String[0];
        Event e = new Event();
        if (eventList.isEmpty()) {
            try {
                all = Files.readAllLines(Paths.get("file.txt")).toArray(new String[Files.readAllLines(Paths.get("file.txt")).size()]);
            } catch (IOException ioe) {
                System.err.println(ioe);
            }

            for (int i = 2; i < all.length; i++) {
                e.writeFromString(all[i]);
                eventList.add(e);
            }
        }
    }

    public Event[] getEventList() {
        return eventList.toArray(new Event[eventList.size()]);
    }

    public int getNextEventIndex() {
        int currentInterval = eventList.size() - 1;
        Event rn = new Event();
        while (currentInterval > 0) {
            if (rn.compareTo(eventList.get(currentInterval)) == -1) {
                currentInterval = currentInterval + (eventList.size() - 1 - currentInterval) / 2;
            } else if (rn.compareTo(eventList.get(currentInterval)) == 1 && rn.compareTo(eventList.get(currentInterval - 1)) == 1) {
                currentInterval /= 2;
            } else {
                return currentInterval;
            }
        }
        return -1;
    }

    public int getEventIndex(Event e) {
        int currentInterval = eventList.size() - 1;
        while (currentInterval > 0) {
            if (e.compareTo(eventList.get(currentInterval)) == 1) {
                currentInterval = currentInterval + (eventList.size() - 1 - currentInterval) / 2;
            } else if (e.compareTo(eventList.get(currentInterval)) == -1) {
                currentInterval /= 2;
            } else {
                if (e.equals(eventList.get(currentInterval))) {
                    return currentInterval;
                }
                return -1;
            }
        }
        return -1;
    }

    private int getNextEventIndex(Event rn) {
        int currentInterval = eventList.size() - 1;
        while (currentInterval > 0) {
            if (rn.compareTo(eventList.get(currentInterval)) == -1) {
                currentInterval = currentInterval + (eventList.size() - 1 - currentInterval) / 2;
            } else if (rn.compareTo(eventList.get(currentInterval)) == 1 && rn.compareTo(eventList.get(currentInterval - 1)) == 1) {
                currentInterval /= 2;
            } else if (rn.compareTo(eventList.get(currentInterval)) == -1){
                return currentInterval + 1;
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
    public Event getCurrentEvent() {
        int temp = getNextEventIndex() - 1;
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

    public int getSize() {
        return eventList.size();
    }

    public void addEvent(Event e) {
        eventList.add(e);
        eventList = new ArrayList<>(Arrays.asList(dateSort(eventList)));
    }

    public void addEventList(Event[] e) {
        eventList.addAll(List.of(e));
    }

    public String toString() {
        String out = "Current schedule:\n";

        for (int i = 0; i < eventList.size(); i++) {
            out += eventList.get(i) + "\n";
        }

        return out;
    }
}