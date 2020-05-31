package project;


public class User {
    public String FIO;
    public String login;
    public String password;

    public User(String FIO, String login, String password) {
        this.FIO = FIO;
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return FIO;
    }
}
