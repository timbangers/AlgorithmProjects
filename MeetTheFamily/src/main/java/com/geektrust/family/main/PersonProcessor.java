package com.geektrust.family.main;

import java.io.File;
import java.io.FileNotFoundException;

import com.geektrust.family.util.InputFileReader;

/**
 * @author jesse timbang
 * This class contains the main method to run the MeetTheFamily program
 * It accepts a file as an argument and processes it
 */
public class PersonProcessor {

	/**
	 * Main method of this class
	 * @param args
	 */
	public static void main(String args[]) {

		InputFileReader processor = new InputFileReader();
		File file = new File(args[0]);
		try {
			processor.process(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
}
