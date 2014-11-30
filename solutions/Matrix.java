package solutions;

import java.util.Arrays;

//This class does a couple of matrix related interview question answers: 
// zeroMatrix - takes an M X N matrix and zero's out the entire row and column for each zero element in the input array, modifies the input matrix
// rotateMatrix - takes an N X N matrix and rotates it 90 degrees
//Neither is meant to be complete, only to show the key parts of how you'd code an answer and includes println statements for elucidation of what's going on
public class Matrix 
{
	//takes a M X N matrix, for each element set entire row and column =0 if the element is 0
	public static void zeroMatrix (int[][] matrix)
	{	int m = matrix.length;
		int n;
		
		
		if (m==0)		//if it's a one dimensional matrix, don't try to get the length of a subarray
		{
			n = 0;
		}
		else
		{
			n = matrix[0].length;
		}
		
		//use two arrays to mark which rows and columns need to be zero'd out
		boolean[] rows = new boolean[n];
		boolean[] columns = new boolean[m];
		
		//for each element in the matrix, flip the bit on it's corresponding rows and columns array
		for (int i =0; i < m; i++)
		{
			for (int j=0; j < n; j++)
			{
				if (matrix[i][j]==0)
				{
					rows[j]=true;
					columns[i]=true;
				}
			}
		}
		
		//System.out.println (Arrays.toString(rows));
		//System.out.println (Arrays.toString(columns));
		
		
		//now zero out the appropriate rows & columns based on the flags in rows and columns
		for (int k =0; k < m; k++)
		{
			if (columns[k])
			{
				zeroColumn(matrix, k);
			}
		}
		for (int k =0; k < n; k++)
		{
			if (rows[k])
			{
				zeroRow(matrix, k);
			}
		}
	}
	
	//zero out one given column
	private static void zeroColumn (int[][] matrix, int column)
	{
		for (int i = 0 ; i< matrix[0].length; i++)
		{
			matrix[column][i] = 0;
		}
		
	}
	
	//sero out one given row
	private static void zeroRow (int[][] matrix, int row)
	{
		for (int i = 0 ; i< matrix.length; i++)
		{
			matrix[i][row] = 0;
		}
		
	}
	
	//just to let you see what's going in a graphical way
	//prints array with first index across the top of the square, the 2nd down the side
	public static void printMatrix (int[][] matrix)
	{	
		int m=0;
		int n=0;
		
		if (matrix!=null)
		{
			m=matrix.length;
		}
		
		if (m==0)
		{
			n=0;
		}
		else
		{
			n = matrix[0].length;
		}
		
		System.out.println ("M=" + m);
		System.out.println ("N=" + n);
		
		for (int i=0; i< n; i++)
		{
			StringBuilder sb = new StringBuilder();
			for (int j =0; j < m; j++)
			{	
				sb.append("[");
				sb.append(matrix[j][i]);
				sb.append("]");
			}
			System.out.println (sb.toString());
		}
		
	}
	
	//rotate's a square matrix by 90 degrees, counter-clockwise (no reason, had to pick one cuz the question didn't specify)
	//note the logic works the same to rotate clock-wise
	public static void rotateMatrix (int[][] matrix)
	{
		if (matrix!=null)
		{
			int n = matrix.length;
			if (n!=0)
			{
				int o = matrix[0].length;
				assert (n==o): "Matrix is not square, can't rotate!";
				rotateMatrix (matrix,0,n-1);
			}
		}
	}
	private static void rotateMatrix (int[][] matrix, int leftIdx, int rightIdx)
	{
		int tmp;
		
		if (leftIdx < rightIdx)			//base case, matrix of 1 or 0 elements doesn't need to be rotated
		{
			//rotate the outer edge elements, do in place
			// walk the top row and for each element, do a 4 way swap around the square
			for (int i = 0; i < (rightIdx-leftIdx); i++)
			{
				//tmp = matrix[i][leftIdx];
				//matrix[i][leftIdx] = matrix[rightIdx][leftIdx+i];
				//matrix[rightIdx][leftIdx+i] = matrix[rightIdx-i][rightIdx];
				//matrix[rightIdx-i][rightIdx] = matrix[leftIdx][rightIdx-i];
				//matrix[leftIdx][rightIdx-i] = tmp;
				
				tmp = matrix[leftIdx+i][leftIdx];
				matrix[leftIdx+i][leftIdx] = matrix[rightIdx][leftIdx+i];
				matrix[rightIdx][leftIdx+i] = matrix[rightIdx-i][rightIdx];
				matrix[rightIdx-i][rightIdx] = matrix[leftIdx][rightIdx-i];
				matrix[leftIdx][rightIdx-i] = tmp;
	
			}
			
			//call recursively on the "inner square"
			rotateMatrix (matrix, leftIdx+1, rightIdx-1);
		}
	}

}
