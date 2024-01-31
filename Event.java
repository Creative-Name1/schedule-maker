import java.io.FileWriter;
import java.lang.reflect.Type;
import java.sql.Time;
import java.util.Calendar;
import java.util.Objects;
import java.io.*;

public class Event implements Comparable {
    private Time start;
    private Time end;
    private final String[] VALID_TYPES = new String[]{
            "Work",
            "Leisure",
            "Break",
            "Excercise",
            "Personal Project",
            "Transition",
            "Break_Special"
    };
    private String type;
    private String name;
    public static final int START_DATE = 0;
    public static final int END_DATE = 1;


    public Event() {
        this(new Time(0, 0, 0), new Time(0, 0, 0), "Leisure", "Play video games");
    }

    public Event(int startH, int startM, int startS, int endH, int endM, int endS) {
        this(startH, startM, startS, endH, endM, endS, "Not specified", "Unnamed Event", Calendar.getInstance());
    }

    public Event(int startH, int startM, int startS, int endH, int endM, int endS, String n) {
        this(startH, startM, startS, endH, endM, endS, "Not specified", n, Calendar.getInstance());
    }

    public Event(int startH, int startM, int startS, int endH, int endM, int endS, String t, String n) {
        this(startH, startM, startS, endH, endM, endS, t, n, Calendar.getInstance());
    }

    public Event(int startH, int startM, int startS, int endH, int endM, int endS, String t, String n, Calendar c) {
        this(new Time(startH, startM, startS), new Time(endH, endM, endS), t, n);
    }

    public Event(Time s, Time e, String t, String n) {
        start = s;
        end = e;
        name = n;
        for (int i = 0; i < VALID_TYPES.length; i++) {
            if (t.equals(VALID_TYPES[i])) {
                type = t;
                break;
            } else if (i == VALID_TYPES.length - 1) {
                type = "Not Specified";
            }
        }
    }

    public Event(Event e) { // for deep copy
        setStartTime(e.getStartTime());
        setEndTime(e.getEndTime());
        name = e.getName();
        type = e.getType();
    }


    public void writeToFile() {
        try {
            FileWriter fw = new FileWriter("Schedule.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(name + "~" + type + "~" + start.getHours() + "~" + start.getMinutes() + "~" + start.getSeconds() + "~" + end.getHours() + "~" + end.getMinutes() + "~" + end.getSeconds());
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void writeFromString(String str) {
        name = substringFinder(1, str);
        this.setType(substringFinder(2, str));
        start.setHours(Integer.parseInt(substringFinder(3, str)));
        start.setMinutes(Integer.parseInt(substringFinder(4, str)));
        start.setSeconds(Integer.parseInt(substringFinder(5, str)));
        end.setHours(Integer.parseInt(substringFinder(6, str)));
        end.setMinutes(Integer.parseInt(substringFinder(7, str)));
        end.setSeconds(Integer.parseInt(substringFinder(8, str)));
    }


    // stolen from my own 2.3 excercises
    private static boolean validChar(char chr) {
        return !(chr == '~');
    }

    private static String substringFinder(int which, String str) {
        str += " ";
        int index1 = 0;
        int index2 = 0;
        for (int i = 1; i <= which; i++) {
            index1 = index2;
            for (int j = index1; !validChar(str.charAt(j)); j++) {
                index1 = j + 1;
            }
            for (int j = index1; validChar(str.charAt(j)); j++) {
                index2 = j + 1;
            }
        }
        return str.substring(index1, index2);
    }

    // getters
    public Time getStartTime() {
        return start;
    }

    public Time getEndTime() {
        return end;
    }

    public int getStartHour() {
        return start.getHours();
    }

    public int getStartMinute() {
        return start.getMinutes();
    }

    public int getStartSecond() {
        return start.getSeconds();
    }

    public int getEndHour() {
        return end.getHours();
    }

    public int getEndMinute() {
        return end.getMinutes();
    }

    public int getEndSecond() {
        return end.getSeconds();
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setEndTime(Time t) {
        end = new Time(t.getHours(), t.getMinutes(), t.getSeconds());
    }

    public void setEndTime(int h, int m, int s) {
        Calendar c = Calendar.getInstance();
        end = new Time(h, m, s);
    }

    public void setEndHour(int n) {
        end.setHours(n);
    }

    public void setEndMinute(int n) {
        end.setMinutes(n);
    }

    public void setEndSecond(int n) {
        end.setSeconds(n);
    }

    public void setStartHour(int n) {
        start.setHours(n);
    }

    public void setStartMinute(int n) {
        start.setMinutes(n);
    }

    public void setStartSecond(int n) {
        start.setSeconds(n);
    }

    public void setStartTime(Time t) {
        start = new Time(t.getHours(), t.getMinutes(), t.getSeconds());
    }

    public void setType(String s) {
        for (int i = 0; i < VALID_TYPES.length; i++) {
            if (s.equals(VALID_TYPES[i])) {
                type = s;
                break;
            }
        }
    }

    public void setName(String s) {
        name = s;
    }

    // misc
    public String toString() {
        return (
                "Activity " + name +
                        "; Type " + type +
                        "; Start " + start.getHours() + ":" +
                        start.getMinutes() + ":" + start.getSeconds() +
                        "; End " + end.getHours() + ":" +
                        end.getMinutes() + ":" + end.getSeconds()
        );
    }

    public int compareTo(Object o) {
        // IF O IS AN EVENT OBJECT
        if (o instanceof Event) {

            // compares o starting hour to this, if they equal, compares start minute, if those equal, compares start second.
            if (((Event) o).getStartHour() > this.getStartHour()) {
                return 1;
            } else if (((Event) o).getStartHour() < this.getStartHour()) {
                return -1;
            } else {
                if (((Event) o).getStartMinute() > this.getStartMinute()) {
                    return 1;
                } else if (((Event) o).getStartMinute() < this.getStartMinute()) {
                    return -1;
                } else {
                    if (((Event) o).getStartSecond() > this.getStartSecond()) {
                        return 1;
                    } else if (((Event) o).getStartSecond() < this.getStartSecond()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        }

        // IF O IS NOT AN EVENT OBJECT, THROWS ERROR
        else {
            try {
                throw new Exception("Not an event object, dummy");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean collidesWith(Event e) {
        if (this.compareTo(e) != -1) {
            if (this.compareTo(e, END_DATE, START_DATE) != -1) {
                return false;
            }
        } else if (this.compareTo(e, START_DATE, END_DATE) != 1) {
            if (this.compareTo(e, END_DATE, END_DATE) != 1) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object o) {
        if (o instanceof Event) {
            Event e = (Event) o;
            if (e.getStartTime().equals(this.getStartTime()) && e.getEndTime().equals(this.getEndTime()) && e.getName().equals(this.getName()) && e.getType().equals(this.getType())) {
                return true;
            }
            return false;
        }
        // IF O IS NOT AN EVENT OBJECT, THROWS ERROR
        else {
            try {
                throw new Exception("Not an event object, dummy");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    // to allow quick comparisons of end dates and start dates
    // rather than just start dates
    public int compareTo(Object obj, int obj1, int obj2) {
        // IF O IS AN EVENT OBJECT
        if (obj instanceof Event) {
            Event o = ((Event) obj);
            int h1, m1, s1, h2, m2, s2;
            if (obj2 == 0) {
                h1 = o.getStartHour();
                m1 = o.getStartMinute();
                s1 = o.getStartSecond();
            } else {
                h1 = o.getEndHour();
                m1 = o.getEndMinute();
                s1 = o.getEndSecond();
            }
            if (obj1 == 0) {
                h2 = this.getStartHour();
                m2 = this.getStartMinute();
                s2 = this.getStartSecond();
            } else {
                h2 = this.getEndHour();
                m2 = this.getEndMinute();
                s2 = this.getEndSecond();
            }

            // compares o starting hour to this, if they equal, compares start minute, if those equal, compares start second.
            if (h1 > h2) {
                return 1;
            } else if (h1 < h2) {
                return -1;
            } else {
                if (m1 > m2) {
                    return 1;
                } else if (m1 < m2) {
                    return -1;
                } else {
                    if (s1 > s2) {
                        return 1;
                    } else if (s1 < s2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        }

        // IF O IS NOT AN EVENT OBJECT, THROWS ERROR
        else {
            try {
                throw new Exception("Not an event object, dummy");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}