import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DecisionTree dt = new DecisionTree();
        Scanner input = new Scanner(System.in);
        String choice;

        while (true) {
            System.out.println("\n--- Travel Expert System ---");
            System.out.println("1. Build an Expert System");
            System.out.println("2. Find a Destination");
            System.out.println("3. Save Tree to File");
            System.out.println("4. Quit");
            System.out.print("Enter choice: ");
            choice = input.nextLine();

            switch (choice) {
                case "1":
                    dt.buildTree(input);
                    break;
                case "2":
                    dt.findDestination(input);
                    break;
                case "3":
                    System.out.print("Enter filename to save tree: ");
                    String filename = input.nextLine();
                    dt.saveToFile(filename);
                    break;
                case "4":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}


