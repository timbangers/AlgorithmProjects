package com.geektrust.family.util;

import com.geektrust.family.constants.Constants;
import java.util.ArrayList;
import java.util.List;

import com.geektrust.family.constants.Gender;
import com.geektrust.family.objects.Person;

public class FamilyTreeProcessor {

	private static final String FEMALE = "Female";

	private Person familyHead;
	
	private Person findMember(Person head, String memberName) {
		if (memberName == null || head == null) {
			return null;
		}

		Person member = null;
		if (memberName.equals(head.getName())) {
			return head;
		} else if (head.getSpouse() != null && memberName.equals(head.getSpouse().getName())) {
			return head.getSpouse();
		}

		List<Person> childlist = new ArrayList<>();
		if (head.getGender() == Gender.Female) {
			childlist = head.getChildrenList();
		} else if (head.getSpouse() != null) {
			childlist = head.getSpouse().getChildrenList();
		}

		for (Person m : childlist) {
			member = findMember(m, memberName);
			if (member != null) {
				break;
			}
		}
		
		return member;
	}
	
	public void addFamilyHead(String name, String gender) {
		Gender g = (FEMALE.equals(gender)) ? Gender.Female : Gender.Male;
		this.familyHead = new Person(name, g, null, null);
	}

	public void addSpouse(String memberName, String spouseName, String gender) {
		Person member = findMember(familyHead, memberName);
		if (member != null && member.getSpouse() == null) {
			Gender g = (FEMALE.equals(gender)) ? Gender.Female : Gender.Male;
			Person sp = new Person(spouseName, g, null, null);
			sp.addSpouse(member);
			member.addSpouse(sp);
		}
	}
	
	public String addchild(String motherName, String childName, String gender) {
		String result;
		Person member = findMember(familyHead, motherName);
		if (member == null) {
			result = Constants.PERSON_NOT_FOUND;
		} else if (childName == null || gender == null) {
			result = Constants.CHILD_ADDITION_FAILED;
		} else if (member.getGender() == Gender.Female) {
			Gender g = (FEMALE.equals(gender)) ? Gender.Female : Gender.Male;
			Person child = new Person(childName, g, member.getSpouse(), member);
			member.addChild(child);
			result = Constants.CHILD_ADDITION_SUCCEEDED;
		} else {
			result = Constants.CHILD_ADDITION_FAILED;
		}
		return result;
	}

	public String getRelationships(String person, String relationship) {	
		String relations;
		Person member = findMember(familyHead, person);
		if (member == null) {
			relations = Constants.PERSON_NOT_FOUND;
		} else if (relationship == null) {
			relations = Constants.PROVIDE_VALID_RELATION;
		} else {
			relations = getRelationship(member, relationship);
		}

		return relations;

	}

	private String getRelationship(Person member, String relationship) {
		String relations = "";
		switch (relationship) {

		case Constants.DAUGHTER:
			relations = member.findChild(Gender.Female);
			break;

		case Constants.SON:
			relations = member.findChild(Gender.Male);
			break;

		case Constants.SIBLINGS:
			relations = member.findSiblings();
			break;

		case Constants.SISTER_IN_LAW:
			relations = findInLaws(member, Gender.Female);
			break;

		case Constants.BROTHER_IN_LAW:
			relations = findInLaws(member, Gender.Male);
			break;

		case Constants.MATERNAL_AUNT:
			if (member.getMother() != null)
				relations = member.getMother().findAuntOrUncle(Gender.Female);
			break;

		case Constants.PATERNAL_AUNT:
			if (member.getFather() != null)
				relations = member.getFather().findAuntOrUncle(Gender.Female);
			break;

		case Constants.MATERNAL_UNCLE:
			if (member.getMother() != null)
				relations = member.getMother().findAuntOrUncle(Gender.Male);
			break;

		case Constants.PATERNAL_UNCLE:
			if (member.getFather() != null)
				relations = member.getFather().findAuntOrUncle(Gender.Male);
			break;

		default:
			relations = Constants.NOT_YET_IMPLEMENTED;
			break;
		}

		return ("".equals(relations)) ? Constants.NONE : relations;

	}

	private String findInLaws(Person member, Gender gender) {
		StringBuilder sb = new StringBuilder("");
		String res = "";
		// if single
		if (member.getSpouse() == null) {
			res = member.getMother().findInLawIfSingle(gender, member.getName());
		}
		//if spouse who is outside the family
		if (member.getSpouse() != null &&  member.getSpouse().getMother() != null) {
			res = member.getSpouse().getMother().findInLawIfNotChildren(gender, member);
		}
		//if a son or daughter
		if (member.getMother() != null ||member.getFather() !=null) {
			res = member.getMother().findInLawIfChildren(gender, member);
		}
		sb.append(res);
		return sb.toString().trim();
	}
	
	


}
