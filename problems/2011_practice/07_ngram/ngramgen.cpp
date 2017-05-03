#include <fstream>
#include <iostream>
#include <cstdlib>
#include <string>

using namespace std;

string RL()
{
	static char *pool="abcdefghijklmnopqrstuvwxyz ,.";
	static int pn=strlen(pool);
	string s="";
	int len,i,letter;
	len=rand()%70+1;
	letter=rand()%len;
	for(i=0;i<len;i++)
	{
		if(i==letter)
		{
			s+=(rand()%26+'a');
		}
		else
		{
			s+=pool[rand()%pn];
		}
	}
	return s;
}

int main()
{
	ofstream fout("ngramgen.out");
	int N,cc,n,i;
	cout<<"Enter number of Paragraphs: ";
	cin>>N;

	srand(133);
	for(cc=1;cc<=N;cc++)
	{
		n=rand()%50+1;
		fout<<n<<endl;
		for(i=0;i<n;i++)
		{
			fout<<RL()<<endl;
		}
	}
}