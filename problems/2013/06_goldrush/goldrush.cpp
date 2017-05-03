#include<stdio.h>
#include<iostream>
using namespace std;

typedef long long LL;

template<int mod>
struct PrimeModulo{
public:
    PrimeModulo(){value=1;}
    PrimeModulo(int v){value=v;}
    int value;
    PrimeModulo operator+(int other){return PrimeModulo((value+other)%mod);}
    PrimeModulo operator-(int other){return PrimeModulo((value-other+mod)%mod);}
    PrimeModulo operator*(LL other){return PrimeModulo((other*value)%mod);}
    PrimeModulo operator/(int other){return (*this)*(PrimeModulo(other)^(mod-2)).value;}
    PrimeModulo operator^(LL pw){
        if(pw==0) return PrimeModulo(1);
        PrimeModulo ret = (*this)^(pw>>1);
        ret=ret*ret.value;
        if(pw&1) ret=ret*value;
        return ret;
    }
    PrimeModulo inv(){return (*this)^(mod-2);}
};

const int MOD = 1000003;
PrimeModulo<MOD> fact[MOD];
PrimeModulo<MOD> inv[MOD];

PrimeModulo<MOD> Choose(int N,int R){return R>N?0:(fact[N]*inv[R].value*inv[N-R].value);}
PrimeModulo<MOD> Lucas(LL N,LL R){
    if(R>N) return 0;
    if(N==0) return 1;
    return (Choose(N%MOD,R%MOD)*Lucas(N/MOD,R/MOD).value).value;
}

PrimeModulo<MOD> C;
PrimeModulo<MOD> Calc(LL N,LL K){
    if(N==1) return PrimeModulo<MOD>(1);
    return (C^(N/2))*Calc((N+1)/2,K).value;
}

int main(){
    int T;
    freopen("goldrush.in","r",stdin);
    LL N,K;
    for(int i=2;i<MOD;++i){fact[i]=fact[i-1]*i;inv[i] = fact[i].inv();}
    cin>>T;
    while(T--){
        cin>>N>>K;
        C = Lucas(K,K/2)*2;
        cout<<Calc(N,K).value<<endl;
    }
    return 0;
}
