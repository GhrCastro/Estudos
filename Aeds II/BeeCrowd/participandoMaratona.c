#include<stdio.h>

typedef struct Corredor
{
    int t,s;/*tempo de início e velocidade de cada corredor */
    int posAtual;
}Corredor;

typedef struct Fotos
{
    int count;
    int u;/* número de fotos*/
    int a,b;
    /* u - momento a ser tirada a foto(tempo) 
     a,b - segmento da pista(região de espaço)*/
}Fotos;


int contaCorredoresPorFoto(Corredor c[], int r, Fotos foto){
    int i,count =0;
    for (i = 0; i < r; i++) {
        if (foto.u >= c[i].t) {
            c[i].posAtual = (foto.u - c[i].t) * c[i].s;
        } else {
            c[i].posAtual = -1; // Fora da pista
        }
    }

    for(i=0;i<r;i++){
        if(c[i].posAtual >=foto.a && c[i].posAtual<=foto.b){
            count++;
        }
    }

    return count;
}

int main(){
    int i,j;
    int r; /*número de corredors*/
    scanf("%d",&r);
    Corredor corredores[r];

    for(i=0;i<r;i++){
        scanf("%d %d",&corredores[i].t,&corredores[i].s);
    }
/*--------------------------------------------------------------------------------------*/
    int p;
    scanf("%d",&p);
    Fotos fotos[p];

    for(i=0;i<p;i++){
        scanf("%d %d %d",&fotos[i].u,&fotos[i].a,&fotos[i].b);
        fotos[i].count=0;
    }


/*--------------------------------------------------------------------------------------*/
    int q;/*número de solicitações?*/
    scanf("%d",&q);
    for(i=0;i<q;i++){
    Corredor corredoresAndJhonny[r+1];
    for(j=0;j<r;j++){
            corredoresAndJhonny[j] = corredores[j];
    }

        scanf("%d %d",&corredoresAndJhonny[r].t,&corredoresAndJhonny[r].s);

        int contaDescartes =0;

        for (j = 0; j < p; j++) {
            int contaJhonnys = contaCorredoresPorFoto(corredoresAndJhonny, r + 1, fotos[j]);
            if (contaJhonnys == 0) {
                contaDescartes++;
            }
        }
            printf("%d\n",contaDescartes);
    }

    return 0;
}