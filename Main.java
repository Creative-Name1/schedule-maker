public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("fromBatch")) {
            System.out.println("Program started from batch file");
        } else {
            System.out.println("Manually started program");
        }

        Event[] eList = new Event[] {
                new Event(12, 34, 6),
                new Event(12, 34, 5),
                new Event(6, 22, 0),
                new Event(23, 12, 27),
                new Event(7, 9, 32),
                new Event(16, 27, 6),
                new Event(14, 22, 0),
                new Event(21, 12, 27),
                new Event(19, 30, 0),
                new Event(17, 23, 0),
                new Event(3, 2, 27)
        };

        Schedule s = new Schedule(eList);
        Event[] currentSchedule = s.getEventList();
        System.out.println(s.getNextEvent());
    }
}