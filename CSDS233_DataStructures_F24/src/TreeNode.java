public class TreeNode {
    String data;
    TreeNode yes;
    TreeNode no;

    public TreeNode(String data) {
        this.data = data;
        this.yes = null;
        this.no = null;
    }

    public boolean isLeaf() {
        return yes == null && no == null;
    }
}


