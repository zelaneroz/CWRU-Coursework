package HW2;

public class StackifiedQueueTest {
    public static void main(String[] args) {
        IntegerQueue();
        StringQueue();
        DoubleQueue();
        BooleanQueue();

    }

    public static void IntegerQueue() {
        // TEST INTEGER TYPE
        System.out.println("--QUEUE OPERATIONS ON INTEGER DATA TYPE--");
        StackifiedQueue<Integer> queue1 = new StackifiedQueue<>();
        System.out.println("1 Poll from Empty Queue: " + queue1.poll());
        Integer[] data = {61, 385, 380, 448, 904, 410};
        for (int i = 0; i < data.length; i++) {
            queue1.add(data[i]);
        }
        System.out.println("\n2 Add Elements to Queue.\nExpected: [61, 385, 380, 448, 904, 410] | Observed: " + queue1.getQueue());
        System.out.println("\n3 Peek Operation\nExpected: 61 | Observed: " + queue1.peek());
        queue1.poll();
        queue1.poll();
        System.out.println("\n4 Poll Operation\nExpected: [380, 448, 904, 410] | Observed: " + queue1.getQueue());
        while (!queue1.isEmpty()) {
            queue1.poll();
        }
        System.out.println("\n5 Empty Out Queue\nExpected: [] | Observed: " + queue1.getQueue());
        queue1.add(5);
    }

    public static void StringQueue() {
        // TEST STRING TYPE
        System.out.println("--QUEUE OPERATIONS ON STRING DATA TYPE--");
        StackifiedQueue<String> queue = new StackifiedQueue<>();
        System.out.println("1 Poll from Empty Queue: " + queue.poll());
        String[] data = {"apple", "banana", "cherry", "date", "elderberry", "fig"};
        for (int i = 0; i < data.length; i++) {
            queue.add(data[i]);
        }
        System.out.println("\n2 Add Elements to Queue.\nExpected: [apple, banana, cherry, date, elderberry, fig] | Observed: " + queue.getQueue());
        System.out.println("\n3 Peek Operation\nExpected: apple | Observed: " + queue.peek());
        queue.poll();
        queue.poll();
        System.out.println("\n4 Poll Operation\nExpected: [cherry, date, elderberry, fig] | Observed: " + queue.getQueue());
        while (!queue.isEmpty()) {
            queue.poll();
        }
        System.out.println("\n5 Empty Out Queue\nExpected: [] | Observed: " + queue.getQueue());
    }

    public static void DoubleQueue() {
        // TEST DOUBLE TYPE
        System.out.println("--QUEUE OPERATIONS ON DOUBLE DATA TYPE--");
        StackifiedQueue<Double> queue = new StackifiedQueue<>();
        System.out.println("1 Poll from Empty Queue: " + queue.poll());
        Double[] data = {3.14, 2.71, 1.62, 4.20, 5.55};
        for (int i = 0; i < data.length; i++) {
            queue.add(data[i]);
        }
        System.out.println("\n2 Add Elements to Queue.\nExpected: [3.14, 2.71, 1.62, 4.20, 5.55] | Observed: " + queue.getQueue());
        System.out.println("\n3 Peek Operation\nExpected: 3.14 | Observed: " + queue.peek());
        queue.poll();
        queue.poll();
        System.out.println("\n4 Poll Operation\nExpected: [1.62, 4.20, 5.55] | Observed: " + queue.getQueue());
        while (!queue.isEmpty()) {
            queue.poll();
        }
        System.out.println("\n5 Empty Out Queue\nExpected: [] | Observed: " + queue.getQueue());
    }

    public static void BooleanQueue() {
        // TEST BOOLEAN TYPE
        System.out.println("--QUEUE OPERATIONS ON BOOLEAN DATA TYPE--");
        StackifiedQueue<Boolean> queue = new StackifiedQueue<>();
        System.out.println("1 Poll from Empty Queue: " + queue.poll());
        Boolean[] data = {true, false, true, false};
        for (int i = 0; i < data.length; i++) {
            queue.add(data[i]);
        }
        System.out.println("\n2 Add Elements to Queue.\nExpected: [true, false, true, false] | Observed: " + queue.getQueue());
        System.out.println("\n3 Peek Operation\nExpected: true | Observed: " + queue.peek());
        queue.poll();
        queue.poll();
        System.out.println("\n4 Poll Operation\nExpected: [true, false] | Observed: " + queue.getQueue());
        while (!queue.isEmpty()) {
            queue.poll();
        }
        System.out.println("\n5 Empty Out Queue\nExpected: [] | Observed: " + queue.getQueue());
    }

}
