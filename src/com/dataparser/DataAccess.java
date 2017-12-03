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
	
	public void insertData(String file, String table) {
		final String DB_URL = "jdbc:mysql://localhost:3306/movie_recommender?useSSL=false";
		
		// Database Credentials
		final String USER = "root";
		final String PASS = "root";
		
		try {
			// 1. Get a connection to database
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			// 2. Create a statement
			Statement stmt = conn.createStatement();
			
			file = file.replace("\\", "/");
			
			// 3. Execute SQL query
			int i = stmt.executeUpdate("LOAD DATA LOCAL INFILE '" + file + "' INTO TABLE " + table + " CHARACTER SET LATIN1 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' IGNORE 1 LINES");
			
			stmt.close();
			conn.close();
			
			System.out.println("Finished inserting " + file + ". Number of rows inserted: " + i + " .");

		}
		catch (Exception e){
			
			e.printStackTrace();
		}		
	}
	
	// Special case for the movie_locations.dat file, we created a PK column in the DB that does not exist in the file being imported. Needs a custom LOAD DATA statement
	public void insertMovieLocations(String file) {
		final String DB_URL = "jdbc:mysql://localhost:3306/movie_recommender?useSSL=false";
		
		// Database Credentials
		final String USER = "root";
		final String PASS = "root";
		
		try {
			// 1. Get a connection to database
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			// 2. Create a statement
			Statement stmt = conn.createStatement();
			
			file = file.replace("\\", "/");
			
			// 3. Execute SQL insert	
			int i = stmt.executeUpdate("LOAD DATA LOCAL INFILE '" + file + "' INTO TABLE movie_locations CHARACTER SET LATIN1 FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' IGNORE 1 LINES (movieID, location1, location2, location3, location4) SET movieLocationsID = NULL ");

			stmt.close();
			conn.close();
			
			System.out.println("Finished inserting " + file + ". Number of rows inserted: " + i + " .");

		}
		catch (Exception e){
			
			e.printStackTrace();
		}		
	}
}
