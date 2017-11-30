package com.userinterface;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class MainController implements Initializable{
	@FXML private TextField searchText;
	@FXML private TableView<Result> MovieTable;
	@FXML private TableColumn<Result, String> MoviePicture1;
	@FXML private TableColumn<Result, String> MoviePicture2;
	@FXML private TableColumn<Result, String> MovieTitle;
	@FXML private TableColumn<Result, Integer> Year;
	@FXML private TableColumn<Result, Integer> AudienceScore;
	@FXML private ComboBox<String> choices;
	@FXML private ListView<String> topList;
	@FXML private ImageView img;
	private boolean isSearch=false;
	
	public ObservableList<Result> results=FXCollections.observableArrayList();
	ObservableList<String> comboBoxcontent=FXCollections.observableArrayList("Title","Director Name","Actor Name","Tag","User Name");
	ObservableList<String> listViewContent=FXCollections.observableArrayList("Top popular movies","Top popular directors","Top popular actors");

	public void SearchQueries(){
		isSearch=true;
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
            //query6 (need to ask about the AVG func ????)
            case "Tag":  MovieTable.setVisible(true);query="SELECT m.`title`,m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m, `movie_tags` mt, `tags` t WHERE m.`movieID`=mt.`movieID` AND mt.`tagID`=t.`tagID` AND t.`value` LIKE ? ORDER BY (m.`rtAudienceScore`);";
                     break;
            //query9 
            case "User Name":  MovieTable.setVisible(false);query="";
                     break;
            default: query="";
                     break;
        }
		RunQuery( query);	
		
	}
	
	
	public  void RunQuery( String query) {
		final String DB_URL = "jdbc:mysql://localhost:3306/movie_recommender?useSSL=false";

		// Database Credentials
		final String USER = "root";
		final String PASS = "root";
		
		try {
			// 1. Get a connection to database
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			// 2. Create a statement 
			
			PreparedStatement ps=conn.prepareStatement(query);
			
			if (isSearch==true){
			String searchParameter=searchText.getText();
			ps.setString(1, "%"+searchParameter+"%");
			}
			
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
		
		MoviePicture1.setCellValueFactory(new PropertyValueFactory("rtPictureURL"));
		// SETTING THE CELL FACTORY FOR THE ALBUM ART                 
		MoviePicture1.setCellFactory(new Callback<TableColumn<Result,String>,TableCell<Result,String>>(){        
            @Override
            public TableCell<Result,String> call(TableColumn<Result,String> param) {                
                TableCell<Result,String> cell = new TableCell<Result,String>(){
                	@Override
                    public void updateItem(String item, boolean empty) {                        
                        if(item!=null){                            
                            ImageView imageview = new ImageView();
                            imageview.setImage(new Image(item));
                            setGraphic(imageview);
                        }
                    }
                };
                System.out.println(cell.getIndex());               
                return cell;
            }
        });  
		
		MoviePicture2.setCellValueFactory(new PropertyValueFactory("imdbPictureURL"));
		// SETTING THE CELL FACTORY FOR THE ALBUM ART                 
		MoviePicture2.setCellFactory(new Callback<TableColumn<Result,String>,TableCell<Result,String>>(){        
            @Override
            public TableCell<Result,String> call(TableColumn<Result,String> param) {                
                TableCell<Result,String> cell = new TableCell<Result,String>(){
                	@Override
                    public void updateItem(String item2, boolean empty) {                        
                        if(item2!=null){                            
                            ImageView imageview = new ImageView();
                            // I put static value because i did not work for me (until we fix the problem
                            imageview.setImage(new Image("http://content9.flixster.com/movie/10/94/17/10941715_det.jpg"));
                            System.out.println(item2);
                            setGraphic(imageview);
                        }
                    }
                };
                
                System.out.println(cell.getIndex());               
                return cell;
            }
        });  

		
//		MoviePicture2.setCellValueFactory(new PropertyValueFactory<Result,String>("imdbPictureURL"));
		MovieTitle.setCellValueFactory(new PropertyValueFactory<Result,String>("title"));
		Year.setCellValueFactory(new PropertyValueFactory<Result,Integer>("year"));
		AudienceScore.setCellValueFactory(new PropertyValueFactory<Result,Integer>("rtAudienceScore"));
		MovieTable.setItems(results);
		choices.setItems(comboBoxcontent);
		choices.getSelectionModel().select("Title");
		topList.setItems(listViewContent);
		
		
		//TopList Change Listener
				topList.getSelectionModel().selectedItemProperty().addListener(
		                new ChangeListener<String>() {
		                    public void changed(ObservableValue<? extends String> ov, 
		                        String old_val, String new_val) {
		                    		String query = null;
		                    		//query1 // need to implement query3 here
		                    		if (new_val.equals("Top popular movies"))
		                    			query="SELECT m.`title`, m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m ORDER BY m.`rtAudienceScore` LIMIT 5;";
		                    		//query7
		                    		else if (new_val.equals("Top popular directors"))
		                    			query="";
		                    		//query8
		                    		else if (new_val.equals("Top popular actors"))
		                    			query="";
		                    		RunQuery(query);
		                            
		                    }
		                });
		
	}

}
