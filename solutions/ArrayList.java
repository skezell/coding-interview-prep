package solutions;
import java.util.Iterator;

public class ArrayList <E extends Comparable> implements Iterable<E>
{
	//Implements some aspects of the ArrayList class with a few notable changes (since's a learning class), you'd just use ArrayList in a production piece of code)
	//Notable differences:
	//1) this class keeps track of it's sorted status
	//2) it tracks compare and swap operations when finding/sorting, so you can see how the different algorithms are performing
	//3) it implements multiple finds and sorts, to let you see how each works and the performance as measured by number of operations
	//4) it requires that you insert Comparable objects, otherwise find/sort would be very uninteresting, note that the use of a generic
	//		param means while you can use with any Comparable, you have to declare at usage time which Comparable you are going to use
	
	//Uses an array to store the items plus a pointer to the end, so we can have extra space
	//assumes size won't exceed Integer.MAX_VALUE
	
	private static final int DEFAULT_CAPACITY = 100;	//if user doesn't specify, we will start off with room for this many elements
	private static final int NULLIDX=-1;				//marker than indicates empty ArrayList
	
	private Comparable [] array;							//storage for our objects
	private int endIdx=NULLIDX;								//keeps track of last item we're actually using in array
	private boolean sorted = false;							//keeps track of our sorted status, find uses a binary search if the ArrayList is sorted
	private int threshold = 3;								//quick sort implements a threshold beyond which it switches to insertion sort for efficiency
	
	//some variables for "instrumentation", since this is a demonstration class to learn about sorts & finds, keep track of 
	//how many compares & swaps we do in find/sort operations;
	private int numOpCompares = 0;
	private int numOpSwaps = 0;
	
	//nested (inner class) iterator for this implementation of ArrayList
	class MyIterator implements Iterator<E>
	{
		int currentIdx=0;
		
		public boolean hasNext()
		{	
			return (endIdx!=NULLIDX) && (currentIdx<endIdx);
		}
		
		public E next()
		{	E obj = null;
		
			if (this.hasNext())
			{
				obj = (E) array[currentIdx++];
			}
			
			return obj;
		}
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
	
	//Constructors
	public ArrayList()
	{
		array = new Comparable[DEFAULT_CAPACITY];	
	}
	
	public ArrayList(int capacity)
	{
		array = new Comparable[capacity];
	}
	
	//public methods
	
	public Iterator<E> iterator()
	{
		return new MyIterator();
	}
	
	public boolean isEmpty()
	{
		return (endIdx == NULLIDX);
	}
	
	public void clear()
	{
		//if you wanted to optimize for speed, you could just reset endIdx and let the elements get overwritten as needed
		//the rest of the implementation ONLY examines endIdx to determine is the ArrayList is empty, so it would all work
		//however, then we would not release the objects in the array until the elements were overwritten, which is not desirable
		array = new Comparable[DEFAULT_CAPACITY];
		endIdx= NULLIDX;
	}
	
	public void setCapacity (int newCapacity)
	{	
		Comparable[] newArray;
		
		//first things first, we assume that newCapacity is valid and that array !=null
		assert array!=null;
		assert (newCapacity >0): newCapacity;
		
		//what if the user passes in a capacity that is not sufficient for the number of items already in the ArrayList?
		//in a real implementation, you'd probably throw an exception to the consumer of the ArrayList
		assert (newCapacity>endIdx+1): newCapacity;
		
		//Ok, now that we have ensured that our inputs are good, create a new array
		if (newCapacity != getCapacity())		//if it's already the specified capacity, don't really do anything
		{
			//make a new array
			newArray = new Comparable[newCapacity];
			
			//copy the elements to the new array
			System.arraycopy(array, 0, newArray, 0, endIdx+1);
			
			//finally, replace our current array with the new, larger one
			array =newArray;
		}
		
	}
	
	public int getCapacity()
	{		
		assert array==null;
		return (array.length);
	}

	public int getSize()
	{
		//endIdx is index of last item in our list, beyond which possibly is empty space in our array
		//since the array is 0 index, add 1 to represent the number of elements in the ArrayList
		return (endIdx+1);
	}
	
	public boolean setItem(E obj, int idx)
	{
		boolean success= false;
		
		//simply overwrites the item at this idx with the new one, does NOT insert & returns true i
		//production implementation would normally throw some exceptions for invalid input, but here we just don't do the operation
		
		if (isValidIdx(idx))	//if location specified is between 0 and the end of the in-use part of the array
		{
			array[idx]= obj;
			success = true;
		}
		
		return success;
	}
	
	public E getItem (int idx)
	{
		//returns the item at idx, null if idx is not valid
		
		if (isValidIdx(idx))	//if location is between 0 and the end of the in-use part of the array
		{
			return ((E) array[idx]);
		}
		else
		{
			return null;
		}
	}
	
	public void add (E obj)
	{
		//if no location specified, put it at the end of the list
		add (obj, endIdx+1);	
	}
	
	public void add (E obj, int idx)
	{	int cp;
	
		//check to see if we need to increase our capacity before inserting	
		cp = getCapacity();
		if (idx+1>cp)
		{			
			setCapacity((int)Math.round(cp + (cp*.5)));
		}
		
		endIdx++;		//increase our count, because we are adding an element
		
		//if not at end, shift elements to the right to make a space for obj 
		if (idx != endIdx)
		{
			shiftRight(idx);
		}
		
		//put the new element at idx
		array[idx]= obj;
	
		//if we just added an element, we may have messed up the sort order
		sorted = false;
		
	}
	
	public boolean isSorted()
	{
		return sorted;
	}
	
	public E remove()
	{
		//if no location specified, pulls item off the end of the array
		return (remove(endIdx));
	}
	
	public E remove(int idx)
	{
		E obj = null;
		
		if (isValidIdx(idx))		//valid idx 
		{
			//grab the element
			obj = (E) array[idx];
			
			//move everything to the left to fill the gap created
			//note that when the gap is filled, this will release the reference to the removed item
			shiftLeft (idx);
			
			//to make sure we're not keeping extra references that we don't need, 
			//make sure we blank out the item at the very end (is now duplicate, after the shiftLeft)
			array[endIdx] = null;
			
			//decrement idx, since we just pulled out an element
			endIdx--;
		}

		return obj;
	}
	
	public void setThreshold(int n)
	{
		threshold=n;
	}
	
	//performs search on the target item and also keeps track of how many compares are involved in the search
	//note that if we are sorted, do a binary search (faster) but leaves it up to the user to call sort previously if they 
	//want to incur the sort hit in order to get faster searching
	public E find(E itm)
	{
		E obj = null;
		int i = 0;
		boolean found = false;
			
		instrumentationReset();
	
		if (sorted)
		{
			obj=binarySearch(0, endIdx, itm);
		}
		else
		{
			obj = linearSearch (0, endIdx, itm);
		}
		
		System.out.println ("FIND-->" + instrumentationToString());

		return (obj);
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("[ARRAYLIST ARRAY SIZE=");
		sb.append(array.length);
		sb.append("]");
		
		sb.append("[ENDIDX=");
		sb.append(endIdx);
		sb.append("]");
		
		if (!isEmpty())
		{
			for (int i = 0; i<=endIdx; i++)
			{
				sb.append (array[i].toString());
			
			}
		}
		
		return (sb.toString());
	}
	
	public void sort(boolean optimize)
	{
		instrumentationReset();
		
		//selectionSort (0, endIdx);
		//insertionSort (0, endIdx);
		quickSort(0,endIdx, optimize);
		
		sorted = true;
		
		System.out.println ("SORT-->" + instrumentationToString());
	}
	
	//private methods
	
	private E linearSearch (int startIdx, int endIdx, E itm)
	{	
		int i = startIdx;
		boolean found= false;
		E obj = null;
		
		//first, make sure we've got good inputs; since this is a private utility function, these are reasonable assumptions
		assert startIdx>=0: startIdx;
		assert endIdx< this.endIdx: endIdx;
		assert itm!=null;
		
		//walk the list and compare with each item, stop when found
		while ((i<=endIdx) && !(found))
		{
			if (instrumentedIsEqual((E)array[i], itm))
			{
				obj = (E)array[i];
				found=true;
			}
			i++;
		}
		
		return obj;
	}
	
	private E binarySearch(int startIdx, int endIdx, E itm)
	{
		int middle;
		int cmp;
		E foundObj = null;
		
		//first, make sure we've got good inputs; since this is a private utility function, these are reasonable assumptions
		assert startIdx>=0: startIdx;
		assert endIdx <= this.endIdx: endIdx;
		assert itm!=null;
		
		//if there down to one element (either idx's are equal or startIdx has moved past endIdx
		if (startIdx>=endIdx)
		{ 
			cmp = instrumentedCompareTo (itm, (E)array[startIdx]);
			if (cmp==0)
			{
				foundObj = (E)array[startIdx];
			}
		}
		else
		{
			//find the middle of the sublist we've been passed
			middle = startIdx + Math.round((endIdx-startIdx)/2);
			
			// compare middle to the target item
			cmp = instrumentedCompareTo (itm, (E)array[middle]);
			
			if (cmp==0)				//items are the same, we found a match, return it
			{
				foundObj = (E)array[middle];
			}
			else if (cmp == -1)   	//item is less than the middle, search the left part of the list recursively
			{
				foundObj = binarySearch (startIdx, middle-1, itm);
			}
			else					//item is greater than the middle, search the right part of the list recursively
			{
				foundObj = binarySearch(middle+1, endIdx, itm);
			}
		}

		return foundObj;
	}
	
	private void shiftRight (int startIdx)
	{
		//starting at the end of the array, shift all elements to the right one spot to make a gap for a new element
		//AF: there is enough room in array to shift right, someone else has increasedCapacity or thrown an exception
		for (int i =endIdx; i >= startIdx; i--)
		{
			array[i] = array[i-1];
		}
	}
	
	private void shiftLeft (int startIdx)
	{	
		//starting at startIdx, shift all elements to the left one (fill a gap where an element was removed)
		for (int i = startIdx; i <= endIdx; i++)
		{
			array[i] = array[i+1];
		}
		
		//note that this function does not alter endIdx, it's a utility function that just shifts array elements, 
		//the calling method has responsibility for keeping endIdx current with deletes/removes
	}
	
	private void instrumentationReset()
	{
		numOpCompares=0;
		numOpSwaps=0;
	}
	
	private String instrumentationToString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append ("[NUM CMPS=");
		sb.append (numOpCompares);
		sb.append ("]");
		
		sb.append ("[NUM SWAPS=");
		sb.append (numOpSwaps);
		sb.append ("]");
		
		return (sb.toString());
	}
	
	private boolean instrumentedIsEqual(E obj1, E obj2)
	{
		//just wraps the comparison operation so that we can keep track of how many we are doing
		numOpCompares++;
		return (obj1.compareTo(obj2)==0);
	}
	
	private int instrumentedCompareTo (E obj1, E obj2)
	{
		//just wraps the comparison operation so that we can keep track of how many we are doing
		numOpCompares++;
		return (obj1.compareTo(obj2));
	}
	
	private void instrumentedSwap (int i, int j)
	{
		Comparable tmp;
		
		//just wraps the swap between i and j elements, so that we can keep track of how many we are doing
		numOpSwaps++;
		
		tmp = array[i];
		array[i] = array[j];
		array[j]=tmp;
		
	}
	
	private boolean isValidIdx(int idx)
	{
		//ensures that referencing our array at idx won't cause an array out of bounds exception to be thrown
		return ((idx>=0) && (idx<=endIdx));
	}
	
	private void selectionSort(int startIdx, int endIdx)
	{	
		int swap, cmp;
	
		assert startIdx>0: startIdx;
		assert endIdx<= this.endIdx;
		
		for (int i = 0; i<=endIdx; i++)
		{
			//examine the ith element
			swap=i;
			
			//walk the array and see if any element is smaller, if so swap with the ith
			for (int j = i+1; j <= endIdx; j++)
			{
				//compare the two elements
				cmp = instrumentedCompareTo ((E)array[swap], (E)array[j]);
				
				//if array[j] < array[swap]
				if (cmp == 1)
				{
					//found a smaller item, make that be the swap item
					swap = j;
				}
				
				//swap the current element with the next smallest in the rest of the array
				//note that in some cases, it just swaps with itself
				//this is clearer code, to optimize, you'd check to see if i and swap were the same and not actually swap
			}
			
			instrumentedSwap(i, swap);
		}
	}
	
	private void insertionSort(int startIdx, int endIdx)
	{
		E workingElement;		//to hold the one we are currently trying to place 
		int hole;				//the current spot for insertion
		
		//start at the 2nd element; for each element you examine, perform an insertion at the correct place in the subarray to the left of i
		for (int i = 1; i <= endIdx; i++)
		{
			//grab the element we are going to place
			workingElement = (E) array[i];
			
			//the place where we just grabbed an element becomes our "hole"
			hole = i;
			
			//work backwards, shifting each element to the right one until we have the "hole" in the right place for workingElement
			while ((hole>0) && (instrumentedCompareTo((E)array[hole-1], workingElement)==1))
			{
				numOpSwaps++;
				array[hole]=array[hole-1];
				hole--;
			}
			
			array[hole]= workingElement;
		}
	}
	
	private void quickSort(int startIdx, int endIdx, boolean optimize)
	{
		int partitionIdx;
		
		//base case is subarray of 0 or 1 element, by definition it's sorted already
		if (startIdx < endIdx)
		{
			if (((endIdx-startIdx) > threshold) || !optimize)
			{
				//split the array, moving elements into the correct part of the subarray 
				//based on if they are greater or less than the partition element 
				//(also returns the place where it put the partition element)
				partitionIdx = partition (startIdx, endIdx);
				
				//recursively sort left and right subarrays
				quickSort (startIdx, partitionIdx-1, optimize);
				quickSort (partitionIdx+1, endIdx, optimize);
			}
			else
			{
				insertionSort (startIdx, endIdx);
			}
		}
		
	}
	
	private int partition (int leftIdx, int rightIdx)
	{
		int pivotIdx, storeIdx;
		E pivotElement;
		
		pivotIdx = pickPivotIdx(leftIdx, rightIdx);				//pick a pivot point
		pivotElement=(E) array[pivotIdx];						//grab the pivot element
		
		if (pivotIdx!=rightIdx)			//if our pivot is the right index, we don't need to move anything
		{
			//move right to pivot, so that the array to be processed now is left to right-1
			//note that we don't need to save the one we're overwriting at pivotIdx since it's also in pivotElement
			array[pivotIdx] = array[rightIdx];
			numOpSwaps++;
		}
		
		//start placing elements in the first position of this subarray
		storeIdx=leftIdx;
		
		//for each element in the array up to right (remember, right is our pivot element)
		for (int i = leftIdx; i < rightIdx; i++)
		{
			if (instrumentedCompareTo ((E)array[i], pivotElement)==-1)
			{
				instrumentedSwap (i, storeIdx);
				storeIdx++;
			}
		}
		
		//swap store and right 
		numOpSwaps++;
		array[rightIdx]=array[storeIdx];
		array[storeIdx] = pivotElement;
	
		return storeIdx;
	}
	
	private int pickPivotIdx(int leftIdx, int rightIdx)
	{
		//best performance for quick sort in most cases results if you pick the median of left, right and middle for your partition
		//note it would be better performance here also to just get the integers in the E objects that are in the ArrayList once, then do all our comparisons
		//but then the sort implementation would have to know more about the objects being stored 
		//Current this ArrayList/sort implementation only requires that you add the same object (<E> param) & that it implement the Comparable interface
		//in a real implementation, you'd probably create your own interface that knows about computing the median in addition to being Comparable
		
		int middleIdx = leftIdx + Math.round((rightIdx-leftIdx)/2);
		E left = (E) array[leftIdx];
		E middle = (E) array[middleIdx];
		E right = (E) array[rightIdx];
		int medianIdx;
		
		//only have 3 values, so only 6 possibilities to check, maximum of 3 comparisons
		if (left.compareTo(middle)<0)			//left<middle ==> either left<middle<right OR left<right<middle OR right<left<middle
		{
			if (middle.compareTo(right)<0)		//middle<right ==> **left<middle<right**
			{
				medianIdx = middleIdx;
			}
			else								//right<middle ==> either left<right<middle OR right<left<middle
			{
				if (left.compareTo(right)<0)	//left<right ==> **left<right<middle**
				{
					medianIdx=rightIdx;
				}
				else							//right<leftt ==> **right<left<middle**
				{
					medianIdx=leftIdx;
				}
			}
		}
		else									//middle<left ==> either middle<left<right OR middle<right<left OR right<middle<left
		{
			if (right.compareTo(middle)<0)		//right<middle ==> **right<middle<left**
			{
				medianIdx = middleIdx;
			}
			else								//middle<right ==> either middle<left<right OR middle<right<left
			{
				if (left.compareTo(right) <0)	//left<right ==> **middle<left<right**
				{
					medianIdx = leftIdx;
				}
				else							//right<left ==> **middle<right<left**
				{
					medianIdx = rightIdx;
				}
			}
		}
		
		return medianIdx;

	}
}


