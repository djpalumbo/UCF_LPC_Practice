#include "stdio.h"


int gcd (int a, int b);
int lcm (int a, int b);


int main(){

	int i, t, m, g, f, ans;

	FILE *ifp;

	ifp=fopen("stopping.in", "r");
	fscanf(ifp, "%d", &t);

	for (i = 0; i < t; i++)
	{

		fscanf(ifp, "%d", &m);
		fscanf(ifp, "%d", &g);
		fscanf(ifp, "%d", &f);

		printf ("%d %d %d\n", m, g, f);

		// Ignore when a stop is made at the destination
		m--;

		// Use the inclusion-exclusion principle
		ans = m/g + m/f - m/lcm(f, g);

		printf ("%d\n", ans);

	}

	fclose(ifp);


	return 0;
}

int gcd (int a, int b){
	if (b == 0)
		return a;
	return gcd(b, a%b);
}

int lcm (int a, int b){
	return b / gcd(a,b) * a;
}
