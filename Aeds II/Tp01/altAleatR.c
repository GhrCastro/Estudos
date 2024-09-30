#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char *randomCreator(char *string, char *nwString, char l, char nL, int i) {
    if (string[i] == '\0') {
        nwString[i] = '\0';
        strcpy(string, nwString); // Copia a nova string de volta para a string original
        return string;
    }

    if (string[i] == l) {
        nwString[i] = nL;
    } else {
        nwString[i] = string[i];
    }

    return randomCreator(string, nwString, l, nL, i + 1);
}

int main() {
    char string1[1000];

    do {
        fgets(string1, 1000, stdin);
        
        if (string1[strlen(string1) - 1] == '\n') {
            string1[strlen(string1) - 1] = '\0';
        }

        if (strcmp(string1, "FIM") != 0) {
            start:
            srand(time(NULL));
            char  L = 97 + (char)(rand()%26);
            char nL = 97 + (char)(rand()%26);  
            if(nL!=L){
            char nwS[strlen(string1) + 1];
            nwS[strlen(string1) + 1] = randomCreator(string1,nwS,L,nL,0);
            printf("%s\n",nwS);
            }else{
                goto start;
            }
            
        }

    } while (strcmp(string1, "FIM") != 0);

    return 0;
}
