package sample;

public class Result {
    private boolean win;
    private boolean livesEnded;
    private int countLives;
    private int countHelp;
    private GameTimer timer;

    public Result() {
        this.countLives = 3;
        this.countHelp = 0;
    }

    public void setTimer(GameTimer timer) {
        this.timer = timer;
    }

    public GameTimer getTimer() {
        return timer;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isLivesEnded() {
        return livesEnded;
    }

    public void setLivesEnded(boolean livesEnded) {
        this.livesEnded = livesEnded;
    }

    public int getCountLives() {
        return countLives;
    }

    public void setCountLives(int countLives) {
        this.countLives = countLives;
    }

    public int getCountHelp() {
        return countHelp;
    }

    public void setCountHelp(int countHelp) {
        this.countHelp = countHelp;
    }
}
