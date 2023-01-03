import java.util.List;

public interface IUSSD{
    public List<User> loadUsers();
    public List<Account> loadAccounts();
    public User login(String username, int pin);
    public double checkBalance();
    public void exit();
    public User getUser(String id);
    public Account getMyAccount();
    public Account getUserAccount(String id);
    public void startApplication(String ussdcode);
    public void showMenu();
    public void startFunctionality();
    public boolean hasEnoughMoney(double amount);
    public void updateAccountDatabase();
}
