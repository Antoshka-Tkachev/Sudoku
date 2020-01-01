package sample;

import java.sql.*;

public class DataBasePlayer {

    private Player player;

    public boolean select(String login) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./src/dataBase/Player.db");
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Player WHERE login='" + login + "'";
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            statement.close();
            connection.close();
            return true;
        }

        statement.close();
        connection.close();
        return false;
    }

    public Player select(String login, String password) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./src/dataBase/Player.db");
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Player WHERE login='" + login + "' AND " +
                "password='" + password + "'";
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            player = new Player();
            player.setLogin(resultSet.getString("login"));
            player.setPassword(resultSet.getString("password"));
            player.setLevel(resultSet.getInt("level"));
            player.setExperience(resultSet.getInt("exp"));
            player.setCountWin(resultSet.getInt("countWin"));
            player.setCountLose(resultSet.getInt("countLose"));
        }

        statement.close();
        connection.close();

        return player;
    }

    public void insert(String login, String password, int level, int exp, int countWin, int countLose) throws Exception {

        String query = "INSERT INTO Player (login, password, level, exp, countWin, countLose) " +
                "VALUES ('" +
                login + "', '" +
                password + "', '" +
                level + "', '" +
                exp + "', '" +
                countWin + "', '" +
                countLose + "')";

        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./src/dataBase/Player.db");
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);

        statement.close();
        connection.close();

    }

    public void update(String login, int level, int exp, int countWin, int countLose) throws Exception {

        String query = "UPDATE Player SET level = " + level +
                ", exp = " + exp +
                ", countWin = " + countWin +
                ", countLose = " + countLose +
                " WHERE login = '" + login + "';";

        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./src/dataBase/Player.db");
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);

        statement.close();
        connection.close();

    }

}
