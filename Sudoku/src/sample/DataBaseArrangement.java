package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class DataBaseArrangement {
    private int [][] arrangementNumbers = new int[9][9];
    private boolean [][] arrangementGaps = new boolean[9][9];

    DataBaseArrangement() { }

    public int[][] getArrangementNumbers() {
        return arrangementNumbers;
    }

    public boolean[][] getArrangementGaps() {
        return arrangementGaps;
    }

    public void select(int levelOfDifficulty) {
        Random random = new Random();
        String numbers = null;
        String gaps = null;
        String query = null;
        try {

            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:./src/dataBase/Arrangement.db");
            Statement statement = connection.createStatement();

            switch (levelOfDifficulty) {
                case 1:
                    query = "SELECT ArrangementNumbers, ArrangementGaps FROM ArrangementEasy WHERE id=" + (random.nextInt(2) + 1);
                    break;
                case 2:
                    query = "SELECT ArrangementNumbers, ArrangementGaps FROM ArrangementMiddle WHERE id=" + (random.nextInt(2) + 1);
                    break;
                case 3:
                    query = "SELECT ArrangementNumbers, ArrangementGaps FROM ArrangementHard WHERE id=" + (random.nextInt(2) + 1);
                    break;
                default:
                    break;
            }

            ResultSet resultSet = statement.executeQuery(query);
            numbers = resultSet.getString("ArrangementNumbers");
            gaps = resultSet.getString("ArrangementGaps");

            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        int k = 0;
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                arrangementNumbers[i][j] = numbers.charAt(k) - 48;
                if (gaps.charAt(k) == '1') {
                    arrangementGaps[i][j] = true;
                } else {
                    arrangementGaps[i][j] = false;
                }
                k++;
            }
        }
    }

}
