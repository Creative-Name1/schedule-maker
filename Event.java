import java.util.Date;
import java.util.Calendar;
import java.util.Objects;

public class Event implements Comparable {
    private Date time;
    private Date end;
    String type;
    String name;

    public Event() {
        this(new Date(), new Date(), "Leisure", "Play video games");
    }
    public Event(int startH, int startM, int startS, int duraH, int duraM, int duraS) {
        this(startH, startM, startS, duraH, duraM, duraS, "Not specified", "Unnamed Event", Calendar.getInstance());
    }
    public Event(int startH, int startM, int startS, int duraH, int duraM, int duraS, String n) {
        this(startH, startM, startS, duraH, duraM, duraS, "Not specified", n, Calendar.getInstance());
    }
    public Event(int startH, int startM, int startS, int duraH, int duraM, int duraS, String t, String n) {
        this(startH, startM, startS, duraH, duraM, duraS, t, n, Calendar.getInstance());
    }
    public Event(int startH, int startM, int startS, int duraH, int duraM, int duraS, String t, String n, Calendar c) {
        this(
                new Date(
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH), startH, startM, startS
                ), new Date(
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH),
                        startH + duraH, startM + duraM, startS + duraS
                ), t, n
        );
    }
    public Event(Date d, Date e, String t, String n) {
        time = d;
        end = e;
        type = t;
        name = n;
    }



    // For testing purposes
    public Event(int startH, int startM, int startS) {
        this(startH, startM, startS, 0, 0, 0, "Not specified", "Unnamed Event", Calendar.getInstance());
    }






    // getters
    public Date getStartDate() {
        return time;
    }
    public Date getEndDate() {
        return end;
    }
    public int getStartHour() {
        return time.getHours();
    }
    public int getStartMinute() {
        return time.getMinutes();
    }
    public int getStartSecond() {
        return time.getSeconds();
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

    // misc
    public String toString() {
        return (
                        "Activity name: " + name +
                        "\nActivity type: " + type +
                        "\nStart: " + time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds() +
                        "\nEnd: " + end.getHours() + ":" + end.getMinutes() + ":" + end.getSeconds()
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
}