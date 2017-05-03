// Satej Mahmud
#include<iostream>
#include<queue>
using namespace std;

int cap[150][150];
int flow[150][150];
int V,source,sink;

int d[150];
int pre[150];

bool executeFlow(){
    int i;
    memset(flow,0,sizeof(flow));
    int cnt=0;
    while(true){
        memset(d,-1,sizeof(d));
        memset(pre,-1,sizeof(pre));
        d[source]=0;
        queue<int> q;
        q.push(source);
        while(!q.empty()){
            int t1 = q.front();
            q.pop();
            for(i=0;i<=sink;++i){
                if(d[i]>=0||flow[t1][i]>=cap[t1][i]){
                    continue;
                }
                d[i] = d[t1]+1;
                pre[i] = t1;
                q.push(i);
            }
        }
        if(pre[sink]==-1){
            break;
        }
        vector<int> path;
        int cur = sink;
        while(cur!=-1){
            path.push_back(cur);
            cur = pre[cur];
        }
        reverse(path.begin(),path.end());
        for(i=1;i<path.size();++i){
            flow[path[i-1]][path[i]]++;            
            flow[path[i]][path[i-1]]--;            
        }
        ++cnt;
    }
    return cnt==126;
}



int main(){
    int T;
    int i,j,N,d,s,e;
    freopen("campout.in","r",stdin);
    cin>>T;
    for(int cas = 1; cas <= T; ++cas){
        source = 0; 
        sink = 53;
        V = 54;
        for(i=1;i<=10;++i) cap[source][i] = 20;
        for(i=1;i<=10;++i) for(j=11;j<=52;++j) cap[i][j] = 1;
        for(i=11;i<=52;++i) cap[i][53] = 3;
        for(i=1;i<=10;++i){
            cin>>N;
            while(N--){
                cin>>d>>s>>e;
                for(j=s;j<e;++j){
                    cap[i][(d-1)*6+11+j/4] = 0;
                }
            } 
        }
        cout<<"Case# "<<cas<<": ";
        if(executeFlow()){
            cout<<"YES\n\n";
        }
        else{
            cout<<"NO\n\n";
        }
    }
    return 0;
}
