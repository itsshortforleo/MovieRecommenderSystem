package com.dataparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DataAccess {
	
	public void testConnection() {
		final String DB_URL = "jdbc:mysql://localhost:3306/movie_recommender?useSSL=false";
		
		// Database Credentials
		final String USER = "root";
		final String PASS = "root";

		try {
			// 1. Get a connection to database
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			// 2. Create a statement
			Statement stmt = conn.createStatement();
			
			// 3. Execute SQL query
			ResultSet myRs = stmt.executeQuery("select * from movie_actors");
			
			// 4. Process the result set
			while(myRs.next()) {
				System.out.println(myRs.getString("actorName"));
			}
			
			stmt.close();
			conn.close();
		}
		catch (Exception e){
			
			e.printStackTrace();
		}		
	}
	
	public void insertData() {
		final String DB_URL = "jdbc:mysql://localhost:3306/movie_recommender?useSSL=false";
		
		// Database Credentials
		final String USER = "root";
		final String PASS = "root";

		try {
			// 1. Get a connection to database
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			// 2. Create a statement
			Statement stmt = conn.createStatement();
			
			
			String fileName = System.getProperty("user.dir") + "\\Rotten Tomatos Dataset\\movies.dat";
			fileName = fileName.replace("\\", "/");
			//String fn = "D://Dropbox//CSC 204 - Data Models for Database Management Systems//Assignments//Semester Programming Project//CSC204ProjectGitRepo//Rotten Tomatos Dataset//movies.dat";
			
			// 3. Execute SQL query
			//ResultSet myRs = stmt.executeQuery("LOAD DATA LOCAL INFILE '" + fileName + "' INTO TABLE movies FIELDS TERMINATED BY '\t' ENCLOSED BY '' LINES TERMINATED BY '\n'");
			
			int i = stmt.executeUpdate("LOAD DATA LOCAL INFILE '" + fileName + "' INTO TABLE movies FIELDS TERMINATED BY '\t' ENCLOSED BY '' LINES TERMINATED BY '\n'");

			
			/*// 4. Process the result set
			while(myRs.next()) {
				System.out.println(myRs.getString("actorName"));
			}
			*/
			stmt.close();
			conn.close();
		}
		catch (Exception e){
			
			e.printStackTrace();
		}		
	}
}
