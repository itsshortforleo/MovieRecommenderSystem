package com.dataparser;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

//for any result in the table
public class Result {
	
	private SimpleStringProperty  title;
	private SimpleIntegerProperty year;
	private SimpleIntegerProperty rtAudienceScore;
	private SimpleStringProperty  rtPictureURL;
	private SimpleStringProperty imdbPictureURL;
	
	public Result(String title, Integer year, Integer rtAudienceScore,
			String rtPictureURL, String imdbPictureURL) {
		super();
		this.title = new SimpleStringProperty ( title);
		this.year = new SimpleIntegerProperty(year);
		this.rtAudienceScore = new SimpleIntegerProperty(rtAudienceScore);
		this.rtPictureURL = new SimpleStringProperty(rtPictureURL);
		this.imdbPictureURL = new SimpleStringProperty(imdbPictureURL);
	}
	
	//get methods
	public int getYear(){
		return year.get();
	}
	
	public int getRtAudienceScore(){
		return rtAudienceScore.get();
	}
	
	public String getTitle(){
		return title.get();
	}
	
	public String getRtPictureURL(){
		return rtPictureURL.get();
	}
	
	public String getImdbPictureURL(){
		return imdbPictureURL.get();
	}


}
