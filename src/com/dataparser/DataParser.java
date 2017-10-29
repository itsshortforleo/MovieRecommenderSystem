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
		
		DataAccess da = new DataAccess();
		da.insertData();
		
	    // Insert the movies table
		
		String fileName = System.getProperty("user.dir") + "\\Rotten Tomatos Dataset\\movies.dat";
				
        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader br = 
                new BufferedReader(fileReader);

            while((line = br.readLine()) != null) {
            	/*int count = 0;
            	String[] tokens = line.split("\t");
            	for (int i = 0; i < tokens.length; i++) {
					tokens[i]
				}
            	String name = tokens[0];
            	String value = tokens[1];*/
            	
                //System.out.println(line);
            }   

            // Always close files.
            br.close();         
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
