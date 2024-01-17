import java.util.Date;
import java.util.Calendar;

public class Event {
    private Date time;
    private Date end;
    String type;
    String name;

    public Event() {
        this(new Date(), new Date(), "Leisure", "Play video games");
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
}