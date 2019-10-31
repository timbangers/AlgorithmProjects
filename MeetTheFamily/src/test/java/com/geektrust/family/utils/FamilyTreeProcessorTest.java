package com.geektrust.family.utils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.geektrust.family.constants.Gender;
import com.geektrust.family.objects.Person;
public class FamilyTreeProcessorTest {
	
		
	@Test
	public void testAddRoot() {
		FamilyTreeProcessor processor = new FamilyTreeProcessor();
		processor.addRoot("John", Gender.Male.name());
		assertNotNull (processor.getRoot());
		
	}
	
	@Test
	//finds a person given a name and a parent root
	public void testFindPerson() {
		FamilyTreeProcessor processor = new FamilyTreeProcessor();
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
		Person p = processor.findPerson(ginerva, "Lily");
		assertNotNull(p);
		assertEquals("Lily", p.getName());		
	}
	

	@Test
	//finds a person given a name and a parent root
	public void testFindRelationship() {
		FamilyTreeProcessor processor = new FamilyTreeProcessor();
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
		
		assertEquals("Darcy Alice", processor.getRelationship(lily, "Sister-In-Law"));
		assertEquals("NONE", processor.getRelationship(lily, "Brother-In-Law"));
	}
	
	@Test
	//finding an in law if the person is single
	public void testFindInLaws1() {
		FamilyTreeProcessor processor = new FamilyTreeProcessor();

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
		
		assertNotNull(processor.findInLaws(lily, Gender.Female));			
		assertEquals("Darcy Alice", processor.findInLaws(lily, Gender.Female));		
	}	
	
	@Test
	//finding an in law if the person is single and if there is also another single person in the family
	public void testFindInLaws2() {		
		FamilyTreeProcessor processor = new FamilyTreeProcessor();

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
		
		assertNotNull(processor.findInLaws(lily, Gender.Female));			
		assertEquals("Alice", processor.findInLaws(lily, Gender.Female));		
	}
	
	@Test
	//finding a sister in law if the person married to a member of the family (in other words he/she is not a direct descendant of king arthur)
	public void testFindInLaws3() {		
		FamilyTreeProcessor processor = new FamilyTreeProcessor();

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
		
		assertNotNull(processor.findInLaws(darcy, Gender.Female));				
		assertEquals("Lily Alice",  processor.findInLaws(darcy, Gender.Female));	
	}
	
	@Test
	//finding a brother in law if the person married to a member of the family (in other words he/she is not a direct descendant of king arthur)
	public void testFindInLaws4() {		
		FamilyTreeProcessor processor = new FamilyTreeProcessor();

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
		
		assertNotNull(processor.findInLaws(darcy, Gender.Male));		
		assertEquals("Albus", processor.findInLaws(darcy, Gender.Male));	
	}
	
	
	@Test
	//finding an in law if the person is a direct descendant
	public void testFindInLaws5() {	
		FamilyTreeProcessor processor = new FamilyTreeProcessor();

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
		
		assertNotNull(processor.findInLaws(james, Gender.Male));				
		assertEquals("", processor.findInLaws(james, Gender.Male));
		assertEquals("", processor.findInLaws(albus, Gender.Male));	
		assertEquals("Alice", processor.findInLaws(james, Gender.Female));	
		assertEquals("Darcy", processor.findInLaws(albus, Gender.Female));	

	}
	
	@Test
	//can only add a child to a married mom
	public void addChildTest1() {
		FamilyTreeProcessor processor = new FamilyTreeProcessor();

		Person ginerva = new Person("Ginerva", Gender.Female, null, null);
		processor.setRoot(ginerva);

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
		assertEquals ("CHILD_ADDITION_FAILED", processor.addchild("Lily", "Baby", Gender.Female.name()));
	}
	
	@Test
	//cannot add a child to a husband
	public void addChildTest2() {
		FamilyTreeProcessor processor = new FamilyTreeProcessor();

		Person ginerva = new Person("Ginerva", Gender.Female, null, null);
		processor.setRoot(ginerva);

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
		assertEquals ("CHILD_ADDITION_FAILED", processor.addchild("James", "Baby", Gender.Female.name()));
	}
	
	@Test
	//adding normally to a married female
	public void addChildTest3() {
		FamilyTreeProcessor processor = new FamilyTreeProcessor();

		Person ginerva = new Person("Ginerva", Gender.Female, null, null);
		processor.setRoot(ginerva);

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
		assertEquals ("CHILD_ADDITION_SUCCEEDED", processor.addchild("Alice", "Baby", Gender.Female.name()));
		assertEquals ("CHILD_ADDITION_SUCCEEDED", processor.addchild("Darcy", "Baby", Gender.Female.name()));
	}	
	
}
