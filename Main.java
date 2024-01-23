import java.io.*;
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
                "Personal Project"
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
            // main(new String[1]); // reruns itself to go into "timer mode"
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

                                System.out.println("Enter event name:");
                                tempEvent.setName(scanner.nextLine());
                                System.out.println("\n\n");

                                tempEvent.setStartHour(intChoice("Enter event starting hour:", -1, 24));
                                tempEvent.setStartMinute(intChoice("Enter event starting minute:", -1, 24));
                                tempEvent.setStartSecond(intChoice("Enter event starting second:", -1, 24));
                                tempEvent.setEndHour(intChoice("Enter event ending hour:", -1, 24));
                                tempEvent.setEndMinute(intChoice("Enter event ending minute:", -1, 24));
                                tempEvent.setEndSecond(intChoice("Enter event ending second:", -1, 24));
                            }

                            schedule.addEvent(tempEvent);
                        }

                        else if (userChoice == 1) {
                            schedule.applyPreset();
                        }


                        else { // WOULD YOU LIKE TO PROCEED WITH THIS SCHEDULE?
                            System.out.println("Would you like to proceed with this schedule?");
                            userChoice = choice(FINAL_MENU, "No");

                            if (userChoice == 0) {

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

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}