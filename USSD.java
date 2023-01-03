import java.util.*;

public class USSD extends Transaction implements IUSSD {
    private Scanner input;
    private User session;
    private int attempts = 3;

    USSD() {
        super();
        input = new Scanner(System.in);
    }

    @Override
    public User login(String username, int pin) {
        for (User usr : users) {
            if (usr.getPhoneID().compareTo(username) == 0 && usr.getPin() == pin) {
                return usr;
            }
        }
        return null;
    }

    @Override
    public void exit() {
        session = null;
        System.out.println("Goodbye...");
    }

    @Override
    public void showMenu() {
        System.out.println("\nBalance: R" + checkBalance(session));
        if (checkBalance(session) > 50) {
            System.out.println("1. Send Money");
            System.out.println("2. Withdraw Money");
        }
        System.out.println("3. Deposit Money");
        System.out.println("999. Exit\n");
    }

    @Override
    public void initApp() {
        while (true) {
            if(attempts == 0){ 
                System.out.println("You've used-up all your attempts... \nTry again later!");
                break;
            }

            System.out.print("[Attempt "+(attempts)+"/3] Enter USSD Code: ");
            String ussdcode = input.next();

            if (ussdcode.compareTo("*130*321#") == 0) {
                System.out.print("Enter your phone number: ");
                String numberId = input.next();
                System.out.print("Enter your pin: ");
                int pin = input.nextInt();

                if ((session = login(numberId, pin)) != null) {
                    System.out.println("\nLogin success...");
                    startApp();
                    // Terminate the app
                    break;
                } else {
                    System.out.println("\nInvalid login credentials provided!!");
                }
            } else {
                attempts -= 1;
            }
        }
    }

    @Override
    public void startApp() {
        while (session != null) {
            showMenu();
            int action = input.nextInt();

            switch (action) {
                case 1:
                    // SendMoney
                    if (sendMoney(session)) {
                        System.out.println("Transaction successful...");
                    } else {
                        System.out.println("Transaction unsuccessful...");
                    }
                    break;
                case 2:
                    // Withdraw Money
                    if (withdrawMoney(session)) {
                        System.out.println("Transaction successful...");
                    } else {
                        System.out.println("Transaction unsuccessful...");
                    }
                    break;
                case 3:
                    // Deposit Money
                    if (depositMoney(session)) {
                        System.out.println("Transaction successful...");
                    } else {
                        System.out.println("Transaction unsuccessful...");
                    }
                    break;
                case 999:
                    exit();
                default:
                    continue;
            }

            // Updating Account Records
            this.updateDB();
        }
    }
}
