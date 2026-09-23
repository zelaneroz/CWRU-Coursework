import java.io.*;
import java.util.Scanner;

public class DecisionTree {
    private TreeNode root;

    public DecisionTree() {
        root = null;
    }

    public void buildTree(Scanner input) {
        if (root == null) {
            System.out.print("Enter root question: ");
            root = new TreeNode(input.nextLine());
            System.out.print("Enter destination if YES: ");
            root.yes = new TreeNode(input.nextLine());
            System.out.print("Enter destination if NO: ");
            root.no = new TreeNode(input.nextLine());
        }
        expandTree(root, input);
    }

    private void expandTree(TreeNode node, Scanner input) {
        if (node.isLeaf()) return;

        System.out.println(node.data);
        String response = input.nextLine().trim().toLowerCase();
        if (response.equals("yes") || response.equals("y")) {
            if (node.yes.isLeaf()) {
                handleLeaf(node, true, input);
            } else {
                expandTree(node.yes, input);
            }
        } else if (response.equals("no") || response.equals("n")) {
            if (node.no.isLeaf()) {
                handleLeaf(node, false, input);
            } else {
                expandTree(node.no, input);
            }
        }
    }

    private void handleLeaf(TreeNode parent, boolean isYes, Scanner input) {
        TreeNode current = isYes ? parent.yes : parent.no;
        System.out.println("I’d recommend you going to: " + current.data);
        System.out.print("Is this where you want to go? ");
        String confirm = input.nextLine().trim().toLowerCase();

        if (confirm.equals("yes") || confirm.equals("y")) {
            System.out.println("Thank you for using the Travel Expert System!");
            return;
        }

        System.out.print("Please tell me where you are thinking of going: ");
        String newDestination = input.nextLine();
        System.out.print("Please give me a yes/no question to distinguish between \"" +
                current.data + "\" and \"" + newDestination + "\": ");
        String newQuestion = input.nextLine();
        System.out.print("What would the answer be for \"" + newDestination + "\"? (yes/no): ");
        String answer = input.nextLine().trim().toLowerCase();

        TreeNode questionNode = new TreeNode(newQuestion);
        if (answer.equals("yes") || answer.equals("y")) {
            questionNode.yes = new TreeNode(newDestination);
            questionNode.no = current;
        } else {
            questionNode.no = new TreeNode(newDestination);
            questionNode.yes = current;
        }

        if (isYes) parent.yes = questionNode;
        else parent.no = questionNode;
    }

    public void findDestination(Scanner input) {
        if (root == null) {
            System.out.print("Enter filename to load tree: ");
            String filename = input.nextLine();
            loadFromFile(filename);
        }

        TreeNode current = root;
        while (!current.isLeaf()) {
            System.out.println(current.data);
            String response = input.nextLine().trim().toLowerCase();
            if (response.equals("yes") || response.equals("y")) {
                current = current.yes;
            } else if (response.equals("no") || response.equals("n")) {
                current = current.no;
            } else {
                System.out.println("Please answer yes or no.");
            }
        }

        System.out.println("I recommend the following Destination: " + current.data);
    }

    public void saveToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            saveTree(root, bw);
        } catch (IOException e) {
            System.err.println("Failed to save tree: " + e.getMessage());
        }
    }

    private void saveTree(TreeNode node, BufferedWriter bw) throws IOException {
        if (node == null) {
            bw.write("null\n");
            return;
        }

        bw.write(node.data + "\n");
        saveTree(node.yes, bw);
        saveTree(node.no, bw);
    }

    public void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            root = loadTree(br);
        } catch (IOException e) {
            System.err.println("Failed to load tree: " + e.getMessage());
        }
    }

    private TreeNode loadTree(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null || line.equals("null")) return null;
        TreeNode node = new TreeNode(line);
        node.yes = loadTree(br);
        node.no = loadTree(br);
        return node;
    }
}

