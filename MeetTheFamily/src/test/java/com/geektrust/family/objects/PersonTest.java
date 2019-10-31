package com.geektrust.family.objects;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import com.geektrust.family.constants.Gender;

public class PersonTest {
	
	
	@Test
	public void findSiblingsTest1() {
		Person p = new Person ("John", Gender.Male, null, null);
		p.findSiblings();
		assertEquals("", p.findSiblings());
	}
	@Test
	//Mom is empty
	public void findSiblingsTest2() {
		Person p = new Person ("John", Gender.Male, null, null);
		assertEquals("", p.findSiblings());
	}
	
	@Test
	//Mom is alive but no other kids
	public void findSiblingsTest3() {
		Person mom = new Person ("Demi", Gender.Female, null, null);
		Person dad = new Person ("Bruce", Gender.Male, null, null);
		Person kid = new Person ("John", Gender.Male, dad, mom);
		assertEquals("", kid.findSiblings());
	}
	
	@Test
	//Mom is alive with other kids
	public void findSiblingsTest4() {
		Person mom = new Person ("Demi", Gender.Female, null, null);
		Person dad = new Person ("Bruce", Gender.Male, null, null);
		Person anotherChild = new Person ("Ashton", Gender.Male, dad, mom);
		mom.addChild(anotherChild);
		Person kid = new Person ("John", Gender.Male, dad, mom);
		assertEquals("Ashton", kid.findSiblings());
	}
	
	@Test
	//paternal-uncle
	public void findAuntOrUncle1() {
		Person dadOfBruce = new Person ("Arnold", Gender.Male, null, null);
		Person bruce = new Person ("Bruce", Gender.Male, dadOfBruce, null);
		Person wayne = new Person ("Wayne", Gender.Male, dadOfBruce, null);
		Person demi = new Person ("Demi", Gender.Female, null, null);
		demi.addChild(wayne);
		dadOfBruce.setSpouse(demi);
		Person john = new Person ("John", Gender.Male, bruce, demi);		
		assertEquals("Wayne", john.findAuntOrUncle(Gender.Male));
	}
	
	@Test
	//paternal-aunt
	public void findAuntOrUncle2() {
		Person dadOfBruce = new Person ("Arnold", Gender.Male, null, null);
		Person bruce = new Person ("Bruce", Gender.Male, dadOfBruce, null);
		Person diana = new Person ("Diana", Gender.Female, dadOfBruce, null);
		Person demi = new Person ("Demi", Gender.Female, null, null);
		demi.addChild(diana);
		dadOfBruce.setSpouse(demi);
		Person john = new Person ("John", Gender.Male, bruce, demi);		
		assertEquals("Diana", john.findAuntOrUncle(Gender.Female));
	}
	
	@Test
	//maternal-aunt
	public void findAuntOrUncle3() {
		Person momOfBruce = new Person ("Linda", Gender.Female, null, null);
		Person bruce = new Person ("Bruce", Gender.Male, momOfBruce, null);
		Person jen = new Person ("Jen", Gender.Female, momOfBruce, null);
		Person kyle = new Person ("Kyle", Gender.Male, null, null);
		kyle.addChild(jen);
		momOfBruce.setSpouse(kyle);
		Person john = new Person ("John", Gender.Male, bruce, kyle);		
		assertEquals("Jen", john.findAuntOrUncle(Gender.Female));
	}
	
	@Test
	//maternal-uncle
	public void findAuntOrUncle4() {
		Person momOfBruce = new Person ("Linda", Gender.Female, null, null);
		Person bruce = new Person ("Bruce", Gender.Male, momOfBruce, null);
		Person chris = new Person ("Chris", Gender.Male, momOfBruce, null);
		Person kyle = new Person ("Kyle", Gender.Male, null, null);
		kyle.addChild(chris);
		momOfBruce.setSpouse(kyle);
		Person john = new Person ("John", Gender.Male, bruce, kyle);		
		assertEquals("Chris", john.findAuntOrUncle(Gender.Male));
	}
	
	@Test
	//mom has no bros
	public void findAuntOrUncle5() {
		Person momOfJames = new Person ("Gramma", Gender.Female, null, null);
		Person james = new Person ("Bruce", Gender.Male, null, null);
		Person darcy = new Person ("Darcy", Gender.Female, null, null);
		darcy.setSpouse(james);
		momOfJames.addChild(james);

		Person john = new Person ("William", Gender.Male, james, darcy);		
		assertEquals("", john.findAuntOrUncle(Gender.Male));
	}
	
	@Test
	//dad has no bros...this seems to be the same as above, regardless of gender
	//as well as the parent, regardless of the gender
	public void findAuntOrUncle6() {
		Person momOfJames = new Person ("Gramma", Gender.Female, null, null);
		Person james = new Person ("Bruce", Gender.Male, null, null);
		Person darcy = new Person ("Darcy", Gender.Female, null, null);
		darcy.setSpouse(james);
		momOfJames.addChild(james);

		Person john = new Person ("William", Gender.Male, james, darcy);		
		assertEquals("", john.findAuntOrUncle(Gender.Male));
	}
	
	@Test
	//finds child if any
	public void findChild1() {
		Person hubby = new Person ("Josh", Gender.Male, null, null);
		Person wife = new Person ("Josie", Gender.Female, null, null);
		Person son = new Person ("Fitz", Gender.Male, hubby, wife);
		wife.addChild(son);
		hubby.setSpouse(wife);		
		assertEquals("Fitz", hubby.findChild(Gender.Male));
	}	
	
	@Test
	//finding a daughter but only a son is alive
	public void findChild2() {
		Person hubby = new Person ("Josh", Gender.Male, null, null);
		Person wife = new Person ("Josie", Gender.Female, null, null);
		Person son = new Person ("Fitz", Gender.Male, hubby, wife);
		wife.addChild(son);
		hubby.setSpouse(wife);			
		assertEquals("", hubby.findChild(Gender.Female));
	}	
	
	@Test
	//finding an in law if the person is single
	public void findInLaws1() {
		
		Person ginerva = new Person("Ginerva", Gender.Female, null, null);
		Person harry = new Person("Harry", Gender.Male, null, null);
		ginerva.setSpouse(harry);
		
		Person darcy = new Person("Darcy", Gender.Female, harry, ginerva);
		Person james = new Person("James", Gender.Male, harry, ginerva);
		james.setSpouse(darcy);
		
		Person alice = new Person("Alice", Gender.Female, harry, ginerva);
		Person albus = new Person("Albus", Gender.Male, harry, ginerva);
		albus.setSpouse(alice);
		

		Person lily = new Person("Lily", Gender.Female, harry, ginerva); // no spouse :(
		ginerva.addChild(lily);
		ginerva.addChild(james);
		ginerva.addChild(albus);
		
		assertNotNull(lily.findInLawIfSingle(Gender.Female, "Lily"));			
		assertEquals("Darcy Alice", ginerva.findInLawIfSingle(Gender.Female, "Lily"));		
	}	
	
	@Test
	//finding an in law if the person is single and if there is also another single person in the family
	public void findInLaws2() {		
		Person ginerva = new Person("Ginerva", Gender.Female, null, null);
		Person harry = new Person("Harry", Gender.Male, null, null);
		ginerva.setSpouse(harry);		
		Person james = new Person("James", Gender.Male, harry, ginerva);		
		Person alice = new Person("Alice", Gender.Female, harry, ginerva);
		Person albus = new Person("Albus", Gender.Male, harry, ginerva);
		albus.setSpouse(alice);	

		Person lily = new Person("Lily", Gender.Female, harry, ginerva); // no spouse :(
		ginerva.addChild(lily);
		ginerva.addChild(james);
		ginerva.addChild(albus);
		
		assertNotNull(ginerva.findInLawIfSingle(Gender.Female, "Lily"));			
		assertEquals("Alice", ginerva.findInLawIfSingle(Gender.Female, "Lily"));		
	}
	
	@Test
	//finding a sister in law if the person married to a member of the family (in other words he/she is not a direct descendant of king arthur)
	public void findInLawIfNotChildren1() {		
		Person ginerva = new Person("Ginerva", Gender.Female, null, null);
		Person harry = new Person("Harry", Gender.Male, null, null);
		ginerva.setSpouse(harry);
		
		Person darcy = new Person("Darcy", Gender.Female, null, null);
		Person james = new Person("James", Gender.Male, harry, ginerva);
		james.setSpouse(darcy);
		darcy.setSpouse(james);
		
		Person alice = new Person("Alice", Gender.Female, null, null);
		Person albus = new Person("Albus", Gender.Male, harry, ginerva);
		albus.setSpouse(alice);
		alice.setSpouse(albus);
		

		Person lily = new Person("Lily", Gender.Female, harry, ginerva); // no spouse :(
		ginerva.addChild(lily);
		ginerva.addChild(james);
		ginerva.addChild(albus);
		
		assertNotNull(ginerva.findInLawIfNotChildren(Gender.Female, darcy));			
		assertEquals("Lily Alice", ginerva.findInLawIfNotChildren(Gender.Female, darcy));	
	}
	
	@Test
	//finding a brother in law if the person married to a member of the family (in other words he/she is not a direct descendant of king arthur)
	public void findInLawIfNotChildren2() {		
		Person ginerva = new Person("Ginerva", Gender.Female, null, null);
		Person harry = new Person("Harry", Gender.Male, null, null);
		ginerva.setSpouse(harry);
		
		Person darcy = new Person("Darcy", Gender.Female, null, null);
		Person james = new Person("James", Gender.Male, harry, ginerva);
		james.setSpouse(darcy);
		darcy.setSpouse(james);
		
		Person alice = new Person("Alice", Gender.Female, null, null);
		Person albus = new Person("Albus", Gender.Male, harry, ginerva);
		albus.setSpouse(alice);
		alice.setSpouse(albus);
		

		Person lily = new Person("Lily", Gender.Female, harry, ginerva); // no spouse :(
		ginerva.addChild(lily);
		ginerva.addChild(james);
		ginerva.addChild(albus);
		
		assertNotNull(ginerva.findInLawIfNotChildren(Gender.Male, darcy));			
		assertEquals("Albus", ginerva.findInLawIfNotChildren(Gender.Male, darcy));	
	}
	
	
	@Test
	//finding an in law if the person is a direct descendant
	public void findInLawIfChildren1() {		
		Person ginerva = new Person("Ginerva", Gender.Female, null, null);
		Person harry = new Person("Harry", Gender.Male, null, null);
		ginerva.setSpouse(harry);
		
		Person darcy = new Person("Darcy", Gender.Female, null, null);
		Person james = new Person("James", Gender.Male, harry, ginerva);
		james.setSpouse(darcy);
		darcy.setSpouse(james);
		
		Person alice = new Person("Alice", Gender.Female, null, null);
		Person albus = new Person("Albus", Gender.Male, harry, ginerva);
		albus.setSpouse(alice);
		alice.setSpouse(albus);		

		Person lily = new Person("Lily", Gender.Female, harry, ginerva); // no spouse :(
		ginerva.addChild(lily);
		ginerva.addChild(james);
		ginerva.addChild(albus);
		
		assertNotNull(ginerva.findInLawIfChildren(Gender.Male, james));			
		assertEquals("", ginerva.findInLawIfChildren(Gender.Male, james));	
		assertEquals("", ginerva.findInLawIfChildren(Gender.Male, albus));	
		assertEquals("Alice", ginerva.findInLawIfChildren(Gender.Female, james));	
		assertEquals("Darcy", ginerva.findInLawIfChildren(Gender.Female, albus));	

	}
}
