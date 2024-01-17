public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("fromBatch")) {
            System.out.println("Program started from batch file");
        } else {
            System.out.println("Manually started program");
        }
    }
}