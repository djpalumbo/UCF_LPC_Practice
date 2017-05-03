import java.io.*;
import java.util.*;

public class ngram {
	public static void main(String[] args) throws IOException
	{
		Scanner fin = new Scanner(new File("ngram.in"));
		int set = 1;
		while(true)
		{
			int lines = fin.nextInt();
			if(lines == 0)
				return;
			
			fin.nextLine();
			
			String input="";
			for(int i=0; i<lines;i++)
			{
				input += " " + fin.nextLine();
			}
			
			int[] uni = new int[26];
			int[][] bi = new int[26][26];
			int[][][] tri = new int[26][26][26];
			
			StringTokenizer s = new StringTokenizer(input," .,\n\r\t");
			while(s.hasMoreTokens())
			{
				String word = s.nextToken();
				for(int i=0;i<word.length();i++)
				{
					uni[word.charAt(i)-'a']++;
				}
				
				for(int i=1;i<word.length();i++)
				{
					bi[word.charAt(i-1)-'a'][word.charAt(i)-'a']++;
				}
				for(int i=2;i<word.length();i++)
				{
					tri[word.charAt(i-2)-'a'][word.charAt(i-1)-'a'][word.charAt(i)-'a']++;
				}
			}
						
			System.out.println("Paragraph #"+set+":");
			set++;
			
			int besti=0;
			int bestj=0;
			int bestk=0;
			int best=0;
			for(int i=0;i<26;i++)
				if(uni[i]>best)
				{
					besti = i;
					best = uni[i];
				}
			
			System.out.println("   Unigram: "+(char)(besti+'a'));
			
			besti=0;
			best=0;
			for(int i=0;i<26;i++)
				for(int j=0;j<26;j++)
					if(bi[i][j]>best)
					{
						besti = i;
						bestj = j;
						best = bi[i][j];
					}
			
			System.out.println("   Bigram:  "+(char)(besti+'a')+(char)(bestj+'a'));
			
			besti=0;
			bestj=0;
			best=0;
			for(int i=0;i<26;i++)
				for(int j=0;j<26;j++)
					for(int k=0;k<26;k++)
						if(tri[i][j][k]>best)
						{
							besti = i;
							bestj = j;
							bestk = k;
							best = tri[i][j][k];
						}
			
			System.out.println("   Trigram: "+(char)(besti+'a')+(char)(bestj+'a')+(char)(bestk+'a'));
			System.out.println();
		}
	}
}
