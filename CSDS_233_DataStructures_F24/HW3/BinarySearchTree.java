package HW3;

public class BinarySearchTree {
    private class Node{
        int key;
        Node left;
        Node right;

        public Node(int key){
            this.key = key;
            left = null;
            right = null;
        }
    }

    Node root;
    public BinarySearchTree() {
        root = null;
    }

    //1. INSERT OPERATION: INSERTS A NEW NODE WITH THE GIVEN KEY
    public void insert(int key) {
        if(root == null) {
            root = new Node(key);
            return;
        }
        Node current = root;
        while(true) {
            if(key<current.key){
                if(current.left == null) {
                    current.left = new Node(key);
                    return;
                }
                current = current.left;
            } else if(key>current.key){
                if(current.right == null) {
                    current.right = new Node(key);
                    return;
                }
                current = current.right;
            } else{
                return; //Key already exists, do nothing.
            }
        }
    }

    public void createTree(int[] values){
        for(int value:values){
            insert(value);
        }
    }

    public boolean search(int key) {
        return searchRec(root, key);
    }
    private boolean searchRec(Node root, int key){
        if(root==null)
            return false;
        if(root.key==key)
            return true;
        if(key<root.key)
            return searchRec(root.left,key);
        return searchRec(root.right, key);
    }

    public void delete(int key){
        Node current = root, parent=null;

        //STEP 1: FIND NODE TO DELETE
        while(current!=null && current.key!=key){
            parent=current;
            if(key<current.key) {
                current = current.left;
            }else{
                current = current.right;
            }
        }

        // IF KEY NOT FOUND IN TREE
        if(current==null){
            System.out.println("Key "+key+ " not found in tree.");
        }

        //STEP 2: HANDLE ALL CASES OF DELETION WHEN FOUND

        //CASE 1: NODE HAS NO CHILDREN (LEAF NODE)
        if(current.left==null && current.right==null){
            if(current==root){
                root=null;
            }else{
                if (parent.left == current) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
        }
        //CASE 2: NODE HAS 1 CHILD
        else if (current.left == null) {
            if (current == root) {
                root = current.right;
            } else if (parent.left == current) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        } else if (current.right == null) {
            if (current == root) {
                root = current.left;
            } else if (parent.left == current) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
        }
        //CASE 3: NODE HAS 2 CHILDREN
        else {
            Node successorParent = current;
            Node successor = current.right;

            // Find in-order successor (smallest value in right subtree)
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }

            // Replace current's key with successor's key
            current.key = successor.key;

            // Delete the successor node
            if (successorParent.left == successor) {
                successorParent.left = successor.right;
            } else {
                successorParent.right = successor.right;
            }
        }
    }


    public int findMin(){//FIND THE LEFT-MOST NODE
        Node current = root;
        while(current.left!=null){
            current=current.left;
        }
        return current.key;
    }

    public int findMax(){        //FIND THE RIGHT-MOST NODE
        Node current = root;
        while(current.right!=null){
            current=current.right;
        }
        return current.key;
    }

    public int height(){
        return height(root);
    }

    private int height(Node node) {
        if (node == null) {
            return -1;  // Return 0 for an empty tree or subtree
        }

        // Recursively calculate the height of left and right subtrees
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        // Return the maximum of the two heights plus 1 for the current node
        return 1 + (leftHeight > rightHeight ? leftHeight : rightHeight);
    }

    public void inorderTrav() {
        inorderRec(root);
        System.out.println("\n");
    }

    void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.key + " ");
            inorderRec(root.right);
        }
    }

    public void preorderTrav(){
        preorderRec(root);
        System.out.println("\n");
    }

    void preorderRec(Node root) {
        if (root != null) {
            System.out.print(root.key + " ");
            preorderRec(root.left);
            preorderRec(root.right);
        }
    }

    public void postorderTrav(){
        postorderRec(root);
        System.out.println("\n");
    }

    void postorderRec(Node root) {
        if (root != null) {
            postorderRec(root.left);
            postorderRec(root.right);
            System.out.print(root.key + " ");
        }
    }


}

