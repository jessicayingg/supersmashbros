// Name: Jessica Ying
// Date: June 18, 2023
// Description: This is a Comparator class for sorting Characters in a program that is a
// rendition of Super Smash Bros

import java.util.*;

public class SortByNumPlayed implements Comparator <Character> {

	// Description: This method compares two Characters based on their number of times played
	// Parameters: Two Characters to compare
	// Return: An integer representing the difference between their number of times played
	public int compare(Character c1, Character c2) 
	{
		return c2.getNumPlayed() - c1.getNumPlayed();
	}

}
