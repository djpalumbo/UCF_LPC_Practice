#include "stdio.h"

int main(){

	int i, n, c, p, ans;

	FILE *ifp;

	ifp=fopen("bulk.in", "r");
	fscanf(ifp, "%d", &n);

	for (i = 0; i < n; i++)
	{

		fscanf(ifp, "%d", &c);
		fscanf(ifp, "%d", &p);

		printf ("%d %d\n", c, p);

		ans = p + (p-2)*(c-1);

		printf ("%d\n", ans);

	}

	fclose(ifp);


	return 0;
}
