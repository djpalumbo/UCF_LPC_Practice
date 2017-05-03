
#include <stdio.h>

int main()
{
    FILE * ftp;
    int num_cases, i, num_words, j, num_locations, num_edges, x, y, k, l;
    char trial[23];
    int found;

    ftp = fopen("minion.in", "r");

    fscanf(ftp, "%d", &num_cases);

    for(i = 0; i < num_cases; i++)
    {
        fscanf(ftp, "%d", &num_words);

        char trials[num_words][23];

        for(j = 0; j < num_words; j++)
        {
            fscanf(ftp, "%s", trials[j]);
        }

        fscanf(ftp, "%d %d", &num_locations, &num_edges);

        int grid[num_locations][num_locations];

        for(j = 0; j < num_locations; j++)
        {
            for(k = 0; k < num_locations; k++)
            {
                grid[j][k] = 10000;
            }
        }

        for(j = 0; j < num_edges; j++)
        {
            fscanf(ftp, "%d %d %s", &x, &y, trial);

            found = 0;

            for(k = 0; k < num_words; k++)
            {
                if(strcmp(trial, trials[k]) == 0)
                {
                    found = 1;
                }
            }

            if(!found)
            {
                grid[x][y] = 1;
                grid[y][x] = 1;
            }
        }

        for(j = 0; j < num_locations; j++)
        {
            for(k = 0; k < num_locations; k++)
            {
                for(l = 0; l < num_locations; l++)
                {
                    if(grid[k][l] > grid[k][j] + grid[j][l])
                    {
                        grid[k][l] = grid[k][j] + grid[j][l];
                    }
                }
            }
        }

        if(grid[0][num_locations - 1] == 10000)
        {
            printf("0\n");
        }
        else
        {
            printf("1\n");
        }
    }

    fclose(ftp);
}
