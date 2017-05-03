#include <stdio.h>
#include <math.h>
#include <string.h>
#include <stdlib.h>

struct Point
{
    long x, y;
};

double areaPoly(Point poly[], int numVerts)
{
    double totalArea;
    double avg;
    int i, j;

    totalArea = 0.0;

    for (i = 0; i < numVerts; i++)
    {
        j = (i + 1) % numVerts;
        avg = (poly[i].y + poly[j].y) / 2.0;
        totalArea += avg * (poly[i].x - poly[j].x);
    }

    return fabs(totalArea);
}

int main(void)
{
    FILE *fp;
    Point lot[16], house[16], bed[16][16];
    int lawnNum, lotCount, houseCount, bedCount[16];
    char line[256];
    int numBeds, numVerts;
    int i, j;
    double area;

    fp = fopen("lawn.in", "r");

    fgets(line, sizeof(line), fp);
    lotCount = atoi(strtok(line, " \n"));
    lawnNum = 1;
    while (lotCount != 0)
    {
        // Lot coordinates
        for (i = 0; i < lotCount; i++)
        {
            lot[i].x = atoi(strtok(NULL, " \n"));
            lot[i].y = atoi(strtok(NULL, " \n"));
        }

        // House
        fgets(line, sizeof(line), fp);
        houseCount = atoi(strtok(line, " \n"));
        for (i = 0; i < houseCount; i++)
        {
            house[i].x = atoi(strtok(NULL, " \n"));
            house[i].y = atoi(strtok(NULL, " \n"));
        }

        // Beds
        fgets(line, sizeof(line), fp);
        numBeds = atoi(strtok(line, " \n"));
        for (i = 0; i < numBeds; i++)
        {
            fgets(line, sizeof(line), fp);
            bedCount[i] = atoi(strtok(line, " \n"));
            for (j = 0; j < bedCount[i]; j++)
            {
                bed[i][j].x = atoi(strtok(NULL, " \n"));
                bed[i][j].y = atoi(strtok(NULL, " \n"));
            }
        }

        // Compute the lot's area
        area = areaPoly(lot, lotCount);
        //printf("Lot area   = %0.2lf\n", area);

        // Subtract the house's area
        area -= areaPoly(house, houseCount);
        //printf("House area = %0.2lf\n", areaPoly(house, houseCount));

        // Subtract the beds' areas
        for (i = 0; i < numBeds; i++)
        {
            area -= areaPoly(bed[i], bedCount[i]);
            //printf("Bed %d area = %0.2lf\n", i,
            //     areaPoly(bed[i], bedCount[i]));
        }

        // Print the number of bags to buy
        printf("Lawn #%d: buy %d bag(s)\n\n", lawnNum,
            (int)ceil(area / 1000.0));

        // Get the next lot's vertex count
        fgets(line, sizeof(line), fp);
        lotCount = atoi(strtok(line, " \n"));

        // Increment the lawn number
        lawnNum++;
    }

    fclose(fp);
}
