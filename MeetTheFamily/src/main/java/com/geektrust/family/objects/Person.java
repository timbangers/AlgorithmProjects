package com.geektrust.family.objects;

import java.util.ArrayList;
import java.util.List;
import com.geektrust.family.constants.Gender;

/**
 * @author jesse timbang
 * This Person class is a node in a tree
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


	public void addChild(Person child) {
		this.getChildrenList().add(child);
	}


	public void addSpouse(Person spouse) {
		this.setSpouse(spouse);
	}

	public String findChild(Gender gender) {
		StringBuilder sb = new StringBuilder("");
		//check if the parent in question is male, if yes, need to look at the spouse
		if (this.getGender() == Gender.Male) {
			for (Person m : this.getSpouse().getChildrenList()) {
				if (m.getGender() == gender) {
					sb.append(m.getName()).append(" ");
				}
			}
		}
		//process it normally for females
		for (Person m : this.getChildrenList()) {
			if (m.getGender() == gender) {
				sb.append(m.getName()).append(" ");
			}
		}
		return sb.toString().trim();
	}
	
	public String findSiblings() {
		StringBuilder sb = new StringBuilder("");
		if (this.getMother() != null) {
			for (Person m : this.getMother().getChildrenList()) {
				if (!this.getName().equals(m.getName()) ) {
					sb.append(m.getName()).append(" ");
				}
			}	
		}
		
		return sb.toString().trim();
	}

	public String findChildren(Gender gender, String personName) {
		StringBuilder sb = new StringBuilder("");

		for (Person m : this.getChildrenList()) {
			if (!personName.equals(m.getName()) && m.getGender() == gender) {
				sb.append(m.getName()).append(" ");
			}
		}
		
		return sb.toString().trim();
	}
	
	//ifSingle
	public String findInLawIfSingle(Gender gender, String personName) {
		StringBuilder sb = new StringBuilder("");

		for (Person m : this.getChildrenList()) {
			if (!personName.equals(m.getName())) {	
				if (m.getSpouse().getGender().name() == gender.name()) {
					sb.append(m.getSpouse().getName()).append(" ");
				}
			}
		}
		
		return sb.toString().trim();
	}
	
	//ifSpousewithnoparent
	public String findInLawIfNotChildren(Gender gender, Person person) {
		StringBuilder sb = new StringBuilder("");

		for (Person m : this.getChildrenList()) {
			//this means this person is a spouse, will need to get the 
			if (m.getSpouse() == null && gender == m.getGender()) {
				sb.append(m.getName()).append(" ");
				continue;
			}
			if (person.getMother() == null || person.getFather() == null) {
				if (gender == m.getGender() && person.getName() != m.getSpouse().getName()) {
					sb.append(m.getName()).append(" ");
					continue;
				}
			}
			if (m.getSpouse() != null && gender == m.getSpouse().getGender() && m.getSpouse().getName() != person.getName()) {
				sb.append(m.getSpouse().getName()).append(" ");
				continue;
			}				
		}			
		return sb.toString().trim();
	}
		
	//ifsonordaugther
	public String findInLawIfChildren(Gender gender, Person person) {
		StringBuilder sb = new StringBuilder("");
		for (Person m : this.getChildrenList()) {
			if (m.getSpouse() != null && gender == m.getSpouse().getGender() && person.getName() != m.getName()) {
				sb.append(m.getSpouse().getName()).append(" ");
			}			
		}			
		return sb.toString().trim();
	}

	public String findAuntOrUncle(Gender gender) {
		StringBuilder sb = new StringBuilder("");
		if (this.getMother() != null) {
			for (Person m : this.getMother().getChildrenList()) {
				if (!this.getName().equals(m.getName()) && m.getGender() == gender) {
					sb.append(m.getName()).append(" ");
				}
			}
		}
		return sb.toString().trim();
	}


}