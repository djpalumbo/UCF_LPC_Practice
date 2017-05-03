#include<iostream>
using namespace std;

int mat[35][35];

int mn = 100;
int mx = -100;
void gen(int r,int c,int diff){
    int i,j;
    int sum = 0;
    for(i=0;i<r;++i){
        for(j=0;j<c;++j){
            mat[i][j] = -8+rand()%17;
            if((i+j)%2) sum-=mat[i][j];
            else sum+=mat[i][j];
        }
    }
    while(sum!=diff){
        int tr = rand()%r;
        int tc = rand()%c;
        if(sum<diff){
            sum++;
            if((tr+tc)%2){
                mat[tr][tc]--;
            }
            else{
                mat[tr][tc]++;
            }
        }
        else{
            sum--;
            if((tr+tc)%2){
                mat[tr][tc]++;
            }
            else{
                mat[tr][tc]--;
            }
        
        }
    }
    int sum1 = 0;
    for(i=0;i<r;++i){
        for(j=0;j<c;++j){
            if((i+j)%2) sum1-=mat[i][j];
            else sum1+=mat[i][j];
        }
    }
    cout<<r<<" "<<c<<endl;
    for(i=0;i<r;++i){
        for(j=0;j<c;++j){
            if(j) cout<<" ";
            cout<<mat[i][j];
            mn<?=mat[i][j];
            mx>?=mat[i][j];
        }
        cout<<endl;
    }
    
}




int main(){
    int i;
    freopen("matrix.in","w",stdout);
    cout<<30<<endl;
    for(i=0;i<30;++i){
        int r = 5 + rand()%25;
        int c = 5 + rand()%25;
        if(rand()%100<50){
            gen(r,c,0);
        }
        else{
            gen(r,c,rand()%5);
        }
        
    }
    return 0;
}
