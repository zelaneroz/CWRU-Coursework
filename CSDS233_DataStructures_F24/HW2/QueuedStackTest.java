package HW2;/*
Create a test class to demonstrate creating a HW2.QueuedStack object
with different data types, adding some elements,
and performing stack operations.
 */

public class QueuedStackTest{
    //MAIN METHOD FOR TESTING
    public static void main(String[] args){
        // TEST INTEGER TYPE
        IntegerStack();
        StringStack();
        PopEmptyStack(); //Code successfully trhows an EmptyStackException
    }

    public static void PopEmptyStack(){
        QueuedStack<Integer> empty_stack = new QueuedStack<>();
        empty_stack.pop();
    }

    public static void StringStack(){
        QueuedStack<String> string_stack = new QueuedStack<>();
        System.out.println("\n--TESTING STACK WITH STRING DATA TYPE---");
        string_stack.push("a");
        string_stack.push("z");
        string_stack.push("x");
        System.out.println("Top element is: "+string_stack.peek());
        System.out.println("Popped element is: "+string_stack.pop());
        System.out.println("Top element after pop is: "+string_stack.peek());
        System.out.println("Stack is empty: "+string_stack.isEmpty());
    }

    public static void IntegerStack(){
        QueuedStack<Integer> stack = new QueuedStack<>();
        System.out.println("\n--TESTING STACK WITH INTEGER DATA TYPE---");
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("Top element is: "+stack.peek());
        System.out.println("Popped element is: "+stack.pop());
        System.out.println("Top element after pop is: "+stack.peek());
        System.out.println("Stack is empty: "+stack.isEmpty());
    }
}