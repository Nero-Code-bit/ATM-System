import java.util.Scanner;
import java.util.HashMap;

public class atmsys {
    // HashMap to store accounts and their details
    static HashMap<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create an initial account with default details for testing purposes
        accounts.put("234567", new Account("234567", "98765", 1000.00));

        // Main menu loop
        while (true) {
            System.out.println("\nATM System:");
            System.out.println("1. Log In");
            System.out.println("2. Create New Account");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    login(scanner); // Handle login
                    break;
                case 2:
                    createNewAccount(scanner); // Handle new account creation
                    break;
                case 3:
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Login process
    public static void login(Scanner scanner) {
        while (true) {
            System.out.println("\nEnter Account Number: ");
            String accountNumber = scanner.next();

            System.out.println("Enter PIN: ");
            String pin = scanner.next();

            // Validate login
            if (accounts.containsKey(accountNumber) && accounts.get(accountNumber).getPin().equals(pin)) {
                System.out.println("Login Successful!");
                Account loggedInAccount = accounts.get(accountNumber);
                atmMenu(scanner, loggedInAccount); // Show ATM menu
                break;
            } else {
                System.out.println("Invalid Account Number or PIN. Try again.");
            }
        }
    }

    // ATM Menu after login is successful
    public static void atmMenu(Scanner scanner, Account account) {
        while (true) {
            System.out.println("\nATM MENU:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Log Out");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    // Display balance
                    System.out.println("Your balance is: $" + account.getBalance());
                    break;
                case 2:
                    // Deposit money
                    System.out.println("Enter amount to deposit: ");
                    double deposit = scanner.nextDouble();
                    if (deposit > 0) {
                        account.deposit(deposit);
                        System.out.println("Deposited: $" + deposit);
                        System.out.println("New balance: $" + account.getBalance());
                    } else {
                        System.out.println("Invalid amount. Deposit canceled.");
                    }
                    break;
                case 3:
                    // Withdraw money
                    System.out.println("Enter amount to withdraw: ");
                    double withdrawal = scanner.nextDouble();
                    if (withdrawal > 0 && withdrawal <= account.getBalance()) {
                        account.withdraw(withdrawal);
                        System.out.println("Withdrawn: $" + withdrawal);
                        System.out.println("New balance: $" + account.getBalance());
                    } else if (withdrawal > account.getBalance()) {
                        System.out.println("Insufficient funds for this withdrawal.");
                    } else {
                        System.out.println("Invalid amount. Withdrawal canceled.");
                    }
                    break;
                case 4:
                    // Log out
                    System.out.println("Logging out...");
                    return; // Exit the atmMenu and return to login
                default:
                    // Invalid option
                    System.out.println("Invalid option. Please select again.");
                    break;
            }
        }
    }

    // Create a new account
    public static void createNewAccount(Scanner scanner) {
        System.out.println("\nCreate a New Account");

        System.out.println("Enter Account Number: ");
        String newAccountNumber = scanner.next();

        // Check if the account number already exists
        if (accounts.containsKey(newAccountNumber)) {
            System.out.println("Account number already exists. Please try again with a different number.");
            return;
        }

        System.out.println("Enter a PIN: ");
        String newPin = scanner.next();

        // Validate PIN length
        if (newPin.length() < 4) {
            System.out.println("PIN should be at least 4 characters long.");
            return;
        }

        System.out.println("Enter Initial Deposit: ");
        double initialDeposit = scanner.nextDouble();

        if (initialDeposit < 0) {
            System.out.println("Invalid deposit amount. The deposit must be positive.");
            return;
        }

        // Create a new account and add it to the HashMap
        Account newAccount = new Account(newAccountNumber, newPin, initialDeposit);
        accounts.put(newAccountNumber, newAccount);
        System.out.println("Account created successfully! You can now log in with your new credentials.");
    }
}

// Account class to store account details
class Account {
    private String accountNumber;
    private String pin;
    private double balance;

    public Account(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }
}
