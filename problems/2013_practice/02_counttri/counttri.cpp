// Arup Guha
// 9/2/08
// Solution to 2008 UCF Local Contest Problem: Count Triangles

#include <iostream>
#include <fstream>
#include <math.h>

using namespace std;

#define MAX_SIZE 100
#define EPSILON 0.000001

struct pt {
    int x;
    int y;
};

bool mkTri(struct pt *a, struct pt *b, struct pt *c);
double slope(struct pt *a, struct pt*b);

int main() {
     
     ifstream fin("counttri.in");
     
     int n;
     
     fin >> n;

     // Loop through all of the cases.     
     for (int i=1; i<=n; i++) {
     
         int numPts;
         fin >> numPts;         
         
         struct pt list[MAX_SIZE];
         
         // Read in all the points.
         for (int j=0; j<numPts; j++) 
             fin >> list[j].x >> list[j].y;
             
         int cnt = 0;
         
         // Go through each combination of three points.
         for (int a=0; a<numPts; a++) 
             for (int b=a+1; b<numPts; b++)
                 for (int c=b+1; c<numPts; c++)
                 
                     // If they form a triangle, add one to our count.
                     if (mkTri(&list[a],&list[b],&list[c]))
                         cnt++;
                      
         // Output our result.                
         cout << "Test case #" << i << ": " << cnt << " triangle(s) can be formed." << endl;
         cout << endl;
     }
     
     fin.close();
     return 0;
}

// Returns true iff the points pointed to by a, b and c form a triangle.
bool mkTri(struct pt *a, struct pt *b, struct pt *c) {

    // The three points all lie on a vertical line.
    if (a->x == b->x && b->x == c->x)
        return false;
        
    // Exactly 2 of the points lie on a vertical line, so a triangle IS formed.
    if (a->x == b->x || b->x == c->x)
        return true;
        
    // The slope formed by ab and bc is the same. The EPSILON is for accuracy
    // errors of doubles. Due to the numbers involved, I am guaranteed that
    // the slopes have to be the same, since the lowest difference between 
    // slopes must exceed 1/38000.
    if (fabs(slope(a,b) - slope(b,c)) < EPSILON)
        return false;
    
    // Otherwise, the points aren't collinear.
    return true;
}

// Returns the slope formed by points a and b. Should NOT be called if the
// two points form a vertical line.
double slope(struct pt *a, struct pt *b) {
    return ((double)(b->y - a->y))/(b->x - a->x);       
}
