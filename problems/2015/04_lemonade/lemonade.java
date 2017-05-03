// Arup Guha
// 8/7/2015
// Solution to 2015 UCF Locals Problem: Lemonade

import java.util.*;

public class lemonade {

    final public static int OZ_SUGAR_PER_BAG = 80;

    public static void main(String[] args) {

        Scanner stdin = new Scanner(System.in);
        int numCases = stdin.nextInt();

        // Process each case.
        for (int loop=0; loop<numCases; loop++) {

            // Get case parameters.
            int numDays = stdin.nextInt();
            int lemonsPerCup = stdin.nextInt();
            int ozSugarPerCup = stdin.nextInt();

            // Read in daily information.
            day[] schedule = new day[numDays+1];
            for (int i=0; i<numDays; i++) {
                int cups = stdin.nextInt();
                int lemon = stdin.nextInt();
                int sugar = stdin.nextInt();
                schedule[i] = new day(cups, lemon, sugar);
            }

            // Dummy day to process last actual day that ingredients (separately) are bought.
            schedule[numDays] = new day(0,0,0);

            // Calculate cost of lemons - set up all necessary variables for day 1.
            int curPrice = schedule[0].costLemon, curLemons = lemonsPerCup*schedule[0].numCups, res = 0;

            // Go through all future days - update anytime we get a better lemon price.
            for (int i=1; i<=numDays; i++) {

                // We will buy on day i - process cost and reset future lemon cost & # of lemons.
                if (schedule[i].costLemon < curPrice) {
                    res += (curLemons*curPrice);
                    curPrice = schedule[i].costLemon;
                    curLemons = 0;
                }

                // Always adjust the number of lemons we need extra for this day.
                curLemons += lemonsPerCup*schedule[i].numCups;
            }

            // Reset everything to process sugar.
            curPrice = schedule[0].costBagSugar;
            int curOzSugarNeed = ozSugarPerCup*schedule[0].numCups;

             // Go through all future days - update anytime we get a better sugar price.
            for (int i=1; i<=numDays; i++) {

                // We will buy on day i - process cost and reset future lemon cost.
                if (schedule[i].costBagSugar < curPrice) {

                    // This is strange - sometimes we'll need to buy no bags of sugar due to previous leftover.
                    int bagsNeeded = curOzSugarNeed > 0 ? (curOzSugarNeed+OZ_SUGAR_PER_BAG-1)/OZ_SUGAR_PER_BAG : 0;

                    // These updates are straight-forward.
                    res += bagsNeeded*curPrice;
                    curPrice = schedule[i].costBagSugar;

                    // This one is strange - frequently this will be negative, indicating we have a surplus
                    // of sugar for right now.
                    curOzSugarNeed -= bagsNeeded*OZ_SUGAR_PER_BAG;
                }

                // Always adjust the ounces of sugar we need extra for this day.
                curOzSugarNeed += ozSugarPerCup*schedule[i].numCups;
            }

            // Here is our final cost in cents.
            System.out.println(res);
        }
    }
}

class day {

    public int numCups;
    public int costLemon;
    public int costBagSugar;

    public day(int cups, int lemon, int sugar) {
        numCups = cups;
        costLemon = lemon;
        costBagSugar = sugar;
    }
}
