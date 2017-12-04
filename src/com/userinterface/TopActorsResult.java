package com.userinterface;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TopActorsResult {
	
	private final SimpleStringProperty actorID;
	private final SimpleStringProperty  actorName;
	private final SimpleIntegerProperty actorMovieCount;
	private final SimpleFloatProperty averageAudienceScore;
	
	// Constructor for the TopDirectorsResult class
	public TopActorsResult(String actorID, String actorName,
			int actorMovieCount, float averageAudienceScore) {
		super();
		this.actorID = new SimpleStringProperty(actorID);
		this.actorName = new SimpleStringProperty(actorName);
		this.actorMovieCount = new SimpleIntegerProperty(actorMovieCount);
		this.averageAudienceScore = new SimpleFloatProperty(averageAudienceScore);
	}

	public String getActorID() {
		return actorID.get();
	}

	public String getActorName() {
		return actorName.get();
	}

	public int getActorMovieCount() {
		return actorMovieCount.get();
	}

	public float getAverageAudienceScore() {
		return averageAudienceScore.get();
	}
}


