package com.geektrust.family.objects;

import java.util.ArrayList;
import java.util.List;
import com.geektrust.family.constants.Gender;

/**
 * @author jesse timbang
 * This Person class is a node in a tree
 *
 */
/**
 * @author jesse
 *
 */
public class Person {

	private String name;
	private Gender gender;
	private Person mother;
	private Person father;
	private Person spouse;
	private List<Person> childrenList;	

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Person getMother() {
		return mother;
	}

	public void setMother(Person mother) {
		this.mother = mother;
	}

	public Person getFather() {
		return father;
	}

	public void setFather(Person father) {
		this.father = father;
	}

	public Person getSpouse() {
		return spouse;
	}

	public void setSpouse(Person spouse) {
		this.spouse = spouse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Person> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<Person> childrenList) {
		this.childrenList = childrenList;
	}

	/**
	 * This constructor creates the Person object with the following values: 
	 * @param name - name of the Person
	 * @param gender - gender of the Person
	 * @param father - father of the Person in the family tree
	 * @param mother - mother of the Person in the family tree
	 */
	public Person(String name, Gender gender, Person father, Person mother) {
		this.setName(name);
		this.setGender(gender);
		this.setMother(mother);
		this.setFather(father);
		this.setSpouse(null);
		this.setChildrenList(new ArrayList<Person>());
	}


	/**
	 * Adds a child to the children list
	 * @param child
	 */
	public void addChild(Person child) {
		this.getChildrenList().add(child);
	}

	/**
	 * This method is used for finding siblings
	 * @return siblingName
	 */
	public String findSiblings() {
		StringBuilder sb = new StringBuilder();
		if (this.getMother() != null) {
			for (Person p : this.getMother().getChildrenList()) {
				if (!this.getName().equals(p.getName()) ) {
					sb.append(p.getName()).append(" ");
				}
			}	
		}
		
		return sb.toString().trim();
	}
	
	/**
	 * This finds the aunt or uncle based on the gender
	 * @param gender
	 * @return relativeName
	 */
	public String findAuntOrUncle(Gender gender) {
		StringBuilder sb = new StringBuilder();
		if (this.getMother() != null) {
			for (Person p : this.getMother().getChildrenList()) {
				if (!this.getName().equals(p.getName()) && p.getGender() == gender) {
					sb.append(p.getName()).append(" ");
				}
			}
		}
		return sb.toString().trim();
	}
	
	/**
	 * This overloaded method is used for finding siblings of specific gender
	 * @return relativeName
	 */
	public String findSiblings(Gender gender) {
		StringBuilder sb = new StringBuilder();
		if (this.getMother() != null) {
			for (Person m : this.getMother().getChildrenList()) {
				if (!this.getName().equals(m.getName()) && this.getGender() != gender ) {
					sb.append(m.getName()).append(" ");
				}
			}	
		}		
		return sb.toString().trim();
	}
	
	/**
	 * This method finds a child 
	 * @param gender
	 * @return childName
	 */
	public String findChild(Gender gender) {
		StringBuilder sb = new StringBuilder();
		//check if the parent in question is male, if yes, need to look at the spouse
		if (this.getGender() == Gender.Male) {
			for (Person m : this.getSpouse().getChildrenList()) {
				if (m.getGender() == gender) {
					sb.append(m.getName()).append(" ");
				}
			}
		} else {
			//process it normally for females
			for (Person m : this.getChildrenList()) {
				if (m.getGender() == gender) {
					sb.append(m.getName()).append(" ");
				}
			}
		}		
		return sb.toString().trim();
	}		
	
	/**
	 * This finds the in laws if the person is single
	 * @param gender
	 * @param personName
	 * @return relativeName
	 */
	public String findInLawIfSingle(Gender gender, String personName) {
		StringBuilder sb = new StringBuilder();
		for (Person p : this.getChildrenList()) {
			if (!personName.equals(p.getName())) {
				if (p.getSpouse() == null) {
					//this is another single sibling so continue on to the next sibling
					continue;
				}
				if (p.getSpouse().getGender().name() == gender.name()) {
					sb.append(p.getSpouse().getName()).append(" ");
				}
			}
		}		
		return sb.toString().trim();
	}
	
	/**
	 * This will find an in law if the person in question is not a direct descendant (a spouse)
	 * @param gender
	 * @param person
	 * @return relativeName
	 */
	public String findInLawIfNotChildren(Gender gender, Person person) {
		StringBuilder sb = new StringBuilder();

		for (Person p : this.getChildrenList()) {
			if (p.getSpouse() == null && gender == p.getGender()) {
				sb.append(p.getName()).append(" ");
				continue;
			}
			if (person.getMother() == null || person.getFather() == null) {
				if (gender == p.getGender() && person.getName() != p.getSpouse().getName()) {
					sb.append(p.getName()).append(" ");
					continue;
				}
			}
			if (p.getSpouse() != null && gender == p.getSpouse().getGender() && p.getSpouse().getName() != person.getName()) {
				sb.append(p.getSpouse().getName()).append(" ");
				continue;
			}				
		}			
		return sb.toString().trim();
	}
		
	/**
	 * This will find the in law if the person in question is a direct descendant
	 * @param gender
	 * @param person
	 * @return relativeName
	 */
	public String findInLawIfChildren(Gender gender, Person person) {
		StringBuilder sb = new StringBuilder();
		for (Person p : this.getChildrenList()) {
			if (p.getSpouse() != null && gender == p.getSpouse().getGender() && person.getName() != p.getName()) {
				sb.append(p.getSpouse().getName()).append(" ");
			}			
		}			
		return sb.toString().trim();
	}
}