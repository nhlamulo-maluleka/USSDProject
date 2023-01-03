import java.util.List;
import java.util.Scanner;

public abstract class Transaction {
    List<User> users;
    List<Account> accounts;
    Scanner input;

    Transaction(List<User> users, List<Account> accounts){
        this.users = users;
        this.accounts = accounts;
        this.input = new Scanner(System.in);
    }

    public boolean depositMoney() {
        System.out.print("How much money do you want to deposit: ");
        double depositAmount = input.nextDouble();

        Account myAccount = getMyAccount();
        myAccount.setAccountBalance(myAccount.getAccountBalance() + depositAmount);
        return true;
    }

    public boolean sendMoney() {
        System.out.print("Receipient: ");
        String uId = input.next();

        System.out.print("How much: ");
        double amount = input.nextDouble();

        Account recepieAccount = getUserAccount(uId);
        Account myAccount = getMyAccount();

        if (recepieAccount != null) {
            myAccount.setAccountBalance(myAccount.getAccountBalance() - amount);
            recepieAccount.setAccountBalance(recepieAccount.getAccountBalance() + amount);
            return true;
        } else {
            System.out.println("User account does not exist!!");
        }
        return false;
    }

    public boolean withdrawMoney() {
        System.out.print("How much money do you want to withdraw: ");
        double withdrawAmount = input.nextDouble();

        if (hasEnoughMoney(withdrawAmount)) {
            Account myAccount = getMyAccount();
            myAccount.setAccountBalance(myAccount.getAccountBalance() - withdrawAmount);
            return true;
        } else {
            System.out.println("You don't have sufficient money to make that withdrawal!!");
        }

        return false;
    }
}
