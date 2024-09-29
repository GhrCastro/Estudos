import java.util.Scanner;



public class LAB04Q01{
    public static void main(String[] args) {
        Scanner sc  =new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        while(N!=0 && M!=0 ){
            int[] entradas = new int[N];
            
            for(int i=0;i<N;i++){
                entradas[i] = sc.nextInt();
            }

            imparSort(entradas,M,N);
            
            System.out.println(N + " " + M);
            for(int i=0;i<N;i++){
                System.out.println(entradas[i]);
            }

            N = sc.nextInt();
            M = sc.nextInt();
        }
        System.out.println("0 0");
        sc.close();
    }

    //Critérios para a ordenação enunciado: N mod M (resto da divisão por M), caso mod dos elementos seja igual, verificar par ou impar, se ambos impar, o Maior impar primeiro, se ambos par, o menor par primeiro, se diferentes, impar primeiro]

    private static void imparSort(int []array, int m,int n){
        //começo ordenando do critério "mais baixo para o mais alto", dessa forma, consigo ter uma organização, e fazer com que o critério mais alto persista ao ser aplicado por último
        for(int i=1;i<n;i++){
            int tmp=array[i];
            int j=i-1;
            while((j>=0)&&(array[j]%2==0)&&(tmp%2==0)&&(array[j]>tmp)){//se forem pares, e o arr[j] for maior, então ele é trocado com tmp, dessa forma o menor elemento par é colocado na sua posição mais à esquerda
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = tmp;
        }/*primeiro insertion sort, onde a complexidade tanto para comparações quanto para movimentações seria, para melhor caso: O(n) e pior caso O(n²) */

        for(int i=1;i<n;i++){
            int tmp=array[i];
            int j=i-1;
            while((j>=0)&&(array[j]%2==0)&&(tmp%2!=0)){//se são diferentes (um par e um impar), então o elemento arr[j] troca de posição com tmp, desta forma o elemento ímpar assume sua posição mais à esquerda
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = tmp;
        }/*segundo insertion sort, onde a complexidade tanto para comparações quanto para movimentações seria, para melhor caso: O(n) e pior caso O(n²) */

        for(int i=1;i<n;i++){
            int tmp=array[i];
            int j=i-1;
            while((j>=0)&&(array[j]%2!=0)&&(tmp%2!=0)&&(array[j]<tmp)){//se ambos são ímpares, desta vez eu verifico qual o maior, e então ele é colocado mais à esquerda
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = tmp;
        }/*terceiro insertion sort, onde a complexidade tanto para comparações quanto para movimentações seria, para melhor caso: O(n) e pior caso O(n²) */

        //por último, enfim verifico o mod M, e então dessa forma, os elementos terão sido organizados de acordo com os critérios da ordenação
        for(int i=1;i<n;i++){//começo em i, pois será o elemento a ser inserido no conjunto ordenado
            int tmp = array[i];//armazeno este elemento em uma varíavel tmp
            int j=i-1;//começo j na maior posição do conjunto já ordenado e no laço interno irei decrementar a fim de comparar com i
            while( (j>=0)/*<- impede número negativo */ && (array[j]%m)>(tmp%m)){//ordenação primária, por mod m
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = tmp;
        }/*quarto insertion sort, onde a complexidade tanto para comparações quanto para movimentações seria, para melhor caso: O(n) e pior caso O(n²) */
        
    }
}
/*
Como cada novo insertion sort faz no melhor caso O(n) tanto para comparações quanto de movimentações, assim como no pior caso que seria O(n²), sendo assim, junto com os 2 for's antes da chamada do método, que cada um teria complexidade de comparação de O(n), porém não teria movimentações, teríamos uma conta para comparações como: 

O(n)+O(n)+4*O(n)=O(n) Comparações no melhor caso
O(n)+O(n)+4*O(n²)=O(n²) Comparações no pior caso
4*O(n) Movimentações no melhor caso
4*O(n²) Movimentações no pior caso

Assim sendo, O(n²) domina a complexidade, e teríamos O(n²) como complexidade de movimentação e comparação no pior caso, e O(n) no melhor.
*/
