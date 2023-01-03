public interface IUSSD{
    public User login(String username, int pin);
    public void exit();
    public void initApp();
    public void showMenu();
    public void startApp();
}
