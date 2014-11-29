package solutions;

public class TestItm implements Comparable {
	//this class is just to represent a generic 'thing' that you might put in your collection
	//right now, just store a number so that we can test out our collection implementations
	int num;
	
	public TestItm(int num) {
		this.num = num;
	}
	
	public int getNum()
	{
		return this.num;
	}
	
	public void setNum (int num)
	{
		this.num = num;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append ("[");
		sb.append (num);
		sb.append("]");
		
		return sb.toString();
	}

	public int compareTo(Object o1) 
	{
		if (this.num == ((TestItm) o1).num)
		{
			return 0;
		}
	    else if ((this.num) > ((TestItm) o1).num)
	    {
	    	return 1;
	    }
	    else
	    {
	    	return -1;
	    }
	}

}
