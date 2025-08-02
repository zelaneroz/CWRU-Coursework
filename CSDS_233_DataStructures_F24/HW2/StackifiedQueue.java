package HW2;

import java.util.Stack;

public class StackifiedQueue<E> {
    private Stack<E> stack1;
    private Stack<E> stack2;

    public StackifiedQueue(){
        super();
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public Stack<E> getQueue(){
        return stack1;
    }

    public boolean add(E element){
        stack1.push(element);
        return true;
    }

//    public E poll(){
//        if(isEmpty()){
//            return null;
//        }
//
//        if(stack2.isEmpty()){
//            while(!stack1.isEmpty()){
//                stack2.push(stack1.pop());
//            }
//        }
//        return stack2.pop();
//    }

    public E poll(){
        if(isEmpty()){
            return null;
        }

        if(stack2.isEmpty()){
            while(!stack1.isEmpty()){
                stack2.push(stack1.pop());
            }
        }
        E x= stack2.pop();
        if(stack1.isEmpty()){
            while(!stack2.isEmpty()){
                stack1.push(stack2.pop());
            }
        }
        return x;
    }

    public E peek(){
        if(isEmpty()){
            return null;
        }
        if(stack2.isEmpty()){
            while(!stack1.isEmpty()){
                stack2.push(stack1.pop());
            }
        }
        E x= stack2.peek();
        if(stack1.isEmpty()){
            while(!stack2.isEmpty()){
                stack1.push(stack2.pop());
            }
        }
        return x;
    }

    public boolean isEmpty(){
        return stack1.isEmpty() && stack2.isEmpty();
    }


}
