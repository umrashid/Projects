package application;

import java.sql.*;
public class Database {
	private Connection con;
	private Statement stmt;
	final private String URL = "jdbc:mysql://localhost:3306/QuizMe";
	final private String USER = "admin";
	final private String PASSWORD = "password";
	
	
	public Database(){
		try {
			registerDriver();
			this.con = DriverManager.getConnection(URL,USER,PASSWORD);
			this.stmt=this.con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public ResultSet runQuery(String query){
		ResultSet rs = null;
		try {
			rs = this.stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Running Query Failed");
		}
		return rs;
		
		
	}
	
	public void changeQuery(String query){
		
		try {
			this.stmt.executeUpdate(query);
			System.out.println("Updating Query Successful");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Updating Query Failed");
		}
		
		
	}
	
	public void closeConn(){
		try {
			this.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Close Connection Failed");
		}
	}
	
	public void registerDriver(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			System.out.println("Driver Registered Successfully");
			}
			catch(ClassNotFoundException ex) {
				System.out.println("Error: unable to load driver class!");
				System.exit(1);
			} catch (InstantiationException e) {
				System.out.println("Error: access problem while loading!");
				System.exit(2);
			} catch (IllegalAccessException e) {
				System.out.println("Error: unable to instantiate driver!");
				System.exit(3);
			}
	}
	

}
