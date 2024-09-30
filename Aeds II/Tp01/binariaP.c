#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_IDS 100

char** readElementsFromFile(const char* fileName, char** ids, int numIds, int* size);
int binarySearch(char** elementsArray, int size, const char* key);

int main() {
    char* ids[MAX_IDS]; // Array para armazenar os IDs
    int numIds = 0;

    // Leitura dos IDs do teclado até encontrar "FIM"
    char input[100];
    while (scanf("%s", input) == 1 && strcmp(input, "FIM") != 0 && numIds < MAX_IDS) {
    if(strcmp(input,"FIM")!=0){
        ids[numIds] = strdup(input);
        numIds++;

    }
    }

    // Leitura dos nomes do teclado até encontrar "FIM"
    char** elementsArray = readElementsFromFile("/tmp/characters.csv", ids, numIds, &numIds);

    
    while (fgets(input, sizeof(input), stdin) != NULL && strcmp(input, "FIM") != 0) {
        input[strcspn(input, "\n") + 1] = '\0';
        // Pesquisa binária para verificar se o nome está presente nas linhas filtradas
        if(strcmp(input,"FIM")!=0){
            if (binarySearch(elementsArray, numIds, input)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }else{
        break;
    }
        
    }

    // Libera a memória alocada
    for (int i = 0; i < numIds; i++) {
        free(ids[i]);
    }
    for (int i = 0; i < numIds; i++) {
        free(elementsArray[i]);
    }
    free(elementsArray);

    return 0;
}
char** readElementsFromFile(const char* fileName, char** ids, int numIds, int* size) {
    FILE* file = fopen(fileName, "r");
    if (!file) {
        printf("Erro ao abrir o arquivo: %s\n", fileName);
        exit(1);
    }

    char line[1000];
    int count = 0;
    char** elementsArray = NULL;

    // Ignora a primeira linha (cabeçalho)
    if (fgets(line, sizeof(line), file) == NULL) {
        printf("Erro: arquivo vazio ou formato inválido.\n");
        exit(1);
    }

    // Leitura do arquivo linha por linha
    while (fgets(line, sizeof(line), file) != NULL) {
        line[strcspn(line, "\n")] = '\0'; // Remover o caractere de nova linha
        // Verifica se a linha contém algum dos IDs fornecidos
        for (int i = 0; i < numIds; i++) {
            if (strstr(line, ids[i]) != NULL) {
                // Linha contém um dos IDs, armazene-a
                elementsArray = realloc(elementsArray, (count + 1) * sizeof(char*));
                elementsArray[count] = strdup(line);
                count++;
                break; // Pare de procurar IDs nesta linha
            }
        }
    }

    fclose(file);
    *size = count;
    return elementsArray;
}


int binarySearch(char** elementsArray, int size, const char* key) {
    int inicio = 0, fim = size - 1, meio;

    while (inicio <= fim) {
        meio = (inicio + fim) / 2;
        int cmp = strcmp(elementsArray[meio], key);
        if (cmp == 0) {
            return 1; // Encontrou o elemento
        } else if (cmp < 0) {
            inicio = meio + 1; // Busca na metade direita
        } else {
            fim = meio - 1; // Busca na metade esquerda
        }
    }
    return 0; // Não encontrou o elemento
}

