// UCF Local Contest — September 1, 2012
// Safely Stacking Leftover Pizzas
// cgouge - 2012 Aug 30
//
// NOTE: run 'java safepizza 2>err >out' or something similar to get rid of the debug outputs.

import java.io.*;
import java.util.*;

class safepizza {

    // Simple data structure for pizzas: name and fraction to eat each day
    static class Pizza implements Comparable {
        Pizza(String name, int eatSlices, int cutSlices) {
            this.name = name;
            this.eatSlices = eatSlices;
            this.cutSlices = cutSlices;
        }
        public String name;
        public int eatSlices;
        public int cutSlices;
        // Override compareTo so that pizzas can be sorted on name.
        // Don't worry about slices since names will be unique for each test case.
        public int compareTo(Pizza other) {
            return this.name.compareTo(other.name);
        }
        public int compareTo(Object other) {
            return this.compareTo((Pizza)other);
        }
    }

    // This is a convenience class to reduce the typing of <>'s and casts
    static class PizzaList extends ArrayList<Pizza> {
        PizzaList() {
        }
        public PizzaList clone() {
            return (PizzaList)super.clone();
        }
    }

    // This is a convenience class to reduce the typing of <>'s
    static class ListOfPizzaLists extends ArrayList<PizzaList> {
        ListOfPizzaLists () {
        }
    }

    public static void main(String[] unused) throws IOException {
        Scanner sin = new Scanner(new File("safepizza.in"));
        int n = sin.nextInt();
        for (int i=1; i <= n; ++i) {
            // Process a test case (a "semester") from the input.
            // First get the two numbers P and D
            int nPizzas = sin.nextInt();
            int nDays = sin.nextInt();

            // Now read in all P of the Pizza descriptions
            Pizza[] pizzaInput = new Pizza[nPizzas];
            for (int j=0; j < nPizzas; ++j) {
                pizzaInput[j] = new Pizza(sin.next(), sin.nextInt(), sin.nextInt());
            }
            // Sort the input list of pizzas. This will help later, in choosing the "first alphabetically" stack.
            Arrays.sort(pizzaInput);

            // validate input
            System.err.printf("### Validating input for case %d:%n", i);
            for (Pizza p : pizzaInput) {
                System.err.printf("### %d/%d %s%n", p.eatSlices, p.cutSlices, p.name);
            }

            // We need to remember all the possible stacking orders for the pizzas.
            // These will be all the permutations of the input pizza list.
            ListOfPizzaLists permutationList = new ListOfPizzaLists();

            // Put all the pizzas in a 'working' list to start with, and create an empty
            // 'stacking' list, to set things up to generate permutations. The permutation
            // algorithm will move pizzas between the 'working' list and the 'stacking' list
            // to create different orderings, and store each ordering  as a list in the
            // 'permutationList'.
            PizzaList workingList = new PizzaList();
            for (Pizza p : pizzaInput) {
                workingList.add(p);
            }
            PizzaList orderToStack = new PizzaList();
            // Once we've set things up, only one call is needed to 'permute', it works recursively.
            permute(permutationList, orderToStack, workingList);

            // validate permutations
            System.err.printf("### Validating permutations generated correctly for case %d: (names truncated)%n", i);
            for (PizzaList pl : permutationList) {
                System.err.printf("### [");
                for (Pizza p : pl) {
                    System.err.printf(" %-12.12s", p.name);
                }
                System.err.printf(" ]%n###%n");
            }

            // Now that we have all the permutations (all possible stacking orders), simulate each one
            // for nDays of eating, and count the risky adjacencies.
            int[] riskyAdjacencies = new int[permutationList.size()];
            for (int r=0; r < permutationList.size(); ++r) {
                PizzaList stackingOrder = permutationList.get(r);
                int[] leftovers = new int[nPizzas];
                for (int d=1; d <= nDays; ++d) {
                    for (int j=0; j < nPizzas; ++j) {
                        Pizza p = stackingOrder.get(j);
                        leftovers[j] = p.cutSlices - ((d * p.eatSlices) % p.cutSlices);
                        if (leftovers[j] == p.cutSlices) leftovers[j] = 0;
                    }

                    // validate day-by-day leftovers
                    System.err.printf("### Validating Day %3d's leftover slices for stacking order:%n", d);
                    System.err.printf("### [");
                    for (Pizza p : stackingOrder) {
                        System.err.printf(" %-12.12s", p.name);
                    }
                    System.err.printf(" ]%n");
                    System.err.printf("### [");
                    for (int j=0; j < nPizzas; ++j) {
                        System.err.printf(" %-1d/%-10d", leftovers[j], stackingOrder.get(j).cutSlices);
                    }
                    System.err.printf(" ]%n");

                    // Copy the non-zero values for leftover slices to a new list. This is the
                    // equivalent of putting away the non-empty boxes.
                    int k=0;
                    int[] leftoversToStack = new int[nPizzas];
                    Pizza[] leftoverPizza = new Pizza[nPizzas];
                    for (int j=0; j < nPizzas; ++j) {
                        if (leftovers[j] > 0) {
                            leftoversToStack[k] = leftovers[j];
                            leftoverPizza[k] = stackingOrder.get(j);
                            k += 1;
                        }
                    }

                    // Now we can count cases where a pizza is stacked on top of a pizza with
                    // fewer leftover slices.
                    // Compare by cross-multiplying the fractions. (Leftover slices out of cut slices)
                    int risks = 0;
                    for (int j=0; j < k-1; ++j) {
                        if (leftoversToStack[j] * leftoverPizza[j+1].cutSlices > leftoversToStack[j+1] * leftoverPizza[j].cutSlices) {
                            risks += 1;
                        }
                    }
                    riskyAdjacencies[r] += risks;

                    // validate risk numbers
                    System.err.printf("### --------> daily risk = %1d, cumulative risk = %3d%n", risks, riskyAdjacencies[r]);
                    System.err.printf("### %n");

                }
                System.err.printf("### %n");
                System.err.printf("### %n");
            }

            // Now that all the risks are counted up, find the stacking order that has the minimum total risk.
            // Start the min risk value really high, and change it only when we find a smaller number.
            // Since we sorted the input and used an "alphabetized" permutation algorithm, this will be the
            // first one we find. (Other orderings with the same risk will not change the min value.)
            int minRisks = Integer.MAX_VALUE;  // really big number
            int indexOfMin = 0;
            for (int r=0; r < permutationList.size(); ++r) {
                if (riskyAdjacencies[r] < minRisks) {
                    minRisks = riskyAdjacencies[r];
                    indexOfMin = r;
                }
            }
            PizzaList bestStackingOrder = permutationList.get(indexOfMin);

            // Finally we have what we need to output for this case!
            System.out.printf("Leftover Pizza Stacking Order for Semester %d:%n", i);
            for (Pizza p : bestStackingOrder) {
                System.out.printf("%s%n", p.name);
            }
            System.out.printf("%n");  // Don't forget the blank line

        }
    }

    // This permutations algorithm is not very efficient. It allocates a lot of memory for
    // arrays. However, it should be adequate for the limits of the problem.
    //
    // Start with two lists, a 'build' list that is building up an ordering, and an 'avail'
    // list that contains the items we haven't used yet. Try out each of the 'avail' items
    // by moving it into the 'build' list, and then use recursion to solve the problem
    // again for the new 'build' and 'avail' lists. When the 'avail' list is empty (no more
    // items available) and the 'build' list is full, the permutation has been built. Pop
    // out of the recursion, going back to a state where 'avail' isn't full, and try the
    // next item.
    static void permute(ListOfPizzaLists perms, PizzaList buildList, PizzaList availList) {

        //show(buildList, "permute buildList param: ");
        //show(availList, "permute availList param: ");

        // When we have finished building a permutation, the buildList will be full and the availList will be empty.
        if (availList.isEmpty()) {
            //show(buildList, ">>>>>>>> ");
            perms.add(buildList.clone());
            return;
        }

        // Try each of the remaining items in availList
        for (Pizza x : availList) {
            // Make copies of the buildList and availList, but...
            PizzaList tmpBuildList = buildList.clone();
            PizzaList tmpAvailList = availList.clone();

            //show(tmpBuildList, "cloned buildList: ");
            //show(tmpAvailList, "cloned availList: ");

            // ...move 'x' from the copied availList to the copied buildList
            tmpAvailList.remove(x);
            tmpBuildList.add(x);

            //show(tmpBuildList, "moved '"+x+"' into buildList: ");
            //show(tmpAvailList, "moved '"+x+"' from availList: ");

            // Use these new lists to process the rest of the permutation via recursion.
            permute(perms, tmpBuildList, tmpAvailList);
        }
    }


}
