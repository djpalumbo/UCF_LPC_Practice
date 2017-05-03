#include<fstream>
#include<vector>
using namespace std;
typedef long long LL;

ofstream fout;

int T,R,C,S;
int input[110][110];
int sum[110][110];

void handCrafted(){
    int i,j;
    int T1;
    ifstream fin("handcrafted.txt");
    fin>>T1;
    T-=T1;
    int R,C,S;
    while(T1--){
        fin>>R>>C>>S;
        fout<<R<<" "<<C<<" "<<S<<endl;
        for(i=0;i<R;++i){
            for(j=0;j<C;++j){
                if(j) fout<<" ";
                fin>>S;
                fout<<S;
            }
            fout<<endl;
        }
    }
}

void SmallRandom(){
    int i,j,k,l;
    T-=25;
    for(int x=0;x<25;++x){
        int R = rand()%40+10;
        int C = rand()%40+10;
        for(i=1;i<=R;++i) for(j=1;j<=C;++j) input[i][j] = rand()%200-40;
        memset(sum,0,sizeof(sum));
        for(i=1;i<=R;++i) for(j=1;j<=C;++j) sum[i][j] = input[i][j]+sum[i-1][j]+sum[i][j-1]-sum[i-1][j-1];
        int res = R*C+1;
        vector<int> sums;
        for(i=0;i<R;++i) for(j=0;j<C;++j) for(k=i+1;k<=R;++k) for(l=j+1;l<=C;++l)sums.push_back(sum[k][l]-sum[k][j]-sum[i][l]+sum[i][j]);
        sort(sums.begin(),sums.end());
        int S = sums[sums.size()/2];
        fout<<R<<" "<<C<<" "<<S<<endl;
        for(i=1;i<=R;++i) {
            for(j=1;j<=C;++j){
                if(j>1) fout<<" ";
                fout<<input[i][j];
            }
            fout<<endl;
        }
    }
}

int array[100010];
void Line(){
    int T1 = 10;
    T-=T1;
    while(T1--){
        int i;
        int N = 100000-rand()%10;
        int start = 10+rand()%100;
        int end = N-10-rand()%100;
        LL sum = 0;
        for(i=0;i<N;++i){
            if(i>=start&&i<=end){
                array[i] = rand()%2000-100;
                sum+=array[i];
            }
            else{
                array[i] = rand()%2000-1000;
            }   
        }
        int S = sum-rand()%10;
        if(rand()%100<40){
            fout<<"1 "<<N<<" "<<S<<endl;
            for(i=0;i<N;++i){
                if(i) fout<<" ";
                fout<<array[i];
            }
            fout<<endl;
        }
        else{
            fout<<N<<" 1 "<<S<<endl;
            for(i=0;i<N;++i){
                fout<<array[i]<<endl;
            }
        }
    }
}


int matrix[1000][1000];



void Square(){
    int i,j;
    while(T--){
        int R = rand()%100+300;
        int C = (100000-rand()%200)/R;
        int r1 = 5+rand()%10;
        int r2 = R-5-rand()%10;
        int c1 = 5+rand()%10;
        int c2 = C-5-rand()%10;
        int sum = 0;
        for(i=0;i<R;++i){
            for(j=0;j<C;++j){
                if(i>=r1&&i<=r2&&j>=c1&&j<=c2){
                    matrix[i][j] = rand()%2000-100;     
                    sum+=matrix[i][j];
                }
                else{
                    matrix[i][j] = rand()%2000-1000;
                }
            }        
        }
        int S = sum-rand()%50+48;
        fout<<R<<" "<<C<<" "<<S<<endl;
        for(i=0;i<R;++i){
            for(j=0;j<C;++j){
                if(j) fout<<" ";
                fout<<matrix[i][j];
            }
            fout<<endl;
        }    
    }
    
    
    
    
}







int main(){
    T=50;
    
    fout.open("sum.in");
    fout<<T<<endl;
    handCrafted();
    SmallRandom();
    Line();    
    Square();
    return 0;
}
