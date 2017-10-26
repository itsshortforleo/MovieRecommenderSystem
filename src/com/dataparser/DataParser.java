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

		System.out.println("Hi this is the Movie Recommender Data Parser.");
		String fileName = "C:\\Users\\Leo\\git\\CSC204Project\\Rotten Tomatos Dataset\\movie_actors.dat";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	
                System.out.println(line);
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
		
	}

}
