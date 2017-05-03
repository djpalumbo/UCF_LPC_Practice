#include <fstream>
#include <iostream>
#include <string>

using namespace std;

bool core(char c)
{
	return c=='r' || c=='m' || c=='n' || c=='o';
}

bool alpha(char c)
{
	return c=='a' || c=='b' || c=='c';
}

bool beta(char c)
{
	return c=='x' || c=='y' || c=='z';
}

bool rpad(string &s,int end,int start)
{
	while(end-2>=start)
	{
		if(s[end]=='z' && s[end-1]=='y' && s[end-2]=='x') return true;
		if(!(s[end]=='y' && s[end-1]=='x')) return false;
		end-=2;
	}
	return false;
}

int tapadil(string &s,int i)
{
	if(i+6<s.length())
	{
		if(s.find("taxyz",i)==i)
		{
			int x=i+5;
			while(x+1<s.length())
			{
				if(s[x]=='i' && s[x+1]=='l') return x+1;
				if(!(s[x]=='x' && s[x+1]=='y')) return -1;
				x+=2;
			}
		}
	}
	return -1;
}

int txyz(string &s,int i)
{
	if(s.find("txyz",i)==i) return i+3;
	return -1;
}

int parse(string &s,int start,int i)
{
	int len,a,b;
	len=s.length();
	a=i;
	b=a;
	while(b+1<len && core(s[b+1])) b++;

	if(b+1<len)
	{
		if(beta(s[b+1]))
		{
			return b+1;
		}
		if(alpha(s[b+1]))
		{
			if(a-1>=start && beta(s[a-1])) return b+1;
		}
		if(s[b+1]=='t')
		{
			if(rpad(s,a-1,start))
			{
				int end;
				end = tapadil(s,b+1);
				if(end>=0) return end;
				return txyz(s,b+1);
			}
		}
	}
	return -1;
}

int main()
{
	ifstream cin("databug.in");
	int N,cc,x,y,n,i,j,cnt,len;
	int start,end;
	string name,s,sub;
	cc=1;
	cin>>N;
	while(N>0)
	{
		cin>>name;
		s="";
		for(i=0;i<N;i++)
		{
			cin>>sub;
			s+=sub;
		}
		i=0;
		cnt=0;
		start=0;
		len = s.length();
		while(i<len)
		{
			if(core(s[i]))
			{
				end=parse(s,start,i);
				if(end>=0)
				{
					start=end+1;
					cnt++;
					i=start;
				}
				else
				{
					while(i<len && core(s[i])) i++;
					start=i;
				}
			}
			else
			{
				i++;
			}
		}
		cout<<"Character #"<<cc++<<": "<<name<<" has "<<cnt<<" infection(s)!"<<endl<<endl;
		cin>>N;
	}
	return 0;
}