package com.userinterface;

import javafx.beans.property.SimpleStringProperty;

public class TagsResult {
	private final SimpleStringProperty tag;

	public TagsResult(String tag) {
		super();
		this.tag = new SimpleStringProperty(tag);
	}

	public String getTag() {
		return tag.get();
	}
}
