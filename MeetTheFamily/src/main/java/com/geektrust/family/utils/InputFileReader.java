package com.geektrust.family.utils;

import com.geektrust.family.constants.Constants;
import com.geektrust.family.constants.InitialValues;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputFileReader {

	FamilyTreeProcessor familyTree;
	
	public InputFileReader() {
		super();
		familyTree = new FamilyTreeProcessor();
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
					familyTree.addRoot(lineElements[1], lineElements[2]);
					break;
				case Constants.ADD_CHILD:
					familyTree.addchild(lineElements[1], lineElements[2], lineElements[3]);
					break;
				case Constants.ADD_SPOUSE:
					familyTree.addSpouse(lineElements[1], lineElements[2], lineElements[3]);
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
					System.out.println(familyTree.addchild(lineElements[1], lineElements[2], lineElements[3]));
					break;
				case Constants.GET_RELATIONSHIP:
					System.out.println(familyTree.getRelationships(lineElements[1], lineElements[2]));
					break;
				default:
					System.out.println(Constants.INVALID_OPERATION);
					break;
				}
			}
		} 		
	}	
}