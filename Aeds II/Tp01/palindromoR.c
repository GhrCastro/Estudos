#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<stdbool.h>

bool vrfPalindrome(char *string1, char *string2, int i, int j) {
    if (i == 0) {
        string2[j] = '\0';
        if (strcmp(string1, string2) == 0)
            return true;
        else
            return false;
    }

    string2[j] = string1[i-1];
    return vrfPalindrome(string1, string2, i-1, j+1);
}

int main() {
    char string1[1000];
    char string2[1000];

    do {
        fgets(string1, 1000, stdin);
        if (string1[strlen(string1)-1] == '\n')
            string1[strlen(string1)-1] = '\0';

        if (strcmp(string1, "FIM") != 0) {
            if (vrfPalindrome(string1, string2, strlen(string1), 0))
                printf("SIM\n");
            else
                printf("NAO\n");
        }
    } while (strcmp(string1, "FIM") != 0);

    return 0;
}
