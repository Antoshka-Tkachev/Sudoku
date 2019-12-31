package sample;

public class Player {
    private String login;
    private String password;
    private int level;
    private int experience;

    public Player() { }

    public Player(String login, String password) {
        this.login = login;
        this.password = password;
        level = 0;
        experience = 0;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

}
