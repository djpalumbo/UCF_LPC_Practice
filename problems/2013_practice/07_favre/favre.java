// Arup Guha
// 9/6/08 (Yes, I know I suck for writing the solution the day of the contest!)
// Solution for 2008 UCF Local Contest Problem: Favre

import java.util.*;
import java.io.*;


public class favre {
	
	public static void main(String[] args) throws Exception {
		
		Scanner fin = new Scanner(new File("favre.in"));
		
		// Read in the hard way so we have no trouble with I/O when we 
		// are forced to read in whole lines. Generally, you shouldn't mix
		// reading statements that read tokens and those that read in full
		// lines.
		int numCases = Integer.parseInt(fin.nextLine().trim());
		
		// Loop through each case.
		for (int i=1; i<=numCases; i++) {
			
			int numCities = Integer.parseInt(fin.nextLine().trim());
			
			String[] cities = new String[numCities];
			
			// Store all the city names for this case.
			for (int j=0; j<numCities; j++) 
				cities[j] = fin.nextLine();
				
			
			int numLogEntries = Integer.parseInt(fin.nextLine().trim());
			
			System.out.println("Brett Log #"+i+":");
			String curCity = "";
			
			// Loop through each log entry.
			for (int j=0; j<numLogEntries; j++) {
				
				String logEntry = fin.nextLine();
				
				// We always print the first log entry!
				if (j == 0) {
					System.out.println("   "+logEntry);
					curCity = findCity(cities, logEntry);
				}
				
				// This is the regular case.
				else {
					
					// Determine which city name (if any) is in this log entry.
					String nextCity = findCity(cities, logEntry);
					
					// If there was a city AND it's not the last city Brett was
					// in, print it and update the last city he was it.
					if (nextCity != null && !nextCity.equals(curCity)) {
						System.out.println("   "+logEntry);
						curCity = nextCity;
					}
				}
			}
			System.out.println();
		}
		fin.close();
	}
	
	public static String findCity(String[] cities, String logEntry) {
		
		// Look for each city name, one by one.
		for (int i=0; i<cities.length; i++) {
			
			// Do the string match right here...
			// Since I am lazy, I'll do the brute force algorithm =)
			
			// Note: j is the starting index into the logEntry
			for (int j=0; j<=logEntry.length()-cities[i].length(); j++) {
				
				// k is the index into the current city we are checking.
				int k;
				for (k=0; k<cities[i].length(); k++) {
					
					// Break out of the loop if there is a mismatched letter.
					if (cities[i].charAt(k) != logEntry.charAt(j+k))
						break;
				}
				
				// We never broke out of the loop so we got a match!!!
				if (k == cities[i].length())
					return cities[i];
			}
			
		}
		
		// If we ever get here, there was no city name in the entry.
		return null;
	}
}