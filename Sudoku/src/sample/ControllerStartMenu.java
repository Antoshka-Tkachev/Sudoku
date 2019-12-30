package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerStartMenu implements Initializable{
    private Stage stage;
    private GameMode gameMode;

    @FXML
    private Label labelTitle;

    @FXML
    private Button buttonNewGame;

    @FXML
    private Button buttonGameMode;

    @FXML
    private Label labelBackground;

    @FXML
    private Label labelTitleAccount;

    @FXML
    private Label labelLogin;

    @FXML
    private Label labelPassword;

    @FXML
    private TextField textLogin;

    @FXML
    private PasswordField textPassword;

    @FXML
    private Button buttonSignIn;

    @FXML
    private Button buttonSignUp;

    public ControllerStartMenu (Stage stage)  {
        this.stage = stage;
        gameMode = new GameMode();
    }

    public ControllerStartMenu (Stage stage, GameMode gameMode)  {
        this.stage = stage;
        this.gameMode = new GameMode(gameMode);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonNewGame.setOnAction(this::clickedNewGame);
        buttonGameMode.setOnAction(this::clickedGameMode);
        buttonSignIn.setOnAction(this::clickedSignIn);
        buttonSignUp.setOnAction(this::clickedSignUp);
    }

    private void clickedSignIn(ActionEvent actionEvent) {

    }

    private void clickedSignUp(ActionEvent actionEvent) {

    }

    private void clickedNewGame(ActionEvent actionEvent) {
        FXMLLoader loader;
        if (gameMode.isSurvival()) {
            if (gameMode.isWithTips()) {
                loader = new FXMLLoader(getClass().getResource("modeSurvivalWithTips.fxml"));
                loader.setController(new ControllerModeSurvivalWithTips(stage, gameMode));
            } else {
                loader = new FXMLLoader(getClass().getResource("modeSurvival.fxml"));
                loader.setController(new ControllerModeSurvival(stage, gameMode));
            }
        } else {
            if (gameMode.isWithTips()) {
                loader = new FXMLLoader(getClass().getResource("modeWithTips.fxml"));
                loader.setController(new ControllerModeWithTips(stage, gameMode));
            } else {
                loader = new FXMLLoader(getClass().getResource("modeClassic.fxml"));
                loader.setController(new ControllerModeClassic(stage, gameMode));
            }
        }

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

    private void clickedGameMode(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameMode.fxml"));
        loader.setController(new ControllerGameMode(stage, gameMode));
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
