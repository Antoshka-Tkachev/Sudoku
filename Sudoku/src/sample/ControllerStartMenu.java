package sample;

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
import java.util.ResourceBundle;

public class ControllerStartMenu implements Initializable{
    private Stage stage;
    private GameMode gameMode;
    private Logic logic;

    @FXML
    private Label labelTitle;

    @FXML
    private Button buttonNewGame;

    @FXML
    private Button buttonGameMode;

    @FXML
    private Label labelBackgroundLogin;

    @FXML
    private Label labelTitleLogin;

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

    @FXML
    private Label labelBackgroundAccount;

    @FXML
    private ProgressBar progressExp;

    @FXML
    private Label labelNickname;

    @FXML
    private Button buttonProfile;

    @FXML
    private Button buttonExit;

    @FXML
    private Label labelMinExp;

    @FXML
    private Label labelMaxExp;

    @FXML
    private Label labelLevel;


    public ControllerStartMenu (Stage stage)  {
        this.stage = stage;
        gameMode = new GameMode();
        logic = new Logic();
    }

    public ControllerStartMenu (Stage stage, GameMode gameMode)  {
        this.stage = stage;
        this.gameMode = gameMode;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (gameMode.isSignIn()) {
            setVisibleAuthorization(false);
            setVisibleAccount(true);

            labelNickname.setText(gameMode.getPlayer().getLogin());
            labelLevel.setText("Уровень: " + gameMode.getPlayer().getLevel());
            progressExp.setProgress((Double.parseDouble(String.valueOf(gameMode.getPlayer().getExperience()))) / 100);
        } else {
            setVisibleAccount(false);
        }

        buttonNewGame.setOnAction(this::clickedNewGame);
        buttonGameMode.setOnAction(this::clickedGameMode);
        buttonSignIn.setOnAction(this::clickedSignIn);
        buttonSignUp.setOnAction(this::clickedSignUp);
        buttonExit.setOnAction(this::clickedExit);
        buttonProfile.setOnAction(this::clickedProfile);
    }

    private void setVisibleAuthorization(boolean flag) {
        textLogin.setVisible(flag);
        textPassword.setVisible(flag);
        labelLogin.setVisible(flag);
        labelPassword.setVisible(flag);
        buttonSignUp.setVisible(flag);
        buttonSignIn.setVisible(flag);
        labelTitleLogin.setVisible(flag);
        labelBackgroundLogin.setVisible(flag);
    }

    private void setVisibleAccount(boolean flag) {
        labelNickname.setVisible(flag);
        labelLevel.setVisible(flag);
        labelMaxExp.setVisible(flag);
        labelMinExp.setVisible(flag);
        progressExp.setVisible(flag);;
        buttonExit.setVisible(flag);
        buttonProfile.setVisible(flag);
        labelBackgroundAccount.setVisible(flag);
    }

    private void clickedSignIn(ActionEvent actionEvent) {

        try {
            DataBasePlayer dataBasePlayer = new DataBasePlayer();

            gameMode.setPlayer(dataBasePlayer.select(textLogin.getText(), textPassword.getText().hashCode()));

            if (gameMode.getPlayer() != null) {

                gameMode.setSignIn(true);
                setVisibleAuthorization(false);
                setVisibleAccount(true);

                labelNickname.setText(gameMode.getPlayer().getLogin());
                labelLevel.setText("Уровень: " + gameMode.getPlayer().getLevel());
                progressExp.setProgress((Double.parseDouble(String.valueOf(gameMode.getPlayer().getExperience()))) / 100);

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Вы неверно ввели логин или пароль");
                alert.showAndWait();
            }

        } catch (Exception e) {
            System.exit(1);
        }

    }

    private void clickedSignUp(ActionEvent actionEvent) {

        try {

            DataBasePlayer dataBasePlayer = new DataBasePlayer();

            if (dataBasePlayer.select(textLogin.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Данный никнейм занят");
                alert.showAndWait();
            } else {
                gameMode.setSignIn(true);
                gameMode.setPlayer(new Player(textLogin.getText(), textPassword.getText().hashCode()));

                dataBasePlayer.insert(textLogin.getText(), textPassword.getText().hashCode(), 0, 0, 0,0);

                setVisibleAuthorization(false);
                setVisibleAccount(true);

                labelNickname.setText(gameMode.getPlayer().getLogin());
                labelLevel.setText("Уровень: " + gameMode.getPlayer().getLevel());

            }

        } catch (Exception e) {
            System.exit(1);
        }

    }

    private void clickedExit(ActionEvent actionEvent) {
        gameMode.setSignIn(false);

        setVisibleAccount(false);
        setVisibleAuthorization(true);

        textLogin.setText("");
        textPassword.setText("");
        progressExp.setProgress(0);
    }

    private void clickedNewGame(ActionEvent actionEvent) {
        FXMLLoader loader;
        if (gameMode.isSurvival()) {
            if (gameMode.isWithTips()) {
                loader = new FXMLLoader(getClass().getResource("../fxml/modeSurvivalWithTips.fxml"));
                loader.setController(new ControllerModeSurvivalWithTips(stage, gameMode));
            } else {
                loader = new FXMLLoader(getClass().getResource("../fxml/modeSurvival.fxml"));
                loader.setController(new ControllerModeSurvival(stage, gameMode));
            }
        } else {
            if (gameMode.isWithTips()) {
                loader = new FXMLLoader(getClass().getResource("../fxml/modeWithTips.fxml"));
                loader.setController(new ControllerModeWithTips(stage, gameMode));
            } else {
                loader = new FXMLLoader(getClass().getResource("../fxml/modeClassic.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/gameMode.fxml"));
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

    private void clickedProfile(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/profile.fxml"));
        loader.setController(new ControllerProfile(stage, gameMode));
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
