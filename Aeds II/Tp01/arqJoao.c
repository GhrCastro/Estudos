#include <stdio.h>
#include <stdlib.h>

void openAndPrintFile(int n);
void writeAndCloseFile(int n);

int main() {
    int n;
    scanf("%d", &n);

    writeAndCloseFile(n);
    openAndPrintFile(n);

    return 0;
}

void openAndPrintFile(int n) {
    FILE *file = fopen("numsToRead.txt", "rb");
    if (file == NULL) {
        printf("Erro ao abrir o arquivo para leitura.\n");
        return;
    }

    for (int i = n - 1; i >= 0; i--) {
        fseek(file, i * sizeof(double), SEEK_SET);
        double value;
        fread(&value, sizeof(double), 1, file);

        printf("%g\n", value);
    }

    fclose(file);
}

void writeAndCloseFile(int n) {
    FILE *file = fopen("numsToRead.txt", "wb");
    if (file == NULL) {

        return;
    }

    for (int i = 0; i < n; i++) {
        double value;
        scanf("%lf", &value);
        fwrite(&value, sizeof(double), 1, file);
    }

    fclose(file);
}