import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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

    private void removeCollisions() {
        for (int i = 0; i < eventList.size() - 1; i++) {
            if (eventList.get(i).compareTo(eventList.get(i + 1), Event.END_DATE, Event.START_DATE) == -1) {
                eventList.get(i).setEndDate(eventList.get(i + 1).getStartDate());
            }
        }
    }

    public void applyPreset() {
        Event e = new Event();
        Date a = new Date();
        Date b = new Date();
        ArrayList<Event> out = new ArrayList<Event>();
        for (int i = 0; i < eventList.size() - 1; i++) {
            if (!eventList.get(i).getType().equals(eventList.get(i + 1).getType())) {
                if (!checkCollision(eventList.get(i))) {
                    a = eventList.get(i).getStartDate();
                    b = a;
                    if (a.getMinutes() > 9) {
                        a.setMinutes(a.getMinutes() - 10);
                    } else {
                        a.setHours(a.getHours() - 1);
                        a.setMinutes(a.getMinutes() + 50);
                    }
                    out.add(new Event(a, b, "Break_Special", "Transition"));
                }
            }
        }
        eventList.addAll(out);
        eventList = new ArrayList<>(Arrays.asList(dateSort(eventList)));

        while (true) {
            for (int i = 0; i < eventList.size(); i++) {
                out = new ArrayList<Event>();
                e = eventList.get(i);
                if (e.getEndMinute() < 30) {
                    e.setEndMinute(e.getEndMinute() + 30);
                } else {
                    e.setEndHour(e.getEndHour() + 1);
                    e.setEndMinute(e.getEndMinute() - 30);
                }
                if (checkCollision(e)) {
                    if (checkCollisionSpecial(eventList.get(i)).getType().equals("Break_Special")) {
                        eventList.remove(getEventIndex(checkCollisionSpecial(eventList.get(i))));
                    }
                } else {
                    e.setType("Work");
                    if (e.getEndMinute() > 5) {
                        e.setEndMinute(e.getEndMinute() - 5);
                    } else {
                        e.setEndHour(e.getEndHour() - 1);
                        e.setEndMinute(e.getEndMinute() + 55);
                    }
                    out.add(e);
                    e.setStartSecond(e.getEndSecond());
                    e.setStartMinute(e.getEndMinute());
                    e.setStartHour(e.getEndHour());
                    if (e.getEndMinute() < 55) {
                        e.setEndMinute(e.getEndMinute() + 5);
                    } else {
                        e.setEndHour(e.getEndHour() + 1);
                        e.setEndMinute(e.getEndMinute() - 55);
                    }
                    out.add(e);
                }
            }
            if (out.size() == 0) {
                break;
            }
            eventList.addAll(out);
            eventList = new ArrayList<>(Arrays.asList(dateSort(eventList)));
        }

        out = new ArrayList<Event>();
        for (int i = 0; i < eventList.size() - 1; i++) {
            if (eventList.get(i).compareTo(eventList.get(i + 1), Event.END_DATE, Event.START_DATE) == 1) {
                out.add(new Event(eventList.get(i).getStartDate(), eventList.get(i + 1).getEndDate(), "Transition into next activity", "Break_Special"));
            }
        }
        eventList.addAll(out);
        eventList = new ArrayList<>(Arrays.asList(dateSort(eventList)));
    }

    public Event[] checkCollision(Event e, ArrayList<Event> eL) {
        ArrayList<Event> eList = new ArrayList<>(eL);
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

    public Event checkCollisionSpecial(Event e) {
        ArrayList<Event> eList = new ArrayList<>(eventList);
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
        for (int i = 0; i < out.size(); i++) {
            if (out.get(i).getType().equals("Break_Special")) {
                return out.get(i);
            }
        }
        return new Event();
    }

    public boolean checkCollision(Event e) {
        if (eventList.get(getNextEventIndex(e)).compareTo(e, Event.START_DATE, Event.END_DATE) == 1) {
            return true;
        } else {
            if (eventList.get(getNextEventIndex(e) - 2).compareTo(e, Event.END_DATE, Event.START_DATE) == -1) {
                return true;
            }
        }
        return false;
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

    private int getNextEventIndex() {
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

    private int getEventIndex(Event rn) {
        int currentInterval = eventList.size() - 1;
        while (currentInterval > 0) {
            if (rn.compareTo(eventList.get(currentInterval)) == -1) {
                currentInterval = currentInterval + (eventList.size() - 1 - currentInterval) / 2;
            } else if (rn.compareTo(eventList.get(currentInterval)) == 1) {
                currentInterval /= 2;
            } else {
                return currentInterval;
            }
        }
        return 0;
    }

    private int getNextEventIndex(Event rn) {
        int currentInterval = eventList.size() - 1;
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

    public void addEvent(Event e) {
        eventList.add(e);
        eventList = new ArrayList<>(Arrays.asList(dateSort(eventList)));
    }

    public String toString() {
        String out = "Current schedule:\n";

        for (int i = 0; i < eventList.size(); i++) {
            out += eventList.get(i) + "\n";
        }

        return out;
    }
}