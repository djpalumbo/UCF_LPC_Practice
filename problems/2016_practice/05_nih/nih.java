// Arup Guha
// 8/31/2010
// Solution to UCF 2010 Local Contest Problem: NIH

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

public class nih {
	
	private disease[] allDiseases;
	private int budget;
	
	public nih(disease[] list, int money) {
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
			nih thisInstitute = new nih(ourList, budget);
			System.out.println("Budget #"+loop+": Maximum of "+thisInstitute.maxLivesSaved()+" lives saved.\n");
		}
		
		fin.close();		
	}
	
	
	// Solves the given problem using an adaptation of the DP solution to
	// subset sum.
	public int maxLivesSaved() {
		
		// saved[i] will store the most lives you can save 
		// with i million bucks.
		int[] saved = new int[budget+1];
		
		// We haven't saved anyone yet!
		for (int i=0; i<saved.length; i++)
			saved[i] = 0;
		
		// Go through each disease, one by one.
		for (int i=0; i<allDiseases.length; i++) {
			
			int[] temp = copy(saved);
			
			// Go through each level of this disease.
			for (int level=0; level<allDiseases[i].levels.length; level++) {
				
				// This is just like subset sum. The difference is that we can only pick one level of funding,
				// so I look up the old information in saved, but store the better answers in temp.
				for (int money=budget; money>=allDiseases[i].levels[level].cost; money--)
					if (saved[money-allDiseases[i].levels[level].cost] + allDiseases[i].levels[level].numLivesSaved > temp[money])
						temp[money] = saved[money-allDiseases[i].levels[level].cost] + allDiseases[i].levels[level].numLivesSaved;
			}
			
			// We need to copy in these new numbers to get ready for the next disease.
			saved = temp;
		}
		
		// Get max of all levels.
		int max = saved[0];
		for (int i=1; i<saved.length; i++)
			if (saved[i] > max)
				max = saved[i];
				
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