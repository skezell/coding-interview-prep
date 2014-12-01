package solutions;

import java.util.Arrays;

public class Genome 
{
	//solution for codility Genome Range problem
	public static int[] solveGenomeMinbyRange (String S, int[] P, int[] Q)
	{
		//create a new array to hold the results of each query
		int[] R = new int[P.length];
		
		//first create a prefix sum array to keep track of occurrences of each of the A, C or G letters
		//note that you don't need to create one for T because if there are no A, C, G then it must be all T's
		//the ith row corresponds to the number of A, C & G's encountered from the beginning of the string
		//		up until the i+1 letter of the string
		//this means that the difference between any two rows is the number of occurrences of each letter
		//     found in the range the two rows correspond to
		//the 0 indexed row is our base case, this makes the code easier to compute
		int[][] occurrences = new int[3][S.length()+1];
		for (int i =0; i< S.length(); i++)
		{
			//copy the previous row
			occurrences[0][i+1] = occurrences[0][i];
			occurrences[1][i+1] = occurrences[1][i];
			occurrences[2][i+1] = occurrences[2][i];
			
			//translate from A, C or G to an array index
			int column = getColumnIdxfromChar(S.charAt(i));
			
			//increment only the one we just found if it's A, C or G (skip T)
			if (column >=0)
			{
				occurrences[column][i+1]++;
			}
			
		}
		
		System.out.println (Arrays.deepToString(occurrences));
		
		//now that we've built our prefix helper, walk through each of the queries and 
		//put the answer in the R array
		for (int i=0; i < P.length; i++)
		{
			int answer = 4;			//if we don't find any other letters, must be a T
			int diffA, diffC, diffG;
			
			diffA = occurrences[0][Q[i]+1] - occurrences[0][P[i]];
			diffC = occurrences[1][Q[i]+1] - occurrences[1][P[i]];
			diffG = occurrences[2][Q[i]+1] - occurrences[2][P[i]];

			
			if (diffA>0)
			{
				answer=1;
			}
			else if (diffC>0)
			{
				answer=2;
			}
			else if (diffG>0)
			{
				answer=3;
			}
			
			R[i]=answer;
		}
		
		
		return R;
	}
	
	private static int getColumnIdxfromChar (char c)
	{
		int idx= -1;
		
		switch (c)
		{
		case 'A':
			idx=0;
			break;
		case 'C':
			idx=1;
			break;
		case 'G':
			idx=2;
			break;
		}
		
		return idx;
	}
	
	public static void main(String[] args) 
	{	
		String s = new String ("CTACGGTA");
		int[] p = new int[] {0,3,2,4};
		int[] q = new int[] {7,3,4,5};
		
		int[] r = solveGenomeMinbyRange(s, p, q);
		
		System.out.println (Arrays.toString(r));
	}

}
