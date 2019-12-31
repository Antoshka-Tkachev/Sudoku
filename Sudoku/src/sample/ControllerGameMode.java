package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ControllerGameMode implements Initializable {

    private Stage stage;
    private GameMode gameMode;
    private ObservableList<String> levelOfDifficulty;

    @FXML
    private TextField textBet;

    @FXML
    private TextField textTimeToGame;

    @FXML
    private Button buttonMakeABet;

    @FXML
    private CheckBox checkWithBet;

    @FXML
    private Button buttonHelp;

    @FXML
    private ToggleButton buttonSurvival;

    @FXML
    private ToggleButton buttonWithTips;

    @FXML
    private Button buttonApply;

    @FXML
    private ComboBox<String> comboLevelOfDifficulty;


    public ControllerGameMode(Stage stage, GameMode gameMode) {
        this.stage = stage;
        this.gameMode = new GameMode(gameMode);
        //this.gameMode = gameMode;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonApply.setOnAction(this::clickedApply);
        buttonSurvival.setOnAction(this::clickedSurvival);
        buttonWithTips.setOnAction(this::clickedWithTips);
        comboLevelOfDifficulty.getItems().add("Новичок");
        comboLevelOfDifficulty.getItems().add("Любитель");
        comboLevelOfDifficulty.getItems().add("Профи");
        comboLevelOfDifficulty.setOnAction(this::clickedLevelOfDifficulty);

        startGUI();
    }

    private void clickedApply(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("startMenu.fxml"));
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

    private void clickedSurvival(ActionEvent actionEvent) {
        if (buttonSurvival.isSelected()) {
            buttonSurvival.setText("Да");
            gameMode.setSurvival(true);
        } else {
            buttonSurvival.setText("Нет");
            gameMode.setSurvival(false);
        }
    }

    private void clickedWithTips(ActionEvent actionEvent) {
        if (buttonWithTips.isSelected()) {
            buttonWithTips.setText("Да");
            gameMode.setWithTips(true);
        } else {
            buttonWithTips.setText("Нет");
            gameMode.setWithTips(false);
        }
    }

    private void clickedLevelOfDifficulty(ActionEvent actionEvent) {
        switch (comboLevelOfDifficulty.getValue()) {
            case "Новичок":
                gameMode.setLevelOfDifficulty(1);
                break;
            case "Любитель":
                gameMode.setLevelOfDifficulty(2);
                break;
            case "Профи":
                gameMode.setLevelOfDifficulty(3);
                break;
            default:
                break;
        }
    }

    private void startGUI() {
        if (gameMode.isSurvival()) {
            buttonSurvival.setText("Да");
            buttonSurvival.setSelected(true);
        }

        if (gameMode.isWithTips()) {
            buttonWithTips.setText("Да");
            buttonWithTips.setSelected(true);
        }

        switch (gameMode.getLevelOfDifficulty()) {
            case 1:
                comboLevelOfDifficulty.setValue("Новичок");
                break;
            case 2:
                comboLevelOfDifficulty.setValue("Любитель");
                break;
            case 3:
                comboLevelOfDifficulty.setValue("Профи");
                break;
            default:
                break;
        }
    }

}
