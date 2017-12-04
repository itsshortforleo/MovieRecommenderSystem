package com.userinterface;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class userInfo {

	private SimpleIntegerProperty userid;
	private SimpleStringProperty  title;
	private SimpleIntegerProperty rating;
	private SimpleIntegerProperty year;
	private SimpleIntegerProperty month;
	private SimpleIntegerProperty day;
	private SimpleIntegerProperty hour;
	private SimpleIntegerProperty minute;
	private SimpleIntegerProperty second;
	
	// Constructor for the class
	public userInfo(Integer userid,String title, Integer rating, Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second) {
		super();
		this.userid = new SimpleIntegerProperty(userid);
		this.title = new SimpleStringProperty (title);
		this.rating = new SimpleIntegerProperty(rating);
		this.year = new SimpleIntegerProperty(year);
		this.month = new SimpleIntegerProperty(month);
		this.minute = new SimpleIntegerProperty(minute);
		this.second = new SimpleIntegerProperty(second);
		
	}
	
	//get methods
	public int getYear(){
		return year.get();
	}
	
	public String getTitle(){
		return title.get();
	}
	
	public int getMonth(){
		return month.get();
	}
	
	public int getMinute(){
		return minute.get();
	}
	public int getSecond(){
		return second.get();
	}
	

}
