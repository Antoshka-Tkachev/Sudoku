package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerModeClassic extends ControllerAbstractMode implements Initializable {

    public ControllerModeClassic(Stage stage, GameMode gameMode) {
        super(stage, gameMode);
    }

    public ControllerModeClassic(Stage stage, GameMode gameMode, Logic logic) {
        super(stage, gameMode, logic);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bufferBtnNumberForSelection = numberForSelection1;
        numberForSelection1.setStyle("-fx-background-color: green");

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                gridMap.add(buttonsGameField[row][column], row, column);
                buttonsGameField[row][column].setOnAction(this::clickedGameField);

            }
        }

        numberForSelection1.setOnAction(this::clickedNumberForSelection);
        numberForSelection2.setOnAction(this::clickedNumberForSelection);
        numberForSelection3.setOnAction(this::clickedNumberForSelection);
        numberForSelection4.setOnAction(this::clickedNumberForSelection);
        numberForSelection5.setOnAction(this::clickedNumberForSelection);
        numberForSelection6.setOnAction(this::clickedNumberForSelection);
        numberForSelection7.setOnAction(this::clickedNumberForSelection);
        numberForSelection8.setOnAction(this::clickedNumberForSelection);
        numberForSelection9.setOnAction(this::clickedNumberForSelection);
        buttonDelete.setOnAction(this::clickedDelete);

        buttonHelp.setOnAction(this::clickedHelp);
        buttonStart.setOnAction(this::clickedStart);
        buttonPause.setOnAction(this::clickedPause);
        buttonExit.setOnAction(this::clickedExit);
        buttonRestart.setOnAction(this::clickedRestart);

        image = new Image("/resources/image/buttonHelp.jpg", 50,50,false, false);
        buttonHelp.setGraphic(new ImageView((image)));

        image = new Image("/resources/image/pause.jpg", 50,50,false, false);
        buttonPause.setGraphic(new ImageView((image)));

        image = new Image("/resources/image/restart.jpg", 50,50,false, false);
        buttonRestart.setGraphic(new ImageView((image)));

        image = new Image("/resources/image/exit.jpg", 50,50,false, false);
        buttonExit.setGraphic(new ImageView((image)));

        setVisibleScene(true);

        setAccount();
    }

    @Override
    protected void backgroundUpdateHelp() {
        if (flagHelp) {
            buttonsGameField[indexRow][indexColumn].setStyle("-fx-text-fill: #4608ba; -fx-font-weight:bold; -fx-font-size: 24; -fx-font-family: \"System\"");
            flagHelp = false;
        }
    }

    @Override
    protected void gameOver() {
        if (logic.checkGameFieldsIsFilled()) {
            timer.stop();
            if (logic.checkWin()) {
                result.setWin(true);
            } else {
                result.setWin(false);
            }
            result.setLivesEnded(false);
            resultGame();
        }

    }

    @Override
    protected void clickedHelp(ActionEvent actionEvent) {
        labelWarning.setText("");

        backgroundUpdateHelp();

        int result = logic.clickedButtonHelp();
        indexRow = result / 10;
        indexColumn = result % 10;

        buttonsGameField[indexRow][indexColumn].setText(String.valueOf(logic.getGameFieldElement(indexRow, indexColumn)));
        buttonsGameField[indexRow][indexColumn].setStyle("-fx-text-fill: #4608ba; -fx-background-color: yellow; -fx-font-weight:bold; -fx-font-size: 24; -fx-font-family: \"System\"");
        flagHelp = true;

        gameOver();
    }

    @Override
    protected void clickedNumberForSelection(ActionEvent actionEvent) {
        backgroundUpdateHelp();
        if (logic.getSelectedNumber() == 0) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    buttonsGameField[i][j].setOnAction(this::clickedGameField);
                }
            }
        }

        labelWarning.setText("");

        bufferBtnNumberForSelection.setStyle(null);

        bufferBtnNumberForSelection = (Button) actionEvent.getSource();
        bufferBtnNumberForSelection.setStyle("-fx-background-color: green");
        logic.setSelectedNumber(Integer.parseInt(bufferBtnNumberForSelection.getText()));

    }

    @Override
    protected void clickedDelete(ActionEvent actionEvent) {
        backgroundUpdateHelp();
        labelWarning.setText("");

        bufferBtnNumberForSelection.setStyle(null);
        bufferBtnNumberForSelection = (Button) actionEvent.getSource();
        bufferBtnNumberForSelection.setStyle("-fx-background-color: green");
        logic.setSelectedNumber(0);;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                buttonsGameField[i][j].setOnAction(this::clickedGameFieldForDelete);
            }
        }
    }

    @Override
    protected void clickedGameFieldForDelete(ActionEvent actionEvent) {
        backgroundUpdateHelp();

        Button bufferButtonGameField = (Button) actionEvent.getSource();
        searchButtonGameField(bufferButtonGameField);

        if (logic.getArrangementGapsElement(indexRow, indexColumn)) {
            labelWarning.setText("Данное поле нельзя изменять!");
            labelWarning.setAlignment(Pos.CENTER);
            return;
        } else {
            labelWarning.setText("");
        }

        logic.setGameFieldsElement(indexRow, indexColumn);

        bufferButtonGameField.setText("");
        bufferButtonGameField.setStyle(null);

        System.out.println("doClick");
    }

    @Override
    protected void clickedPlay(ActionEvent actionEvent) {
        timer.startTime();
        setVisibleScene(false);
    }

    @Override
    protected void clickedRestart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Вы точно хотите начать заново?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            timer.stop();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("modeClassic.fxml"));
            loader.setController(new ControllerModeClassic(stage, gameMode, logic));
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

    @Override
    protected void clickedGameField(ActionEvent actionEvent) {

        backgroundUpdateHelp();

        Button bufferButtonGameField = (Button) actionEvent.getSource();

        searchButtonGameField(bufferButtonGameField);

        if (logic.getArrangementGapsElement(indexRow, indexColumn)) {
            labelWarning.setText("Данное поле нельзя изменять!");
            labelWarning.setAlignment(Pos.CENTER);
            return;
        } else {
            labelWarning.setText("");
        }

        logic.setGameFieldsElement(indexRow, indexColumn);
        bufferButtonGameField.setText(String.valueOf(logic.getSelectedNumber()));
        bufferButtonGameField.setStyle("-fx-text-fill: #4608ba; -fx-font-weight:bold; -fx-font-size: 24; -fx-font-family: \"System\" "); //для подсказок//
        gameOver();
        System.out.println("doClick");
    }

}
