package com.userinterface;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

public class MainController implements Initializable {
	@FXML private TextField searchText;
	@FXML private TableView<Result> MovieTable;
	@FXML private TableColumn<Result, String> MoviePicture1;
	@FXML private TableColumn<Result, String> MoviePicture2;
	@FXML private TableColumn<Result, String> MovieTitle;
	@FXML private TableColumn<Result, Integer> Year;
	@FXML private TableColumn<Result, Integer> AudienceScore;
	@FXML private ComboBox<String> choices;
	@FXML private ComboBox<String> recomComboBox;
	@FXML private ListView<Result> recomList;
	@FXML private ListView<String> topList;
	@FXML private ListView<String> movieDetailsList;
	@FXML private ImageView movieImg;
	@FXML private SplitPane movieDetailsPane;
	@FXML private ListView<String> listViewTags;
	@FXML private StackPane overallStackPane;
	private boolean isSearch=false;
	
	// Initializable observable list to hold our database data
	public ObservableList<Result> results;
	public ObservableList<String> tagsResults;
	public Node currentUI=MovieTable;
	private DbConnection dc;
	
	ObservableList<String> comboBoxcontent = FXCollections.observableArrayList("Title","Director Name","Actor Name","Tag","User Name");
	ObservableList<String> listViewContent = FXCollections.observableArrayList("Top popular movies","Top popular directors","Top popular actors");

	public void SearchQueries(){
		isSearch=true;
		
		// Adding it as a query list rather than a single query so that we can support multiple queries per action
		List<String> queryList = new ArrayList<String>();
		MovieTable.toFront();
		switch (choices.getValue()) 
        {
			//query2
            case "Title":  MovieTable.setVisible(true);
            	queryList.add("SELECT m.`movieID`, m.`title`, m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m WHERE m.`title` LIKE ?;");
            	queryList.add("SELECT t.`value` as tag FROM `tags` t,`movies` m, `user_tagged_movies` ut WHERE m.`movieID`=ut.`movieID` AND t.`tagID`=ut.`tagID` AND m.`title` LIKE ?;");
            	break;
            //query4
            case "Director Name":  MovieTable.setVisible(true);
            	queryList.add("SELECT m.`movieID`, m.`title`,m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m, `movie_directors` md  WHERE m.`movieID`=md.`movieID` AND md.`directorName` LIKE ?;");
                break;
            //query5
            case "Actor Name":  MovieTable.setVisible(true);
            	queryList.add("SELECT m.`movieID`, m.`title`,m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m, `movie_actors` ma WHERE m.`movieID`=ma.`movieID` AND ma.`actorName` LIKE ?;");
                break;
            //query6
            case "Tag":  MovieTable.setVisible(true);
            	queryList.add("SELECT m.`movieID`, m.`title`,m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m, `movie_tags` mt, `tags` t WHERE m.`movieID`=mt.`movieID` AND mt.`tagID`=t.`tagID` AND t.`value` LIKE ? ORDER BY (m.`rtAudienceScore`);");
                break;
            //query9 
                // TODO this is not yet implemented
            case "User Name":  MovieTable.setVisible(false);
            	queryList.add("SELECT urm.`userID`, urm.`rating`, m.`title`, urm.`date_year`, urm.`date_month`, urm.`date_day`, urm.`date_hour`, urm.`date_minute`, urm.`date_second` FROM `movies` m, `user_rated_movies` urm WHERE m.`movieID`=urm.`movieID` AND urm.`userID` =? ORDER BY urm.`date_year`, urm.`date_month`, urm.`date_day`, urm.`date_hour`, urm.`date_minute`, urm.`date_second`;");
                break;
             
        }
		
		int counter = 0;
		for (String query : queryList) {
			// The first time this runs, it's a movie query
			if(counter == 0) {
				RunMovieQuery(query, null);
			}
			// The second time it runs, it's a tags query
			else if (counter == 1) {
				RunTagsQuery(query, null);
			}
			counter++;
			
		}
			
		
	}

	// This method will run the related tags query and map the results to the listViewTags listview
	private void RunTagsQuery(String query, String otherpar) {
		// Initializing these variables out here so that they're inside the try catch scope
        Connection conn = dc.Connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			// results is a TagsResult list object that will hold our query result data
            tagsResults = FXCollections.observableArrayList();          
            
			ps = conn.prepareStatement(query);

			if (isSearch==true){
				String searchParameter=searchText.getText();
				ps.setString(1, "%"+searchParameter+"%");				
				}
			else if (otherpar != null && !otherpar.isEmpty()){
				System.out.println(otherpar);
				ps.setString(1,"%"+ otherpar+"%");
			}
			
            // Execute query and store result in a result set
			rs = ps.executeQuery();
			
			// 4. Process the result set
            while (rs.next()) {
            	//listViewTags.getItems().add(("tag"));
            	tagsResults.add(rs.getString("tag"));
            }
        } 
		catch (SQLException ex) {
            System.err.println("Error" + ex);
        } 		
		finally {
			try {
				rs.close();
				conn.close();
				ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
        // This binds the results from the DB to the MovieTable ListView control in the MainGUI.fxml file
		listViewTags.setItems(tagsResults);
		
	}

	public  void RunMovieQuery(String query, String otherpar) {
		// Initializing these variables out here so that they're inside the try catch scope
        Connection conn = dc.Connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			// results is a Result list object that will hold our query result data
            results = FXCollections.observableArrayList();
            
            
			ps = conn.prepareStatement(query);

			if (isSearch==true){
				String searchParameter=searchText.getText();
				ps.setString(1, "%"+searchParameter+"%");				
				}
			else if (otherpar != null && !otherpar.isEmpty()){
				System.out.println(otherpar);
				ps.setString(1,"%"+ otherpar+"%");
			}
			
            // Execute query and store result in a result set
			rs = ps.executeQuery();
			
			// 4. Process the result set
            while (rs.next()) {
            	results.add(new Result(
                		rs.getInt("movieID"), 
                		rs.getString("title"),
                		rs.getInt("year"),
                		rs.getInt("rtAudienceScore"),
                		rs.getString("imdbPictureURL"),
                		rs.getString("rtPictureURL")));
            }
        } 
		catch (SQLException ex) {
            System.err.println("Error" + ex);
        } 		
		finally {
			try {
				rs.close();
				conn.close();
				ps.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// Set cell value factory to tableview.
        // NB.PropertyValue Factory must be the same with the one set in custom Result POJO class.
        
        MovieTitle.setCellValueFactory(new PropertyValueFactory<Result,String>("title"));
        Year.setCellValueFactory(new PropertyValueFactory<Result,Integer>("year"));
        AudienceScore.setCellValueFactory(new PropertyValueFactory<Result,Integer>("rtAudienceScore"));

        // Convert URL to an image
        MoviePicture1.setCellValueFactory(new PropertyValueFactory<Result,String>("rtPictureURL"));               
		MoviePicture1.setCellFactory(new Callback<TableColumn<Result,String>,TableCell<Result,String>>(){        
            @Override
            public TableCell<Result,String> call(TableColumn<Result,String> param) {                
                TableCell<Result,String> cell = new TableCell<Result,String>(){
                	@Override
                    public void updateItem(String item, boolean empty) {                        
                        if(item!=null && !item.isEmpty()){                            
                            ImageView imageview = new ImageView();
                            imageview.setImage(new Image(item));
                            setGraphic(imageview);
                        }
                    }
                };
                return cell;
            }
        }); 
		
		// Convert URL to an image
		/*MoviePicture2.setCellValueFactory(new PropertyValueFactory<Result,String>("imdbPictureURL"));
		MoviePicture2.setCellFactory(new Callback<TableColumn<Result,String>,TableCell<Result,String>>(){        
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
                return cell;
            }
        });  */

		
        // This binds the results from the DB to the MovieTable TableView control in the MainGUI.fxml file
        MovieTable.setItems(null);
        MovieTable.setItems(results);
		

	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		dc = new DbConnection();
		
		MovieTable.setEditable(false);
		
		/*Callback<TableColumn<Result,String>,TableCell<Result,String>> integerCellFactory = new Callback<TableColumn<Result,String>,TableCell<Result,String>>() {
			@Override
			public TableCell call(TableColumn p) {
				MyIntegerTableCell cell = new MyIntegerTableCell();
				cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyEventHandler());
				return cell;
				}
			};
	    */
		Callback<TableColumn<Result,String>,TableCell<Result,String>> stringCellFactory = new Callback<TableColumn<Result,String>,TableCell<Result,String>>() {
				@Override
				public TableCell call(TableColumn p) {
				MyStringTableCell cell = new MyStringTableCell();
				cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyEventHandler());
				return cell;
				}
			};

		
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
                    			query="SELECT m.`movieID`, m.`title`, m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m ORDER BY m.`rtAudienceScore` LIMIT 5;";
                    		//query7
                    		else if (new_val.equals("Top popular directors"))
                    			query="";
                    		//query8
                    		else if (new_val.equals("Top popular actors"))
                    			query="";
                    		
                    		RunMovieQuery(query,null);                         
                    }
                }
				);
	}
	
	class MyIntegerTableCell extends TableCell<Result, Integer> {
		@Override
		public void updateItem(Integer item, boolean empty) {
			super.updateItem(item, empty);
			setText(empty ? null : getString());
			setGraphic(null);
			}
		private String getString() {
			return getItem() == null ? "" : getItem().toString();
			}
		}
	
	class MyStringTableCell extends TableCell<Result, String> {
		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			setText(empty ? null : getString());
			setGraphic(null);
			}
		private String getString() {
			return getItem() == null ? "" : getItem().toString();
			}
		}
	
	
	class MyEventHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent t) {
	
			MovieTable.setVisible(false);
			movieDetailsPane.setVisible(true);
			recomList.setVisible(true);
			
			final Result r= MovieTable.getSelectionModel().getSelectedItem();
			movieImg.setImage(new Image(r.getRtPictureURL()));
			ObservableList<String> detailslistContent=FXCollections.observableArrayList("Title: "+r.getTitle(),"Year: "+r.getYear(),"Audience Score: "+r.getRtAudienceScore());
			movieDetailsList.setItems(detailslistContent);
			ObservableList<String> recomComboBoxContent=FXCollections.observableArrayList("By Genre", "By Director","By Movie Star");
			
			recomComboBox.setVisible(true);
			recomComboBox.setItems(recomComboBoxContent);
			recomComboBox.valueProperty().addListener(new ChangeListener<String>() {
		        @Override public void changed(ObservableValue ov, String t, String t1) {
		            System.out.println("newval"+t1);
		            String query=null;
		            String actorid = null;
		            switch (t1) 
		            {
		    			//query2
		                case "By Genre": {
		                	query="SELECT m.`movieID`, m.`title`, m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m;";;
		                	break;
		                }
		                         
		                case "By Director":  query="SELECT m.`movieID`, m.`title`, m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL`, t.`value` FROM `tags` t,`movies` m, `user_tagged_movies` ut WHERE m.`movieID`=ut.`movieID` AND t.`tagID`=ut.`tagID` AND m.`title` LIKE ?;";;
	                    		 break;
		                case "By Movie Star": {
		                	final String DB_URL = "jdbc:mysql://localhost:3306/movie_recommender?useSSL=false";
		            		final String USER = "root";
		            		final String PASS = "root";
		            		try {
		            			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		            			PreparedStatement ps=conn.prepareStatement("SELECT a.`actorID` FROM `movie_actors` a WHERE a.`movieID`=? AND a.`ranking` IN (SELECT  MAX(ma.`ranking`) FROM `movie_actors` ma WHERE ma.`movieID`=?) ;");
		            			Integer movieID=r.getid();
		            			System.out.println("movieID"+movieID);
		            			ps.setInt(1, movieID);
		            			ps.setInt(2, movieID);
		            			ResultSet myRs = ps.executeQuery();
		            			while(myRs.next()) {
		            				 actorid= myRs.getString("actorID");	
		            			}
		            			
		            			myRs.close();
		            			ps.close();
		            			conn.close();
		            		}
		            		catch (Exception e){
		            			
		            			e.printStackTrace();
		            		}
		            		
		                	query="SELECT m.`movieID`, m.`title`,m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m, `movie_actors` ma WHERE m.`movieID`=ma.`movieID` AND ma.`actorID` LIKE ? ;";
		                	System.out.println("actorid"+actorid);
		                	 break;
		                }
		                			            
		        }
		            isSearch=false;
		            RunMovieQuery(query,actorid);
		            recomList.setCellFactory(new Callback<ListView<Result>, ListCell<Result>>() {
						
		            	@Override
						public ListCell<Result> call(ListView<Result> param) {
							ListCell<Result> cell=new ListCell<Result>(){
								@Override
								protected void updateItem(Result res, boolean empty){
									super.updateItem(res, empty);
									if (res != null){
										ImageView imageview = new ImageView();
										imageview.setImage(new Image(res.getRtPictureURL()));
			                            setGraphic(imageview);
			                            setText(res.getTitle());
									}
								}
								
							};
		            		return cell;
		            		
							
						}
		            }
		        );
		            recomList.setItems(results);  
		            
		        }
		        
		    });
		}
	}
}
