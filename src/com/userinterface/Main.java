package com.userinterface;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static void main (String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// Make path relative to the project path, not a particular user's path
		//URL url = new File("src/com/userinterface/MainGUI.fxml").toURL();

/*		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainGUI.fxml"));
		Scene scene= new Scene(root); 
		primaryStage.setTitle("Reel Review");
		primaryStage.setScene(scene);
		primaryStage.show();*/
		
		
		
		String sceneFile = "/fxml/MainGUI.fxml";
	    Parent root = null;
	    URL    url  = null;
	    try
	    {
	        url  = getClass().getResource( sceneFile );
	        root = FXMLLoader.load( url );
	        System.out.println( "  fxmlResource = " + sceneFile );
	        
			Scene scene= new Scene(root); 
			primaryStage.setTitle("Reel Review");
			primaryStage.setScene(scene);
			primaryStage.show();
	        
	    }
	    catch ( Exception ex )
	    {
	        System.out.println( "Exception on FXMLLoader.load()" );
	        System.out.println( "  * url: " + url );
	        System.out.println( "  * " + ex );
	        System.out.println( "    ----------------------------------------\n" );
	        throw ex;
	    }
	    
	    
		
		
	}
}
