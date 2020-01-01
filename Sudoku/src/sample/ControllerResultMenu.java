package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerResultMenu implements Initializable {

    private Stage thisStage;
    private Stage firstStage;
    private GameMode gameMode;
    private Result result;

    @FXML
    private Label labelTitle;

    @FXML
    private Label labelInformation;

    @FXML
    private Button buttonNewGame;

    @FXML
    private Button buttonExit;

    @FXML
    private Label labelTime;

    @FXML
    private Label labelLevelOfDifficulty;

    @FXML
    private Label labelGameMode;

    @FXML
    private Label labelCountHelp;

    @FXML
    private Label labelResult;

    @FXML
    private Label labelLevel;

    @FXML
    private ProgressBar progressExp;

    @FXML
    private Label labelCountLive;

    public ControllerResultMenu(GameMode gameMode, Result result, Stage thisStage, Stage firstStage) {
        this.thisStage = thisStage;
        this.firstStage = firstStage;
        this.gameMode = gameMode;
        this.result = result;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        buttonExit.setOnAction(this::clickedExit);
        buttonNewGame.setOnAction(this::clickedNewGame);

        if (gameMode.isSignIn()) {
            if (result.isWin()) {
                addResultWin();
            } else {
                addResultLose();
            }
            updateDataBasePlayer();
        } else {
            if (result.isWin()) {
                addResultWinNoName();
            } else {
                addResultLoseNoName();
            }
        }

    }

    private void addResultLose() {
        gameMode.getPlayer().setCountLose(gameMode.getPlayer().getCountLose() + 1);

        if (!gameMode.isSurvival()) {
            labelInformation.setText("Вы не правильно собрали судоку");
            labelInformation.setAlignment(Pos.CENTER);
        }

        labelTime.setText("Время: " + result.getTimer().outputInfo() + "   +0 Exp");

        switch (gameMode.getLevelOfDifficulty()) {
            case 1:
                labelLevelOfDifficulty.setText("Уровень сложности: Новичок   +0 Exp");
                break;
            case 2:
                labelLevelOfDifficulty.setText("Уровень сложности: Любитель   +0 Exp");
                break;
            case 3:
                labelLevelOfDifficulty.setText("Уровень сложности: Профи   +0 Exp");
                break;
            default:
                break;
        }

        if (gameMode.isSurvival()) {
            if (gameMode.isWithTips()) {
                labelGameMode.setText("Режим игры: Выживание с подсказками   +0 Exp");
            } else {
                labelGameMode.setText("Режим игры: Выживание   +0 Exp");
            }
        } else {
            if (gameMode.isWithTips()) {
                labelGameMode.setText("Режим игры: C подсказками   +0 Exp");
            } else {
                labelGameMode.setText("Режим игры: Классический   +0 Exp");
            }
        }

        labelCountHelp.setText("Кол-во использованных подсказок: " + result.getCountHelp() + "   +0 Exp");
        labelCountLive.setText("Кол-во оставшихся жизней: " + result.getCountLives() + "   +0 Exp");

        labelResult.setText("Итого: +0 Exp");

        labelLevel.setText("Уровень: " + gameMode.getPlayer().getLevel());
        progressExp.setProgress((Double.parseDouble(String.valueOf(gameMode.getPlayer().getExperience()))) / 100);
    }

    private void addResultWinNoName() {
        gameMode.getPlayer().setCountWin(gameMode.getPlayer().getCountWin() + 1);

        labelTitle.setText("Победа!");
        labelTitle.setStyle("-fx-text-fill: #01922d; -fx-font-weight:bold");
        labelTitle.setAlignment(Pos.CENTER);
        labelInformation.setText("Вы правильно собрали судоку");
        labelInformation.setStyle("-fx-text-fill: #01922d; -fx-font-weight:bold");
        labelInformation.setAlignment(Pos.CENTER);

        labelTime.setText("Время: " + result.getTimer().outputInfo());

        switch (gameMode.getLevelOfDifficulty()) {
            case 1:
                labelLevelOfDifficulty.setText("Уровень сложности: Новичок");
                break;
            case 2:
                labelLevelOfDifficulty.setText("Уровень сложности: Любитель");
                break;
            case 3:
                labelLevelOfDifficulty.setText("Уровень сложности: Профи");
                break;
            default:
                break;
        }


        if (gameMode.isSurvival()) {
            if (gameMode.isWithTips()) {
                labelGameMode.setText("Режим игры: Выживание с подсказками");
            } else {
                labelGameMode.setText("Режим игры: Выживание");
            }
        } else {
            if (gameMode.isWithTips()) {
                labelGameMode.setText("Режим игры: C подсказками");
            } else {
                labelGameMode.setText("Режим игры: Классический");
            }
        }


        if (result.getCountHelp() != 0) {
            labelCountHelp.setText("Кол-во использованных подсказок: " + result.getCountHelp());
        } else {
            labelCountHelp.setText("Кол-во использованных подсказок: " + result.getCountHelp());
        }

        labelCountLive.setText("Кол-во оставшихся жизней: " + result.getCountLives());
        labelResult.setText("Вы не поучаете опыт, авторизуйтесь");
        labelLevel.setText("Уровень: Не определено");
        progressExp.setProgress(0);

    }

    private void addResultLoseNoName() {
        gameMode.getPlayer().setCountLose(gameMode.getPlayer().getCountLose() + 1);

        if (!gameMode.isSurvival()) {
            labelInformation.setText("Вы не правильно собрали судоку");
            labelInformation.setAlignment(Pos.CENTER);
        }

        labelTime.setText("Время: " + result.getTimer().outputInfo() );

        switch (gameMode.getLevelOfDifficulty()) {
            case 1:
                labelLevelOfDifficulty.setText("Уровень сложности: Новичок");
                break;
            case 2:
                labelLevelOfDifficulty.setText("Уровень сложности: Любитель");
                break;
            case 3:
                labelLevelOfDifficulty.setText("Уровень сложности: Профи");
                break;
            default:
                break;
        }

        if (gameMode.isSurvival()) {
            if (gameMode.isWithTips()) {
                labelGameMode.setText("Режим игры: Выживание с подсказками");
            } else {
                labelGameMode.setText("Режим игры: Выживание");
            }
        } else {
            if (gameMode.isWithTips()) {
                labelGameMode.setText("Режим игры: C подсказками");
            } else {
                labelGameMode.setText("Режим игры: Классический");
            }
        }

        labelCountHelp.setText("Кол-во использованных подсказок: " + result.getCountHelp());
        labelCountLive.setText("Кол-во оставшихся жизней: " + result.getCountLives());
        labelResult.setText("Вы не поучаете опыт, авторизуйтесь");
        labelLevel.setText("Уровень: Не определено");
        progressExp.setProgress(0);
    }

    private void addResultWin() {
        gameMode.getPlayer().setCountWin(gameMode.getPlayer().getCountWin() + 1);

        int sumExp = 0;
        int exp = 0;

        labelTitle.setText("Победа!");
        labelTitle.setStyle("-fx-text-fill: #01922d; -fx-font-weight:bold");
        labelTitle.setAlignment(Pos.CENTER);
        labelInformation.setText("Вы правильно собрали судоку");
        labelInformation.setStyle("-fx-text-fill: #01922d; -fx-font-weight:bold");
        labelInformation.setAlignment(Pos.CENTER);

        if (result.getTimer().getTimerHour() < 1 && result.getTimer().getTimerMin() < 15) {
            exp = 3;
            if (result.getTimer().getTimerMin() < 10) {
                exp = 6;
            }
            if (result.getTimer().getTimerMin() < 5) {
                exp = 10;
            }
        } else {
            exp = 0;
        }
        sumExp += exp;

        labelTime.setText("Время: " + result.getTimer().outputInfo() + "   +" + exp + " Exp");

        switch (gameMode.getLevelOfDifficulty()) {
            case 1:
                exp = 5;
                labelLevelOfDifficulty.setText("Уровень сложности: Новичок   +" + exp + " Exp");
                break;
            case 2:
                exp = 10;
                labelLevelOfDifficulty.setText("Уровень сложности: Любитель   +" + exp + " Exp");
                break;
            case 3:
                exp = 15;
                labelLevelOfDifficulty.setText("Уровень сложности: Профи   +" + exp + " Exp");
                break;
            default:
                break;
        }

        sumExp += exp;

        if (gameMode.isSurvival()) {
            if (gameMode.isWithTips()) {
                exp = 2;
                labelGameMode.setText("Режим игры: Выживание с подсказками   +" + exp + " Exp");
            } else {
                exp = 3;
                labelGameMode.setText("Режим игры: Выживание   +" + exp + " Exp");
            }
        } else {
            if (gameMode.isWithTips()) {
                exp = 0;
                labelGameMode.setText("Режим игры: C подсказками   +" + exp + " Exp");
            } else {
                exp = 1;
                labelGameMode.setText("Режим игры: Классический   +" + exp + " Exp");
            }
        }

        sumExp += exp;

        exp = result.getCountHelp() * 5;
        if (result.getCountHelp() != 0) {
            labelCountHelp.setText("Кол-во использованных подсказок: " + result.getCountHelp() + "   -" + exp + " Exp");
        } else {
            labelCountHelp.setText("Кол-во использованных подсказок: " + result.getCountHelp() + "   +0 Exp");
        }
        sumExp -= exp;

        exp = result.getCountLives() * 2;
        labelCountLive.setText("Кол-во оставшихся жизней: " + result.getCountLives() + "   +" + exp + " Exp");
        sumExp += exp;

        if (sumExp > 0) {
            labelResult.setText("Итого: +" + sumExp + " Exp");
            int countLvl = 0;

            sumExp += gameMode.getPlayer().getExperience();

            labelLevel.setText("Уровень: " + gameMode.getPlayer().getLevel());
            progressExp.setProgress((Double.parseDouble(String.valueOf(sumExp))) / 100);
            gameMode.getPlayer().setExperience(sumExp);

            while (sumExp  >= 100) {
                countLvl++;
                gameMode.getPlayer().setLevel(gameMode.getPlayer().getLevel() + 1);
                labelLevel.setText("Уровень: " + gameMode.getPlayer().getLevel() + " +" + countLvl);
                sumExp -= 100;

                gameMode.getPlayer().setExperience(sumExp);
                progressExp.setProgress((Double.parseDouble(String.valueOf(sumExp))) / 100);
                progressExp.setStyle("-fx-accent: green");
            }

        } else {
            labelResult.setText("Итого: +0 Exp");
            labelLevel.setText("Уровень: " + gameMode.getPlayer().getLevel());
            progressExp.setProgress((Double.parseDouble(String.valueOf(gameMode.getPlayer().getExperience()))) / 100);
        }
    }

    private void updateDataBasePlayer() {
        DataBasePlayer dataBasePlayer = new DataBasePlayer();
        try {
            dataBasePlayer.update(
                    gameMode.getPlayer().getLogin(),
                    gameMode.getPlayer().getLevel(),
                    gameMode.getPlayer().getExperience(),
                    gameMode.getPlayer().getCountWin(),
                    gameMode.getPlayer().getCountLose());
        } catch (Exception e) {
            System.exit(1);
        }
    }

    private void clickedExit(ActionEvent actionEvent) {
        thisStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/startMenu.fxml"));
        loader.setController(new ControllerStartMenu(firstStage, gameMode));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        firstStage.setScene(new Scene(root));
        firstStage.show();
    }

    private void clickedNewGame(ActionEvent actionEvent) {
        thisStage.close();

        FXMLLoader loader;
        if (gameMode.isSurvival()) {
            if (gameMode.isWithTips()) {
                loader = new FXMLLoader(getClass().getResource("../fxml/modeSurvivalWithTips.fxml"));
                loader.setController(new ControllerModeSurvivalWithTips(firstStage, gameMode));
            } else {
                loader = new FXMLLoader(getClass().getResource("../fxml/modeSurvival.fxml"));
                loader.setController(new ControllerModeSurvival(firstStage, gameMode));
            }
        } else {
            if (gameMode.isWithTips()) {
                loader = new FXMLLoader(getClass().getResource("../fxml/modeWithTips.fxml"));
                loader.setController(new ControllerModeWithTips(firstStage, gameMode));
            } else {
                loader = new FXMLLoader(getClass().getResource("../fxml/modeClassic.fxml"));
                loader.setController(new ControllerModeClassic(firstStage, gameMode));
            }
        }

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        firstStage.setScene(new Scene(root));
        firstStage.show();
    }

}
