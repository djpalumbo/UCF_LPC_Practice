#include <fstream>
#include <iostream>
#include <cstring>
#include <math.h>

using namespace std;

const int N=64;
const double esp=1e-9;

struct pass
{
 bool x;
 bool v[4];
};

bool eq(double x,double y)
{
	return fabs(x-y)<esp;
}

void dig(pass &p,char *s)
{
	char *haz="FEWP";
	int i;
	p.x=true;
	for(i=0;i<4;i++)
	{
		if(strchr(s,haz[i])) p.v[i]=true;
	}	
}

bool passible(double r[4],pass p)
{
	if(!p.x) return false;
	
	int i;
	for(i=0;i<4;i++)
   if(p.v[i])
	{
		if(r[i]<=esp) return false;
	}
	return true;
}

double sum(double r[4])
{
	double x;
	int i;
	x=0;
	for(i=0;i<4;i++) x+=r[i];
	return x;
}

double remain(double man,double need)
{
	if(eq(need,0)) return man;
	if(eq(man,0)) return man;
	return man-1;
}

int main()
{
 ifstream cin("pickmen.in");
 int n,en,x,y,z,T,cc,i,j,k,ref;
 double min[N][4],need[N];
 char s[256];
 bool done[N];
 pass m[N][N];
 
 cin>>T;
 for(cc=1;cc<=T;cc++)
 {
   cin>>n>>en;
	for(x=1;x<=n;x++)
	{
	 done[x]=false;
	 for(y=0;y<4;y++) min[x][y]=-1;
	 for(y=1;y<=n;y++) 
	 {
	 	m[x][y].x=false;
		for(z=0;z<4;z++) m[x][y].v[z]=false;
    }
	}
	
	for(y=0;y<4;y++) cin>>min[1][y];
	for(x=1;x<=n;x++) cin>>need[x];
	for(i=0;i<en;i++)
	{
	 cin>>x>>y>>s;
	 dig(m[x][y],s);
	 m[y][x]=m[x][y];
	}
	
	ref=0;
	for(i=1;i<4;i++) if(min[1][i]>min[1][ref]) ref=i;
	x=1;
	while(x>=1 && x!=n)
	{
		done[x]=true;
		for(y=1;y<=n;y++)
		if(passible(min[x],m[x][y]) && sum(min[x])>=need[y] && remain(min[x][ref],need[y])>min[y][ref])
		{
			for(i=0;i<4;i++) min[y][i]=remain(min[x][i],need[y]);
		}
		
		x=-1;
		for(y=1;y<=n;y++)
	   if(!done[y] && (x==-1 || min[y][ref]>min[x][ref])) x=y;
	}
	x=(int)(sum(min[n])+esp);
	cout<<"Cave #"<<cc<<": ";
	if(x>=0)
	{
		cout<<"Commander Oroojimar can escape with "<<x<<" Pick Men."<<endl;
	}
	else
	{
		cout<<"Commander Oroojimar is doomed."<<endl;
	}
	cout<<endl;
 }
 
 return 0;
}