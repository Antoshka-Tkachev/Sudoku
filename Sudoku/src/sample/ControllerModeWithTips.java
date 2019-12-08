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

public class ControllerModeWithTips extends ControllerAbstractMode implements Initializable {

    public ControllerModeWithTips(Stage stage, GameMode gameMode) {
        super(stage, gameMode);
    }

    public ControllerModeWithTips(Stage stage, GameMode gameMode, Logic logic) {
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

                if (buttonsGameField[row][column].getText().equals("1")) {
                    buttonsGameField[row][column].setStyle("-fx-background-color: green; -fx-font-weight:bold; -fx-font-size: 24; -fx-font-family: \"System\" ");
                }
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
    }

    @Override
    protected void backgroundUpdateHelp() {
        if (flagHelp) {
            if(logic.getGameFieldElement(indexRow, indexColumn) == logic.getSelectedNumber()) { //если 9 потавлены
                buttonsGameField[indexRow][indexColumn].setStyle("-fx-background-color: green; -fx-text-fill: #4608ba; -fx-font-weight:bold; -fx-font-size: 24; -fx-font-family: \"System\"");
            } else {
                buttonsGameField[indexRow][indexColumn].setStyle("-fx-text-fill: #4608ba; -fx-font-weight:bold; -fx-font-size: 24; -fx-font-family: \"System\"");
            }
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

        checkFullnessNumberForSelection(logic.getGameFieldElement(indexRow, indexColumn));

        gameOver();
    }

    @Override
    protected void clickedNumberForSelection(ActionEvent actionEvent) {
        if (logic.getSelectedNumber() == 0) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    buttonsGameField[i][j].setOnAction(this::clickedGameField);
                }
            }
        }
        labelWarning.setText("");

        bufferBtnNumberForSelection.setStyle(null);
        offHighlightingSelectedNumbers();

        bufferBtnNumberForSelection = (Button) actionEvent.getSource();
        bufferBtnNumberForSelection.setStyle("-fx-background-color: green");
        logic.setSelectedNumber(Integer.parseInt(bufferBtnNumberForSelection.getText()));

        onHighlightingSelectedNumbers();
    }

    @Override
    protected void clickedDelete(ActionEvent actionEvent) {
        labelWarning.setText("");

        bufferBtnNumberForSelection.setStyle(null);
        offHighlightingSelectedNumbers();

        bufferBtnNumberForSelection = (Button) actionEvent.getSource();
        bufferBtnNumberForSelection.setStyle("-fx-background-color: green");
        logic.setSelectedNumber(0);

        onHighlightingSelectedNumbers();

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

        if (!bufferButtonGameField.getText().equals("")) {
            logic.removeCountNumberForSelectionElement(Integer.parseInt(bufferButtonGameField.getText()));
            if (logic.getCountNumberForSelectionElement(Integer.parseInt(bufferButtonGameField.getText())) < 9) {
                setDisableNumberForSelection(Integer.parseInt(bufferButtonGameField.getText()), false);
            }
        }

        bufferButtonGameField.setText("");
        bufferButtonGameField.setStyle(null);

        System.out.println("doClick");
    }

    @Override
    protected void clickedPlay(ActionEvent actionEvent) {
        timer.startTime();
        setVisibleScene(false);

        for (int i = 1; i < 10; i++) {
            if (logic.checkFullnessNumberForSelection(i)) {
                setDisableNumberForSelection(i, true);
            }
        }
    }

    @Override
    protected void clickedRestart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Вы точно хотите начать заново?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            timer.stop();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("modeWithTips.fxml"));
            loader.setController(new ControllerModeWithTips(stage, gameMode, logic));
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
        if (logic.checkFullnessNumberForSelection(Integer.parseInt(bufferBtnNumberForSelection.getText()))) { // если поставлены все 9 цифр
            labelWarning.setText("Выберете другую цифру. Все " + bufferBtnNumberForSelection.getText() + " поставлены");
            labelWarning.setAlignment(Pos.CENTER);
            return;
        }

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
        String lastNumber = bufferButtonGameField.getText();
        bufferButtonGameField.setText(String.valueOf(logic.getSelectedNumber()));
        bufferButtonGameField.setStyle("-fx-text-fill: #4608ba; -fx-background-color: green; -fx-font-weight:bold; -fx-font-size: 24; -fx-font-family: \"System\" "); //для подсказок//

        if (lastNumber.equals("")) {
            checkFullnessNumberForSelection(Integer.parseInt(bufferButtonGameField.getText()));
        } else if (!lastNumber.equals(bufferButtonGameField.getText())) {
            logic.removeCountNumberForSelectionElement(Integer.parseInt(lastNumber));
            setDisableNumberForSelection(Integer.parseInt(lastNumber), false);
            checkFullnessNumberForSelection(Integer.parseInt(bufferButtonGameField.getText()));
        }

        gameOver();
        System.out.println("doClick");
    }

    @Override
    protected void checkFullnessNumberForSelection(int number) {
        logic.setCountNumberForSelectionElement(number);
        if (logic.checkFullnessNumberForSelection(number)) {
            setDisableNumberForSelection(number, true);
        }
    }

    @Override
    protected void setDisableNumberForSelection(int number, boolean flag) {
        switch (number) {
            case 1:
                numberForSelection1.setDisable(flag);
                break;
            case 2:
                numberForSelection2.setDisable(flag);
                break;
            case 3:
                numberForSelection3.setDisable(flag);
                break;
            case 4:
                numberForSelection4.setDisable(flag);
                break;
            case 5:
                numberForSelection5.setDisable(flag);
                break;
            case 6:
                numberForSelection6.setDisable(flag);
                break;
            case 7:
                numberForSelection7.setDisable(flag);
                break;
            case 8:
                numberForSelection8.setDisable(flag);
                break;
            case 9:
                numberForSelection9.setDisable(flag);
                break;
            default:
                break;

        }
    }

    @Override
    protected void onHighlightingSelectedNumbers() { //для подсказок//
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (buttonsGameField[i][j].getText().equals(bufferBtnNumberForSelection.getText())) {
                    if (logic.getArrangementGapsElement(i, j)) {
                        buttonsGameField[i][j].setStyle("-fx-background-color: green; -fx-font-weight:bold; -fx-font-size: 24; -fx-font-family: \"System\" ");
                    } else {
                        buttonsGameField[i][j].setStyle("-fx-text-fill: #4608ba; -fx-background-color: green; -fx-font-weight:bold; -fx-font-size: 24; -fx-font-family: \"System\" ");
                    }
                } else {
                    if (!logic.getArrangementGapsElement(i, j)) {
                        buttonsGameField[i][j].setStyle("-fx-text-fill: #4608ba; -fx-font-weight:bold; -fx-font-size: 24; -fx-font-family: \"System\" ");
                    }
                }
            }
        }

        for (int i = 0; i < logic.getErrorSize(); i++) {
            buttonsGameField[logic.getErrorIndexRowElement(i)][logic.getErrorIndexColumnElement(i)].setStyle("-fx-text-fill: #4608ba; -fx-background-color: red; -fx-font-weight:bold; -fx-font-size: 24; -fx-font-family: \"System\" ");;
        }
    }

    @Override
    protected void offHighlightingSelectedNumbers() { //для подсказок//
        backgroundUpdateHelp();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (buttonsGameField[i][j].getText().equals(bufferBtnNumberForSelection.getText())) {
                    buttonsGameField[i][j].setStyle("-fx-font-weight:bold; -fx-font-size: 24; -fx-font-family: \"System\" ");
                }
            }
        }
    }

}