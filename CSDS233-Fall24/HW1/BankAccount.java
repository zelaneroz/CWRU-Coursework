package HW1;

public class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber1, double balance1){
        accountNumber = accountNumber1;
        balance = balance1;
    }

    public void deposit(double amount){
        if(amount>0){
            balance+=amount;
        }else{
            System.out.println("Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount){
        if(amount<=balance){
            balance-=amount;
        }else{
            System.out.println("Insufficient funds.");
        }
    }

    public double getBalance(){
        return balance;
    }

}


// Write a test class that creates a HW1.BankAccount object, makes some deposits and withdrawals, and prints the final balance.
