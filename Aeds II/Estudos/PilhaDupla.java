package Estudos;

class CelulaDupla{
    int elemento;
    boolean isFirst;
    CelulaDupla next,prev;//encadeando Duplamente

    /*public CelulaDupla(){
        this(0,true);
    }//Construtor vazio para célula

    public CelulaDupla(int elemento){
        this(elemento,false);
    }

    public CelulaDupla(int elemento, boolean isFirst){
        if(isFirst){
            this.elemento = elemento;
            this.next = null;
            this.prev = null;
        }else{
            this.elemento = elemento;
            this.next = null;
            this.prev.next = this;
        }
    }*/ //Nada disso é necessário, já que .next e .prev serão referenciados ao inserir, logo o correto seria:

    public CelulaDupla(){
        this(0);
    }

    public CelulaDupla(int elemento){
        this.elemento = elemento;
        this.prev = this.next = null;
    }
}

public class PilhaDupla {
    CelulaDupla topo,base;

    public PilhaDupla(){
        this.base = new CelulaDupla(); // nó cabeça
        this.topo = new CelulaDupla(); // nó cabeça
        this.base.next = this.topo;
        this.topo.prev = this.base;
    }


    public void push(int x){
        CelulaDupla tmp = new CelulaDupla(x);

        if(this.topo == this.base){
            tmp.prev = this.base;
            this.base.next = tmp;
            this.topo.prev = tmp;
        }else{
            tmp.prev = this.topo.prev;// |this.topo.prev| <- |tmp| -> null
            tmp.next = this.topo;     // |this.topo.prev| <- |tmp| -> |topo|
            this.topo.prev.next = tmp;
            this.topo.prev = tmp;     // |tmp| <- |topo| ->
        }


        /*Ordem final: 
            (Se pilha vazia:){ null <- |this.base| -> <- |tmp| -> <-|this.topo| -> null  }
            (Caso contrário:){ null <- |this.base| -> ... <-|tmp|-> <-|this.topo| -> null }   
        */   

        tmp = null;               // Esvazia célula temporária afim de manter a organização 
        //Após a célula tmp ser esvaziada, não há problema, já que as células na pilha encadeada ainda possuem a refêrencia para o endereço de memória que guarda o conteúdo que era apontado por tmp:

        /*Ordem final (Após Esvaziar tmp): 
            (Se pilha vazia:){ null <- |this.base| -> <- |Célula Inserida| -> <-|this.topo| -> null  }
            (Caso contrário:){ null <- |this.base| -> ... <-|Célula Inserida|-> <-|this.topo| -> null }   
        */ 
        /*Agora, desde que não se percam,ao mesmo tempo, base e topo, ou a referência .next de uma das células no meio, ao mesmo tempo que .prev, que faria com que isso acontecesse:
        { null <- |this.base| -> ... <- |Cell1| ->  ~ |Cell2| ~  <- |topo| ->}
        Ainda assim, seria necessário que se perdessem os pointeiros de Cell1, e de topo, para se perder de forma irreversível a cadeia de referências, já que é uma pilha duplamente encadeada, mas ainda assim, poderia ser quebrado o fluxo do código facilmente.
        */
    }



    public int pop() throws Exception {
        if(this.topo == this.base ){
            throw new Exception ("Erro ao remover!");
        }

        int resp = this.topo.prev.elemento;   //  |this.base| -> ... <- |Célula| -> <- |this.topo|  // resp = Célula.elemento
        //Estamos removendo do topo por se tratar de uma pilha
        
        CelulaDupla tmp = this.topo.prev;     // |this.base| -> ... <- |tmp| -> <- |this.topo| -> null
        this.topo.prev = tmp.prev;            // |this.base| -> ... <-|tmp.prev| <- |this.topo| -> null      // |tmp.prev| -> <- |tmp| -> |this.topo|
        tmp.prev.next = this.topo;            // |this.base| -> ... <-|tmp.prev| -> <- |this.topo| -> null   //    |tmp.prev| <- |tmp| -> |this.topo|
        tmp.next = null;                      // |tmp.prev| <- |tmp| -> null
        tmp.prev = null;                      // null <- |tmp| -> null   
        tmp = null;                           // null <- null -> null
        /* Apesar de eu estar limpando os ponteiros de tmp, e também o seu próprio conteúdo, isto não seria teoricamente necessário já que o java possui coletor de Lixo*/ 

        return resp;                          // Retorna o elemento da célula removida 
    }


    public void show(){                       //Método iterativo para impressão
        System.out.print("[ ");
        for(CelulaDupla i = topo.prev; i!=base; i=i.prev){
            System.out.print(i.elemento + " ");
        }
        System.out.println("] ");
    }

    public void showElement(int x){
        int count = 0;
        for(CelulaDupla i=base.next;i!=topo;i=i.next){
            if(i.elemento == x){
                System.out.println("{ " + x + " }" + "at celula:" + count );
                count++;
                break;
            }else{
                count++;
            }
        }
    }

    public void showAsInserted(){                      //Método recursivo para mostrar a pilha da forma como os elementos foram inseridos  (Método de chamada)
        showAsInserted(this.topo.prev);
    }

    private void showAsInserted(CelulaDupla i){        //Método recursivo para mostrar a pilha da forma que foram inseridos (Método propriamente dito)
        if(i!=base){
            showAsInserted(i.prev);
            System.out.print(" " + i.elemento);
        }
    }

    public static void main(String[] args) {  //Método main para testes-->
        PilhaDupla pilha = new PilhaDupla();

        /*Inserção de 5 elementos à pilha, e impressão de cada um individualmente para fins de confirmação: */
        pilha.push(5);
        pilha.showElement(5);
        pilha.push(4);
        pilha.showElement(4);
        pilha.push(3);
        pilha.showElement(3);
        pilha.push(2);
        pilha.showElement(2);
        pilha.push(1);
        pilha.showElement(1);

        /*Impressão da pilha de acordo com a regra padrão de sua estrutura de dados, e como ordem de inserção: */
        System.out.println("Pilha sendo mostrada como LIFO (Last In First Out):");
        pilha.show();
        System.out.println("Pilha sendo mostrada como FIFO (First In First Out):");
        pilha.showAsInserted();
        System.out.println();

        /*Testes de método pop, e "tratamento" básico de exceção (Exibição de erro apenas)*/

        int x=0;
        try{
             x = pilha.pop();
        }catch(Exception e){
            System.out.println(e);
        }

        System.out.println("Elemento removido: " + x);

        try{
            x = pilha.pop();
       }catch(Exception e){
           System.out.println(e);
       }
       
       System.out.println("Elemento removido: " + x);

       try{
        x = pilha.pop();
        }catch(Exception e){
            System.out.println(e);
        }
        
        System.out.println("Elemento removido: " + x);
         
        /*Impressão da pilha de acordo com a regra padrão de sua estrutura de dados, e como ordem de inserção: */
        System.out.println("Pilha sendo mostrada como LIFO (Last In First Out):");
        pilha.show();
        System.out.println("Pilha sendo mostrada como FIFO (First In First Out):");
        pilha.showAsInserted();
        System.out.println();
    }

}
