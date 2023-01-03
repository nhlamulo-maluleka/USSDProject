import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DBModel {

  List<User> users;
  List<Account> accounts;

  public List<User> loadUsers() {
    try {
      BufferedReader bf = new BufferedReader(
        new FileReader("./database/users.txt")
      );
      String line;

      while ((line = bf.readLine()) != null) {
        if (line.trim().length() > 0) {
          String[] userTable = line.split(",");
          users.add(
            new User(
              userTable[0].trim(),
              userTable[1].trim(),
              userTable[2].trim(),
              Integer.valueOf(userTable[3].trim())
            )
          );
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
        new FileReader("./database/accounts.txt")
      );
      String line;

      while ((line = bf.readLine()) != null) {
        if (line.trim().length() > 0) {
          String acc[] = line.split(",");
          accounts.add(
            new Account(getUser(acc[0].trim()), Double.valueOf(acc[1].trim()))
          );
        }
      }

      bf.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return this.accounts;
  }
}
