package solutions;
import java.util.*;

//this class implements the classic "Towers of Hanoi" problem, generalized to be for any number of discs and towers
// returns boolean that describes if there was a solution; both records the sequence of moves and changes the stack state
// did not put a lot of work into exposing the internal stack state, just prints to verify
public class Towers 
{
	//a general solution for N discs and T towers 
	// really, you only ever need 3 towers, it was just an interesting exercise to allow for more towers
	// and simulate a person moving stuff around (I assumed that a real person would always use as many towers as 
	// they had available, since it decreases the total number of moves)
	private int numberOfDiscs=0;
	private int numberOfTowers=0;
	private Stack[] towers = new Stack[numberOfTowers];
	
	//holds the sequence of moves that solve the problem
	public java.util.ArrayList<String> moves = new java.util.ArrayList<String>();
	
	//for simplicity, you just set the parameters of your problem at object creation time
	public Towers(int n, int t)
	{
		numberOfDiscs = n;
		numberOfTowers = t;
		
		//create an array of stacks, each stack is for one tower
		towers = new Stack[numberOfTowers];
		for (int i =0; i< t; i++)
		{
			towers[i]= new Stack<Integer>();
		}
		
		if (t>0)
		{
			//put discs into the start stack towers[0]
			for (int i = n; i>0; i--)
			{
				towers[0].push(new Integer(i));
			}
		}	
	}
	
	//solve the problem and return the number of moves required to reach the solution
	public int solve()
	{
		//reset the sequence of moves, just in case someone calls solve multiple times (-1 if no valid solution)
		moves = new java.util.ArrayList<String>();	
		
		if (numberOfTowers<2)	//if only 1 tower, there is no way to solve the problem
		{
			return -1;
		}
		else if (numberOfDiscs==0)		//if there are no discs to move, the problem is already solved
		{
			return 0;
		}
		else
		{
			//solve for the general case, return the number of moves if successful
			if (solveTowers (numberOfDiscs, 0, 1, computeExtraTowers()))
			{
				return (moves.size());
			}
			else
			{
				return -1;
			}
		}
	}
	
	//figures out which one is the buffer; in most cases the buffer is always idx=0, 1 or 2
	//but it also handles the cases where there are really too few towers and returns -1 to the caller in that case
	private int findValidBuffer(int start, int target)
	{	
		int idx = -1;		// for compactness, idx holds the place of a valid buffer or -1 if none found
				
		//walk the array and find a valid buffer, note that in practice it's always going to be idx = 0, 1 or 2
		//except doing it this way also catches the case where the number of towers is not sufficient for the number of discs
		// e.g. n=1, t=2 has a valid solution even though there is no idx=2
		int i =0;
		while ((i<towers.length) && (idx == -1))
		{			
			//it's valid place to use as a buffer if:
			// 1) it's not our start location
			// 2) it's not our target location, the place we are trying to move to
			if ((i!=start) && (i!=target))
			{
				idx=i;
			}
			
			i++;
		}
		
		return (idx);
			
	}
	
	//solve the portion of towers specified by n (the number of discs to move from start to target)
	private boolean solveTowers(int n, int start, int target, int numExtraTowers)
	{	
		boolean success = true;
		int buffer, disc;
		
		if (n<=0)		//# discs can be less than 0 when we n is small and t is large
		{
			//do nothing, no discs need to be moved
		}
		else if (n==1)
		{
			//move the single disc from start to target
			success = moveDisc (start, target);
		}
		else
		{
			buffer = findValidBuffer(start, target);		//generally, the buffer is always in the set {0,1,2} not start or target
			if (buffer==-1)			
			{
				//couldn't find a place to put the current disc
				success = false;
			}
			else
			{
				//general case algorithm is:
				//if you have any extra towers passed in, use them to cache the discs on the top
				// of the start tower, then put them back on target when you're done
				// if you have extra towers, you could get a valid solution with a)cache the top most discs until close to being done
				// or b)move the top section of the tower, then cache one of the discs further down until you need it
				// --since the top most discs are the one that get moved the most frequently, choosing option a) yields the 
				//	fewest possible moves
				//note that this means that you only use the extras in the first call, successive recursive calls do not
				// use the extras at all
				
				//cache the top most discs to the extra towers
				//careful to not pop off empty stack if numExtraTowers > n
				int actualExtrasUsed =0;
				while ((actualExtrasUsed<numExtraTowers) && !(towers[start].isEmpty()))
				{
					moveDisc (start, actualExtrasUsed+3);
					actualExtrasUsed++;
				}
				
				
				if (solveTowers (n-1- actualExtrasUsed, start, buffer, 0))
				{
					//move the nth disc to target
					moveDisc (start, target);
					
					//move n-1 (minus any extra's cached in the extra towers) discs from buffer back to target, do by calling recursively
					success = success && solveTowers (n-1-actualExtrasUsed, buffer, target,0);
					
					//note: if you had room to move the smaller set of discs, you have room to move it back and the 
					//2nd call to solve towers won't ever fail, this is just double check that the algorithm is working
					//correctly and not getting the stacks into a bad state
				}
				
				//cache the top most discs to the extra towers
				for (int i = actualExtrasUsed; i>0; i--)
				{
					moveDisc (i+2, target);
				}
				
			}
		}
	
		return(success);
		
	}
	
	//computes the number of extra towers available to cache discs
	private int computeExtraTowers()
	{
		if (numberOfTowers<=3)
		{
			return 0;
		}
		else 
		{
			return numberOfTowers-3;		
		}		
	}

	//move the disc at the top of start to target and record the move
	private boolean moveDisc (int start, int target)
	{
		boolean success=false;
		int topOfStack=numberOfDiscs+1;			//if stack is empty, use n+1 as comparison so that all discs are valid
		
		if (!towers[target].isEmpty())
		{
			topOfStack = (Integer)towers[target].peek();
		}
		
		//instead of testing all over the place to see if the stack is already empty, just check here when moving
		// for cases where n is small and t is large, we sometimes call with an empty stack 
		// alternatively, you could create more base cases in the solve algorithm
		if (!towers[start].isEmpty())				
		{
			int disc = (Integer)towers[start].pop();
			
			//so this check is just to "prove" that the rules of the game aren't accidentally violated;
			// in practice, the solve algorithm ensures that this won't ever happen
			if (disc< topOfStack)
			{
				towers[target].push(disc);
				addMove (disc, start, target);	
				success = true;
			}
			else
			{
				System.out.println ("Rules of the game have been violated!");
			}
		}
		else
		{
			success=true;		//if nothing to move, consider this a success :)
		}
		
		
		return (success);
	}
	//this method just lets you record moves, to display to the user (and also keeps the count of moves)
	private void addMove (int disc, int start, int end)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("{MOVE DISC #");
		sb.append (disc);
		sb.append(" FROM TOWER #");
		sb.append (start+1);
		sb.append (" TO TOWER #");
		sb.append(end+1);
		sb.append("}");
		
		moves.add(sb.toString());
	}
		
	//returns a string that expresses the state of the towers
	public String getState()
	{
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < numberOfTowers; i++)
		{
			sb.append("[TOWER #");
			sb.append (i+1);
			sb.append ("=");
			sb.append (towers[i].toString());
			sb.append ("]");
		}
		
		return (sb.toString());
	}
	
	//returns a string that describes the moves used to get to the solution
	public String getMoves()
	{
		return moves.toString();
	}
	
	
}
