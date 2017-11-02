package com.dataparser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainController {
	public void search(){
		System.out.print("inside controller");
		final String DB_URL = "jdbc:mysql://localhost:3306/movie_recommender?useSSL=false";
		
		// Database Credentials
		final String USER = "root";
		final String PASS = "nuha1410";

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

}
