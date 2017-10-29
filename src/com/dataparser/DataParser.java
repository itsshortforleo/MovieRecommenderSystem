package com.dataparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Paths;

public class DataParser {

	public DataParser() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		System.out.println("Hi this is the Reel Review Data Parser.");
		
		String moviesFilePath = System.getProperty("user.dir") + "\\Rotten Tomatos Dataset\\movies.dat";
		String tagsFilePath = System.getProperty("user.dir") + "\\Rotten Tomatos Dataset\\tags.dat";
		String movieActorsFilePath = System.getProperty("user.dir") + "\\Rotten Tomatos Dataset\\movie_actors.dat";
		String movieCountriesFilePath = System.getProperty("user.dir") + "\\Rotten Tomatos Dataset\\movie_countries.dat";
		String movieDirectorsFilePath = System.getProperty("user.dir") + "\\Rotten Tomatos Dataset\\movie_directors.dat";
		String movieGenresFilePath = System.getProperty("user.dir") + "\\Rotten Tomatos Dataset\\movie_genres.dat";
		String movieLocationsFilePath = System.getProperty("user.dir") + "\\Rotten Tomatos Dataset\\movie_locations.dat";
		String movieTagsFilePath = System.getProperty("user.dir") + "\\Rotten Tomatos Dataset\\movie_tags.dat";
		String userRatedMoviesFilePath = System.getProperty("user.dir") + "\\Rotten Tomatos Dataset\\user_ratedmovies.dat";
		String userRatedMoviesTimestampsFilePath = System.getProperty("user.dir") + "\\Rotten Tomatos Dataset\\user_ratedmovies-timestamps.dat";
		String userTaggedMoviesFilePath = System.getProperty("user.dir") + "\\Rotten Tomatos Dataset\\user_taggedmovies.dat";
		String userTaggedMoviesTimestamps = System.getProperty("user.dir") + "\\Rotten Tomatos Dataset\\user_taggedmovies-timestamps.dat";

		DataAccess da = new DataAccess();
		
		da.insertData(moviesFilePath, "movies");
		da.insertData(tagsFilePath, "tags");
		da.insertData(movieActorsFilePath, "movie_actors");
		da.insertData(movieCountriesFilePath, "movie_countries");
		da.insertData(movieDirectorsFilePath, "movie_directors");
		da.insertData(movieGenresFilePath, "movie_genres");
		
		da.insertMovieLocations(movieLocationsFilePath);
		
		da.insertData(movieTagsFilePath, "movie_tags");
		da.insertData(userRatedMoviesFilePath, "user_rated_movies");
		da.insertData(userRatedMoviesTimestampsFilePath, "user_rated_movies_timestamps");
		da.insertData(userTaggedMoviesFilePath, "user_tagged_movies");
		da.insertData(userTaggedMoviesTimestamps, "user_tagged_movies_timestamps");  
		
		System.out.println("Finished inserting data.");

 	}
}
