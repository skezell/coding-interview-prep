package solutions;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class MatrixTest {

	@Rule
    public ExpectedException thrown= ExpectedException.none();
	
	//test cases for ZeroMatrix method
	@Test
	public final void testZeroMatrix_EmptyMatrix() 
	{
		//create a test matrix 
		int[][] testmatrix = new int[0][0];
		int[][] comparematrix = new int[0][0];
		
		//Matrix.printMatrix(testmatrix);
		Matrix.zeroMatrix(testmatrix);
		//Matrix.printMatrix(testmatrix);
		
		org.junit.Assert.assertArrayEquals(comparematrix, testmatrix);
	}
	
	@Test
	public final void testZeroMatrix_OneDimensional() 
	{
		//create a test matrix 
		int[][] testmatrix = getTestMatrix (4,1);
		testmatrix[2][0]=0;
		
		//create a compare matrix, hand code the expected values
		int[][] comparematrix = getTestMatrix (4,1);
		comparematrix[0][0] =0;
		comparematrix[1][0] =0;
		comparematrix[2][0] =0;
		comparematrix[3][0] =0;
		
		//Matrix.printMatrix(testmatrix);
		Matrix.zeroMatrix(testmatrix);
		//Matrix.printMatrix(testmatrix);
		
		org.junit.Assert.assertArrayEquals(comparematrix, testmatrix);
	}
	
	@Test
	public final void testZeroMatrix_SimpleCase() 
	{
		//create a test matrix 
		int[][] testmatrix = getTestMatrix (4,5);
		testmatrix[1][2] = 0;
		
		//create a matrix to compare, zero out the correct elements by hand
		int[][] comparematrix = getTestMatrix(4,5);
		//mark the row for [1][2]
		comparematrix[0][2] = 0;
		comparematrix[1][2] = 0;
		comparematrix[2][2] = 0;
		comparematrix[3][2] = 0;
		//mark one column for [1][2]
		comparematrix[1][0] = 0;
		comparematrix[1][1] = 0;
		comparematrix[1][2] = 0;
		comparematrix[1][3] = 0;
		comparematrix[1][4] = 0;
		
		//Matrix.printMatrix(testmatrix);
		Matrix.zeroMatrix(testmatrix);
		//Matrix.printMatrix(testmatrix);
		
		org.junit.Assert.assertArrayEquals(comparematrix, testmatrix);
	}
	
	@Test
	public final void testZeroMatrix_MultipleZeros() 
	{
		//create a test matrix 
		int[][] testmatrix = getTestMatrix (10,10);
		testmatrix[1][2] = 0;
		testmatrix[4][5] = 0;
		
		//create a matrix to compare, zero out the correct elements by hand
		//note: this code looks really kludgy and it's tempting to right some code to fill the correct rows & columns with zero, but 
		//you'd basically be duplicating the code (or sections of code) from the zeroMatrix method, so it's likely that if you had an error 
		//in the original zeroMatrix algorithm, you'd duplicate the error in the Junit test case code which not a great test, better to "hand" code something in this case
		//once you've verified the method is working for the simple cases, you could use some code to generate your comparison matrix for more involved test cases
		int[][] comparematrix = getTestMatrix(10,10);
		//mark the row for [1][2]
		comparematrix[0][2] = 0;
		comparematrix[1][2] = 0;
		comparematrix[2][2] = 0;
		comparematrix[3][2] = 0;
		comparematrix[4][2] = 0;
		comparematrix[5][2] = 0;
		comparematrix[6][2] = 0;
		comparematrix[7][2] = 0;
		comparematrix[8][2] = 0;
		comparematrix[9][2] = 0;
		//mark one column for [1][2]
		comparematrix[1][0] = 0;
		comparematrix[1][1] = 0;
		comparematrix[1][2] = 0;
		comparematrix[1][3] = 0;
		comparematrix[1][4] = 0;
		comparematrix[1][5] = 0;
		comparematrix[1][6] = 0;
		comparematrix[1][7] = 0;
		comparematrix[1][8] = 0;
		comparematrix[1][9] = 0;
		//mark the row for [4][5]
		comparematrix[0][5] = 0;
		comparematrix[1][5] = 0;
		comparematrix[2][5] = 0;
		comparematrix[3][5] = 0;
		comparematrix[4][5] = 0;
		comparematrix[5][5] = 0;
		comparematrix[6][5] = 0;
		comparematrix[7][5] = 0;
		comparematrix[8][5] = 0;
		comparematrix[9][5] = 0;
		//mark one column for [4][5]
		comparematrix[4][0] = 0;
		comparematrix[4][1] = 0;
		comparematrix[4][2] = 0;
		comparematrix[4][3] = 0;
		comparematrix[4][4] = 0;
		comparematrix[4][5] = 0;
		comparematrix[4][6] = 0;
		comparematrix[4][7] = 0;
		comparematrix[4][8] = 0;
		comparematrix[4][9] = 0;
		
		//Matrix.printMatrix(testmatrix);
		Matrix.zeroMatrix(testmatrix);
		//Matrix.printMatrix(testmatrix);
		
		org.junit.Assert.assertArrayEquals(comparematrix, testmatrix);
	}
	
	//test cases for RotateMatrix method
	@Test
	public final void testRotateMatrix_NullMatrix() 
	{
		//create a test matrix 
		int[][] testmatrix = null;
		
		//create a matrix to compare
		int[][] comparematrix=null;
		
		//Matrix.printMatrix(testmatrix);
		//Matrix.printMatrix(comparematrix);
		Matrix.rotateMatrix(testmatrix);
		//Matrix.printMatrix(testmatrix);
		
		org.junit.Assert.assertArrayEquals(comparematrix, testmatrix);
	}
	
	@Test
	public final void testRotateMatrix_OneElement() 
	{
		//create a test matrix 
		int[][] testmatrix = getTestMatrix(1,1);
		
		//create a matrix to compare
		int[][] comparematrix=getTestMatrix(1,1);
		
		//Matrix.printMatrix(testmatrix);
		//Matrix.printMatrix(comparematrix);
		Matrix.rotateMatrix(testmatrix);
		//Matrix.printMatrix(testmatrix);
		
		org.junit.Assert.assertArrayEquals(comparematrix, testmatrix);
	}
	
	@Test
	public final void testRotateMatrix_SimpleCase() 
	{
		//create a test matrix 
		int[][] testmatrix = getTestMatrix (3,3);
		
		//create a matrix to compare
		int[][] comparematrix = new int[3][3];
		comparematrix[0][0] = 3;
		comparematrix[1][0] = 6;
		comparematrix[2][0] = 9;
		comparematrix[0][1] = 2;
		comparematrix[1][1] = 5;
		comparematrix[2][1] = 8;
		comparematrix[0][2] = 1;
		comparematrix[1][2] = 4;
		comparematrix[2][2] = 7;
		
		//Matrix.printMatrix(testmatrix);
		//Matrix.printMatrix(comparematrix);
		Matrix.rotateMatrix(testmatrix);
		//Matrix.printMatrix(testmatrix);
		
		org.junit.Assert.assertArrayEquals(comparematrix, testmatrix);
	}
	
	@Test
	public final void testRotateMatrix_2Square() 
	{
		//create a test matrix 
		int[][] testmatrix = getTestMatrix (2,2);
		
		//create a matrix to compare
		int[][] comparematrix = new int[2][2];
		comparematrix[0][0] = 2;
		comparematrix[1][0] = 4;
		comparematrix[0][1] = 1;
		comparematrix[1][1] = 3;
		
		//Matrix.printMatrix(testmatrix);
		//Matrix.printMatrix(comparematrix);
		Matrix.rotateMatrix(testmatrix);
		//Matrix.printMatrix(testmatrix);
		
		org.junit.Assert.assertArrayEquals(comparematrix, testmatrix);
	}
	
	@Test
	public final void testRotateMatrix_4Square() 
	{
		//create a test matrix 
		int[][] testmatrix = getTestMatrix (4,4);
		
		//create a matrix to compare
		int[][] comparematrix = new int[4][4];
		//first row
		comparematrix[0][0] = 4;
		comparematrix[1][0] = 8;
		comparematrix[2][0] = 12;
		comparematrix[3][0] = 16;
		//2nd row
		comparematrix[0][1] = 3;
		comparematrix[1][1] = 7;
		comparematrix[2][1] = 11;
		comparematrix[3][1] = 15;
		//3rd row
		comparematrix[0][2] = 2;
		comparematrix[1][2] = 6;
		comparematrix[2][2] = 10;
		comparematrix[3][2] = 14;
		//4th row
		comparematrix[0][3] = 1;
		comparematrix[1][3] = 5;
		comparematrix[2][3] = 9;
		comparematrix[3][3] = 13;
		

		//Matrix.printMatrix(testmatrix);
		//Matrix.printMatrix(comparematrix);
		Matrix.rotateMatrix(testmatrix);
		//Matrix.printMatrix(testmatrix);
		
		org.junit.Assert.assertArrayEquals(comparematrix, testmatrix);
	}
	
	@Test
	public final void testRotateMatrix_5Square() 
	{
		//larger odd sized square (5X5->3X3->1X1)
		
		int[][] testmatrix = getTestMatrix (5,5);
		
		//create a matrix to compare
		int[][] comparematrix = new int[5][5];
		//first row
		comparematrix[0][0] = 5;
		comparematrix[1][0] = 10;
		comparematrix[2][0] = 15;
		comparematrix[3][0] = 20;
		comparematrix[4][0] = 25;
		//2nd row
		comparematrix[0][1] = 4;
		comparematrix[1][1] = 9;
		comparematrix[2][1] = 14;
		comparematrix[3][1] = 19;
		comparematrix[4][1] = 24;
		//3rd row
		comparematrix[0][2] = 3;
		comparematrix[1][2] = 8;
		comparematrix[2][2] = 13;
		comparematrix[3][2] = 18;
		comparematrix[4][2] = 23;
		//4th row
		comparematrix[0][3] = 2;
		comparematrix[1][3] = 7;
		comparematrix[2][3] = 12;
		comparematrix[3][3] = 17;
		comparematrix[4][3] = 22;
		//5th row
		comparematrix[0][4] = 1;
		comparematrix[1][4] = 6;
		comparematrix[2][4] = 11;
		comparematrix[3][4] = 16;
		comparematrix[4][4] = 21;
		

		//Matrix.printMatrix(testmatrix);
		//Matrix.printMatrix(comparematrix);
		Matrix.rotateMatrix(testmatrix);
		//Matrix.printMatrix(testmatrix);
		
		org.junit.Assert.assertArrayEquals(comparematrix, testmatrix);
	}
	
	@Test
	public final void testRotateMatrix_6Square() 
	{
		//larger even sized square (6X6->4X4->2X2)
		
		int[][] testmatrix = getTestMatrix (6,6);
		
		//create a matrix to compare
		int[][] comparematrix = new int[6][6];
		//first row
		comparematrix[0][0] = 6;
		comparematrix[1][0] = 12;
		comparematrix[2][0] = 18;
		comparematrix[3][0] = 24;
		comparematrix[4][0] = 30;
		comparematrix[5][0] = 36;
		//2nd row
		comparematrix[0][1] = 5;
		comparematrix[1][1] = 11;
		comparematrix[2][1] = 17;
		comparematrix[3][1] = 23;
		comparematrix[4][1] = 29;
		comparematrix[5][1] = 35;
		//3rd row
		comparematrix[0][2] = 4;
		comparematrix[1][2] = 10;
		comparematrix[2][2] = 16;
		comparematrix[3][2] = 22;
		comparematrix[4][2] = 28;
		comparematrix[5][2] = 34;
		//4th row
		comparematrix[0][3] = 3;
		comparematrix[1][3] = 9;
		comparematrix[2][3] = 15;
		comparematrix[3][3] = 21;
		comparematrix[4][3] = 27;
		comparematrix[5][3] = 33;
		//4th row
		comparematrix[0][4] = 2;
		comparematrix[1][4] = 8;
		comparematrix[2][4] = 14;
		comparematrix[3][4] = 20;
		comparematrix[4][4] = 26;
		comparematrix[5][4] = 32;
		//5th row
		comparematrix[0][5] = 1;
		comparematrix[1][5] = 7;
		comparematrix[2][5] = 13;
		comparematrix[3][5] = 19;
		comparematrix[4][5] = 25;
		comparematrix[5][5] = 31;
		
		//Matrix.printMatrix(testmatrix);
		//Matrix.printMatrix(comparematrix);
		Matrix.rotateMatrix(testmatrix);
		//Matrix.printMatrix(testmatrix);
		
		org.junit.Assert.assertArrayEquals(comparematrix, testmatrix);
	}
	
	@Test
	public final void testRotateMatrix_NotASquare() 
	{
		
		
		int[][] testmatrix = getTestMatrix (3,2);
		try
		{
			Matrix.rotateMatrix(testmatrix);
		}
		catch (IllegalArgumentException e)
		{
			assertNotNull(e);
		}
	}
	//generates a matrix for testing, the contents aren't that important other than 0 elements, so it just assigns 1 - total number of elements
	//that way when you view a matrix, it's obvious if something has been changed
	private static int[][] getTestMatrix(int m, int n)
	{
		int[][] newmatrix = new int[m][n];
		int counter =1;
		
		for (int i=0; i< n; i++)
		{
			for (int j =0; j < m; j++)
			{	
				newmatrix[j][i] = counter++;
			}
		}
		
		return (newmatrix);
	}

}
