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
                new Event(23, 12, 27)
        };

        Schedule s = new Schedule(eList);
        Event[] currentSchedule = s.getEventList();
        for (int i = 0; i < currentSchedule.length; i++) {
            System.out.println(currentSchedule[i]);
        }
    }
}