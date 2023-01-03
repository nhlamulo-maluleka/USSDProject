import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBModel {

  List<User> users;
  List<Account> accounts;

  DBModel(){
    this.users = new ArrayList<>();
    this.accounts = new ArrayList<>();
  }

  public List<User> loadUsers() {
    try {
      BufferedReader bf = new BufferedReader(
          new FileReader("./database/users.txt"));
      String line;

      while ((line = bf.readLine()) != null) {
        if (line.trim().length() > 0) {
          String[] userTable = line.split(",");
          this.users.add(
              new User(
                  userTable[0].trim(),
                  userTable[1].trim(),
                  userTable[2].trim(),
                  Integer.valueOf(userTable[3].trim())));
        }
      }

      bf.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return this.users;
  }

  public List<Account> loadAccounts() {
    try {
      BufferedReader bf = new BufferedReader(
          new FileReader("./database/accounts.txt"));
      String line;

      while ((line = bf.readLine()) != null) {
        if (line.trim().length() > 0) {
          String acc[] = line.split(",");
          accounts.add(
              new Account(getUser(acc[0].trim()), Double.valueOf(acc[1].trim())));
        }
      }

      bf.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return this.accounts;
  }

  public void updateAccountDatabase() {
    String records = "";

    for (Account acc : accounts)
      records += acc.getUser().getPhoneID() + "," + acc.getAccountBalance() + "\n";

    try {
      FileWriter update = new FileWriter("./database/accounts.txt");
      update.write(records);
      update.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public User getUser(String id) {
    for (User user : users) {
      if (user.getPhoneID().compareTo(id) == 0)
        return user;
    }
    return null;
  }
}
