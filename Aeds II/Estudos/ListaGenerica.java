package Estudos;
import java.util.*;
import java.io.*;

// Definindo a exceção personalizada EmptyListException
class EmptyListException extends RuntimeException {
    // Construtor padrão sem argumentos
    public EmptyListException() {
        this("List");  // Passa a string "List" para o outro construtor
    }

    // Construtor que recebe o nome da lista como argumento
    public EmptyListException(String name) {
        super(name + " is empty");  // Define a mensagem da exceção
    }
}

//Declaração das classes ListNode e List:

//Classe de nó da lista, com estrutura de dado de tipo T, ou seja será definido no momento da instância do objeto:
class ListNode<T>{
    //membros do acesso de pacote; List pode acessar esses diretamente
    T data;                 //dados para este nó
    ListNode<T> nextNode;   //referência para o próximo nó da lista


    //Construtor da classe ListNode vazio, chama o construtor que recebe objeto, com valor nulo 
    ListNode(){
        this(null);
    }

    //Construtor da classe ListNode que cria um ListNode que referencia um objeto, ao chamar o construtor que recebe objeto e próximo nó, com valor do nextNode nulo
    ListNode(T object){
        this(object,null);
    }

    //Construtor da classe ListNode que referencia um objeto especificado, e o próximo ListNode
    ListNode(T object, ListNode<T> node){
        this.data = object;
        this.nextNode = node;
    }

    //getter para o dado armazenado no nó de tipo T, que serão especificados no momento da instância do objeto
    public T getData(){
        return this.data;
    }

    //getter para o próximo nó de Tipo ListNode<T>, que mais uma vez, reitero que será especificado no momento da instância do objeto
    public ListNode<T> getNext(){
        return this.nextNode;
    }

}/* Fim da classe ListNode<T> */


//Definição da classe List, também de tipo T, que será quem receberá a especificação de tipo do usuário, ao ter seu construtor utilizado para instanciar um novo objeto
class myList<T>{

    public ListNode<T> firstNode; // Primeiro nó da classe List
    public ListNode<T> lastNode;  // Último nó da classe List
    private String listName;       // Nome|Palavra como "Lista", ou "LinkedList" a ser usada na impressão

    //Construtor vazio que inicializa o nome como Linked List
    public myList(){
        this("Linked List");
    }

    //Construtor que recebe o nome que será dado à estrutura
    public myList(String name){
        this.listName = name;
        firstNode = lastNode = null; //Inicializa ambos os nós firstNode e lastNode como null 
    }

    //Método para inserir no começo da lista (Parte da frente)
    public void inserAtFront(T insertItem){
        if(isEmpty()){

            this.firstNode = this.lastNode = new ListNode<T>(insertItem);//Caso esteja vazia, lastNode e firstNode referenciam o mesmo objeto
                                                                         //  firstNode-> |novoNo| <-lastNode
        }else{      

            this.firstNode = new ListNode<T>(insertItem,firstNode);      //Caso contrário, firstNode referencia o novo Nó
                                                                         //  firstNode-> |novoNo| firstNode.nextNode(ou novoNo.nextNode)-> |firstNode(anterior)|
        }                                                                
    }

    //Método para inserir no final da lista (parte de trás)
    public void insertAtBack(T insertItem){
        if(isEmpty()){

            this.firstNode = this.lastNode = new ListNode<T>(insertItem);    //Caso esteja vazia, lastNode e firstNode referenciam o mesmo objeto
                                                                             //  firstNode-> |novoNo| <-lastNode
        }else{

            this.lastNode = this.lastNode.nextNode = new ListNode<T>(insertItem);//Caso contrário, com uma declaração "recursiva", lastNode.nextNode recebe o valor do novo Nó, referenciando assim o nó criado agora com o novo item, e logo em seguida, lastNode recebe este valor, passando a referenciar o novo nó, desta forma, o nó que antes era lastNode referencia como nó.nextNode o novo nó, e este se "torna" o novo lastNode
        }
    }

    //Método para remover o primeiro item de uma lista (o da frente)
    public T removeFromFront()throws EmptyListException{
        if(isEmpty()){
            throw(new EmptyListException(this.listName));//Se a lista estiver vazia, lança uma exceção 
        }

        T removedItem = this.firstNode.data;        //Recupera os dados a serem removidos

        //Atualiza referências first node e last node
        if(this.firstNode == this.lastNode){
            this.firstNode = this.lastNode = null;
        }else{
            this.firstNode = this.firstNode.nextNode;
        }

        return removedItem;                          //Retorna os dados do nó removido
    }

    //Método para remover o último item de uma lista (o de trás)
    public T removeFromBack()throws EmptyListException{
        if(isEmpty()){
            throw(new EmptyListException(this.listName));
        }

        T removedItem = this.lastNode.data;

        if(this.firstNode == this.lastNode){          //Atualiza as referências de lastNode  firstNode
            this.firstNode = this.lastNode = null;
        }else{                                        //Caso firstNode e lastNode não sejam iguais, localiza o novo último nó
            ListNode<T> current = this.firstNode;

            while(current.nextNode != this.lastNode){ //Loop que redeclara currente como current.nextNode até que seu nextNode seja lastNode
                current = current.nextNode;
            }

            lastNode = current;                       //lastNode agora referencia o mesmo objeto que current  --lastNode-->> |current| --current.next-> |(older)lastNode|
            current.nextNode = null;                  //current.nextNode deixa de referenciar o nó removido (older)lastNode, e assim ele é "removido" pois como não há mais referência a ele, o coletor de lixo faz seu trabalho e trata de coletá-lo
        }                                             // --lastNode-->> |current| --current.next--> null
        return removedItem;
    }

    //Método para mostrar a lista
    public void print(){
        if(isEmpty()){
            System.out.println("Empty"+ this.listName);
            return;
        }

        System.out.println("Here is the requested"+ this.listName + ": ");
        ListNode<T> current = firstNode;

        //Enquanto não estiver no fim da lista, gera saída de dados do nó atual
        while(current != null){
            System.out.println(current.data);
            current = current.nextNode;
        }
    }

    //Determina se a lista está vazia
    public boolean isEmpty(){
        return firstNode == null;   //retorna true se a lista estiver vazia
    }


    public void printElementNode(T elementToPrint){
        if(isEmpty()){
            System.out.println("Empty"+ this.listName);
            return;
        }
        
        int count = 0;
        ListNode<T> current = firstNode;
        while((current.data!=elementToPrint || !current.data.equals(elementToPrint)) && current!=null){
            count++;
            current = current.nextNode;
        }

        System.out.println("Element: " + current.data + " found at node: " + count);
    }
}


//Classe para testest
public class ListaGenerica {
    
    public static void main(String[] args) {
        myList<String> list = new myList<String>();//Cria um objeto do tipo myList

        //Insere inteiros na lista
        list.inserAtFront("Gustavo");
        list.printElementNode("Gustavo");
        list.inserAtFront("Gabriel");
        list.printElementNode("Gustavo");
        list.printElementNode("Gabriel");
        list.insertAtBack("Felipe");
        list.printElementNode("Felipe");
        list.insertAtBack("Max");
        list.printElementNode("Max");

        list.print();//Printa a lista

        //Remove objetos da lista
        try{
            System.out.println("removed: "+list.removeFromFront());
            list.print();

            System.out.println("removed: "+list.removeFromBack());
            list.print();

        }catch(EmptyListException emptyListException){
            emptyListException.printStackTrace();
        }

        for (ListNode<String> current = list.firstNode; current!=null;current = current.nextNode) {
            list.printElementNode(current.data);
        }
    }
}
