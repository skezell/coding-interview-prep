coding-interview-prep
=====================

Code examples that are an intended to be a "brush up" to prepare for a coding interview after a while away from coding. Inspiration comes from "Cracking the Coding Interview" by Gayle McDowell and my first year college textbook "Algorithms" by Robert Sedgewick, but is not intented to be an exhaustive set of solutions for either. 

Note all of the solutions aren't really "complete", just intended to call out the important or interesting details of a particular problem. Use these to understand a data structure/algorithm/solution, not as a complete solution. It does include Junit test classes, but again these do not cover a comprehensive set of test cases or make much effort to use Asserts to check for failure, since the emphasis is on letting the user "see" what's happening rather than a pass/fail status.

TestMatrix is the most interesting of the unit tests.

Problems included:

1) Matrix problems 
  - zero out the rows & columns of any element that is 0, given and MXN matrix
  - rotate NXN matrix by 90 degrees
  
2) ArrayList implementation & finding/sorting algorithms
  - create an array list
  - sort array list via quicksort (with and without optimization)
  - find element in the list (unsorted linear search, sorted binary search)

3) Towers of Hanoi (Towers.java) - implements the classic Towers of Hanoi problem, but generalizes for any number of discs (n) and towers (t); It returns the minimum number of moves required to solve given the parameters and stores the moves used to get to the solution (or -1 if no solution exists). Does not currently support changing the problem parmeters after you create the Towers object, for simplicity.
