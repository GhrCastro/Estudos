#include <stdio.h>
#include<stdlib.h>
#include <string.h>
#include <ctype.h>

int contaMaiusc(char*,int,int);

int main(){
    char *entrada;
    int count;
    entrada = (char*) malloc(100 * sizeof(char)); 

    while(1){
        fgets(entrada,100,stdin);
        entrada[strcspn(entrada, "\n")] = '\0';

        if(strcmp(entrada,"FIM")==0){
            break;
        }

        count=0;
        
        count= contaMaiusc(entrada,strlen(entrada),count);

        printf("%d\n", count);

        free(entrada);
        entrada = (char*) malloc(100* sizeof(char));
    }
    
    free(entrada);
    return 0;
}

int contaMaiusc (char* s, int i, int count){
    if(i<0){
        return count;
    }

    if(isupper(s[i])){
        count++;
    }

    return contaMaiusc(s,i-1,count);
}
//Feito em conjunto por Gustavo H R Castro e João Marcos Freitas