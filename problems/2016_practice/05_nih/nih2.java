// Arup Guha
// 8/31/2010
// Alternate Solution to UCF 2010 Local Contest Problem: NIH
/* Uses Recursion - valid since # diseases <= 10 */

import java.io.*;
import java.util.*;

// Stores one level of funding.
class funding {
	
	// Bad style, but makes life easier to access things.
	public int cost;
	public int numLivesSaved;
	
	public funding(int price, int lives) {
		cost = price;
		numLivesSaved = lives;
	}
}

class disease {
	
	// Also bad style - same reason.
	public funding[] levels;
	
	// Just get this from the file.
	public disease(Scanner fin, int numLevels) {
		levels = new funding[numLevels];
		
		// Fill in our array.
		for (int i=0; i<numLevels; i++) {
			int price = fin.nextInt();
			int lives = fin.nextInt();
			levels[i] = new funding(price, lives);
		}
	}
	
}

public class nih2 {
	
	private disease[] allDiseases;
	private int budget;
	
	public nih2(disease[] list, int money) {
		allDiseases = list;
		budget = money;
	}
	
	public static void main(String[] args) throws Exception {

		Scanner fin = new Scanner(new File("nih.in"));
		int numCases = fin.nextInt(); 
			
		// Go through each test case.
		for (int loop=1; loop<=numCases; loop++) {
			
			// Initial info for all the diseases.
			int numDiseases = fin.nextInt();
			disease ourList[] = new disease[numDiseases];
			int budget = fin.nextInt();
			
			// Read in and create each disease object.
			for (int i=0; i<numDiseases; i++)
				ourList[i] = new disease(fin, 4);
			
			// Creates the nih object and output result.
			nih2 thisInstitute = new nih2(ourList, budget);
			System.out.println("Budget #"+loop+": Maximum of "+thisInstitute.maxLivesSaved()+" lives saved.\n");
		}
		
		fin.close();		
	}
	
	// Wrapper method
	public int maxLivesSaved() {
		return maxLivesSavedRec(allDiseases.length, budget);
	}
	
	
	// Solves the given problem using an adaptation of the DP solution to
	// subset sum.
	public int maxLivesSavedRec(int numDiseases, int moneyLeft) {
		
		// We've reached the end.
		if (moneyLeft <= 0)
			return 0;
		if (numDiseases == 0)
			return 0;
			
		int max = 0;
		
		// Go through each level of this disease.
		for (int level=0; level<allDiseases[numDiseases-1].levels.length; level++) {
				
			// If we have money at this level, let's try to spend it and see if this works.
			if (allDiseases[numDiseases-1].levels[level].cost <= moneyLeft) {
				int livesSaved = maxLivesSavedRec(numDiseases-1, moneyLeft - allDiseases[numDiseases-1].levels[level].cost) +
								 allDiseases[numDiseases-1].levels[level].numLivesSaved;
								 
				if (livesSaved > max)
					max = livesSaved;
			}
		}
		
		// Let's not fund this disease at all.
		int otherWay = maxLivesSavedRec(numDiseases-1, moneyLeft);
		if (otherWay > max)
			max = otherWay;
				
		return max;
	}
	
	// Returns a copy of array.
	public int[] copy(int[] array) {
		int[] temp = new int[array.length];
		for (int i=0; i<array.length; i++)
			temp[i] = array[i];
		return temp;
	}
}