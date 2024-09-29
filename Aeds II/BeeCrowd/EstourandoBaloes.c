#include <stdio.h>
#include<stdlib.h>
 
int main() {
    int n;
    scanf("%d",&n);
    int *h;
    h = (int*)malloc(n*sizeof(int));
    int i;
    for( i =0;i<n;i++){
        scanf("%d",&h[i]);
    }
    
    int *flechas_disparadas = (int*)calloc(1000001, sizeof(int));
    int qtd=0;
    for(i=0;i<n;i++){
        int altura = h[i];

        if(flechas_disparadas[altura]>0){
            flechas_disparadas[altura]--;
            flechas_disparadas[altura-1]++;
        }else{
            qtd++;
            flechas_disparadas[altura-1]++;
        }
    }

    printf("%d",qtd);
    free(flechas_disparadas);
    free(h);
    return 0;
}