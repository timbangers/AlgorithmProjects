package com.geektrust.family.util;

import com.geektrust.family.constants.Constants;
import com.geektrust.family.constants.InitialValues;
import com.geektrust.family.constants.Operations;

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

	private void init() {		
		try (Scanner sc = new Scanner(InitialValues.initialValues)) {
			while (sc.hasNextLine()) {
				String[] commandParams = sc.nextLine().split(";");
				switch (commandParams[0]) {

				case Constants.ADD_FAMILY_HEAD:
					familyTree.addFamilyHead(commandParams[1], commandParams[2]);
					break;

				case Constants.ADD_CHILD:
					familyTree.addchild(commandParams[1], commandParams[2], commandParams[3]);
					break;

				case Constants.ADD_SPOUSE:
					familyTree.addSpouse(commandParams[1], commandParams[2], commandParams[3]);
					break;

				default:
					break;
				}
			}
		}		
		
	}
	
	public void processFileFromInput(File file) {
		
		try (Scanner sc = new Scanner(file)) {
			while (sc.hasNextLine()) {
				String[] commandParams = sc.nextLine().split(" ");
				String commandResult;
				switch (commandParams[0]) {
				case Constants.ADD_CHILD:
					commandResult = familyTree.addchild(commandParams[1], commandParams[2], commandParams[3]);
					break;

				case Constants.GET_RELATIONSHIP:
					commandResult = familyTree.getRelationships(commandParams[1], commandParams[2]);
					break;

				default:
					commandResult = Constants.INVALID_COMMAND;
					break;
				}
				System.out.println(commandResult);				
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found!! Please check the file and the location provided!");
		}			
	}	
}