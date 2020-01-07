package sample;

public class Player {
    private String login;
    private int password;
    private int level;
    private int experience;
    private int countWin;
    private int countLose;

    public Player() { }

    public Player(String login, int password) {
        this.login = login;
        this.password = password;
        level = 0;
        experience = 0;
        countWin = 0;
        countLose = 0;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
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

    public int getCountWin() {
        return countWin;
    }

    public void setCountWin(int countWin) {
        this.countWin = countWin;
    }

    public int getCountLose() {
        return countLose;
    }

    public void setCountLose(int countLose) {
        this.countLose = countLose;
    }
}
