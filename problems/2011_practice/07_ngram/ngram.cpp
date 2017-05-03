#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>
#include <string>
#include <cctype>
#include <cstdlib>

using namespace std;

int L(istream &cin)
{
	static string line;
	getline(cin,line);
	return atoi(line.c_str());
}

string solve(vector<string> &v)
{
	sort(v.begin(),v.end());
	v.push_back("");

	int cnt,max,i;
	string ans;

	ans=""; max=-1;
	cnt=1;
	for(i=1;i<v.size();i++)
	{
		if(v[i]!=v[i-1])
		{
			if(cnt>max)
			{
				max=cnt;
				ans=v[i-1];
			}
			cnt=1;
		}
		else
		{
			cnt++;
		}
	}
	return ans;
}

int main()
{
	ifstream cin("ngram.in");
	char a,b,c;
	int n,i,x,cc;
	vector<string> v1,v2,v3;
	string line,empty;
	
	empty="";
	cc=1;
	n=L(cin);
	while(n>0)
	{
		v1.clear();
		v2.clear();
		v3.clear();
		for(i=0;i<n;i++)
		{
			getline(cin,line);
			a=b=' ';
			for(x=0;x<line.size();x++)
			{
				c=line[x];
				if(isalpha(c))
				{
					v1.push_back(empty+c);
					if(isalpha(b))
					{
						v2.push_back(empty+b+c);
						if(isalpha(a))
						{
							v3.push_back(empty+a+b+c);
						}
					}
				}
				a=b; b=c;
			}
		}//end for i=0..n-1
		if(v1.size()==0 || v2.size()==0 || v3.size()==0)
		{
			cout<<"Case: "<<cc<<" - Bad Data, cannot find T gram"<<endl;
		}
		cout<<"Paragraph #"<<cc<<":"<<endl;
		cc++;
		cout<<"   Unigram: "<<solve(v1)<<endl;
		cout<<"   Bigram:  "<<solve(v2)<<endl;
		cout<<"   Trigram: "<<solve(v3)<<endl;
		cout<<endl;
		n=L(cin);
	}
	return 0;
}