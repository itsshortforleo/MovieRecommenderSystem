package com.dataparser;

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
		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("MainGUI.fxml"));
		Scene scene= new Scene(root); 
		primaryStage.setTitle("Reel Review");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
