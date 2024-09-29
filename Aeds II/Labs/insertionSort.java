/*public class insertionSort {
    private void Sort(int arr) {
        for (int i = 1; i < n; i++) {//o i começa com 1, pois quando temos apenas um elemento, o nosso conjunto já está ordenado, i aponta para o elemento a ser inserido no conjunto ordenado
            int tmp = array[i];
            int j = i - 1;//j já começa na maior posição do conjunto já ordenado e no laço interno ele é decrementado
             while ( (j >= 0) && (array[j] > tmp) ){//o laço interno tem duas tarefas: I)procura a posição de inserção do novo elemento; 
                //a primeira cláusula do loop interno serve apenas para não acessarmos posições negativas na segunda cláusula
            array[j + 1] = array[j];                //II) desloca os elementos maiores que o novo elemento
            j--;
            }
            array[j + 1] = tmp;//neste ponto, inserimos o novo elemento na posição correta
            }
    }
}
*/