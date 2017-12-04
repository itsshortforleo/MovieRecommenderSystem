package com.userinterface;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class userGenre {
	private SimpleStringProperty  genre;
	private SimpleFloatProperty perc;

	public userGenre(float perc,String genre) {
		super();
		this.perc = new SimpleFloatProperty(perc);
		this.genre = new SimpleStringProperty (genre);}
	public String getGenre(){
		return genre.get();
	}
	public float getperc(){
		return perc.get();
	}
	
}
