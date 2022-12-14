public class User{
    private String firstName;
    private String lastName;
    private String userPhoneId;
    private int userPin;

    User(String fname, String lname, String id, int pin){
        this.firstName = fname;
        this.lastName = lname;
        this.userPhoneId = id;
        this.userPin = pin;
    }

    public String getUserFirstName(){
        return this.firstName;
    }

    public String getUserLastName(){
        return this.lastName;
    }

    public String getPhoneID(){
        return this.userPhoneId;
    }

    public int getPin(){
        return this.userPin;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.getUserFirstName() + " " + this.getUserLastName() + " " + this.getPhoneID();
    }
}