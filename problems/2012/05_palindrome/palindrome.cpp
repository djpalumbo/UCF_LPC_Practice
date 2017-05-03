#include<iostream>
#include<queue>
#include<map>
#include<algorithm>
#include<cstdio>
using namespace std;

typedef pair<int,int> PII;
int T,N;
int in[10000];
deque<PII> dq;
map<PII,int> mp;


int main(){
    int i,j;
    freopen("palindrome.in","r",stdin);
    freopen("palindrome.out","w",stdout);
    cin>>T;
    for(int seq = 1;seq<=T;++seq){
        cin>>N;
        for(i=0;i<N;++i) cin>>in[i];
        dq.clear();
        mp.clear();
        PII src = make_pair(0,N-1);
        mp[src]=1;
        dq.push_back(src);
        int result=-1;
        while(dq.size()>0){
            PII cur = dq.front();
            int cd = mp[cur];
            if(cur.first>=cur.second){
                result = mp[cur]-1;
                break;
            }
            dq.pop_front();
            if(in[cur.first]==in[cur.second]){
                PII next = make_pair(cur.first+1,cur.second-1);
                if(mp[next]==0||cd<mp[next]){
                    mp[next] = mp[cur];
                    dq.push_front(next);
                }
            }
            else{
                PII next1 = make_pair(cur.first+1,cur.second);
                if(mp[next1]==0||(cd+1)<mp[next1]){
                    mp[next1] = cd+1;
                    dq.push_back(next1);
                }
                PII next2 = make_pair(cur.first,cur.second-1);
                if(mp[next2]==0||(cd+1)<mp[next2]){
                    mp[next2] = cd+1;
                    dq.push_back(next2);
                }
            }
        }
        printf("Sequence #%d: %d\n\n",seq,result);
    }
    return 0;
}
