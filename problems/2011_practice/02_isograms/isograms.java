import java.util.*;
import java.io.*;

public class isograms
{
	public static void main(String[] args) throws IOException
	{
		Scanner fin = new Scanner(new File("isograms.in"));
		int sets = fin.nextInt();

		for(int set=0;set<sets;set++)
		{
			int cnt[] = new int[26];
			String word = fin.next();

			for(int i=0;i<word.length();i++)
			{
				cnt[word.charAt(i)-'a']++;
			}
			boolean legit = true;

			for(int i=0;i<26;i++)
			{
				if(cnt[i] != 0 && cnt[i] != 2)
					legit = false;
			}
			if(legit)
				System.out.println(word+" --- pair isograms");
			else
				System.out.println(word+" --- not pair isograms");
		   System.out.println();
		}


	}
}