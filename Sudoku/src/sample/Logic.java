package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Logic {
    private int selectedNumber;// = 1;
    private int [][] gameFields = new int[9][9];
    private int [][] arrangementNumbers = new int[9][9];
    private boolean [][] arrangementGaps = new boolean[9][9];
    private int [] countNumberForSelection = new int [9];
    private ArrayList<Integer> errorIndexRow = new ArrayList<>();
    private ArrayList <Integer> errorIndexColumn = new ArrayList<>();
    private int errorSize;// = 0;
    private int lives;// = 3;

    public void restart() {
        selectedNumber = 1;
        errorSize = 0;
        lives = 3;
        errorIndexRow = new ArrayList<>();
        errorIndexColumn = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            countNumberForSelection[i] = 0;
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(arrangementGaps[i][j]) {
                    gameFields[i][j] = arrangementNumbers[i][j];
                } else {
                    gameFields[i][j] = 0;
                }
            }
        }

        setCountNumberForSelection();
    }

    public void setCountNumberForSelection() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (gameFields[i][j] != 0) {
                    countNumberForSelection[gameFields[i][j] - 1]++;
                }
            }
        }
    }

    public void setCountNumberForSelectionElement(int number) {
        int index = number - 1;
        countNumberForSelection[index]++;
    }

    public int getCountNumberForSelectionElement(int number) {
        int index = number - 1;
        return countNumberForSelection[index];
    }

    public void removeCountNumberForSelectionElement(int number) {
        int index = number - 1;
        countNumberForSelection[index]--;
    }

    public boolean checkFullnessNumberForSelection(int number) {
        int index = number - 1;
        return countNumberForSelection[index] >= 9;
    }

    public int getLives() {
        return lives;
    }

    public boolean getArrangementGapsElement(int indexRow, int indexColumn) {
        return arrangementGaps[indexRow][indexColumn];
    }

    public int getArrangementNumberElement(int indexRow, int indexColumn) {
        return arrangementNumbers[indexRow][indexColumn];
    }

    public int getErrorSize() {
        return errorSize;
    }

    public int getErrorIndexRowElement(int indexRow) {
        return errorIndexRow.get(indexRow);
    }

    public int getErrorIndexColumnElement(int indexColumn) {
        return errorIndexColumn.get(indexColumn);
    }

    public void addError(int indexRow, int indexColumn) {

        boolean repetitionFound = false;

        for (int i = 0; i < errorSize; i++) { // добавление ошибки
            if (indexRow == errorIndexRow.get(i) && indexColumn == errorIndexColumn.get(i)) {
                repetitionFound = true;
                break;
            }
        }

        if (!repetitionFound) {
            errorIndexRow.add(indexRow);
            errorIndexColumn.add(indexColumn);
            errorSize++;

        }
        lives--;
    }

    public boolean correctionError(int indexRow, int indexColumn) {

        for (int i = 0; i < errorSize; i++) { // удаление ошибки
            if (indexRow == errorIndexRow.get(i) && indexColumn == errorIndexColumn.get(i)) {
                errorIndexRow.remove(i);
                errorIndexColumn.remove(i);
                errorSize--;
                return true;
            }
        }
        return false;
    }

    public Logic() {
        selectedNumber = 1;
        errorSize = 0;
        lives = 3;

        for (int i = 0; i < 9; i++) {
            countNumberForSelection[i] = 0;
        }
    }

    public void setGameFieldsElement(int row, int column) {
        gameFields[row][column] = selectedNumber;
    }


    public boolean checkGameFields(int indexRow, int indexColumn) {
        return gameFields[indexRow][indexColumn] == arrangementNumbers[indexRow][indexColumn];
    }

    public boolean checkLivesEnded() {
        return lives == 0;
    }

    public boolean checkGameFieldsIsFilled() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (gameFields[i][j] == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkWin() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (gameFields[i][j] != arrangementNumbers[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public void createArrangement(int levelOfDifficulty) {
        DataBaseArrangement arrangement = new DataBaseArrangement();
        arrangement.select(levelOfDifficulty);
        arrangementNumbers = arrangement.getArrangementNumbers();
        arrangementGaps = arrangement.getArrangementGaps();
    }

    public int getSelectedNumber() {
        return selectedNumber;
    }

    public void setSelectedNumber(int selectedNumber) {
        this.selectedNumber = selectedNumber;
    }

    public void fillingInTheField(int levelOfDifficulty) {

        createArrangement(levelOfDifficulty);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(arrangementGaps[i][j]) {
                    gameFields[i][j] = arrangementNumbers[i][j];
                } else {
                    gameFields[i][j] = 0;
                }
            }
        }

        setCountNumberForSelection();

    }

    public int getGameFieldElement(int row, int column) {
        return gameFields[row][column];
    }

    public int clickedButtonHelp() {
        Random random = new Random();
        int indexRow = random.nextInt(9);
        int indexColumn = random.nextInt(9);

        while (gameFields[indexRow][indexColumn] != 0) {
            indexRow = random.nextInt(9);
            indexColumn = random.nextInt(9);
        }

        gameFields[indexRow][indexColumn] = arrangementNumbers[indexRow][indexColumn];
        return  indexRow * 10 + indexColumn;
    }
}
