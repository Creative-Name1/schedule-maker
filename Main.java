import java.io.*;
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // variable assignments
        boolean inSchedule = false;
        int userChoice;
        Event tempEvent;
        Scanner scanner = new Scanner(System.in);
        Schedule schedule = new Schedule(new ArrayList<Event>());
        Event[] tempSched = new Event[] {
                new Event(6, 0, 0, 6, 15, 0, "Transition", "Wake up"),
                new Event(6, 30, 0, 6, 35, 0, "Transition", "Get ready for work"),
                new Event(7, 30, 0, 7, 50, 0, "Transition", "Leave for work"),
                new Event(8, 0, 0, 10, 0, 0, "Work", "Work"),
                new Event(10, 0, 0, 10, 30, 0, "Break", "Break"),
                new Event(10, 30, 0, 12, 00, 0, "Work", "Keep working"),
                new Event(13, 30, 0, 13, 45, 0, "Other", "Call corporate"),
                new Event(15, 0, 0, 15, 30, 0, "Break", "Break"),
                new Event(18, 0, 0, 18, 20, 0, "Transition", "Drive home"),
                new Event(21, 0, 0, 21, 0, 0, "Transition", "Go to bed")
        };
        schedule.addEventList(tempSched);

        // options for ui
        final String[] MAIN_MENU = new String[] {"Make schedule"};
        final String[] SCHEDULE_MAKING_MENU = new String[] {
                "Add event",
                "Apply preset: Heavy work day",
                "Next"
        };
        final String[] FINAL_MENU = new String[] {"Yes"};
        final String[] EVENT_TYPE_MENU = new String[] {
                "Work",
                "Leisure",
                "Break",
                "Excercise",
                "Personal Project",
                "Transition"
        };

        // if there is a schedule active, inSchedule = true
        try {
            FileReader fr = new FileReader("Schedule.txt");
            BufferedReader br = new BufferedReader(fr);
            if (br.readLine().equals("in-schedule")) {
                inSchedule = true;
            }
        } catch (IOException e) {
            System.err.println(e);
        }

        if (inSchedule) { // if there is a schedule already
            Event currentEvent;
            Event nextEvent;
            Date d;
            schedule.readFromFile();
            while (schedule.getNextEventIndex() < schedule.getSize()) {
                clearScreen();
                d = new Date();
                currentEvent = schedule.getCurrentEvent();
                nextEvent = schedule.getNextEvent();
                System.out.println("Current time: " + d.getHours() + "h " + d.getMinutes() + "m " + d.getSeconds() + "s");
                System.out.println(
                        "\nCurrent Task: " + currentEvent.getName() +
                        "\nType: " + currentEvent.getType() +
                        "\nTime left: " +
                        (currentEvent.getEndHour() - d.getHours()) + "h " +
                        (currentEvent.getEndMinute() - d.getMinutes()) + "m " +
                        (currentEvent.getEndMinute() - d.getSeconds()) + "s\n"
                );
                System.out.println(
                        "Next Task: " + nextEvent.getName() + ", starts at " +
                        nextEvent.getStartHour() + "h " +
                        nextEvent.getStartMinute() + "m " +
                        nextEvent.getStartSecond() + "s"
                );
            }
            try {
                FileWriter fw = new FileWriter("Schedule.txt");
                PrintWriter pw = new PrintWriter(fw);
                pw.println("not-in-schedule\n");
                pw.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }




        else { // if the user is just now starting the program, main menu
            while (true) {
                userChoice = choice(MAIN_MENU, "Quit");
                if (userChoice == -1) {
                    break;
                }


                else { // Schedule making menu
                    while (true) {
                        System.out.println(schedule);
                        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                        userChoice = choice(SCHEDULE_MAKING_MENU, "Back");

                        if (userChoice == -1) {
                            break;
                        }

                        else if (userChoice == 0) { // not switch cause large code contents

                            tempEvent = new Event();
                            System.out.println("Enter type of event:");
                            userChoice = choice(EVENT_TYPE_MENU, "Cancel");

                            if (! (userChoice == -1)){
                                tempEvent.setType(EVENT_TYPE_MENU[userChoice]);

                                System.out.println("Enter event name (do not use tilde (~)!):");
                                tempEvent.setName((scanner.nextLine()).replaceAll("~", "-")); // for file reading purposes cannot be '~'
                                System.out.println("\n\n");

                                tempEvent.setStartHour(intChoice("Enter event starting hour:", -1, 24));
                                tempEvent.setStartMinute(intChoice("Enter event starting minute:", -1, 60));
                                tempEvent.setStartSecond(intChoice("Enter event starting second:", -1, 60));
                                tempEvent.setEndHour(intChoice("Enter event ending hour:", -1, 24));
                                tempEvent.setEndMinute(intChoice("Enter event ending minute:", -1, 60));
                                tempEvent.setEndSecond(intChoice("Enter event ending second:", -1, 60));


                                schedule.addEvent(tempEvent);

                                if (tempEvent.compareTo(tempEvent, Event.START_DATE, Event.END_DATE) == -1) {
                                    if (tempEvent.getEndHour() < 12) {
                                        tempEvent.setEndHour(tempEvent.getEndHour() + 12);
                                    } else {
                                        System.out.println("End date cannot be less than Starting date!\n(Press enter to continue)");
                                        scanner.nextLine();
                                    }
                                }
                            }
                        }

                        else if (userChoice == 1) {
                            schedule.applyPreset();
                        }


                        else { // WOULD YOU LIKE TO PROCEED WITH THIS SCHEDULE?
                            System.out.println("Would you like to proceed with this schedule?");
                            userChoice = choice(FINAL_MENU, "No");

                            if (userChoice == 0) {
                                schedule.writeToFile();
                                main(new String[0]); // program recurses itself with the newly made schedule to go into "Timer mode"
                            }
                        }
                    }
                }
            }
        }
    }

    public static int choice(String[] s, String alt) {
        int out = -1;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            for (int i = 0; i < s.length; i++) {
                System.out.println(i + " - " + s[i]);
            }
            System.out.print("-1 - " + alt + "\n\nEnter an integer: ");
            try {
                out = scanner.nextInt();
                if (out > -2 && out < s.length) {
                    return out;
                } else {
                    System.out.println(out + " is not one of the options!");
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Input must be an integer!");
            }
        }
    }

    public static void clearScreen() {
        try {
            Runtime.getRuntime().exec("cls"); // assuming that you are runnning the program on windows
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int intChoice(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println(message);
                System.out.print("Enter an integer: ");
                return scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Must be an integer!");
            }
        }
    }

    public static int intChoice(String message, int lowBound, int upBound) {
        Scanner scanner = new Scanner(System.in);
        int out;
        while (true) {
            try {
                System.out.println(message);
                System.out.print("Enter an integer between " + lowBound + " and " + upBound + ": ");
                out = scanner.nextInt();
                if (out > lowBound && out < upBound) {
                    return out;
                } else {
                    scanner.nextLine();
                    System.out.println("Must be between " + lowBound + " and " + upBound + "!");
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Must be an integer!");
            }
        }
    }

}