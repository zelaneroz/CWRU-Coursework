package HW2;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;


public class QueuedStack<T> {
    private Queue<T> stack;
    private Class<T> type;

    public QueuedStack() {
        stack = new LinkedList<>();
    }

    public T push(T element) {
        stack.add(element);

        int size = stack.size();
        for(int i = 0; i<size; i++) {
            stack.add(stack.remove());
        }
        return element;
    }

    public T pop() {
        if(stack.isEmpty()) {
            System.out.println("Cannot pop from an empty stack.");
            throw new EmptyStackException();
        }
        return stack.remove();
    }

    public T peek(){
        if(stack.isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.peek();
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }

}