package com.userinterface;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

//for any result in the table
public class Result {
	private int mid;
	private SimpleStringProperty  title;
	private SimpleIntegerProperty year;
	private SimpleIntegerProperty rtAudienceScore;
	private static SimpleStringProperty  rtPictureURL;
	private static SimpleStringProperty imdbPictureURL;
	
	public Result(Integer mid,String title, Integer year, Integer rtAudienceScore,
			String rtPictureURL, String imdbPictureURL) {
		super();
		this.mid=mid;
		this.title = new SimpleStringProperty ( title);
		this.year = new SimpleIntegerProperty(year);
		this.rtAudienceScore = new SimpleIntegerProperty(rtAudienceScore);
		this.rtPictureURL = new SimpleStringProperty(rtPictureURL);
		this.imdbPictureURL = new SimpleStringProperty(imdbPictureURL);
	}
	
	//get methods
	public int getid(){
		return mid;
	}
	
	public int getYear(){
		return year.get();
	}
	
	public int getRtAudienceScore(){
		return rtAudienceScore.get();
	}
	
	public String getTitle(){
		return title.get();
	}
	
	public static String getRtPictureURL(){
		return rtPictureURL.get();
	}
	
	public static String getImdbPictureURL(){
		return imdbPictureURL.get();
	}


}
