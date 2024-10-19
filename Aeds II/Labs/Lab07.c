#include <stdio.h>
#include <stdlib.h>

// Função para contar ultrapassagens
int contarUltrapassagens(int* largada, int* chegada, int n) {
    int posicoes[n];
    int ultrapassagens = 0;

    //Array para armazenar as posições dos corredores na largada
    int pos_largada[n + 1];  // Tamanho n + 1 porque os corredores são de 1 a N

    // Preenche o array com as posições da largada
    for (int i = 0; i < n; i++) {
        pos_largada[largada[i]] = i; 
    }

    /*
    i=0, largada[i] = largada[0]=>1
    pos_largada[largada[i]] = pos_largada[largada[0]] = pos_largada[1]=>0
    */

    //Conta as ultrapassagens comparando as posições
    for (int i = 0; i < n; i++) {                                                                              
        for (int j = i + 1; j < n; j++) {                                                                     
            // Se um corredor que estava atrás na largada chegou antes, houve ultrapassagem
            if (pos_largada[chegada[i]] > pos_largada[chegada[j]]) {       i=0; j=0+1; j->chegada[1] = 2; i->chegada[0] = 3;  pos_largada[3] = 4;  pos_largada[2] = 1;       
                ultrapassagens++;
            }
        }
    }

    /*
    largda[n] 0-n largada[0]=>1
    chegada[n] 0-n chegada[0]=>3       
                                  
    i=0, chegada[i] = chegada[0] =>3
    j=0+1, j=1, chegada[j] = chegada[1]=>2

    pos_largada[chegada[i]] = pos_largada[chegada[0]] = pos_largada[3] => 
    */

    return ultrapassagens;
}

int main() {
    int n;

    while (scanf("%d", &n) != EOF) {
        int largada[n], chegada[n];

        for (int i = 0; i < n; i++) {
            scanf("%d", &largada[i]);
        }

        for (int i = 0; i < n; i++) {
            scanf("%d", &chegada[i]);
        }

        int resultado = contarUltrapassagens(largada, chegada, n);
        printf("%d\n", resultado);
    }

    return 0;
}

