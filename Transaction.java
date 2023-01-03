import java.util.List;
import java.util.Scanner;

public abstract class Transaction {
    List<User> users;
    List<Account> accounts;
    DBModel db = new DBModel();
    Scanner input;

    Transaction() {
        this.users = this.db.loadUsers();
        this.accounts = this.db.loadAccounts();
        this.input = new Scanner(System.in);
    }

    public boolean depositMoney(User session) {
        System.out.print("How much money do you want to deposit: ");
        double depositAmount = input.nextDouble();

        Account myAccount = getMyAccount(session);
        myAccount.setAccountBalance(myAccount.getAccountBalance() + depositAmount);
        return true;
    }

    public boolean sendMoney(User session) {
        System.out.print("Receipient: ");
        String uId = input.next();

        System.out.print("How much: ");
        double amount = input.nextDouble();

        Account recepieAccount = getUserAccount(uId);
        Account myAccount = getMyAccount(session);

        if (recepieAccount != null) {
            myAccount.setAccountBalance(myAccount.getAccountBalance() - amount);
            recepieAccount.setAccountBalance(recepieAccount.getAccountBalance() + amount);
            return true;
        } else {
            System.out.println("User account does not exist!!");
        }
        return false;
    }

    public boolean withdrawMoney(User session) {
        System.out.print("How much money do you want to withdraw: ");
        double withdrawAmount = input.nextDouble();

        if (hasEnoughMoney(withdrawAmount, session)) {
            Account myAccount = getMyAccount(session);
            myAccount.setAccountBalance(myAccount.getAccountBalance() - withdrawAmount);
            return true;
        } else {
            System.out.println("You don't have sufficient money to make that withdrawal!!");
        }

        return false;
    }

    public boolean hasEnoughMoney(double amount, User session) {
        return checkBalance(session) >= amount;
    }

    public double checkBalance(User session) {
        return getMyAccount(session).getAccountBalance();
    }

    public Account getMyAccount(User session) {
        for (Account acc : accounts) {
            if (acc.getUser().getPhoneID().compareTo(session.getPhoneID()) == 0) {
                return acc;
            }
        }
        return null;
    }

    public Account getUserAccount(String id) {
        for (Account acc : accounts) {
            if (acc.getUser().getPhoneID().compareTo(id) == 0) {
                return acc;
            }
        }
        return null;
    }

    public void updateDB(){
        this.db.updateAccountDatabase();
    }
}
