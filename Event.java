import java.util.Date;
import java.util.Calendar;
import java.util.Objects;

public class Event implements Comparable {
    private Date time;
    private Date end;
    private String type;
    private String name;
    public static final int START_DATE = 0;
    public static final int END_DATE = 1;
  

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

    public void setEndDate(Date d) {
      end = d;
    }
    public void setEndDate(int h, int m, int s) {
      Calendar c = Calendar.getInstance();
      end = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), h, m, s);
    }

    // misc
    public String toString() {
        return (
          "Activity " + name +
          "; Type " + type +
          "; Start " +  time.getHours() + ":" + 
          time.getMinutes() + ":" + time.getSeconds() +
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
            s2 = this.getStartMinute();
            m2 = this.getStartSecond();
          } else {
            h2 = this.getEndHour();
            s2 = this.getEndMinute();
            m2 = this.getEndSecond();
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