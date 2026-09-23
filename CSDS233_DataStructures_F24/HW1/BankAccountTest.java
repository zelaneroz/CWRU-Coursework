package HW1;

public class BankAccountTest {

    public static void main(String[] args) {
        // Test 1: Creating a bank account and checking initial balance
        BankAccount account = new BankAccount("123456", 1000.00);
        System.out.println("Test 1 - Initial Balance: " + account.getBalance());  // Expected: 1000.00

        // Test 2: Depositing a positive amount
        account.deposit(500.00);
        System.out.println("Test 2 - After Deposit of 500: " + account.getBalance());  // Expected: 1500.00

        // Test 3: Trying to deposit a negative amount (should print an error message)
        System.out.println("Test 3 - After Negative Deposit: " + account.getBalance());  // Expected: 1500.00 (no change)
        account.deposit(-100.00);  // Expected: "Deposit amount must be positive."

        // Test 4: Withdrawing a valid amount
        account.withdraw(300.00);
        System.out.println("Test 4 - After Withdrawal of 300: " + account.getBalance());  // Expected: 1200.00

        // Test 5: Trying to withdraw more than the balance (should print an error message)
        System.out.println("Test 5 - After Withdrawal of 2000: " + account.getBalance());  // Expected: 1200.00 (no change)
        account.withdraw(2000.00);  // Expected: "Insufficient funds."
    }
}
