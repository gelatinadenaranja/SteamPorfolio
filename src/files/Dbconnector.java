package files;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Dbconnector {
	
	private Connection conn;
	
	public Dbconnector() {
		try{
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.home") + "\\SteamPortfolio\\data.db");
		
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public int insert_new_item(String name, int name_count, String market_link, String game_id, String market_hash_name, String iconURL,
			                    double cost, int quantity, double expected_value,
			                    String last_lowest_price, String last_median_price, String last_volume){
		Statement statement = null;
		PreparedStatement prep_statement;
		ResultSet result;
		int id; //Returned id is -1 if something fails.
		
		//Create a new id value for a new row.
		try {
			statement = conn.createStatement();
			result = statement.executeQuery("SELECT MAX(id) FROM item_data;");
			if(!result.next()) {
				id = 0;
			} else {
				id = result.getInt("MAX(id)") + 1;
			}
			statement.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Couldn't create a new id for the item, data won't be saved.\n" + e.toString());
			return -1;
		}
		
		//Inserting the new row into the database.
		try {
			prep_statement = conn.prepareStatement("INSERT INTO item_data(id, name, name_count, market_link, game_id, market_hash_name, iconURL, " +
					                                "cost, quantity, expected_value, " +
					                                "last_lowest_price, last_median_price, last_volume) " +
					                                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			prep_statement.setInt(1, id);
			prep_statement.setString(2, name);
			prep_statement.setInt(3, name_count);
			prep_statement.setString(4, market_link);
			prep_statement.setString(5, game_id);
			prep_statement.setString(6, market_hash_name);
			prep_statement.setString(7, iconURL);
			prep_statement.setDouble(8, cost);
			prep_statement.setInt(9, quantity);
			prep_statement.setDouble(10, expected_value);
			prep_statement.setString(11, last_lowest_price);
			prep_statement.setString(12, last_median_price);
			prep_statement.setString(13, last_volume);
			
			prep_statement.executeUpdate();
			prep_statement.close();
			return id;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Couldn't insert the new data into the database.\n" + e.toString());
			return -1;
		}
	}
	
	public int get_new_name_count_val(String name) {
		PreparedStatement prep_statement;
		ResultSet result;
		
		try{
			prep_statement = conn.prepareStatement("SELECT name_count FROM item_data WHERE(name=?) ORDER BY name_count;");
			prep_statement.setString(1, name);
			
			result = prep_statement.executeQuery();
			
			if(!result.next()) {
				return 0;
			}
			
			int count = 0;
			
			do {
				if(count == result.getInt(1)) {
					count++;
				} else {
					return count;
				}
			} while(result.next());
			
			prep_statement.close();
			result.close();
			return count++;
			
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Couldn't count existing names in database.\n" + e.toString());
			return -1;
		}
	}
	
	public boolean create_tables() {
		//Maybe normalize someday.
		Statement statement = null;
		
		try{
			statement = conn.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS item_data(" +
			"id INT NOT NULL," +
			"name TEXT NOT NULL," +
			"name_count INT NOT NULL," +
			"market_link TEXT NOT NULL," +
			"game_id TEXT NOT NULL," +
			"market_hash_name TEXT NOT NULL," +
			"cost REAL NOT NULL," +
			"quantity INT NOT NULL," +
			"expected_value REAL NOT NULL," +
			"last_lowest_price TEXT NOT NULL," +
			"last_median_price TEXT NOT NULL," +
			"last_volume TEXT NOT NULL," +
			"iconURL TEXT," +
			"PRIMARY KEY(id));");
			statement.close();
			return true;
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Couldn't create the necessary tables.\n" + e.toString());
			return false;
		}
	}
	
	public boolean delete_table_row(String id) {
		PreparedStatement prep_statement;
		
		try {
			prep_statement = conn.prepareStatement("DELETE FROM item_data WHERE(id = ?)");
			prep_statement.setString(1, id);
			prep_statement.executeUpdate();
			prep_statement.close();
			
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Couln't delete data from database.\n" + e.toString());
			return false;
		}
	}
	
	public ResultSet query_data(){
		PreparedStatement prep_statement;
		String query = "SELECT id, name, name_count, quantity, cost, iconURL, expected_value, game_id, market_hash_name FROM item_data;";
		ResultSet result;
		
		try{
			prep_statement = conn.prepareStatement(query);
			result = prep_statement.executeQuery();
			return result;
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Couldn't retrieve data from database.\n" + e.toString());
			return null;
		}
	}
	
	//The functions setQuery() and getQuery() are only used for testing purposes.
	public void setQuery(String query) {
		Statement statement = null;
		
		try{
			statement = conn.createStatement();
			statement.executeUpdate(query);
			statement.close();
		}catch(SQLException e){
			System.out.println(e);
		}
	}
	
	public ResultSet getQuery(String query) {
		Statement statement = null;
		ResultSet result;
		try{
			statement = conn.createStatement();
			result = statement.executeQuery(query);
			statement.close();
			return result;
		}catch(SQLException e){
			System.out.println(e);
			return result = null;
		}
	}
}
