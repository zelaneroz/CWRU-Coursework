package HW1;

public class SquareRootMethod {
    public static void main(String[] args) {
        // TEST CASES
        System.out.println(sqrt(625,1,8));
        System.out.println(sqrt(625,1,27));
        System.out.println(sqrtIterative(625,1,8));
        System.out.println(sqrtIterative(625,1,27));
    }

    public static int sqrt(int x, int low, int high){
        if(low>high || x<0 || low<0 || high<0){
            return -1;
        }
        int p=low+(high-low)/2;
        if(p*p==x){
            return p;
        } else if (p*p>x) {
            return sqrt(x,low,p-1);
        }else{
            return sqrt(x,p+1,high);
        }
    }

    public static int sqrtIterative(int x, int low, int high){
        while(low<high){
            int p=low+(high-low)/2;
            if(p*p==x){
                return p;
            }else if (p*p>x) {
                return sqrt(x,low,p-1);
            }else{
                return sqrt(x,p+1,high);
            }
        }
        return -1;
    }
}
