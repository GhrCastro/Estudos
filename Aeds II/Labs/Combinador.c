#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
    char *entrada1,*entrada2,*saida;
    entrada1 = (char*) malloc(1000* sizeof(char));
    entrada2 = (char*) malloc(1000* sizeof(char));
    
    while(scanf("%s %s",entrada1, entrada2)!=EOF){
        if(entrada1[0]==13){
            return 0;
        }

        saida = (char*) malloc(1000* sizeof(char));
        
        int i=0,j=0,k=0;



       while(entrada1[i]!='\0' && entrada2[j]!='\0'){
        saida[k++] = entrada1[i++];
        saida[k++] = entrada2[j++];
       }

       while(entrada1[i] != '\0'){
        saida[k++] = entrada1[i++];
       }

       while(entrada2[j] != '\0'){
        saida[k++] = entrada2[j++];
       }

        printf("%s\n",saida);
        free(entrada1);
        free(entrada2);
        free(saida);
        entrada1 = (char*) malloc(1000* sizeof(char));
        entrada2 = (char*) malloc(1000* sizeof(char));

    }

        

    





    return 0;
}