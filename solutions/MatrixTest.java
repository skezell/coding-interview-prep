package solutions;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class MatrixTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

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
		
		Matrix.printMatrix(testmatrix);
		//Matrix.printMatrix(comparematrix);
		Matrix.rotateMatrix(testmatrix);
		Matrix.printMatrix(testmatrix);
		
		org.junit.Assert.assertArrayEquals(comparematrix, testmatrix);
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
