#include<stdio.h>

int main(){
    int N;
    int resp = 0;
    int i;
    for(i=0;i<8;i++){
        scanf("%d",&N);
        if(N==9){
            resp = 1;
        }
    }

    if(resp==1){
        printf("F");
    }else{
        printf("S");
    }

    return 0;
}