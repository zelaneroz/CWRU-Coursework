package HW2;

class IntegerNode{
    private int element;
    private IntegerNode nextNode;

    public IntegerNode(int element){
        this.element = element;
        this.nextNode = null;
    }

    public int getElement(){
        return element;
    }

    public IntegerNode getNextNode(){
        return nextNode;
    }

    public void setNextNode(IntegerNode nextNode1){
        nextNode = nextNode1;
    }
}

public class NumLinkedList {
    public IntegerNode head;

    public NumLinkedList(){
        this.head = null;
    }

    public int size(){
        int count = 0;
        IntegerNode currentNode = head;
        while(currentNode!=null){
            count++;
            currentNode = currentNode.getNextNode();
        }
        return count;
    }

    public void add(int element) {
        IntegerNode newNode = new IntegerNode(element);
        if (head == null) {
            head = newNode;
        } else {
            IntegerNode currentNode = head;
            while (currentNode.getNextNode() != null) {
                currentNode = currentNode.getNextNode();
            }
            currentNode.setNextNode(newNode);
        }
    }

    public boolean isSorted(){
        if(head==null || head.getNextNode()==null){
            return true;
        }
        IntegerNode currentNode = head;
        while(currentNode.getNextNode()!=null){
            if(currentNode.getElement()>currentNode.getNextNode().getElement()){
                return false;
            }
            currentNode = currentNode.getNextNode();
        }
        return true;
    }

    public void reverse(){
        // reverse element of the list in place, using constant space and linear time
        IntegerNode prev= null, current=head, next=null;
        while(current!=null){
            next = current.getNextNode();
            current.setNextNode(prev);
            prev = current;
            current = next;
        }
        head = prev;
    }

    public static NumLinkedList merge(NumLinkedList list1, NumLinkedList list2){
        NumLinkedList mergedList = new NumLinkedList();
        IntegerNode node1 = list1.head;
        IntegerNode node2 = list2.head;

        if(list1.isSorted() && list2.isSorted()) {
            //KEEP LIST SORTED
            while(node1!=null && node2!=null){
                if(node1.getElement()<=node2.getElement()){
                    mergedList.add(node1.getElement());
                    node1 = node1.getNextNode();
                }else{
                    mergedList.add(node2.getElement());
                    node2 = node2.getNextNode();
                }
            }
        }

        while(node1!=null){
            mergedList.add(node1.getElement());
            node1 = node1.getNextNode();
        }

        while(node2!=null){
            mergedList.add(node2.getElement());
            node2 = node2.getNextNode();
        }
        return mergedList;
    }

    public static NumLinkedList duplicate(NumLinkedList list1){
        NumLinkedList list2 = list1;
        return list2;
    }

    public void printList(){
        IntegerNode currentNode = this.head;
        while(currentNode != null){
            System.out.print(currentNode.getElement() + ", ");
            currentNode = currentNode.getNextNode();
        }
        System.out.print("\n");
    }
}

