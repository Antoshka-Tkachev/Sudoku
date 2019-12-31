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
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;

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
        this.gameMode = new GameMode(gameMode);

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
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:./src/dataBase/Player.db");
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM Player WHERE login='" + textLogin.getText() + "' AND " +
                    "password='" + textPassword.getText() + "'";

            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                gameMode.setSignIn(true);
                gameMode.getPlayer().setLogin(resultSet.getString("login"));
                gameMode.getPlayer().setPassword(resultSet.getString("password"));
                gameMode.getPlayer().setLevel(resultSet.getInt("level"));
                gameMode.getPlayer().setExperience(resultSet.getInt("exp"));

                labelNickname.setText(resultSet.getString("login"));
                labelLevel.setText("Уровень: " + resultSet.getInt("level"));
                progressExp.setProgress((Double.parseDouble(String.valueOf(resultSet.getInt("exp")))) / 100);

               setVisibleAuthorization(false);
               setVisibleAccount(true);

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Вы неверно ввели логин или пароль");
                alert.showAndWait();
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void clickedSignUp(ActionEvent actionEvent) {

        try{
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:./src/dataBase/Player.db");
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM Player WHERE login='" + textLogin.getText() + "'";

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Данный никнейм занят");
                alert.showAndWait();
            } else {
                gameMode.setPlayer(new Player(textLogin.getText(), textPassword.getText()));
                gameMode.setSignIn(true);

                query = "INSERT INTO Player (login, password, level, exp) " +
                                "VALUES ('" +
                                gameMode.getPlayer().getLogin() + "', '" +
                                gameMode.getPlayer().getPassword() + "', '" +
                                gameMode.getPlayer().getLevel() + "', '" +
                                gameMode.getPlayer().getExperience() + "')";

                statement.executeUpdate(query);

                setVisibleAuthorization(false);
                setVisibleAccount(true);

                labelNickname.setText(gameMode.getPlayer().getLogin());
                labelLevel.setText("Уровень: " + gameMode.getPlayer().getLevel());

            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        System.out.println(gameMode.getPlayer().getLogin());
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
