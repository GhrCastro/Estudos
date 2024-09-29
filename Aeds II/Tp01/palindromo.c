#include<stdio.h>
#include<string.h> 
#include<stdlib.h>
#include<stdbool.h>

bool isPalindrome (char * string1, char * string2){
    int i,j;
    for(i=strlen(string1)-1,j=0;i>=0 && j<strlen(string1);i--,j++){
        string2[j] = string1[i];
    }
    string2[j]='\0';

    if(strcmp(string2,string1)==0)
        return true;
    
    

    return false;
}

int main () {
    char * string1;
    char * string2;



    do{
        string1=(char*)malloc(1000*sizeof(char));

        fgets(string1,1000,stdin);

        if(string1[strlen(string1)-1]=='\n'){
            string1[strlen(string1)-1]='\0';
        }

        string2=(char*)malloc((strlen(string1)+1)*sizeof(char));

        if(strcmp(string1,"FIM")!=0){

            if(isPalindrome(string1,string2)==true){
                printf("SIM\n");
            }else{
                printf("NAO\n");
            }

                free(string1);
                free(string2);
        }

    }while(strcmp(string1,"FIM")!=0);

    

    return 0;
}