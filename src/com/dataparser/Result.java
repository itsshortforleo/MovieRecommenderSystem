package com.dataparser;
//for any result in the table
public class Result {
	
	private String  title;
	private int year;
	private int rtAudienceScore;
	private String  rtPictureURL;
	private String imdbPictureURL;
	//get methods
	public int getYear(){
		return year;
	}
	
	public int getRtAudienceScore(){
		return rtAudienceScore;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getRtPictureURL(){
		return rtPictureURL;
	}
	
	public String getImdbPictureURL(){
		return imdbPictureURL;
	}
	// set methods
	public void setYear(int year){
		this.year= year;
	}
	
	public void setRtAudienceScore(int rtAudienceScore){
		this.rtAudienceScore= rtAudienceScore;
	}
	
	public void setTitle(String title){
		this.title= title;
	}
	
	public void setRtPictureURL(String rtPictureURL){
		this.rtPictureURL= rtPictureURL;
	}
	
	public void setImdbPictureURL(String imdbPictureURL){
		this.imdbPictureURL= imdbPictureURL;
	}

}
