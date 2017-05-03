#include<stdio.h>
#include<math.h>

#define LIMIT 2500
#define EPS 1.0e-6

int is1stOneLargest(double a, double b, double c);
double getProdLog(int n, double lgVal[]);

int main()
{
	//Setting file as source of standard input
	freopen("fact.in", "r", stdin);

    int i;
    double factLogVal[LIMIT + 1];

    //Precomputing the log value of factorials
    factLogVal[0] = 0.0;
    for(i = 1; i <= LIMIT; i++)
        factLogVal[i] = factLogVal[i - 1] + log(i);

    //Input, get product log, compare and output
    int x, y, z, test, caseCount = 1;
    double prodLogA, prodLogB, prodLogC;
    scanf("%d", &test);
    while(test--)
    {
        scanf("%d %d %d", &x, &y, &z);
        prodLogA = getProdLog(x, factLogVal);
        prodLogB = getProdLog(y, factLogVal);
        prodLogC = getProdLog(z, factLogVal);

        printf("Case #%d: ", caseCount);
        if(is1stOneLargest(prodLogA, prodLogB, prodLogC))
            printf("A\n");
        else if(is1stOneLargest(prodLogB, prodLogA, prodLogC))
            printf("B\n");
        else if(is1stOneLargest(prodLogC, prodLogB, prodLogA))
            printf("C\n");
        else
            printf("TIE\n");
        caseCount++;
    }
    return 0;
}

int is1stOneLargest(double a, double b, double c)
{
    if(a - b > EPS && a - c > EPS)
        return 1;
    return 0;
}

//Get the log value for the product of the items of a set
double getProdLog(int n, double lgVal[])
{
    int a, i;
    double prodVal = 0.0;
    for(i = 0; i < n; i++)
    {
        scanf("%d", &a);
        prodVal += lgVal[a];
    }
    return prodVal;
}
