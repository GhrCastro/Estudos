#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

int main(){
    char *entrada;
    int count = 0;
    entrada = (char*) malloc(100 * sizeof(char)); 

    
    while(1){
        fgets(entrada,100,stdin);
        entrada[strcspn(entrada, "\n")] = '\0';

        if(strcmp(entrada,"FIM")==0){
            break;
        }

        count = 0;
        int i;

        for( i=0;i<strlen(entrada);i++){
            if(isupper(entrada[i])){
                count++;
            }
        }

        printf("%d\n", count);
        
        free(entrada);
        entrada = (char*) malloc(100* sizeof(char));
    }

    free(entrada);
    return 0;
}
//Feito em conjunto por Gustavo H R Castro e João Marcos Freitas