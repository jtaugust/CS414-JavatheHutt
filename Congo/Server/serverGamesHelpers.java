package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class serverGamesHelpers {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	// Returns String[] that shows table Column Names for Users DataBase
	// The list of tables are CurrentGames_T, GameBoards, TournamentMatch_T,
	// Tournaments_T
	public String[] getGamesTableInfo(String tableName) throws Exception {

		try {

			Class.forName("org.mariadb.jdbc.Driver");

			connect = DriverManager
					.getConnection("jdbc:mysql://68.234.149.213:8555/Games?" + "user=cs414&password=cs414");

			statement = connect.createStatement();

			resultSet = statement.executeQuery("SELECT * FROM Games." + tableName);

			String[] returnArray = new String[resultSet.getMetaData().getColumnCount()];

			for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {

				returnArray[i] = resultSet.getMetaData().getColumnName(i + 1);

			}

			connect.close();
			
			return returnArray;

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	// creates a new row in CurrentGame_T also creates a new Gameboard with 7 rows
	// by default
	// Leave out both GameNumber and boardID from String[].
	public int createCurrentGames_T(String[] values) throws Exception {

		try {

			Class.forName("org.mariadb.jdbc.Driver");

			connect = DriverManager
					.getConnection("jdbc:mysql://68.234.149.213:8555/Games?" + "user=cs414&password=cs414");

			statement = connect.createStatement();

			resultSet = statement.executeQuery(
					"SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = \"Games\" AND TABLE_NAME = \"CurrentGames_T\"");

			resultSet.next();
			
			int id = resultSet.getInt(1);

			connect = DriverManager
					.getConnection("jdbc:mysql://68.234.149.213:8555/Games?" + "user=cs414&password=cs414");

			preparedStatement = connect
					.prepareStatement("INSERT INTO Games.CurrentGames_T VALUES (default, ?, ?, default, default, ?, default)");

			preparedStatement.setString(1, values[0]);
			preparedStatement.setString(2, values[1]);

			preparedStatement.setString(3, "w");

			preparedStatement.executeUpdate();
			
			connect.close();
			
			return id;

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	// returns a String[] of data values for a given GameNumber from
	// CurrentGames_T
	public String[] readCurrentGames_T(int GameNumber) throws Exception {

		try {

			Class.forName("org.mariadb.jdbc.Driver");

			connect = DriverManager
					.getConnection("jdbc:mysql://68.234.149.213:8555/Games?" + "user=cs414&password=cs414");

			statement = connect.createStatement();

			preparedStatement = connect.prepareStatement("SELECT * FROM Games.CurrentGames_T WHERE GameNumber=(?)");

			preparedStatement.setInt(1, GameNumber);
			resultSet = preparedStatement.executeQuery();

			String[] returnArray = new String[resultSet.getMetaData().getColumnCount()];

			returnArray[0] = Integer.toString(GameNumber);
			resultSet.next();

			returnArray[1] = resultSet.getString(2);
			returnArray[2] = resultSet.getString(3);

			returnArray[3] = Integer.toString(resultSet.getInt(4));
			returnArray[4] = Integer.toString(resultSet.getInt(5));
			returnArray[5] = resultSet.getString(6);
			returnArray[6] = resultSet.getString(7);

			connect.close();
			
			return returnArray;

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}
	
	//Overload to get all current games a given user is in.
	public ArrayList<String[]> readCurrentGames_T(String Username) throws Exception {

		try {

			Class.forName("org.mariadb.jdbc.Driver");

			connect = DriverManager
					.getConnection("jdbc:mysql://68.234.149.213:8555/Games?" + "user=cs414&password=cs414");

			statement = connect.createStatement();

			preparedStatement = connect.prepareStatement("SELECT * FROM Games.CurrentGames_T WHERE User1=(?) OR User2=(?)");

			preparedStatement.setString(1, Username);
			preparedStatement.setString(2, Username);
			resultSet = preparedStatement.executeQuery();
			ArrayList<String[]> returnArray = new ArrayList<String[]>();
			
			while(resultSet.next()) {
				String[] rowArray = new String[resultSet.getMetaData().getColumnCount()];
				rowArray[0] = resultSet.getString(1);
				rowArray[1] = resultSet.getString(2);
				rowArray[2] = resultSet.getString(3);

				rowArray[3] = Integer.toString(resultSet.getInt(4));
				rowArray[4] = Integer.toString(resultSet.getInt(5));
				returnArray.add(rowArray);
			}
			
			connect.close();
			
			return returnArray;

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	// Change a value from CurrentGames_T by providing GameNumber, column name, and
	// the value to change it to.
	// This can not be used to change GameNumber or boardID as PKeys and FKeys can
	// not be changed.
	public void insertCurrentGames_T(String GameNumber, String column, String value) throws Exception {

		try {

			Class.forName("org.mariadb.jdbc.Driver");

			connect = DriverManager
					.getConnection("jdbc:mysql://68.234.149.213:8555/Games?" + "user=cs414&password=cs414");

			if (column != "GameNumber" && column != "boardID") {

				if (column != "User1" && column != "User2") {

					preparedStatement = connect.prepareStatement(
							"UPDATE Games.CurrentGames_T SET " + column + " = '" + value + "' WHERE GameNumber = (?)");

					preparedStatement.setInt(1, Integer.parseInt(GameNumber));

					preparedStatement.executeUpdate();

				} else if (column == "User1" || column == "User2") {

					preparedStatement = connect.prepareStatement(
							"UPDATE Games.CurrentGames_T SET " + column + " = '" + value + "' WHERE GameNumber = (?)");

					preparedStatement.setInt(1, Integer.parseInt(GameNumber));

					preparedStatement.executeUpdate();

				}
				
			} else {
				System.out.println("Cannot change GameNumber or boardID here.");
			}
			
			connect.close();

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	// Only used when creating a new CurrentGames_T row
	private void createGameBoards(int id, int row) throws Exception {

		try {

			Class.forName("org.mariadb.jdbc.Driver");

			connect = DriverManager
					.getConnection("jdbc:mysql://68.234.149.213:8555/Games?" + "user=cs414&password=cs414");

			preparedStatement = connect.prepareStatement(
					"INSERT INTO Games.GameBoards_T VALUES (?, ?, default, default, default, default, default, default, default)");

			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, row);

			preparedStatement.executeUpdate();
			
			connect.close();

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	// Used to read GameState from GameBoards structure is a-g columns and 1-7 rows
	public String[][] readGameState(int id) throws Exception {

		try {

			Class.forName("org.mariadb.jdbc.Driver");

			String[][] returnArray = new String[7][7];

			for (int i = 0; i < 7; i++) {

				connect = DriverManager
						.getConnection("jdbc:mysql://68.234.149.213:8555/Games?" + "user=cs414&password=cs414");

				preparedStatement = connect.prepareStatement("SELECT * FROM Games.GameBoards_T WHERE GameNumber=(?) AND row=(?)");

				preparedStatement.setInt(1, id);
				preparedStatement.setInt(2, i + 1);
				resultSet = preparedStatement.executeQuery();

				resultSet.next();

				returnArray[i][0] = resultSet.getString("a");
				returnArray[i][1] = resultSet.getString("b");
				returnArray[i][2] = resultSet.getString("c");
				returnArray[i][3] = resultSet.getString("d");
				returnArray[i][4] = resultSet.getString("e");
				returnArray[i][5] = resultSet.getString("f");
				returnArray[i][6] = resultSet.getString("g");

				connect.close();
			}

			return returnArray;

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	// Used to change piece position on the board.
	// ID is the game id, row is the board row, and column is a-g, value what you
	// are changing the spot to on the board.
	// One can also not change row or ID here as both are Pkeys.
	public void insertGameBoards(int id, int row, String column, String value) throws Exception {

		try {

			Class.forName("org.mariadb.jdbc.Driver");

			connect = DriverManager
					.getConnection("jdbc:mysql://68.234.149.213:8555/Games?" + "user=cs414&password=cs414");

			if (column != "GameNumber" && column != "row") {

				preparedStatement = connect.prepareStatement(
						"UPDATE Games.GameBoards_T SET " + column + " = '" + value + "' WHERE GameNumber = (?) AND row = (?)");

				preparedStatement.setInt(1, id);
				preparedStatement.setInt(2, row);

				preparedStatement.executeUpdate();

				connect.close();
				
			} else {
				System.out.println("Cannot change ID or row here.");
			}
			
			connect.close();

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}
