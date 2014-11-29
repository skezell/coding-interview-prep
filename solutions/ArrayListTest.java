package solutions;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.AfterClass;
import org.junit.Test;

public class ArrayListTest {
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public final void testArrayList_SimpleUsage() 
	{
		//construct an array list with 50 random elements
		ArrayList<TestItm> al = new ArrayList<TestItm>();			
		Random r = new Random();
		for (int i = 0; i<=50; i++)
		{
			al.add (new TestItm(r.nextInt(10000)));
		}
		
		//print it out
		System.out.println (al.toString());
	}
	
	@Test
	public final void testArrayList_Remove()
	{
		
		//construct an array list with 50 random elements
		ArrayList<TestItm> al = new ArrayList<TestItm>();			
		Random r = new Random();
		for (int i = 0; i<=20; i++)
		{
			al.add (new TestItm(r.nextInt(10000)));
		}
		
		//remove from end of list
		al.remove();
		System.out.println (al.toString());
	
		//remove from specific index
		al.remove(3);
		System.out.println (al.toString());
		
	}
	@Test
	public final void testArrayList_Find()
	{
		//construct an array list with 50 random elements
		ArrayList<TestItm> al = new ArrayList<TestItm>();			
		Random r = new Random();
		for (int i = 0; i<=50; i++)
		{
			al.add (new TestItm(r.nextInt(10000)));
		}
		
		//add a specific test item to search for
		TestItm ti = new TestItm (25);
		al.add(ti);
		
		ti = (TestItm)al.find(ti);
		assertNotNull (ti);
		
		//sort al, then repeat search (so it uses a BinarySearch for the find)
		al.sort(false);
		ti = (TestItm)al.find(ti);
		assertNotNull (ti);
	}
	
	@Test
	public final void testArrayList_Sort()
	{
		ArrayList<TestItm> al = new ArrayList<TestItm>(300);
		Random r = new Random();
	
		//create an arraylist with 200 random numbers between 0-10000
		for (int i = 0; i<=200; i++)
		{
			al.add (new TestItm(r.nextInt(10000)));
		}
		System.out.println ("*UNSORTED*==>" + al.toString());
	
		//sort & print without optimization of quicksort
		al.sort(false);
		System.out.println ("*SORTED*==>"+ al.toString());
	
	
		//set threshold for sorting, this is usually determined experimentally based on typical datasets
		al.setThreshold(7);
	
		//create a new arraylist with 200 random numbers between 0-10000
		al= new ArrayList<TestItm>(200);
		for (int i = 0; i<=200; i++)
		{
			al.add (new TestItm(r.nextInt(10000)));
		}
		System.out.println ("*UNSORTED*==>" + al.toString());
	
		//sort & print with optimization of quicksort
		al.sort(true);
		System.out.println ("*SORTED*==>" + al.toString());
		
	}
}
