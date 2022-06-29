package structure;

import algorithm.Spot;

public class DynamicSpotArray
{   
	public Spot array[];   
	public int count;   
	private int sizeofarray;
	private Spot tmp;
	//creating a constructor of the class that initializes the values  
	public DynamicSpotArray()   
	{   
		array = new Spot[1];   
		count = 0;   
		sizeofarray = 1;   
	}
	public Spot get(int i)
	{
		return array[i];
	}
	public void reverse()
	{
		for(int i=0;i<count/2;i++)
		{
			tmp = array[i];
			array[i] = array[count - 1 - i];
			array[count - 1 - i] = tmp;
		}
	}
	public int indexOf(Spot x)
	{
		for(int i=0;i<count;i++) 
		{
			if(array[i] == x) return i;
		}
		return -1;
	}
	//creating a function that appends an element at the end of the array  
	public void addElement(Spot a)   
	{   
		//compares if the number of elements is equal to the size of the array or not  
		if (count == sizeofarray)   
		{   
			//invoking the growSize() method that creates an array of double size      
			growSize();   
		}   
		//appends an element at the end of the array   
		array[count] = a;   
		count++;   
	}   
	//function that creates an array of double size  
	public void growSize()   
	{   
		//declares a temp[] array      
		Spot temp[] = null;   
		if (count == sizeofarray)   
		{   
			//initialize a double size array of array  
			temp = new Spot[sizeofarray * 2];   
			{   
				for (int i = 0; i < sizeofarray; i++)   
				{   
					//copies all the elements of the old array  
					temp[i] = array[i];   
				}   
			}   
		}   
		array = temp;   
		sizeofarray= sizeofarray * 2;   
	}   
	//the method removes the unused space  
	public void shrinkSize()   
	{   
		//declares a temp[] array      
		Spot temp[] = null;   
		if (count > 0)   
		{   
			//creates an array of the size equal to the count i.e. number of elements the array have  
			temp = new Spot[count];   
			for (int i = 0; i < count; i++)   
			{   
				//copies all the elements of the old array  
				temp[i] = array[i];   
			}   
			sizeofarray = count;   
			array = temp;   
		}   
	}   
	//creating a function that removes the last elements for the array  
	public void removeElement()   
	{   
		if (count > 0)   
		{   
			count--;   
		}   
	}   
	//creating a function that deletes an element from the specified index  
	public void removeElementAt(int index)   
	{   
		if (count > 0)   
		{   
			for (int i = index; i < count - 1; i++)   
			{   
				//shifting all the elements to the left from the specified index  
				array[i] = array[i + 1];   
			}   
			count--;   
		}   
	}
}