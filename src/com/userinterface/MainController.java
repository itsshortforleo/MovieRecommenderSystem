package com.userinterface;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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
	@FXML private Pane userPane;
	@FXML private TableView<userInfo> userTable;
	@FXML private TableColumn<userInfo, Integer> userid;
	@FXML private TableColumn<userInfo, String> movieTitle;
	@FXML private TableColumn<userInfo, Integer> rating;
	@FXML private TableColumn<userInfo, Integer> year;
	@FXML private TableColumn<userInfo, Integer> month;
	@FXML private TableColumn<userInfo, Integer> day;
	@FXML private TableColumn<userInfo, Integer> hour;
	@FXML private TableColumn<userInfo, Integer> minute;
	@FXML private TableColumn<userInfo, Integer> second;
	
	@FXML private TableView<TopDirectorsResult> tableViewTopDirectorsResult;
	@FXML private TableColumn<TopDirectorsResult, String> directorNameColumn;
	@FXML private TableColumn<TopDirectorsResult, Integer> directorMovieCount;
	@FXML private TableColumn<TopDirectorsResult, Float> directorAverageAudienceScore;

	@FXML private Spinner limitTo;
	
	@FXML private TableView<TopActorsResult> tableViewTopActorsResult;
	@FXML private TableColumn<TopActorsResult, String> actorNameColumn;
	@FXML private TableColumn<TopActorsResult, Integer> actorMovieCount;
	@FXML private TableColumn<TopActorsResult, Float> actorAverageAudienceScore;
	
	private boolean isSearch=false;
	
	// Initializable observable list to hold our database data
	public ObservableList<Result> results;
	public ObservableList<userInfo> Userresults;
	public ObservableList<String> tagsResults;
	public ObservableList<TopDirectorsResult> directorResults;
	public ObservableList<TopActorsResult> actorsResults;

	public Node currentUI=MovieTable;
	private DbConnection dc;
	
	ObservableList<String> comboBoxcontent = FXCollections.observableArrayList("Title","Director Name","Actor Name","Tag","User ID","Genre");
	ObservableList<String> listViewContent = FXCollections.observableArrayList("Top popular movies","Top popular directors","Top popular actors");

	public void SearchQueries(){
		isSearch=true;
		String limitStr = "";
		if (Integer.valueOf(limitTo.getEditor().getText()) > 0)
			limitStr = "LIMIT " + limitTo.getEditor().getText();

		// Adding it as a query list rather than a single query so that we can support multiple queries per action
		String query = null;
		
		switch (choices.getValue()) 
        {
			//query2
            case "Title":  MovieTable.toFront();
                query="SELECT m.`movieID`, m.`title`, m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m WHERE m.`title` LIKE ?" + limitStr + ";";
            	RunMovieQuery(query, null);
            	query="SELECT t.`value` as tag FROM `tags` t,`movies` m, `user_tagged_movies` ut WHERE m.`movieID`=ut.`movieID` AND t.`tagID`=ut.`tagID` AND m.`title` LIKE ?" + limitStr + ";";
            	RunTagsQuery(query, null);
            	break;
            //query4
            case "Director Name":  MovieTable.toFront();
            	query="SELECT m.`movieID`, m.`title`,m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m, `movie_directors` md  WHERE m.`movieID`=md.`movieID` AND md.`directorName` LIKE ?" + limitStr + ";";
            	RunMovieQuery(query, null);
            	break;
            //query5
            case "Actor Name":  MovieTable.toFront();
            	query="SELECT m.`movieID`, m.`title`,m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m, `movie_actors` ma WHERE m.`movieID`=ma.`movieID` AND ma.`actorName` LIKE ?" + limitStr + ";";
            	RunMovieQuery(query, null);
            	break;
            //query6
            case "Tag":  MovieTable.toFront();
            	query="SELECT m.`movieID`, m.`title`,m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m, `movie_tags` mt, `tags` t WHERE m.`movieID`=mt.`movieID` AND mt.`tagID`=t.`tagID` AND t.`value` LIKE ? ORDER BY (m.`rtAudienceScore`)" + limitStr + ";";
            	RunMovieQuery(query, null);
            	break;
            //query9 
            case "User ID":  userPane.toFront();
            	query="SELECT urm.`userID`, urm.`rating`, m.`title`, urm.`date_year`, urm.`date_month`, urm.`date_day`, urm.`date_hour`, urm.`date_minute`, urm.`date_second` FROM `movies` m, `user_rated_movies` urm WHERE m.`movieID`=urm.`movieID` AND urm.`userID` =? ORDER BY urm.`date_year`, urm.`date_month`, urm.`date_day`, urm.`date_hour`, urm.`date_minute`, urm.`date_second`" + limitStr + ";";
            	RunUserQuery(query, null);
            	break;
             
        }
		isSearch = false;
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
			} catch (NullPointerException e) {
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
	// this is for Q9
	public  void RunUserQuery(String query, String otherpar) {
		// Initializing these variables out here so that they're inside the try catch scope
        Connection conn = dc.Connect();
        Integer totalRecored=0;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        String searchParameter=null;
        HashMap<String,Integer> hmGenre=new HashMap<String,Integer>();
        
		try {
			// results is a Result list object that will hold our query result data
			Userresults = FXCollections.observableArrayList();
         	ps = conn.prepareStatement(query);
         	if (isSearch==true){
         	   searchParameter=searchText.getText();
				ps.setString(1, searchParameter);				
				}
            // Execute query and store result in a result set
			rs = ps.executeQuery();
			System.out.println(ps);
			// 4. Process the result set
            while (rs.next()) {
            	Userresults.add(new userInfo(
                		rs.getInt("userID"), 
                		rs.getString("title"),
                		rs.getInt("rating"),
                		rs.getInt("date_year"),
                		rs.getInt("date_month"),
                		rs.getInt("date_day"),
                		rs.getInt("date_hour"),
                		rs.getInt("date_minute"),
                		rs.getInt("date_second")));
            }
            
            ps2 = conn.prepareStatement("SELECT COUNT(*) AS total FROM `movies` m, `user_rated_movies` urm WHERE m.`movieID`=urm.`movieID` AND urm.`userID` =? ;");
            ps2.setString(1, searchParameter);
            rs2 =ps2.executeQuery();
            while (rs2.next()) {
            	 totalRecored=rs.getInt("total");
            }
            System.out.println("totalRecored"+ totalRecored);
            
            
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
		
		userid.setCellValueFactory(new PropertyValueFactory<userInfo,Integer>("userid"));
		movieTitle.setCellValueFactory(new PropertyValueFactory<userInfo,String>("title"));
		rating.setCellValueFactory(new PropertyValueFactory<userInfo,Integer>("rating"));
		year.setCellValueFactory(new PropertyValueFactory<userInfo,Integer>("year"));
		month.setCellValueFactory(new PropertyValueFactory<userInfo,Integer>("month"));
		day.setCellValueFactory(new PropertyValueFactory<userInfo,Integer>("day"));
		hour.setCellValueFactory(new PropertyValueFactory<userInfo,Integer>("hour"));
		minute.setCellValueFactory(new PropertyValueFactory<userInfo,Integer>("minute"));
		second.setCellValueFactory(new PropertyValueFactory<userInfo,Integer>("second"));
		
       
		userTable.setItems(null);
		userTable.setItems(Userresults);
		

	}


	public  void RunTopDirectorsQuery(String query, String otherpar) {
		// Initializing these variables out here so that they're inside the try catch scope
        Connection conn = dc.Connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			// results is a TopDirectorsResult list object that will hold our query result data
			directorResults = FXCollections.observableArrayList();
            
            
			ps = conn.prepareStatement(query);

			if (otherpar != null && !otherpar.isEmpty()){
				System.out.println(otherpar);
				ps.setString(1,"%"+ otherpar+"%");
			}
			
            // Execute query and store result in a result set
			rs = ps.executeQuery();
			
			// 4. Process the result set
            while (rs.next()) {
            	directorResults.add(new TopDirectorsResult(
            			rs.getString("directorID"),
            			rs.getString("directorName"),
            			rs.getInt("directorMovieCount"),
            			rs.getFloat("averageAudienceScore")));
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
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		// Bind director columns
		directorNameColumn.setCellValueFactory(new PropertyValueFactory<TopDirectorsResult,String>("directorName"));
		directorMovieCount.setCellValueFactory(new PropertyValueFactory<TopDirectorsResult,Integer>("directorMovieCount"));
		directorAverageAudienceScore.setCellValueFactory(new PropertyValueFactory<TopDirectorsResult,Float>("averageAudienceScore"));

        // This binds the results from the DB to the Director TableView control in the MainGUI.fxml file
       tableViewTopDirectorsResult.setItems(null);
       tableViewTopDirectorsResult.setItems(directorResults);
	}
	
	public  void RunTopActorsQuery(String query, String otherpar) {
		// Initializing these variables out here so that they're inside the try catch scope
        Connection conn = dc.Connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
		try {
			// results is a TopActorsResult list object that will hold our query result data
			actorsResults = FXCollections.observableArrayList();
            
            
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
            	actorsResults.add(new TopActorsResult(
            			rs.getString("actorID"),
            			rs.getString("actorName"),
            			rs.getInt("actorMovieCount"),
            			rs.getFloat("averageAudienceScore")));
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
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		// Bind director columns
		actorNameColumn.setCellValueFactory(new PropertyValueFactory<TopActorsResult,String>("actorName"));
		actorMovieCount.setCellValueFactory(new PropertyValueFactory<TopActorsResult,Integer>("actorMovieCount"));
		actorAverageAudienceScore.setCellValueFactory(new PropertyValueFactory<TopActorsResult,Float>("averageAudienceScore"));

        // This binds the results from the DB to the Director TableView control in the MainGUI.fxml file
       tableViewTopActorsResult.setItems(null);
       tableViewTopActorsResult.setItems(actorsResults);
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

		limitTo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 10));
		
		//TopList Change Listener
		topList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> ov, 
                        String old_val, String new_val) {
                    		String query = null;
                    		//query1 // need to implement query3 here
                    		if (new_val.equals("Top popular movies")) {
                    			MovieTable.toFront();
                    			query="SELECT m.`movieID`, m.`title`, m.`year`, m.`rtAudienceScore`, m.`rtPictureURL`, m.`imdbPictureURL` FROM `movies` m ORDER BY m.`rtAudienceScore` LIMIT 5;";
                        		RunMovieQuery(query,null);    
                    		}
                    		//query7
                    		else if (new_val.equals("Top popular directors")) {
                    			tableViewTopDirectorsResult.toFront();
                    			query="select md.`directorID`,  md.`directorName`, count(md.directorID) as directorMovieCount, avg(m.rtAudienceScore) as averageAudienceScore "
                            			+ " from `movie_directors` md "
                            			+ " join movies m on m.movieID = md.movieID "
                            			+ " group by md.directorID, md.directorName "
                            			+ " having count(md.`movieID`) >=10 "
                            			+ " order by avg(m.rtAudienceScore) desc "
                            			+ " LIMIT 10;";
                    			RunTopDirectorsQuery(query, null);
                    		}	
                    		//query8
                    		else if (new_val.equals("Top popular actors")) {
                    			tableViewTopActorsResult.toFront();
                    			query="select ma.`actorID`,  ma.`actorName`, count(ma.actorID) as actorMovieCount, avg(m.rtAudienceScore) as averageAudienceScore\n" + 
                    					"from `movie_actors` ma\n" + 
                    					"join movies m on m.movieID = ma.movieID\n" + 
                    					"group by ma.actorID, ma.actorName\n" + 
                    					"having count(ma.`movieID`) >=10\n" + 
                    					"order by avg(m.rtAudienceScore) desc\n" + 
                    					"LIMIT 10;";
                    			RunTopActorsQuery(query, null);
                    		}
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
