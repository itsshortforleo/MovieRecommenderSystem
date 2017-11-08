package com.dataparser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainController {
	@FXML private TextField searchText;
	List<Result> results= new LinkedList();

	public  List Query2(){
		final String DB_URL = "jdbc:mysql://localhost:3306/movie_recommender?useSSL=false";
		
		// Database Credentials
		final String USER = "root";
		final String PASS = "nuha1410";
		
		String searchParameter=searchText.getText();
		try {
			// 1. Get a connection to database
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			// 2. Create a statement & Execute SQL query
			String query1="SELECT m.`title`, m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m ORDER BY m.`rtAudienceScore` LIMIT 5;";
			String query2="SELECT m.`title`, m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL`, t.`value` FROM `tags` t,`movies` m, `user_tagged_movies` ut WHERE m.`movieID`=ut.`movieID` AND t.`tagID`=ut.`tagID` AND m.`title` LIKE ?;";
			String query3="SELECT m.`title`,m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m, `movie_genres` mg  WHERE m.`movieID`=mg.`movieID` AND mg.`genre` LIKE '%Adventure%' ORDER BY m.`rtAudienceScore`  LIMIT 5;";
			String query4="SELECT m.`title`,m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m, `movie_directors` md  WHERE m.`movieID`=md.`movieID` AND md.`directorName` LIKE '%John Lasseter%';";
			String query5="SELECT m.`title`,m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m, `movie_actors` ma WHERE m.`movieID`=ma.`movieID` AND ma.`actorName` LIKE '%Annie Potts%';";

			
			PreparedStatement ps=conn.prepareStatement(query2);
			ps.setString(1, "%"+searchParameter+"%");
			ResultSet myRs = ps.executeQuery();
			
			// 4. Process the result set
			while(myRs.next()) {
				System.out.println(myRs.getString("title"));
				Result result= new Result();
				result.setTitle(myRs.getString("title"));
				result.setYear(myRs.getInt("year"));
				result.setRtAudienceScore(myRs.getInt("rtAudienceScore"));
				result.setImdbPictureURL(myRs.getString("imdbPictureURL"));
				result.setRtPictureURL(myRs.getString("rtPictureURL"));
				
				results.add(result);
				
			}
			
			myRs.close();
			ps.close();
			conn.close();
		}
		catch (Exception e){
			
			e.printStackTrace();
		}	
		return results;
	}

}
