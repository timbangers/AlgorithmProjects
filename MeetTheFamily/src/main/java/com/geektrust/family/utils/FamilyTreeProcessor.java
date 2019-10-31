package com.geektrust.family.utils;

import com.geektrust.family.constants.Constants;
import java.util.ArrayList;
import java.util.List;

import com.geektrust.family.constants.Gender;
import com.geektrust.family.objects.Person;

/**
 * This class contains the core logic to:
 * 1. add a child to a family
 * 2. get the names of the relatives given a person and relationship
 * @author jesse timbang
 *
 */
public class FamilyTreeProcessor {

	private Person root;	

	/**
	 * This method is used to initialize the tree and add any spouse
	 * @param personName
	 * @param spouseName
	 * @param gender
	 */
	public void addSpouse(String personName, String spouseName, String gender) {
		Person person = findPerson(getRoot(), personName);
		if (person != null && person.getSpouse() == null) {
			Gender g = (Gender.Female.name().equals(gender)) ? Gender.Female : Gender.Male;
			Person spouse = new Person(spouseName, g, null, null);
			spouse.setSpouse(person);
			person.setSpouse(spouse);
		}
	}
	
	/**
	 * This method adds a child to a parent (female)
	 * @param motherName
	 * @param childName
	 * @param gender
	 * @return response
	 */
	public String addchild(String motherName, String childName, String gender) {
		String response;
		Person personToFind = findPerson(getRoot(), motherName);
		//check if person was found
		if (personToFind == null) {
			response = Constants.PERSON_NOT_FOUND;
		} else if (childName == null || gender == null) { //check if there is a child to be added
			response = Constants.CHILD_ADDITION_FAILED;
		} else if (personToFind.getGender() == Gender.Female && personToFind.getSpouse() != null) {
			//adding child if the parent is female AND married
			Gender g = (Gender.Female.name().equals(gender)) ? Gender.Female : Gender.Male;
			Person child = new Person(childName, g, personToFind.getSpouse(), personToFind);
			personToFind.addChild(child);
			response = Constants.CHILD_ADDITION_SUCCEEDED;
		} else {
			response = Constants.CHILD_ADDITION_FAILED;
		}
		return response;
	}
	
	/**
	 * This method is used to initialize the tree and add the root element (female, Queen Margaret)
	 * @param name
	 * @param gender
	 */
	public void addRoot(String name, String gender) {
		this.setRoot(new Person(name, Gender.Female, null, null));		
	}
	
	/**
	 * This method will return a person given its parent (or head) thru recursion
	 * @param parent
	 * @param personName
	 * @return personToFind
	 */
	public Person findPerson(Person parent, String personName) {
		if (personName == null || parent == null) {
			return null;
		}
		if (personName.equals(parent.getName())) {
			return parent;
		} else if (parent.getSpouse() != null && personName.equals(parent.getSpouse().getName())) {
			return parent.getSpouse();
		}
		List<Person> childlist = new ArrayList<>();
		if (parent.getGender() == Gender.Female) {
			childlist = parent.getChildrenList();
		} else if (parent.getSpouse() != null) {
			childlist = parent.getSpouse().getChildrenList();
		}
		Person personToFind = null;
		for (Person p : childlist) {
			personToFind = findPerson(p, personName);
			if (personToFind != null) {
				break;
			}
		}		
		return personToFind;
	}

	/**
	 * This method takes in a relationship string and returns the name of the relative for that
	 * relationship
	 * @param person
	 * @param relationship
	 * @return nameOfRelative
	 */
	public String getRelationship(Person person, String relationship) {
		String nameOfRelative = "";
		switch (relationship) {
		case Constants.PATERNAL_UNCLE:
			if (person.getFather() != null)
				nameOfRelative = person.getFather().findAuntOrUncle(Gender.Male);
			break;
		case Constants.MATERNAL_UNCLE:
			if (person.getMother() != null)
				nameOfRelative = person.getMother().findAuntOrUncle(Gender.Male);
			break;			
		case Constants.PATERNAL_AUNT:
			if (person.getFather() != null)
				nameOfRelative = person.getFather().findAuntOrUncle(Gender.Female);
			break;
		case Constants.MATERNAL_AUNT:
			if (person.getMother() != null)
				nameOfRelative = person.getMother().findAuntOrUncle(Gender.Female);
			break;
		case Constants.SISTER_IN_LAW:
			nameOfRelative = findInLaws(person, Gender.Female);
			break;
		case Constants.BROTHER_IN_LAW:
			nameOfRelative = findInLaws(person, Gender.Male);
			break;
		case Constants.SON:
			nameOfRelative = person.findChild(Gender.Male);
			break;
		case Constants.DAUGHTER:
			nameOfRelative = person.findChild(Gender.Female);
			break;		
		case Constants.SIBLINGS:
			nameOfRelative = person.findSiblings();
			break;
		//not in requirements but added anyways	
		case Constants.BROTHER:
			nameOfRelative = person.findSiblings(Gender.Male);
			break;
		//not in requirements but added anyways	
		case Constants.SISTER:
			nameOfRelative = person.findSiblings(Gender.Female);
			break;		
		default:
			nameOfRelative = Constants.UNKNOWN;
			break;
		}
		return (nameOfRelative.isEmpty()) ? Constants.NONE : nameOfRelative;
	}
	
	/**
	 * This method finds a person and returns his/her relatives according to the given relationship
	 * @param person
	 * @param relationship
	 * @return nameOfRelative
	 */
	public String getRelationships(String person, String relationship) {	
		String nameOfRelative;
		Person personToFind = findPerson(getRoot(), person);
		if (personToFind == null) {
			nameOfRelative = Constants.PERSON_NOT_FOUND;
		} else if (relationship == null) {
			nameOfRelative = Constants.RELATIONSHIP_IS_NULL;
		} else {
			nameOfRelative = getRelationship(personToFind, relationship);
		}
		return nameOfRelative;
	}

	/**
	 * This method will find an in law depending on their stature
	 * @param person
	 * @param gender
	 * @return
	 */
	public String findInLaws(Person person, Gender gender) {
		StringBuilder sb = new StringBuilder("");
		String nameOfRelative = "";
		// if single
		if (person.getSpouse() == null) {
			nameOfRelative = person.getMother().findInLawIfSingle(gender, person.getName());
		}
		//if spouse who is outside the family
		if (person.getSpouse() != null &&  person.getSpouse().getMother() != null) {
			nameOfRelative = person.getSpouse().getMother().findInLawIfNotChildren(gender, person);
		}
		//if a son or daughter
		if (person.getMother() != null ||person.getFather() !=null) {
			nameOfRelative = person.getMother().findInLawIfChildren(gender, person);
		}
		sb.append(nameOfRelative);
		return sb.toString().trim();
	}

	public Person getRoot() {
		return root;
	}

	public void setRoot(Person root) {
		this.root = root;
	}

}
