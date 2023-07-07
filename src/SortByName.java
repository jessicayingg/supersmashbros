// Name: Jessica Ying
// Date: June 18, 2023
// Description: This is a Comparator class for sorting Characters in a program that is a
// rendition of Super Smash Bros

import java.util.*;

public class SortByName implements Comparator <Character> {

	// Description: This method compares two Characters based on their names
	// Parameters: Two Characters to compare
	// Return: An integer representing a comparison of the letters of their names
	public int compare(Character c1, Character c2) 
	{
		return c1.getName().compareTo(c2.getName());
	}

	
}
