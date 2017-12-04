package com.userinterface;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TopDirectorsResult {
	
	private final SimpleStringProperty directorID;
	private final SimpleStringProperty  directorName;
	private final SimpleIntegerProperty directorMovieCount;
	private final SimpleFloatProperty averageAudienceScore;
	
	// Constructor for the TopDirectorsResult class
	public TopDirectorsResult(String directorID, String directorName,
			int directorMovieCount, float averageAudienceScore) {
		super();
		this.directorID = new SimpleStringProperty(directorID);
		this.directorName = new SimpleStringProperty(directorName);
		this.directorMovieCount = new SimpleIntegerProperty(directorMovieCount);
		this.averageAudienceScore = new SimpleFloatProperty(averageAudienceScore);
	}

	public String getDirectorID() {
		return directorID.get();
	}

	public String getDirectorName() {
		return directorName.get();
	}

	public int getDirectorMovieCount() {
		return directorMovieCount.get();
	}

	public float getAverageAudienceScore() {
		return averageAudienceScore.get();
	}
}


