/*
isogramsgen.in contains 1 integer denoting the number of strings to make
*/

#include <fstream>
#include <cstdlib>
#include <vector>
#include <string>
#include <iostream>

using namespace std;

void swap(string &s,int x,int y)
{
	char c;
	c=s[x];
	s[x]=s[y];
	s[y]=c;
}

int main()
{
   ifstream cin("isogramsgen.in");
	int N,i,j,len,x,y;
	string s;
	cin>>N;
	srand(1443);
	while(N>0)
	{
	 s="";
	 if(rand()%2==1)
	 {
	 	len = rand()%52+1;
		for(i=0;i<len;i++) s+=(char)(rand()%26+'a');
	 }
	 else
	 {
	   len = rand()%26+1;
	   string pool="abcdefghijklmnopqrstuvwxyz";
		for(i=0;i<len;i++)
		{
			x=rand()%pool.length();
			s+=pool[x];
			//cout<<"B: pool = "<<pool<<endl;
			pool.erase(pool.begin()+x);
			//cout<<"A: pool = "<<pool<<endl;
		}
		s+=s;
		for(i=0;i<1000;i++)
		{
			x=rand()%s.length();
			y=rand()%s.length();
			swap(s,x,y);
		}
	 }
	 cout<<s<<endl;
	 N--;
	}
}