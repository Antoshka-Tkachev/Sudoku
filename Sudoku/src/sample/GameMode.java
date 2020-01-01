package sample;

public class GameMode {
    private boolean survival;
    private boolean withTips;
    private int levelOfDifficulty;
    private Player player;
    private boolean signIn;

    public GameMode() {
        player = new Player();
        signIn = false;
        survival = false;
        withTips = false;
        levelOfDifficulty = 1;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isSignIn() {
        return signIn;
    }

    public void setSignIn(boolean signIn) {
        this.signIn = signIn;
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

}
