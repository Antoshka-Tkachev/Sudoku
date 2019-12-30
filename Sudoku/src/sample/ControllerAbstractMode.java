package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class ControllerAbstractMode {
    protected Logic logic;
    protected Stage stage;
    protected GameMode gameMode;
    protected Result result;

    protected Button[][] buttonsGameField = new Button[9][9];
    protected Button bufferBtnNumberForSelection;
    protected int indexRow;
    protected int indexColumn;
    protected boolean flagHelp = false;
    protected GameTimer timer;
    protected Image image;

    @FXML
    protected Line lineUp;

    @FXML
    protected Line lineDown;

    @FXML
    protected Line lineLeft;

    @FXML
    protected Line lineRight;

    @FXML
    protected GridPane gridMap;

    @FXML
    protected Label labelTimer;

    @FXML
    protected Label labelWarning;

    @FXML
    protected Button numberForSelection1;

    @FXML
    protected Button numberForSelection2;

    @FXML
    protected Button numberForSelection3;

    @FXML
    protected Button numberForSelection4;

    @FXML
    protected Button numberForSelection5;

    @FXML
    protected Button numberForSelection6;

    @FXML
    protected Button numberForSelection7;

    @FXML
    protected Button numberForSelection8;

    @FXML
    protected Button numberForSelection9;

    @FXML
    protected Button buttonDelete;

    @FXML
    protected Button buttonHelp;

    @FXML
    protected Button buttonStart;

    @FXML
    protected Button buttonPause;

    @FXML
    protected Button buttonRestart;

    @FXML
    protected Button buttonExit;

    abstract void backgroundUpdateHelp();
    abstract void gameOver();
    abstract void clickedHelp(ActionEvent actionEvent);
    abstract void clickedNumberForSelection(ActionEvent actionEvent);
    abstract void clickedDelete(ActionEvent actionEvent);
    abstract void clickedGameFieldForDelete(ActionEvent actionEvent);
    abstract void clickedPlay(ActionEvent actionEvent);
    abstract void clickedRestart(ActionEvent actionEvent);
    abstract void clickedGameField(ActionEvent actionEvent);

    protected void clickedGameFieldFinal(ActionEvent actionEvent) { }
    protected void checkFullnessNumberForSelection(int number) { }
    protected void setDisableNumberForSelection(int number, boolean flag) { }
    protected void onHighlightingSelectedNumbers() { }
    protected void offHighlightingSelectedNumbers() { }


    public ControllerAbstractMode(Stage stage, GameMode gameMode) {
        result = new Result();
        this.stage = stage;
        this.gameMode = gameMode;

        logic = new Logic();

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {

                buttonsGameField[row][column] = new Button("");
                buttonsGameField[row][column].setMinSize(50, 50);
                buttonsGameField[row][column].setStyle("-fx-font-weight:bold; -fx-font-size: 24; -fx-font-family: \"System\" ");

            }
        }

        fillingInTheField();
    }

    public ControllerAbstractMode(Stage stage, GameMode gameMode, Logic logic) {
        result = new Result();
        this.stage = stage;
        this.gameMode = gameMode;
        this.logic = logic;
        this.logic.restart();

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {

                buttonsGameField[row][column] = new Button("");
                buttonsGameField[row][column].setMinSize(50, 50);
                buttonsGameField[row][column].setStyle("-fx-font-weight:bold; -fx-font-size: 24; -fx-font-family: \"System\" ");
                if (logic.getGameFieldElement(row, column) != 0) {
                    buttonsGameField[row][column].setText(String.valueOf(logic.getGameFieldElement(row, column)));
                }
            }
        }

    }

    private void fillingInTheField() {
        logic.fillingInTheField(gameMode.getLevelOfDifficulty());
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (logic.getGameFieldElement(i,j) != 0) {
                    buttonsGameField[i][j].setText(String.valueOf(logic.getGameFieldElement(i, j)));
                }
            }
        }
    }

    protected void setVisibleScene(boolean flag) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                buttonsGameField[row][column].setDisable(flag);
            }
        }

        buttonHelp.setDisable(flag);
        buttonPause.setDisable(flag);
        buttonRestart.setDisable(flag);
        buttonExit.setDisable(flag);

        numberForSelection1.setDisable(flag);
        numberForSelection2.setDisable(flag);
        numberForSelection3.setDisable(flag);
        numberForSelection4.setDisable(flag);
        numberForSelection5.setDisable(flag);
        numberForSelection6.setDisable(flag);
        numberForSelection7.setDisable(flag);
        numberForSelection8.setDisable(flag);
        numberForSelection9.setDisable(flag);

        buttonDelete.setDisable(flag);

        buttonStart.setVisible(flag);
    }

    protected void searchButtonGameField(Button buffer) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(buffer == buttonsGameField[i][j]) {
                    indexRow = i;
                    indexColumn = j;
                    return;
                }
            }
        }
    }

    protected void resultGame() {

        setVisibleScene(true);
        buttonStart.setVisible(false);
        buttonExit.setDisable(false);

        result.setTimer(timer);

        Stage secondStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resultMenu.fxml"));
        loader.setController(new ControllerResultMenu(gameMode, result, secondStage, stage));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root != null;
        secondStage.setScene(new Scene(root));
        secondStage.show();
    }

    protected void clickedPause(ActionEvent actionEvent) {
        timer.stop();
        setVisibleScene(true);
        buttonStart.setText("");
        image = new Image("/resources/image/play.png", 250, 250, false, false);
        buttonStart.setGraphic(new ImageView((image)));
        buttonStart.setOnAction(this::clickedPlay);
    }

    protected void clickedExit(ActionEvent actionEvent) {
        timer.stop();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Вы точно хотите выйти в меню?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            timer.stop();

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

    }

    protected void clickedStart(ActionEvent actionEvent)  {
        setVisibleScene(false);
        timer = new GameTimer(labelTimer);
        timer.startTime();
    }

}
