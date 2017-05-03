///////////////////////////////////////////////////////////
//
//	Maze solution checker
//	Ben Douglass
//
//	The actual purpose and behavior of this program require a
//  a bit of explanation. This program takes in three files and
//  produces a file. It takes in 
//		1. The input file named "mazetwins.in"
//		2. An output file known to be correct named "mazetwins.out"
//		3. An output file to be verified named "team.out"
//
//	The output, "teamreal.out", is essentially a modified version of file
//	3 with correct format. This program does detect if the path 
//  specified in the output file goes out of the maze, is too
//  long, is too short, or doesn't meet up in the end. In any
//  of those cases, it quits writing the correct format version
//  and instead writes an explanation of the problem, then
//  terminates.	
// 
///////////////////////////////////////////////////////////

#include <stdio.h>

int maze1[16][16];
int maze2[16][16];
int n;


int abs(int x)
{
	return (x<0)?-x:x;
}

int main(void)
{
	int set;
	int sets;

	int i;
	int j;

	int mackx;
	int macky;
	int zackx;
	int zacky;

	int bdist;

	char s[1024];
	char namec[1024];
	char namer[1024];
	char dir[1024];
	int zhit;
	int cdist;

	FILE *fin; // The input file
	FILE *freal; // The correct output file
	FILE *fcheck; // The output file that is being checked
	FILE *fout; // The file output by this program

	fin = fopen("mazetwins.in","r");
	freal=fopen("mazetwins.out","r");
	fcheck=fopen("team.out","r");
	fout =fopen("teamreal.out","w");

	fscanf(fin,"%d",&sets);
	for(set=1;set<=sets;set++)
	{
		fscanf(fin,"%d",&n);

		// Read the maze from the input file.
		// maze1 is the maze in which Mack can move, and maze2 is the
		// maze in which Zack can move. A value of -1 in a maze means
		// that a twin can't move there, and a value of 1 means that 
		// the twin can.
		for(i=0;i<n;i++)
		{
			fscanf(fin,"%s",s);
			for(j=0;j<n;j++)
			{
				if(s[j] == '0')
					maze1[i][j] = maze2[i][j] = -1;
				else if(s[j] == '1')
				{
					maze1[i][j] = 0;
					maze2[i][j] = -1;
				}
				else if(s[j] == '2')
				{
					maze2[i][j] = 0;
					maze1[i][j] = -1;
				}
				else if(s[j] == '3')
				{
					maze1[i][j] = 0;
					maze2[i][j] = 0;
				}
				else if(s[j] == 'M')
				{
					maze1[i][j] = 0;
					maze2[i][j] = -1;
					mackx = i;
					macky = j;
				}
				else if(s[j] == 'Z')
				{
					maze1[i][j] = -1;
					maze2[i][j] = 0;
					zackx = i;
					zacky = j;
				}
			}

		}
		
		fprintf(fout,"Maze #%d:\n",set);
	
		fscanf(fcheck,"%*s %*s");
		fscanf(freal,"%*s %*s");
		zhit = 0; // All of Mack's moves must come before Zack's moves
					// When zhit becomes 1, this indicates that a
					// "Zack" move has been hit

		// This loop tracks the movements of Mack and Zack through the
		// maze as specified by the file to be checked
		while(1)
		{
			fscanf(fcheck,"%s %*s %s",namec,dir);
			fscanf(freal,"%s %*s %*s",namer);
			
			// Check to see if the paths are over.
			// If so, write the correct best number of moves.
			if(namer[0] == 'T' && (namec[0]=='T' || namec[0] =='t'))
			{
				fscanf(fcheck, "%*s %d",&cdist);
				fscanf(freal,  "%*s %d",&bdist);

				fprintf(fout,"   Total number of moves: %d\n\n",bdist);
				break;
			}
			// Check to see if one file has more moves than another.
			// If a mismatch exists, stop immediately
			if((namer[0] == 'T') || (namec[0]=='T' || namec[0] =='t'))
			{
				fprintf(fout,"WRONG(# of moves)!!!!!!!!!!\n");
				return 0;
			}
			// Handle a move by Zack
			if(namec[0] == 'z' || namec[0] == 'Z')
			{
				zhit = 1;
				// Make sure that Zack's name has correct capitalization
				if(namec[0] == 'z')
					fprintf(fout,"Format ");

				// Move Zack
				if(dir[0] == 'e' || dir[0] == 'E')
				{
					zacky++;
					fprintf(fout,"   Zack move east\n");
				}
				else if(dir[0] == 'w' || dir[0] == 'W')
				{
					zacky--;
					fprintf(fout,"   Zack move west\n");
				}
				else if(dir[0] == 'n' || dir[0] == 'N')
				{
					zackx--;
					fprintf(fout,"   Zack move north\n");
				}
				else if(dir[0] == 's' || dir[0] == 'S')
				{
					zackx++;
					fprintf(fout,"   Zack move south\n");
				}

				// If the move caused Zack to leave the maze, there
				// is a problem.
				if(zacky >=n || zackx >= n || zackx<0 || zacky<0)
				{
					fprintf(fout,"WRONG(O.B)!!!!!!!!!!\n");
					return 0;
				}

				// If the move caused Zack to move to a square he can't move
				// to, then there is a problem.
				if(maze2[zackx][zacky] <0)
				{
					fprintf(fout,"WRONG(Wall)!!!!!!!!!!\n");
					return 0;
				}
				
			}

			// Handle a move by Mack
			if(namec[0] == 'm' || namec[0] == 'M')
			{
				// If this Mack move happened after a Zack move
				// there is a format problem
				if(zhit == 1)
					fprintf(fout,"Sequence ");
				// Make sure that Mack's name has correct capitalization
				if(namec[0] == 'm')
					fprintf(fout,"Format ");

				// Move Mack
				if(dir[0] == 'e' || dir[0] == 'E')
				{
					macky++;
					fprintf(fout,"   Mack move east\n");
				}
				else if(dir[0] == 'w' || dir[0] == 'W')
				{
					macky--;
					fprintf(fout,"   Mack move west\n");
				}
				else if(dir[0] == 'n' || dir[0] == 'N')
				{
					mackx--;
					fprintf(fout,"   Mack move north\n");
				}
				else if(dir[0] == 's' || dir[0] == 'S')
				{
					mackx++;
					fprintf(fout,"   Mack move south\n");
				}

				// If the move caused Mack to leave the maze, there
				// is a problem.
				if(macky >=n || mackx >= n || mackx<0 || macky<0)
				{
					fprintf(fout,"WRONG(O.B)!!!!!!!!!!\n");
					return 0;
				}
				// If the move caused Mack to move to a square he can't move
				// to, then there is a problem.
				if(maze1[mackx][macky] <0)
				{
					fprintf(fout,"WRONG(Wall)!!!!!!!!!!\n");
					return 0;
				}
				
			}
		}

		// If Mack and Zack have not met up at the end of their
		// paths, then the checked file is incorrect
		if(abs(mackx-zackx) + abs(macky-zacky) != 1)
		{
			fprintf(fout,"WRONG(No meet up)!!!!!!!!!!\n");
		}
	
	}
	return 0;
}
