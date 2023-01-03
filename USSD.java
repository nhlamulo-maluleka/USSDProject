import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class USSD extends Transaction implements IUSSD{
    List<User> users = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();;
    Scanner input;
    User session;

    USSD() {
        super(loadUsers(), loadAccounts());
        input = new Scanner(System.in);
    }

    @Override
    public User login(String username, int pin) {
        for (User usr : users) {
            if (usr.getPhoneID().compareTo(username) == 0 && usr.getPin() == pin) {
                session = usr;
                return usr;
            }
        }
        return null;
    }

    @Override
    public double checkBalance() {
        return getMyAccount().getAccountBalance();
    }

    @Override
    public void exit() {
        session = null;
        System.out.println("Goodbye...");
    }

    @Override
    public User getUser(String id) {
        for (User user : users) {
            if (user.getPhoneID().compareTo(id) == 0) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void startApplication(String ussdcode) {
        this.loadUsers();
        this.loadAccounts();

        if (ussdcode.compareTo("*130*321#") == 0) {
            System.out.print("Enter your phone number: ");
            String numberId = input.nextLine();
            System.out.print("Enter your pin: ");
            int pin = input.nextInt();

            if (login(numberId, pin) != null) {
                System.out.println("\nLogin success...");
                startFunctionality();
            } else {
                System.out.println("\nInvalid login credentials provided!!");
            }
        } else {
            System.out.println("Invalid USSD Code...");
        }
    }

    @Override
    public void showMenu() {
        System.out.println("\nBalance: R" + checkBalance());
        if (checkBalance() > 50) {
            System.out.println("1. Send Money");
            System.out.println("2. Withdraw Money");
        }
        System.out.println("3. Deposit Money");
        System.out.println("999. Exit\n");
    }

    @Override
    public void startFunctionality() {
        while (session != null) {
            showMenu();
            int action = input.nextInt();

            switch (action) {
                case 1:
                    // SendMoney
                    if (sendMoney()) {
                        System.out.println("Transaction successful...");
                        updateAccountDatabase();
                    } else {
                        System.out.println("Transaction unsuccessful...");
                    }
                    break;
                case 2:
                    // Withdraw Money
                    if (withdrawMoney()) {
                        System.out.println("Transaction successful...");
                        updateAccountDatabase();
                    } else {
                        System.out.println("Transaction unsuccessful...");
                    }
                    break;
                case 3:
                    // Deposit Money
                    if (depositMoney()) {
                        System.out.println("Transaction successful...");
                        updateAccountDatabase();
                    } else {
                        System.out.println("Transaction unsuccessful...");
                    }
                    break;
                case 999:
                    exit();
            }
        }
    }

    @Override
    public boolean hasEnoughMoney(double amount) {
        return checkBalance() >= amount;
    }

    @Override
    public Account getMyAccount() {
        for (Account acc : accounts) {
            if (acc.getUser().getPhoneID().compareTo(session.getPhoneID()) == 0) {
                return acc;
            }
        }
        return null;
    }

    @Override
    public void updateAccountDatabase() {
        String records = "";

        for (Account acc : accounts) {
            records += acc.getUser().getPhoneID() + "," + acc.getAccountBalance() + "\n";
        }

        try {
            FileWriter update = new FileWriter("./database/accounts.txt");
            update.write(records);
            update.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Account getUserAccount(String id) {
        for (Account acc : accounts) {
            if (acc.getUser().getPhoneID().compareTo(id) == 0) {
                return acc;
            }
        }
        return null;
    }
}
