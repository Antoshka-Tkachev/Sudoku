package sample;

public class GameMode {
    private boolean survival;
    private boolean withTips;
    private int levelOfDifficulty;
    private boolean withBet;
    private int bet;
    private String timeToGame;



    public GameMode() {
        survival = false;
        withTips = false;
        withBet = false;
        levelOfDifficulty = 1;
    }

    public GameMode(GameMode other) {
        this.survival = other.survival;
        this.withTips = other.withTips;
        this.withBet = other.withBet;
        this.levelOfDifficulty = other.levelOfDifficulty;

        if (withBet) {
            this.bet = other.bet;
            this.timeToGame = other.timeToGame;
        }

    }

    public boolean isSurvival() {
        return survival;
    }

    public void setSurvival(boolean survival) {
        this.survival = survival;
    }

    public boolean isWithTips() {
        return withTips;
    }

    public void setWithTips(boolean withTips) {
        this.withTips = withTips;
    }

    public int getLevelOfDifficulty() {
        return levelOfDifficulty;
    }

    public void setLevelOfDifficulty(int levelOfDifficulty) {
        this.levelOfDifficulty = levelOfDifficulty;
    }

    public boolean isWithBet() {
        return withBet;
    }

    public void setWithBet(boolean withBet) {
        this.withBet = withBet;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public String getTimeToGame() {
        return timeToGame;
    }

    public void setTimeToGame(String timeToGame) {
        this.timeToGame = timeToGame;
    }

    @Override
    public String toString() {
        return "GameMode{" +
                "survival=" + survival +
                ", withTips=" + withTips +
                ", levelOfDifficulty=" + levelOfDifficulty +
                ", withBet=" + withBet +
                ", bet=" + bet +
                ", timeToGame='" + timeToGame + '\'' +
                '}';
    }
}
