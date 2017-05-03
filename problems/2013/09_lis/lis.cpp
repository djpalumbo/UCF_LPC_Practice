#include<cstdio>
#include<cstring>
#include<iostream>
using namespace std;


const int Limit=100010;
const int Max = Limit*12;
const int MOD=1000000007;

int tree[Max+1];
int sum(int idx){int ret = 0;for(int i=idx-1;i>0;i-=(i&-i)) ret=(ret+tree[i])%MOD;return ret;}
void add(int idx,int value){while(idx<=Max){tree[idx]=(tree[idx]+value)%MOD;idx+=(idx&-idx);}}

int in;
int N,K;
int T;


int main(){
    int i,j;
    freopen("lis.in","r",stdin);
    scanf("%d",&T);
    while(T--){
        scanf("%d%d",&N,&K);
        memset(tree,0,sizeof(tree));
        for(i=0;i<N;++i){
            scanf("%d",&in);
            ++in;
            for(j=K-1;j>0;--j){
                int idx = j*Limit+in;
                add(idx,(sum(idx)-sum(idx-Limit)+MOD)%MOD);
            }
            add(in,sum(in)+1);    
        }
        cout<<(sum(K*(Limit))-sum((K-1)*(Limit))+MOD)%MOD<<endl;
        
    }
    return 0;
}
