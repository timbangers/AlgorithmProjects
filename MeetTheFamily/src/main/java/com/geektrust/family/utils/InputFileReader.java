package com.geektrust.family.utils;

import com.geektrust.family.constants.Constants;
import com.geektrust.family.constants.InitialValues;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class initializes the family tree and performs operations against it using the inputs from the file
 * @author jesse timbang
 *
 */
public class InputFileReader {

	FamilyTreeProcessor ftp;
	
	public InputFileReader() {
		super();
		ftp = new FamilyTreeProcessor();
		init();
	}

	/**
	 * This init method reads the initial values file and constructs the family tree
	 */
	private void init() {		
		try (Scanner scanner = new Scanner(InitialValues.initialValues)) {
			while (scanner.hasNextLine()) {
				String[] lineElements = scanner.nextLine().split(" ");				
				switch (lineElements[0]) {
				case Constants.ADD_ROOT:
					ftp.addRoot(lineElements[1], lineElements[2]);
					break;
				case Constants.ADD_CHILD:
					ftp.addchild(lineElements[1], lineElements[2], lineElements[3]);
					break;
				case Constants.ADD_SPOUSE:
					ftp.addSpouse(lineElements[1], lineElements[2], lineElements[3]);
					break;
				default:
					break;
				}
			}
		}		
	}
	
	/**
	 * This method will take the input file and process it.
	 * It will print the response on the console
	 * @param file
	 * @throws FileNotFoundException
	 */
	public void process(File file) throws FileNotFoundException {		
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String[] lineElements = scanner.nextLine().split(" ");
				switch (lineElements[0]) {
				case Constants.ADD_CHILD:
					System.out.println(ftp.addchild(lineElements[1], lineElements[2], lineElements[3]));
					break;
				case Constants.GET_RELATIONSHIP:
					System.out.println(ftp.getRelationships(lineElements[1], lineElements[2]));
					break;
				default:
					System.out.println(Constants.INVALID_OPERATION);
					break;
				}
			}
		} 		
	}	
}