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
    private Label labelInformation;

    @FXML
    private Label labelResult;

    @FXML
    private Label labelTime;

    @FXML
    private Button buttonNewGame;

    @FXML
    private Button buttonExit;

    public ControllerResultMenu(GameMode gameMode, Result result, Stage thisStage, Stage firstStage) {
        this.thisStage = thisStage;
        this.firstStage = firstStage;
        this.gameMode = gameMode;
        this.result = result;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (result.isWin()) {
            labelResult.setText("Победа!");
            labelResult.setAlignment(Pos.CENTER);
            labelInformation.setText("Вы правильно собрали судоку");
            labelInformation.setAlignment(Pos.CENTER);
        } else if (!gameMode.isSurvival()) {
            labelInformation.setText("Вы не правильно собрали судоку");
            labelInformation.setAlignment(Pos.CENTER);
        }

        labelTime.setText("Время: " + result.getTimer().outputInfo());

        buttonExit.setOnAction(this::clickedExit);
        buttonNewGame.setOnAction(this::clickedNewGame);
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
