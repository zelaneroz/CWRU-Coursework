package HW1;

public class ExponentialMethod {
    public static void main(String[] args) {
        //TEST CASES
        System.out.println("\nIterative Method (multiply) Result: " + multiply(3)+ "\nRecursive Method (multiplyRecursive) Result: "+multiplyRecursive(3));
        System.out.println("\nIterative Method (multiply) Result: " + multiply(2)+ "\nRecursive Method (multiplyRecursive) Result: "+multiplyRecursive(2));
        System.out.println("\nIterative Method (multiply) Result: " + multiply(0)+ "\nRecursive Method (multiplyRecursive) Result: "+multiplyRecursive(0));
        System.out.println("\nIterative Method (multiply) Result: " + multiply(231)+ "\nRecursive Method (multiplyRecursive) Result: "+multiplyRecursive(231));
    }

    public static int multiply(int x){
        int y=2;
        for(int i=0; i<x; i++){
            y*=x;
        }
        return y;
    }

    public static int multiplyRecursive(int x){
        return 2*HelpermultiplyRecursive(x,x);
    }

    private static int HelpermultiplyRecursive(int x,int n){
        if(n==0) {
            return 1;
        }
        return x*HelpermultiplyRecursive(x,n-1);
    }
}