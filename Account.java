public class Account {
    private User accountUser;
    private double accountBalance;

    Account(User auser, double accBalance){
        this.accountUser = auser;
        this.accountBalance = accBalance;
    }

    public User getUser(){
        return this.accountUser;
    }

    public double getAccountBalance(){
        return this.accountBalance;
    }

    public void setAccountBalance(double amount){
        this.accountBalance = amount;
    }
}
