#include <stdio.h>

int main(){
    int n,i;
    scanf("%d",&n);

    char letra[n];
    scanf("%s",letra);
    

    int count=0;
    int tamanhoAtual = 0;

    for(i=0;i<n;i++){
        if(letra[i]=='a'){
            tamanhoAtual++;
        }else{
            if(tamanhoAtual>1){
                count+=tamanhoAtual;
            }
        tamanhoAtual = 0;
        }
    }

    if(tamanhoAtual>1){
                count+=tamanhoAtual;
            }
    
    printf("%d\n",count);
    return 0;
}