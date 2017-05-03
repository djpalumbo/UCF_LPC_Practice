// Stephen Fulwider
//	2010 UCF Local Contest - Plate Spinning

// This problem only requires a couple very simple observations to solve.
//	First, if there is only one plate, we can always succeed, since Chester can just
//	stand at that plate and continue spinning. With that case out of the way, we can
//	focus on the more general case of when there is > 1 plate in the circle. Since it takes
//	the same amount of time to travel between any two plates, then you should
//	always go to the slowest spinning plate next, breaking ties arbitrarily. With
//	that in mind, you can simply travel the plates in a circle, taking .5*N seconds.
//	Since the plates decelerate at 5 m/s/s, then a plate from full speed will stop
//	spinning in V/5 seconds. Thus, if .5*N <= P/5, you will be able to come back around
//	to the original plate in time. Otherwise, you will fail. In other words, check if:

//			 N		P
//			--- <= ---    ===>    5*N <= 2*P.
//			 2		5

// We express the equation as 5*N <= 2*P so that we do not have to do any division and
//	we can avoid working with doubles, which are imprecise and may cause precision errors.

import java.util.Scanner;


public class plate
{

	public static void main(String[] args)
	{
		new plate();
	}
	
	plate()
	{
		Scanner in=new Scanner(System.in);
		for (int T=in.nextInt(),TC=1; T-->0; ++TC)
		{
			int N=in.nextInt();
			int P=in.nextInt();
			System.out.printf("Circus Act %d:%n",TC);
			if (N==1 || 5*N <= 2*P)
				System.out.println("Chester can do it!");
			else
				System.out.println("Chester will fail!");
			System.out.println();
		}
	}

}
