package testclasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class pruebasqlite{
	
	private Connection conn;
	private Statement statement;
	private ResultSet result;
	
	public pruebasqlite(){
		try{
		Class.forName("org.sqlite.JDBC");
		this.conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Termostato\\Desktop\\test.db");
		this.statement = this.conn.createStatement();
		
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void setQuery(String query){
		try{
			this.statement.executeUpdate(query);
		}catch(SQLException e){
			System.out.println(e);
		}
	}
	
	public ResultSet getQuery(String query){
		try{
			return this.result = this.statement.executeQuery(query);
		}catch(SQLException e){
			System.out.println(e);
			return this.result = null;
		}
	}
}