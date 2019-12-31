package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ControllerProfile implements Initializable {
    private Stage stage;
    private GameMode gameMode;

    @FXML
    private Label labelLogin;

    @FXML
    private Label labelLevel;

    @FXML
    private Label labelCountGame;

    @FXML
    private Label labelCountWin;

    @FXML
    private Label labelCountLose;

    @FXML
    private Label labelWinLose;

    @FXML
    private Label labelPercentWin;

    @FXML
    private ProgressBar progressExp;

    @FXML
    private Label labelExp;

    @FXML
    private Button buttonOk;

    public ControllerProfile(Stage stage, GameMode gameMode) {
        this.stage = stage;
        this.gameMode = gameMode;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelLogin.setText("Никнейм: " + gameMode.getPlayer().getLogin());
        labelLevel.setText("Уровень: " + gameMode.getPlayer().getLevel());
        labelExp.setText("Опыт: " + gameMode.getPlayer().getExperience());
        progressExp.setProgress((Double.parseDouble(String.valueOf(gameMode.getPlayer().getExperience()))) / 100);

        setStatistic();

        buttonOk.setOnAction(this::clickedOk);
    }

    private void setStatistic() {
        labelCountGame.setText("Сыграно игр: " + (gameMode.getPlayer().getCountWin() + gameMode.getPlayer().getCountLose()));
        labelCountWin.setText("Кол-во побед: " + gameMode.getPlayer().getCountWin());
        labelCountLose.setText("Кол-во поражений: " + gameMode.getPlayer().getCountLose());

        if (gameMode.getPlayer().getCountWin() == 0 && gameMode.getPlayer().getCountLose() == 0) {
            labelWinLose.setText("Победы/поражения: Не определено");
            labelPercentWin.setText("Процент побед: Не определено");
        } else if (gameMode.getPlayer().getCountWin() == 0) {
            labelWinLose.setText("Победы/поражения: 0");
            labelPercentWin.setText("Процент побед: 0 %");
        } else  if (gameMode.getPlayer().getCountLose() == 0) {
            labelWinLose.setText("Победы/поражения: 1.00");
            labelPercentWin.setText("Процент побед: 100 %");
        } else {
            DecimalFormat format = new DecimalFormat("#0.00");
            double winLose = (Double.parseDouble(String.valueOf(gameMode.getPlayer().getCountWin())) / Double.parseDouble(String.valueOf(gameMode.getPlayer().getCountLose())));
            labelWinLose.setText("Победы/поражения: " + format.format(winLose));
            format = new DecimalFormat("#");
            double percentWin = (Double.parseDouble(String.valueOf(gameMode.getPlayer().getCountWin())) / Double.parseDouble(String.valueOf((gameMode.getPlayer().getCountWin() + gameMode.getPlayer().getCountLose()))))  * 100;
            labelPercentWin.setText("Процент побед: " + format.format(percentWin) + " %");
        }
    }

    private void clickedOk(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/startMenu.fxml"));
        loader.setController(new ControllerStartMenu(stage, gameMode));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }
}
