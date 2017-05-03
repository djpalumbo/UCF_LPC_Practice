#include<iostream>
#include<map>
#include<queue>
using namespace std;

#define INF 100000000000000000LL

typedef long long LL;
typedef pair<LL,LL> PLL;

int dr[8] = { -2, -1, 1, 2, 2, 1, -1, -2 };
int dc[8] = { 1, 2, 2, 1, -1, -2, -2, -1 }; 




LL kdist(LL r,LL c){
    if(r<c) return kdist(c,r);
    if(r==c){
        if(r%3==0){
            return (r/3)*2;
        }
        else{
            return INF;
        }
    }
    if(c==0){
        if(r%4==0){
            return r/2;
        }
        else{
            return INF;
        }
    }
    if(r>2*c){
        return c+kdist(r-c*2,0);
    }
    LL d = r-c;
    return d+kdist(r-d*2,c-d);
}


int T;
LL N,R1,C1,R2,C2;

bool valid(PLL p){
    return p.first>=1&&p.first<=N&&p.second>=1&&p.second<=N;
}

LL longabs(LL a){
    if(a<0) return -a;
    return a;
}

int main(){
    freopen("bknight.in","r",stdin);
    freopen("bknight.out","w",stdout);
    cin>>T;
    for(int cas=1;cas<=T;++cas){
        cin>>N>>R1>>C1>>R2>>C2;
        map<PLL,LL> dist;
        dist[PLL(R1,C1)] = 0;
        queue<PLL> q;
        q.push(PLL(R1,C1));
        while(!q.empty()){
            PLL t1 = q.front();
            q.pop();
            int current = dist[t1];
            if(current>25) break;
            for(int i=0;i<8;++i){
                PLL t2(t1.first+dr[i],t1.second+dc[i]);
                if(!valid(t2)) continue;
                if(dist.find(t2)==dist.end()){
                    dist[t2] = current+1;
                    q.push(t2);
                }
            }
            
        }
        LL res = INF;
        for(map<PLL,LL>::iterator it = dist.begin();it!=dist.end();++it){
            LL t1 = it->second+kdist(longabs((it->first).first-R2),longabs((it->first).second-C2));
            if(t1<res) res = t1;
        }
        cout<<"Case #"<<cas<<": "<<res<<endl<<endl;
    }
    return 0;
}
