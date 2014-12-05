package solutions;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class TowersTest {

	public static final int MAX_DISCS=7;
	public static final int MAX_TOWERS=5;
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public final void testTowers_BaseCases() 
	{
		//run through a whole bunch of different parameters, making sure that we don't hit any exceptions
		//for simplicity, just print out the beginning state, sequence of moves and ending state
		//if this were a real unit test you'd want to expose some way to get the state of the towers object and compare
		// with expected state for some specific test cases
		
		try
		{
			for (int i = 0; i <= 3; i++)
			{
				for (int j = 0; j <= 5; j++)
				{
					System.out.println("Test Case: DISCS=" + i + " TOWERS=" + j);
					Towers problem = new Towers(i,j);
					
					//System.out.println (problem.getState());
					int test = problem.solve();
					if (test!=-1)
					{
						System.out.println ("Moves required to solve problem:" + test);
						System.out.println (problem.getState());
						//System.out.println (problem.getMoves());
					}
					else
					{
						System.out.println ("No valid solution!");
					}
				}
			}
		}
		catch (Exception e)
		{
			fail ("Exception was thrown:" + e.toString());
		}
		

	}
	@Test
	public final void testTowers_BasicRange() 
	{
		//run through a whole bunch of different parameters, making sure that we don't hit any exceptions
		//for simplicity, just print out the beginning state, sequence of moves and ending state
		//if this were a real unit test you'd want to expose some way to get the state of the towers object and compare
		// with expected state for some specific test cases
		
		try
		{
			for (int i = 2; i <= MAX_DISCS; i++)
			{
				for (int j = 3; j <= MAX_TOWERS; j++)
				{
					System.out.println("Test Case: DISCS=" + i + " TOWERS=" + j);
					Towers problem = new Towers(i,j);
					
					//System.out.println (problem.getState());
					int test = problem.solve();
					if (test!=-1)
					{
						System.out.println ("Moves required to solve problem:" + test);
						System.out.println (problem.getState());
						//System.out.println (problem.getMoves());
					}
					else
					{
						System.out.println ("No valid solution!");
					}
				}
			}
		}
		catch (Exception e)
		{
			fail ("Exception was thrown:" + e.toString());
		}
		

	}


}
