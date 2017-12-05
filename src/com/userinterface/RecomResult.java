package com.userinterface;

import javafx.beans.property.SimpleStringProperty;

public class RecomResult {
	private SimpleStringProperty  title;
	private static SimpleStringProperty  rtPictureURL;
	
	public RecomResult(String title,String rtPictureURL) {
	super();
	this.title = new SimpleStringProperty (title);
	this.rtPictureURL = new SimpleStringProperty(rtPictureURL);}
	
	public String getTitle(){
		return title.get();
	}
	
	public static String getRtPictureURL(){
		return rtPictureURL.get();
	}
	
}
