package HW3;

public class BinarySearchTreeMain {
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

        // 0. Test height method for an empty tree
        System.out.println("Height of the empty tree: " + bst.height()+"\n");

        // 1. Insert Nodes and Display Traversals
        System.out.println("Inserting nodes: 50, 30, 20, 40, 70, 60, 80");
        bst.insert(50);
        bst.insert(30);
        bst.insert(20);
        bst.insert(40);
        bst.insert(70);
        bst.insert(60);
        bst.insert(80);

        System.out.println("\nIn-order Traversal:");
        bst.inorderTrav();

        System.out.println("Pre-order Traversal:");
        bst.preorderTrav();

        System.out.println("Post-order Traversal:");
        bst.postorderTrav();

        // 2. Test CreateTree Method
        System.out.println("\nCreating a tree from array: {15, 10, 20, 8, 12, 17, 25}");
        int[] values = {15, 10, 20, 8, 12, 17, 25};
        BinarySearchTree newTree = new BinarySearchTree();
        newTree.createTree(values);

        System.out.println("In-order Traversal of the newly created tree:");
        newTree.inorderTrav();

        // 3. Test Search Method
        System.out.println("\nSearching for values (present and absent): 20, 70, 100");
        System.out.println("Search for 20: " + bst.search(20));  // Should be true
        System.out.println("Search for 70: " + bst.search(70));  // Should be true
        System.out.println("Search for 100: " + bst.search(100));  // Should be false

        System.out.println("Search for 15 (new tree): " + newTree.search(15));  // Should be true
        System.out.println("Search for 8 (new tree): " + newTree.search(8));  // Should be true
        System.out.println("Search for 30 (new tree): " + newTree.search(30));  // Should be false

        // 4. Test Delete Method
        System.out.println("\nDeleting nodes: 20, 30, 50");
        bst.delete(20);
        System.out.println("In-order Traversal after deleting 20:");
        bst.inorderTrav();

        bst.delete(30);
        System.out.println("In-order Traversal after deleting 30:");
        bst.inorderTrav();

        bst.delete(50);
        System.out.println("In-order Traversal after deleting 50:");
        bst.inorderTrav();

        // 5. Test Find Min and Max Operations
        System.out.println("\nTesting Find Min and Max:");
        System.out.println("Minimum value in the tree: " + bst.findMin());
        System.out.println("Maximum value in the tree: " + bst.findMax());

        System.out.println("Minimum value in new tree: " + newTree.findMin());
        System.out.println("Maximum value in new tree: " + newTree.findMax());

        // 6. Test Tree Height Method
        System.out.println("\nTesting Tree Height:");
        System.out.println("Height of the original tree: " + bst.height());
        System.out.println("Height of the new tree: " + newTree.height());
    }
}
