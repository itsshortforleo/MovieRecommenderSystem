package com.dataparser;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController implements Initializable{
	@FXML private TextField searchText;
	@FXML private TableView<Result> MovieTable;
	@FXML private TableColumn<Result, String> MoviePicture1;
	@FXML private TableColumn<Result, String> MoviePicture2;
	@FXML private TableColumn<Result, String> MovieTitle;
	@FXML private TableColumn<Result, Integer> Year;
	@FXML private TableColumn<Result, Integer> AudienceScore;
	@FXML private ComboBox<String> choices;
	
	public ObservableList<Result> results=FXCollections.observableArrayList();
	ObservableList<String> comboBoxcontent=FXCollections.observableArrayList("Title","Director Name","Actor Name","Tag","User Name");

	public  void Query(){
		final String DB_URL = "jdbc:mysql://localhost:3306/movie_recommender?useSSL=false";

		// Database Credentials
		final String USER = "root";
		final String PASS = "nuha1410";
		
		String searchParameter=searchText.getText();
		try {
			// 1. Get a connection to database
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			// 2. Create a statement 
			// based on the ComboBox, the query will change
			String query;
			switch (choices.getValue()) 
	        {
				//query2
	            case "Title":  MovieTable.setVisible(true);query="SELECT m.`title`, m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL`, t.`value` FROM `tags` t,`movies` m, `user_tagged_movies` ut WHERE m.`movieID`=ut.`movieID` AND t.`tagID`=ut.`tagID` AND m.`title` LIKE ?;";;
	                     break;
	            //query4
	            case "Director Name":  MovieTable.setVisible(true);query="SELECT m.`title`,m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m, `movie_directors` md  WHERE m.`movieID`=md.`movieID` AND md.`directorName` LIKE ?;";
	                     break;
	            //query5
	            case "Actor Name":  MovieTable.setVisible(true);query="SELECT m.`title`,m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m, `movie_actors` ma WHERE m.`movieID`=ma.`movieID` AND ma.`actorName` LIKE ?;";
	                     break;
	            //query6
	            case "Tag":  MovieTable.setVisible(true);query="";
	                     break;
	            //query9
	            case "User Name":  MovieTable.setVisible(false);query="";
	                     break;
	            default: query="";
	                     break;
	        }

			PreparedStatement ps=conn.prepareStatement(query);
			ps.setString(1, "%"+searchParameter+"%");
			ResultSet myRs = ps.executeQuery();
			
			// 4. Process the result set
			while(myRs.next()) {
				//System.out.println(myRs.getString("title"));
				Result result= new Result(myRs.getString("title"),myRs.getInt("year"), myRs.getInt("rtAudienceScore"),myRs.getString("imdbPictureURL"), myRs.getString("rtPictureURL") );
				results.add(result);
				
			}
			
			myRs.close();
			ps.close();
			conn.close();
		}
		catch (Exception e){
			
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		MoviePicture1.setCellValueFactory(new PropertyValueFactory<Result,String>("rtPictureURL"));
		MoviePicture2.setCellValueFactory(new PropertyValueFactory<Result,String>("imdbPictureURL"));
		MovieTitle.setCellValueFactory(new PropertyValueFactory<Result,String>("title"));
		Year.setCellValueFactory(new PropertyValueFactory<Result,Integer>("year"));
		AudienceScore.setCellValueFactory(new PropertyValueFactory<Result,Integer>("rtAudienceScore"));
		MovieTable.setItems(results);
		choices.setItems(comboBoxcontent);
		
	}

}
