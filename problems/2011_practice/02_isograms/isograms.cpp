#include <iostream>
#include <fstream>

using namespace std;

bool good(int f[26])
{
 int i;
 for(i=0;i<26;i++)
 if(f[i]!=0 && f[i]!=2) return false;
 return true;
}

int main()
{
 ifstream cin("isograms.in");
 int N,i,j,x,y,z,f[26];
 char s[256];
 cin>>N;
 while(N>0)
 {
  cin>>s;
  for(i=0;i<26;i++) f[i]=0;
  for(i=0;s[i];i++) f[s[i]-'a']++;
  cout<<s<<" --- ";
  if(!good(f)) cout<<"not ";
  cout<<"pair isograms"<<endl<<endl;
  N--;
 }
 return 0;
}