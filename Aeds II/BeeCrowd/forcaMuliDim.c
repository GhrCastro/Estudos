#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define MAX_N 10000
#define MAX_C 12

// Função para contar as compatibilidades entre duas palavras
int contar_compatibilidades(char* p1, char* p2, int c) {
    int compatibilidade = 0;
    for (int i = 0; i < c; i++) {
        if (p1[i] != '*' && p2[i] != '*' && p1[i] == p2[i]) {
            compatibilidade++;
        }
    }
    return compatibilidade;
}

int main() {
    int n, c;
    scanf("%d %d", &n, &c);

    // Vetor para armazenar as palavras com caracteres desconhecidos
    char palavras[MAX_N][MAX_C + 1];

    for (int i = 0; i < n; i++) {
        scanf("%s", palavras[i]);
    }

    int max_compatibilidades = -1;
    char melhor_palavra[MAX_C + 1];
    
    // Gerar todas as possíveis palavras substituindo os caracteres '*'
    for (int i = 0; i < n; i++) {
        char palavra_teste[MAX_C + 1];
        strcpy(palavra_teste, palavras[i]);

        // Substituir '*' por uma letra correspondente de outra palavra
        for (int j = 0; j < c; j++) {
            if (palavra_teste[j] == '*') {
                // Substituir pelo caractere correspondente de outra palavra que não seja '*'
                for (int k = 0; k < n; k++) {
                    if (palavras[k][j] != '*') {
                        palavra_teste[j] = palavras[k][j];
                        break;
                    }
                }
            }
        }

        // Contar compatibilidades da palavra gerada com as demais
        int compatibilidades = 0;
        for (int j = 0; j < n; j++) {
            compatibilidades += contar_compatibilidades(palavra_teste, palavras[j], c);
        }

        // Atualizar a palavra com mais compatibilidades
        if (compatibilidades > max_compatibilidades || 
           (compatibilidades == max_compatibilidades && strcmp(palavra_teste, melhor_palavra) < 0)) {
            max_compatibilidades = compatibilidades;
            strcpy(melhor_palavra, palavra_teste);
        }
    }

    printf("%s %d\n", melhor_palavra, max_compatibilidades);

    return 0;
}
