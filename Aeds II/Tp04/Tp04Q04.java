

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.io.*;
import java.text.SimpleDateFormat;

/**
 * Tp02Q01
 */

class Pokemon{ 
    private int id;
    private int generation;
    private String name;
    private String description;
    private ArrayList<String> types;
    private ArrayList<String> abilities;
    private double weight;
    private double height;
    private int captureRate;
    private boolean isLegendary;    
    private Date captureDate;

    //Leitura-->
    public Pokemon read(String id) {
        try {
            File file = new File("/tmp/pokemon.csv"); //C:/Users/gugsh/Documents/GitHub/Estudos/Aeds II/tmp/pokemon.csv
            Scanner scanner = new Scanner(file);
            scanner.nextLine(); // Pula a linha de cabeçalho
    
            while (scanner.hasNextLine()) {
                SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
    
                String linha = scanner.nextLine();
                String[] preParts = linha.split("\"");
    
                String novaLinha = preParts.length > 2 ? preParts[0] + preParts[2] : linha; // Verifica se há aspas
    
                String[] parts = novaLinha.split(",");
                if (parts.length >= 10 && parts[0].equals(id)) {
                    this.id = Integer.parseInt(parts[0]);
                    this.generation = Integer.parseInt(parts[1]);
                    this.name = parts[2];
                    this.description = parts[3];
    
                    // Adiciona tipos
                    this.types.add(parts[4].trim());
                    if (!parts[5].trim().isEmpty()) { // Verifica se o segundo tipo não está vazio
                        this.types.add(parts[5].trim());
                    }
    
                    // Adiciona habilidades
                    this.abilities = Lista(preParts[1]);
    
                    this.weight = parts[7].isEmpty() ? 0.0 : Double.parseDouble(parts[7]);
                    this.height = parts[8].isEmpty() ? 0.0 : Double.parseDouble(parts[8]);
                    this.captureRate = Integer.parseInt(parts[9]);
                    this.isLegendary = parts[10].equals("0") ? false : true;
                    if (!parts[11].isEmpty()) {
                        this.captureDate = sdfInput.parse(parts[11]);
                    }
    
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    
        return this;
    }
    
    //Método para transformar o trecho de dados abilities em lista
    public ArrayList<String> Lista(String elementos){
        ArrayList<String> novaLista = new ArrayList<>();
    
        // Remove colchetes, aspas simples e espaços
        elementos = elementos.replace("[", "").replace("]", "").replace("'", "").trim();
    
        // Divide os elementos pela vírgula e adiciona à lista
        for (String habilidade : elementos.split(",")) {
            novaLista.add(habilidade.trim());
        }
    
        return novaLista;
    }

   /* Deprecated Method here: 
   /* the following method is deprecated since the use of ".split()" was approved.
   /*  public static String[] separarLinha(String linha){
        StringBuilder part = new StringBuilder();
        String [] partFinal =  new String[11];
        int tam = linha.length();

        int  listaEnd =0;
        int j=0;
        for(int i=0;i<tam;i++){
            if(linha.charAt(i)=='"'){
                for(int k=i+1;k<tam;k++){
                    if(linha.charAt(k)!='"'){
                        part.append(linha.charAt(k));
                    }else if(linha.charAt(k)=='"'){
                        listaEnd = k;
                        break;
                    }
                }
                part.append(linha.charAt(listaEnd));
                partFinal[j] = part.toString();
                part.setLength(0);
                j++;
            }else if(linha.charAt(i)!=','){
                part.append(linha.charAt(i));
            }else{
                partFinal[j] = part.toString();
                part.setLength(0);
                j++;
            }
        }
        partFinal[j] = part.toString();
        return partFinal;
    }*/

    //<--Leitura


    //Impressão-->

    public void print() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        System.out.print("[" + "#" + getId() + " -> " + getName() + ": " + getDescription() + " - " + "[");

        if (getTypes() != null) {
            for (int i = 0; i < getTypes().size(); i++) {
                System.out.print("'" + getTypes().get(i) + "'");
                if (i + 1 < getTypes().size()) {
                    System.out.print(", ");
                }
            }
        }

        System.out.print("]" + " - " + "[");

         if (getAbilities() != null) {
            for (int i = 0; i < getAbilities().size(); i++) {
                System.out.print("'" + getAbilities().get(i) + "'");
                if (i + 1 < getAbilities().size()) {
                    System.out.print(", ");
                }
            }
        }

        System.out.print("]" + " - " + getWeight() + "kg" + " - " + getHeight() + "m" + " - " + getCaptureRate() + "%" + " - " + getIsLegendary() + " - " + getGen() + " gen" + "]");

        if (getCaptureDate() != null) {
            System.out.print( " - " + sdf.format(getCaptureDate()));
        } else {
            System.out.print("Data não disponível");
        }

        System.out.println();
    }

    //<--Impressão


    //Construtores-->

    //Construtor Vazio
    public Pokemon (){
         this(0,0,"","", new ArrayList<>(), new ArrayList<>(),0.0,0.0,0,false,null);
    }

    //Construtor com atributos  (Se necessário, criar um com atributos específicos a partir do vazio.)
    public Pokemon(int id, int geracao, String nome, String descricao, ArrayList<String> tipos, ArrayList<String>habilidades, double peso, double altura, int taxaDeCaptura, boolean eLendario, Date dataDeCaptura){
        this.id = id;
        this.generation = geracao;
        this.name = nome;
        this.description = descricao;
        this.types = tipos;
        this.abilities = habilidades;
        this.weight = peso;
        this.height = altura;
        this.captureRate = taxaDeCaptura;
        this.isLegendary = eLendario;
        this.captureDate = dataDeCaptura;
    }

    //<--Construtores


    //Clone-->
    public Pokemon clone(Pokemon pokemon){

        Pokemon clone = new Pokemon(
            pokemon.getId(),
            pokemon.getGen(),
            pokemon.getName(),
            pokemon.getDescription(),
            pokemon.getTypes(),
            pokemon.getAbilities(),
            pokemon.getWeight(),
            pokemon.getHeight(),
            pokemon.getCaptureRate(),
            pokemon.getIsLegendary(),
            pokemon.getCaptureDate());

        return clone ;
    }
    //<--Clone


    //Setters-->

    public void setId(int id){
        this.id = id;
    }

    public void setGeneration(int generation){
        this.generation = generation;
    }

    public void setName (String name){
        this.name = name;
    }

    public void setDescription (String description){
        this.description = description;
    }

    public void setTypes (ArrayList<String> types){
        this.types = types;
    }

    public void setAbilities (ArrayList<String>abilities){
        this.abilities = abilities;
    }

    public void setWeight (double weight){
        this.weight = weight;
    }

    public void setHeight (double height){
        this.height = height;
    }

    public void setCaptureRate (int captureRate){
        this.captureRate = captureRate;
    }

    public void setIsLegendary (boolean isLegendary){
        this.isLegendary = isLegendary;
    }

    public void setCaptureDate (Date captureDate){
        this.captureDate = captureDate;
    }

    //<--Setters


    //Getters-->
    
    public int getId(){
        return this.id;
    }

    public int getGen(){
        return this.generation;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public ArrayList<String> getTypes(){
        return this.types;
    }

    public ArrayList<String> getAbilities(){
        return this.abilities;
    }

    public double getWeight(){
        return this.weight;
    }

    public double getHeight(){
        return this.height;
    }

    public int getCaptureRate(){
        return this.captureRate;
    }

    public boolean getIsLegendary(){
        return this.isLegendary;
    }

    public Date getCaptureDate(){
        return this.captureDate;
    }

    //<--Getters

}

//Classe da Questão 01 do Tp02:
 class Q01{
    /*public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;
        entrada=  sc.nextLine();
        
        ArrayList<Pokemon> pokemons= new ArrayList<>();//Array dos pokemons que serão pegos pelos ids
        
        while(!entrada.equals("FIM")){
            String id = entrada.trim();
            
            Pokemon pokemon = new Pokemon();
            pokemon.read(id);
            pokemons.add(pokemon);
            entrada = sc.nextLine();
        }


        for(Pokemon pokemon : pokemons){//itera sobre cada item do Array List de tipo Pokemon, chamado 'pokemons', ao criar um elemento 'pokemon'
            pokemon.print();
        }
        sc.close();
    }*/
}

//Classe da Questão 03 do Tp02:
 class Q03{

   /*  public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;
        entrada=  sc.nextLine();
        
        ArrayList<Pokemon> pokemons= new ArrayList<>();//Array dos pokemons que serão pegos pelos ids
        
        while(!entrada.equals("FIM")){
            String id = entrada.trim();
            
            Pokemon pokemon = new Pokemon();
            pokemon.read(id);
            pokemons.add(pokemon);
            entrada = sc.nextLine();
        }

        

        entrada = sc.nextLine();
        while(!entrada.equals("FIM")){
            boolean yes = false;
            String name = entrada.trim();
            for(Pokemon pokemon : pokemons){
                if(name.equals(pokemon.getName())){
                    System.out.println("SIM");
                    yes = true;
                }
            }
            if(yes==false){
                System.out.println("NAO");
            }
            entrada = sc.nextLine();
        }
        
        sc.close();
    } */
}

//Classe da Questão 05 do Tp02:
class Q05 {

    public static void selection_sort(Pokemon[] pokes) {
    int fim = pokes.length;

    for (int i = 0; i < fim; i++) {
        for (int j = i + 1; j < fim; j++) {
            // Comparação para ordenar pelo nome do Pokemon
            if (pokes[i].getName().compareTo(pokes[j].getName()) > 0) {
                Pokemon tmp = pokes[j];
                pokes[j] = pokes[i];
                pokes[i] = tmp;
            }
        }
        
    }
}


   /* public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;
        entrada=  sc.nextLine();
        
        ArrayList<Pokemon> pokemons= new ArrayList<>();//Array dos pokemons que serão pegos pelos ids
        
        while(!entrada.equals("FIM")){
            String id = entrada.trim();
            
            Pokemon pokemon = new Pokemon();
            pokemon.read(id);
            pokemons.add(pokemon);
            entrada = sc.nextLine();
        }

        Pokemon []pokes = new Pokemon[pokemons.size()];

        int i=0;
        for(Pokemon pokemon : pokemons){
            pokes[i] = pokemon;
            i++;
        }

        selection_sort(pokes);

        for(i=0;i<pokes.length;i++){
            pokes[i].print();
        }

        sc.close();
    } */
}


//Classe da Questão 07 do Tp02:
 class Q07 {

    public static void insertionSort(Pokemon []pokes){
        for(int i=0;i<pokes.length;i++){
            for(int j=i;j>0;j--){
                if(pokes[j].getCaptureDate().compareTo(pokes[j-1].getCaptureDate())<0){
                    Pokemon tmp = pokes[j];
                    pokes[j] = pokes[j-1];
                    pokes[j-1] = tmp;
                }
            }
        }
    }

    /*public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;
        entrada=  sc.nextLine();
        
        ArrayList<Pokemon> pokemons= new ArrayList<>();//Array dos pokemons que serão pegos pelos ids
        
        while(!entrada.equals("FIM")){
            String id = entrada.trim();
            
            Pokemon pokemon = new Pokemon();
            pokemon.read(id);
            pokemons.add(pokemon);
            entrada = sc.nextLine();
        }
        sc.close();

        Pokemon []pokes = new Pokemon[pokemons.size()];

        int i=0;
        for(Pokemon pokemon : pokemons){
            pokes[i] = pokemon;
            i++;
        }

        insertionSort(pokes);

        for(i=0;i<pokes.length;i++){
            pokes[i].print();
        }

    }*/
}

//Classe da Questão 09 do Tp02:
 class Q9 {
    //Método swap universal, está público para uso futuro em qualquer classe
    public static void swap(Pokemon []array, int i, int j){
        Pokemon tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    //Método de comparação para a Q09 onde o critério é height, e desempate name, pode ser usado como base para outras questões->(Deve ser comentado primeiro caso seja reutilizado)
    private static int compara(Pokemon a, Pokemon b) {
        // Primeiro, compara a altura
        if (a.getHeight() != b.getHeight()) {
            return Double.compare(a.getHeight(), b.getHeight()); // Compara as alturas
        } else {
            // Se as alturas forem iguais, compara os nomes
            return a.getName().compareTo(b.getName()); // Compara os nomes como critério de desempate
        }
    }

    //Método para construir o heap
    private static void construir(Pokemon []array, int tamHeap){
        //Faz a "subida" dos elementos para formar um heap
        for(int i= tamHeap; i>1 && compara(array[i-1],array[i/2-1])>0;i/=2){
            swap(array, i-1, i/2-1);
        }
    }

    //Método para reconstruir o heap após a troca
    private static void reconstruir(Pokemon []array, int tamHeap){
        int i=1;//Começa pelo primeiro elemento
        //Enquanto o nó atual tiver filhos
        while(i<=(tamHeap/2)){
            int filho = getMaiorFilho(array, i, tamHeap);//Obtém o índice do maior filho.
            //Se o nó atual for menor que o maior filho, troca
            if(compara(array[i-1], array[filho-1])<0){
                swap(array, i-1, filho-1);//Troca os elementos
                i = filho; //Move par ao filho para continuar a reconstrução
            }else{
                i = tamHeap;//Se não houver troca, termina o loop
            }

        }
    }

    //Método para obter o índice do maior filho, privado pois é destinado apenas à heapSort
    private static int getMaiorFilho(Pokemon []array, int i, int tamHeap){
        //Se o nó atual tiver apenas o filho à esquerda
        if(2 * i==tamHeap || compara(array[2* i -1], array[2*i])>0){
            return 2*i;//Retorna o índice do filho à esquerda
        }else{
            return 2*i+1;//Retorna o índice do filho à direita
        }
    }

    public static void heapSort(Pokemon []array){
        int n = array.length;

        //Construção do heap: converte a lista de Pokémon em um heap
        for(int tamHeap = 2;tamHeap <=n; tamHeap++){
            construir(array, tamHeap);//Chama o  método para construir o heap
        }

        //Ordenação propriamente dita
        int tamHeap = n;//Inicializa o tamanho do heap
        while(tamHeap > 1){//Enquanto houver mais de um elememnto do heap
            swap(array,0,tamHeap - 1);//Troca o primeiro elemento com o último
            tamHeap--;//Reduz o tamanho do heap
            reconstruir(array, tamHeap);//Reconstrói o heap após a troca
        }


    }
   /*  public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;
        entrada=  sc.nextLine();
        
        ArrayList<Pokemon> pokemons= new ArrayList<>();//Array dos pokemons que serão pegos pelos ids
        
        while(!entrada.equals("FIM")){
            String id = entrada.trim();
            
            Pokemon pokemon = new Pokemon();
            pokemon.read(id);
            pokemons.add(pokemon);
            entrada = sc.nextLine();
        }
        sc.close();

        Pokemon []pokes = new Pokemon[pokemons.size()];

        int i=0;
        for(Pokemon pokemon : pokemons){
            pokes[i] = pokemon;
            i++;
        }

        heapSort(pokes);

        for(i=0;i<pokes.length;i++){
            pokes[i].print();
        }

    }*/
}

//Classe da Questão 11 do Tp02:
 class Q11 {
    //retorna o maior elemento do array
    private static int getMaior(Pokemon []pokes,int n){
        int maior = pokes[0].getCaptureRate();

        for(int i=0;i<n;i++){
            if(maior<pokes[i].getCaptureRate()){
                maior = pokes[i].getCaptureRate();
            }
        }

        return maior;
    }

  //countingSort por name
  /*   public static void countingSort(Pokemon[] pokes) {
        // Array para contar o número de ocorrências de cada captureRate
        int[] count = new int[getMaior(pokes, pokes.length) + 1];
        Pokemon[] ordenado = new Pokemon[pokes.length];
        
        // Inicializar o array de contagem com zeros
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
    
        // Contagem das ocorrências de cada captureRate
        for (int i = 0; i < pokes.length; i++) {
            count[pokes[i].getCaptureRate()]++;
        }
    
        // Transformar a contagem para que contenha as posições finais de cada captureRate
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
    
        // Colocando os elementos no array ordenado de maneira estável
        for (int i = pokes.length - 1; i >= 0; i--) {
            int currentCaptureRate = pokes[i].getCaptureRate();
            int position = count[currentCaptureRate] - 1;
    
            // Insere o Pokémon na posição correta no array ordenado
            ordenado[position] = pokes[i];
            count[currentCaptureRate]--;
        }
    
        // Neste ponto, o array está ordenado por captureRate, mas pode não estar desempate por nome
        // Agora faremos a ordenação estável, considerando desempate por nome
    
        for (int i = 0; i < pokes.length; i++) {
            for (int j = i + 1; j < pokes.length; j++) {
                // Se os captureRates forem iguais, fazer o desempate pelo nome
                if (ordenado[i].getCaptureRate() == ordenado[j].getCaptureRate() &&
                    ordenado[i].getName().compareTo(ordenado[j].getName()) > 0) {
                    
                    // Trocar os Pokémon de posição para ordenar pelo nome
                    Pokemon temp = ordenado[i];
                    ordenado[i] = ordenado[j];
                    ordenado[j] = temp;
                }
            }
        }
    
        // Copiando o array ordenado para o array original
        for (int i = 0; i < pokes.length; i++) {
            pokes[i] = ordenado[i];
        }
    }
    */

    public static void countingSort(Pokemon[] pokes) {
        // Array para contar o número de ocorrências de cada captureRate
        int[] count = new int[getMaior(pokes, pokes.length) + 1];
        Pokemon[] ordenado = new Pokemon[pokes.length];
    
        // Inicializar o array de contagem com zeros
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
    
        // Contagem das ocorrências de cada captureRate
        for (int i = 0; i < pokes.length; i++) {
            count[pokes[i].getCaptureRate()]++;
        }
    
        // Transformar a contagem para que contenha as posições finais de cada captureRate
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }
    
        // Colocando os elementos no array ordenado de maneira estável
        for (int i = pokes.length - 1; i >= 0; i--) {
            int currentCaptureRate = pokes[i].getCaptureRate();
            int position = count[currentCaptureRate] - 1;
    
            // Insere o Pokémon na posição correta no array ordenado
            ordenado[position] = pokes[i];
            count[currentCaptureRate]--;
        }
    
        // Neste ponto, o array está ordenado por captureRate
        // Agora faremos a ordenação estável, considerando desempates por generation e nome
    
        for (int i = 0; i < pokes.length; i++) {
            for (int j = i + 1; j < pokes.length; j++) {
                // Se os captureRates forem iguais
                if (ordenado[i].getCaptureRate() == ordenado[j].getCaptureRate()) {
                    // Desempate por generation
                    if (ordenado[i].getGen() > ordenado[j].getGen()) {
                        // Trocar os Pokémon de posição para ordenar pela generation
                        Pokemon temp = ordenado[i];
                        ordenado[i] = ordenado[j];
                        ordenado[j] = temp;
                    }
                    // Se as gerações também forem iguais, desempatar por nome
                    else if (ordenado[i].getGen() == ordenado[j].getGen() &&
                             ordenado[i].getId()>ordenado[j].getId()) {
                        // Trocar os Pokémon de posição para ordenar por nome
                        Pokemon temp = ordenado[i];
                        ordenado[i] = ordenado[j];
                        ordenado[j] = temp;
                    }
                }
            }
        }
    
        // Copiando o array ordenado para o array original
        for (int i = 0; i < pokes.length; i++) {
            pokes[i] = ordenado[i];
        }
    }
    
    

/*public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;
        entrada=  sc.nextLine();
        
        ArrayList<Pokemon> pokemons= new ArrayList<>();//Array dos pokemons que serão pegos pelos ids
        
        while(!entrada.equals("FIM")){
            String id = entrada.trim();
            
            Pokemon pokemon = new Pokemon();
            pokemon.read(id);
            pokemons.add(pokemon);
            entrada = sc.nextLine();
        }
        sc.close();

        Pokemon []pokes = new Pokemon[pokemons.size()];

        int i=0;
        for(Pokemon pokemon : pokemons){
            pokes[i] = pokemon;
            i++;
        }

        countingSort(pokes);

        for(i=0;i<pokes.length;i++){
            pokes[i].print();
        }

    }*/
}

//Classe da Questão 13 do Tp02:
 class Q13 {

    public static void mergeSort(Pokemon[] array) {
        if (array.length > 1) {
            int mid = array.length / 2;
    
            // Dividindo o array em duas metades
            Pokemon[] a1 = new Pokemon[mid];
            Pokemon[] a2 = new Pokemon[array.length - mid];
    
            // Copiando os elementos para os arrays temporários
            for (int i = 0; i < mid; i++) {
                a1[i] = array[i];
            }
            for (int i = mid; i < array.length; i++) {
                a2[i - mid] = array[i];
            }
    
            // Recursão para ordenar as duas metades
            mergeSort(a1);
            mergeSort(a2);
    
            // Intercalando os arrays ordenados
            intercalar(array, a1, a2);
        }
    }

    public static void intercalar(Pokemon[] array, Pokemon[] a1, Pokemon[] a2) {
        int n1 = a1.length;
        int n2 = a2.length;
    
        int i = 0, j = 0, k = 0;
    
        // Comparando os elementos e intercalando
        while (i < n1 && j < n2) {
            // Comparação pelo primeiro tipo
            int comparison = a1[i].getTypes().get(0).compareTo(a2[j].getTypes().get(0));
    
            if (comparison == 0) {
                // Se o primeiro tipo é igual, comparar pelo nome
                comparison = a1[i].getName().compareTo(a2[j].getName());
            }
    
            if (comparison == 0 && a1[i].getTypes().size() > 1 && a2[j].getTypes().size() > 1) {
                // Se o nome também é igual, comparar pelo segundo tipo, se disponível
                comparison = a1[i].getTypes().get(1).compareTo(a2[j].getTypes().get(1));
            }
    
            if (comparison <= 0) {
                array[k] = a1[i];
                i++;
            } else {
                array[k] = a2[j];
                j++;
            }
            k++;
        }
    
        // Copiar os elementos restantes de a1, se houver
        while (i < n1) {
            array[k] = a1[i];
            i++;
            k++;
        }
    
        // Copiar os elementos restantes de a2, se houver
        while (j < n2) {
            array[k] = a2[j];
            j++;
            k++;
        }
    }
    

   /* public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;
        entrada=  sc.nextLine();
        
        ArrayList<Pokemon> pokemons= new ArrayList<>();//Array dos pokemons que serão pegos pelos ids
        
        while(!entrada.equals("FIM")){
            String id = entrada.trim();
            
            Pokemon pokemon = new Pokemon();
            pokemon.read(id);
            pokemons.add(pokemon);
            entrada = sc.nextLine();
        }
        sc.close();

        Pokemon []pokes = new Pokemon[pokemons.size()];

        int i=0;
        for(Pokemon pokemon : pokemons){
            pokes[i] = pokemon;
            i++;
        }

        mergeSort(pokes);

        for(i=0;i<pokes.length;i++){
            pokes[i].print();
        }

    }*/
}

//Classe da Questão 15 do Tp02:
 class Q15 {

    public static void selectionSortParcial(Pokemon[] pokes) {
        int fim = pokes.length;
        int count = 0;
    
        for (int i = 0; i < fim; i++) {
            for (int j = i + 1; j < fim; j++) {
                // Comparação para ordenar pelo nome do Pokemon
                if (pokes[i].getName().compareTo(pokes[j].getName()) > 0) {
                    Pokemon tmp = pokes[j];
                    pokes[j] = pokes[i];
                    pokes[i] = tmp;
                }
            }
            count++;
            if(count == 10){
                break;
            }
        }
    }

      /*public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;
        entrada=  sc.nextLine();
        
        ArrayList<Pokemon> pokemons= new ArrayList<>();//Array dos pokemons que serão pegos pelos ids
        
        while(!entrada.equals("FIM")){
            String id = entrada.trim();
            
            Pokemon pokemon = new Pokemon();
            pokemon.read(id);
            pokemons.add(pokemon);
            entrada = sc.nextLine();
        }

        Pokemon []pokes = new Pokemon[pokemons.size()];

        int i=0;
        for(Pokemon pokemon : pokemons){
            pokes[i] = pokemon;
            i++;
        }

        selectionSortParcial(pokes);

        for(i=0;i<10;i++){
            pokes[i].print();
        }

        sc.close();
    } */
}

 class Q18 {

    public static void swap(Pokemon[] pokes, int i, int j) {
        Pokemon tmp = pokes[i];
        pokes[i] = pokes[j];
        pokes[j] = tmp;
    }
    

    public static int compararPokemon(Pokemon p1, Pokemon p2) {
        // Comparação pelo gen
        int result = Integer.compare(p1.getGen(), p2.getGen());
        
        // Se os gens forem iguais, compara pelo nome
        if (result == 0) {
            result = p1.getName().compareTo(p2.getName());
        }
        
        return result;
    }
    

    public static void quickSortParcial(Pokemon[] pokes, int esq, int dir) {
        int i = esq, j = dir;
        Pokemon pivo = pokes[(dir + esq) / 2];
    
        while (i <= j) {
            // Use o comparador para comparar os pokemons
            while (compararPokemon(pokes[i], pivo) < 0) i++;
            while (compararPokemon(pokes[j], pivo) > 0) j--;
            
            if (i <= j) {
                swap(pokes, i, j);  // Troca correta dos Pokémons
                i++;
                j--;
            }
        }
    
        if (esq < j) quickSortParcial(pokes, esq, j);
        if (i < 10 && i < dir) quickSortParcial(pokes, i, dir);
    }
    
    /*  public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;
        entrada=  sc.nextLine();
        
        ArrayList<Pokemon> pokemons= new ArrayList<>();//Array dos pokemons que serão pegos pelos ids
        
        while(!entrada.equals("FIM")){
            String id = entrada.trim();
            
            Pokemon pokemon = new Pokemon();
            pokemon.read(id);
            pokemons.add(pokemon);
            entrada = sc.nextLine();
        }

        Pokemon []pokes = new Pokemon[pokemons.size()];

        int i=0;
        for(Pokemon pokemon : pokemons){
            pokes[i] = pokemon;
            i++;
        }

        quickSortParcial(pokes,0,pokes.length-1);

        for(i=0;i<10;i++){
            pokes[i].print();
        }

        sc.close();
    } */
}

 class Q1{

    public static class pokeList{
        Pokemon [] lista;
        int n;

        public pokeList(){
            this(13);//Só por que 13 é galo!!!
        }

        public pokeList(int i){
            this.lista = new Pokemon [i];
            this.n = 0;
        }

        public void mostrar(){
            for(int i=0;i<this.n;i++){
                System.out.print("[" + i + "] ");
                lista[i].print();
            }
        }

        void inserirInicio(Pokemon pokemon) throws Exception {
            if (this.n >= this.lista.length) {
                throw new Exception("Full List");
            }

            for (int i = this.n; i > 0; i--) {
                this.lista[i] = this.lista[i - 1];
            }
            this.lista[0] = pokemon;
            this.n++;
        }


        void inserirFim(Pokemon pokemon) throws Exception {
            if (this.n >= this.lista.length) {
                throw new Exception("Full List");
            }

            this.lista[n] = pokemon;
            n++;
        }

        void inserir(Pokemon pokemon, int pos) throws Exception {
            if (this.n >= this.lista.length || pos < 0 || pos > this.n) {
                throw new Exception("Invalid Position");
            }

            for (int i = this.n; i > pos; i--) {
                this.lista[i] = this.lista[i - 1];
            }
            this.lista[pos] = pokemon;
            this.n++;
        }

        Pokemon removerInicio() throws Exception {
            if (this.n == 0) {
                throw new Exception("Empty List");
            }

            Pokemon pokemon = this.lista[0];
            this.n--;
            for (int i = 0; i < this.n; i++) {
                this.lista[i] = this.lista[i + 1];
            }
            return pokemon;
        }

        Pokemon removerFim()throws Exception{
            if(this.n==0){
                throw new Exception("Lista Vazia");
            }

            return this.lista[--this.n];
        }

        Pokemon remover(int pos) throws Exception {
            if (this.n == 0 || pos < 0 || pos >= this.n) {
                throw new Exception("Invalid Position");
            }

            Pokemon pokemon = this.lista[pos];
            this.n--;

            for (int i = pos; i < this.n; i++) {
                this.lista[i] = this.lista[i + 1];
            }
            return pokemon;
        }

        public void operar(String operacao, Pokemon objeto,int pos){
            try {
                switch (operacao) {
                    case "II":
                        this.inserirInicio(objeto);
                        break;
                    case "I*":
                        this.inserir(objeto, pos);
                        break;
                    case "IF":
                        this.inserirFim(objeto);
                        break;
                    case "RI":
                        System.out.println("(R) " + this.removerInicio().getName());
                        break;
                    case "R*":
                        System.out.println("(R) " + this.remover(pos).getName());
                        break;
                    case "RF":
                        System.out.println("(R) " + this.removerFim().getName());
                        break;
                    default:
                        System.out.println("Operação inválida: " + operacao);
                }
            } catch (Exception e) {
                System.out.println("Falha ao executar " + operacao + ": " + e.getMessage());
            }
            
        }
    }

}

//Classe da questão 09 do Tp 03:
class Tp03Q09{
   public static class CelulaDupla{
        Pokemon elemento;
        boolean isFirst;
        CelulaDupla next,prev;//encadeando Duplamente
    
        public CelulaDupla(){
            this(null);
        }
    
        public CelulaDupla(Pokemon elemento){
            this.elemento = elemento;
            this.prev = this.next = null;
        }
    }

   public static class PilhaDupla {
        CelulaDupla topo,base;
    
        public PilhaDupla(){
            this.base = new CelulaDupla(); // nó cabeça
            this.topo = new CelulaDupla(); // nó cabeça
            this.base.next = this.topo;
            this.topo.prev = this.base;
        }
    
    
        public void push(Pokemon x){
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
     
    
            tmp = null;
        }
    
    
    
        public Pokemon pop() throws Exception {
            if(this.topo == this.base ){
                throw new Exception ("Erro ao remover!");
            }
    
            Pokemon resp = this.topo.prev.elemento; 
            
            CelulaDupla tmp = this.topo.prev;
            this.topo.prev = tmp.prev;
            tmp.prev.next = this.topo;
            tmp.next = null;
            tmp.prev = null; 
            tmp = null; 
    
            return resp;                        
        }
    
    
        public void show(){  
            System.out.print("[ ");
            for(CelulaDupla i = topo.prev; i!=base; i=i.prev){
                System.out.print(i.elemento + " ");
            }
            System.out.println("] ");
        }
    
        public void showElement(Pokemon x){
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

        public void operar(String operacao, Pokemon objeto,int pos){
            try {
                switch (operacao) {
                    case "I":
                        this.push(objeto);
                        break;
                    case "R":
                        System.out.println("(R) " + this.pop().getName());
                        break;
                    default:
                        System.out.println("Operação inválida: " + operacao);
                }
            } catch (Exception e) {
                System.out.println("Falha ao executar " + operacao + ": " + e.getMessage());
            }
            
        }

        public void mostrar(){
            int j=0;
            for(CelulaDupla i = this.base.next;i!=this.topo;i=i.next){
                System.out.print("[" + j + "] ");
                i.elemento.print();
                j++;
            }
        }
    }
 }
    
 class Tp03Q10 {

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
    public static class ListNode<T>{
        //membros do acesso de pacote; List pode acessar esses diretamente
        T data;                 //dados para este nó
        ListNode<T> nextNode;   //referência para o próximo nó da lista
    
    
        //Construtor da classe ListNode vazio, chama o construtor que recebe objeto, com valor nulo 
        public ListNode(){
            this(null);
        }
    
        //Construtor da classe ListNode que cria um ListNode que referencia um objeto, ao chamar o construtor que recebe objeto e próximo nó, com valor do nextNode nulo
        public ListNode(T object){
            this(object,null);
        }
    
        //Construtor da classe ListNode que referencia um objeto especificado, e o próximo ListNode
        public ListNode(T object, ListNode<T> node){
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
    public static class myList<T>{
    
        public ListNode<T> firstNode; // Primeiro nó da classe List
        public ListNode<T> lastNode;  // Último nó da classe List
        private String listName;       // Nome|Palavra como "Lista", ou "LinkedList" a ser usada na impressão
    
        //Construtor vazio que inicializa o nome como Linked List
        public  myList(){
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
                System.out.println("Erro"); 
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
                System.out.println("Erro");
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


    // Classes ListNode, myList e EmptyListException como descritas anteriormente

     // Classes ListNode, myList e EmptyListException como descritas anteriormente

    // Método de QuickSort para a lista encadeada
    public static <T> myList<T> quickSort(myList<T> list, java.util.Comparator<T> comparator) {
        // Base da recursão: lista vazia ou com apenas 1 elemento já está ordenada
        if (list.isEmpty() || list.firstNode == list.lastNode) {
            return list;
        }

        // Escolher o pivô (usaremos o primeiro nó)
        T pivot = list.firstNode.getData();

        // Listas para armazenar elementos menores, iguais e maiores que o pivô
        myList<T> less = new myList<>();
        myList<T> equal = new myList<>();
        myList<T> greater = new myList<>();

        // Particionar a lista original
        ListNode<T> current = list.firstNode;
        while (current != null) {
            int comparison = comparator.compare(current.getData(), pivot);
            if (comparison < 0) {
                less.insertAtBack(current.getData());
            } else if (comparison == 0) {
                equal.insertAtBack(current.getData());
            } else {
                greater.insertAtBack(current.getData());
            }
            current = current.getNext();
        }

        // Recursivamente ordenar as sublistas
        less = quickSort(less, comparator);
        greater = quickSort(greater, comparator);

        // Combinar as listas: less + equal + greater
        return concatenate(less, equal, greater);
    }

    // Método para concatenar três listas
    private static <T> myList<T> concatenate(myList<T> less, myList<T> equal, myList<T> greater) {
        myList<T> result = new myList<>();
        
        // Adicionar elementos da lista less
        ListNode<T> current = less.firstNode;
        while (current != null) {
            result.insertAtBack(current.getData());
            current = current.getNext();
        }

        // Adicionar elementos da lista equal
        current = equal.firstNode;
        while (current != null) {
            result.insertAtBack(current.getData());
            current = current.getNext();
        }

        // Adicionar elementos da lista greater
        current = greater.firstNode;
        while (current != null) {
            result.insertAtBack(current.getData());
            current = current.getNext();
        }

        return result;
    }

    /*public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        myList<Pokemon> pokemonList = new myList<>();
        
        String entrada = sc.nextLine();
        while (!entrada.equals("FIM")) {
            Pokemon pokemon = new Pokemon();
            pokemon.read(entrada.trim());
            pokemonList.insertAtBack(pokemon);
            entrada = sc.nextLine();
        }

        // Ordenar a lista usando QuickSort
        pokemonList = quickSort(pokemonList, (p1, p2) -> {
            // Comparação pelo gen
            int result = Integer.compare(p1.getGen(), p2.getGen());
            if (result == 0) {
                result = p1.getName().compareTo(p2.getName());
            }
            return result;
        });

        // Imprimir todos os Pokémons ordenados
        ListNode<Pokemon> current = pokemonList.firstNode;
        while (current != null) {
            current.getData().print();
            current = current.getNext();
        }

        sc.close();
    }*/
}

 class Tp04Q01{
    public static class No{
        public Pokemon elemento;
        public No esq, dir;

        public No(Pokemon elemento){
            this(elemento,null,null);
        }

        public No(Pokemon elemento, No esq, No dir){
            this.elemento = elemento;
            this.esq = esq;
            this.dir = dir;
        }
    }

    public static class ArvoreBinaria{
        private No raiz;

        public ArvoreBinaria(){
            raiz = null;
        }

        public boolean pesquisarName(String x){
            return pesquisarName(x,raiz);
        }

        private boolean pesquisarName(String x, No i){
            boolean resp;
            if(i==null){
                resp = false;
            }else if(x.equals(i.elemento.getName())){
                resp = true;
            }else if(x.compareTo(i.elemento.getName())<0){
                System.out.print("esq ");
                resp = pesquisarName(x,i.esq);
            }else{
                System.out.print("dir ");
                resp = pesquisarName(x,i.dir);
            }
            return resp;
        }

        public void caminharCentral(){
            System.out.println("[");
            caminharCentral(raiz);
            System.out.println("]");
        }

        private void caminharCentral(No i){
            if(i!=null){
                caminharCentral(i.esq);
                System.out.println(i.elemento + " ");
                caminharCentral(i.dir);
            }
        }

        public void inserir(Pokemon x) throws Exception{
            raiz = inserir(x,raiz);
        }

        private No inserir(Pokemon x, No i) throws Exception{
            if(i==null){
                i = new No(x);
            }else if(x.getName().compareTo(i.elemento.getName())<0){
                i.esq = inserir(x,i.esq);
            }else if(x.getName().compareTo(i.elemento.getName())>0){
                i.dir = inserir(x,i.dir);
            }else{
                throw new Exception("Erro ao inserir!");
            }

            return i;
        }
    }
}

public class Tp04Q04 {

   static class NoAN{
        public boolean cor;
        public Pokemon elemento;
        public NoAN esq, dir;

        public NoAN(){
            this(null);
        }

        public NoAN(Pokemon elemento){
            this(elemento,false,null,null);
        }

        public NoAN(Pokemon elemento, boolean cor){
            this(elemento,cor,null,null);
        }

        public NoAN(Pokemon elemento, boolean cor,NoAN esq, NoAN dir){
            this.cor = cor;
            this.elemento = elemento;
            this.esq = esq;
            this.dir = dir;
        }
    }

    static class Alvinegra{
        private NoAN raiz;

        public Alvinegra(){
            this.raiz = null;
        }

        public void inserir(Pokemon elemento) throws Exception{
            //Se a árvore estiver vazia
            if(this.raiz==null){
                raiz = new NoAN(elemento);
                //Se a árvore tiver um elemento

            }else if(this.raiz.esq == null && this.raiz.dir == null){
                if(elemento.getName().compareTo(this.raiz.elemento.getName())<0){
                    this.raiz.esq = new NoAN(elemento);
                }else{
                    raiz.dir = new NoAN(elemento);
                }

                //Senão, se a árvore tiver dois elementos(raiz e dir)
            }else if(this.raiz.esq == null){
                if(elemento.getName().compareTo(this.raiz.elemento.getName())<0){
                    raiz.esq = new NoAN(elemento);
                }else if(elemento.getName().compareTo(this.raiz.dir.elemento.getName())<0){
                    raiz.esq = new NoAN(this.raiz.elemento);
                    this.raiz.elemento =elemento;
                }else{
                    this.raiz.esq = new NoAN(this.raiz.elemento);
                    this.raiz.elemento = this.raiz.dir.elemento;
                    this.raiz.dir.elemento = elemento;
                }
                this.raiz.esq.cor = this.raiz.dir.cor = false;

                //Senão, se a árvore tiver dois elementos(raiz e esq)
            }else if(this.raiz.dir == null){
                if(elemento.getName().compareTo(this.raiz.elemento.getName())>0){
                    this.raiz.dir = new NoAN(elemento);
                }else if(elemento.getName().compareTo(this.raiz.esq.elemento.getName())>0){
                    this.raiz.dir = new NoAN(this.raiz.elemento);
                    this.raiz.elemento = elemento;
                }else{
                    this.raiz.dir = new NoAN(this.raiz.elemento);
                    this.raiz.elemento = this.raiz.esq.elemento;
                    this.raiz.esq.elemento = elemento;
                }
                this.raiz.esq.cor = this.raiz.dir.cor = false;

                //Senão, a árvore tem três ou mais elementos
            }else{
                inserir(elemento,null,null,null,this.raiz);
            }
            this.raiz.cor = false;
        }

        private void balancear(NoAN bisavo, NoAN avo, NoAN pai, NoAN i){
            //Se o pai também é preto, reequilibrar a árvore, rotacionando o alvo
            if(pai.cor ==true){
                if(pai.elemento.getName().compareTo(avo.elemento.getName())>0){
                    if(i.elemento.getName().compareTo(pai.elemento.getName())>0){
                        avo = rotacaoEsq(avo);
                    }else{
                        avo = rotacaoDirEsq(avo);
                    }
                }else{//Rotação Direita ou Esquerda-Direita
                    if(i.elemento.getName().compareTo(pai.elemento.getName())<0){
                        avo = rotacaoDir(avo);
                    }else{
                        avo = rotacaoEsqDir(avo);
                    }
                }

                if(bisavo==null){
                    raiz = avo;
                }else if(avo.elemento.getName().compareTo(bisavo.elemento.getName())<0){
                    bisavo.esq = avo;
                }else{
                    bisavo.dir = avo;
                }
                //reestabeleceer as cores apos a rotação
                avo.cor = false;
                avo.esq.cor = avo.dir.cor =  true;
            }
        }

        private void inserir(Pokemon elemento,NoAN bisavo, NoAN avo, NoAN pai, NoAN i)throws Exception{
            if(i==null){
                if(elemento.getName().compareTo(pai.elemento.getName())<0){
                    i = pai.esq = new NoAN(elemento,true);
                }else{
                    i = pai.dir = new NoAN(elemento, true);
                }
                if(pai.cor == true){
                    balancear(bisavo,avo,pai,i);
                }
            }else{
                //Achou um 4-No: é preciso fragmentá-lo e reequilibrar a árvore
                if(i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true){
                    i.cor = true;
                    i.esq.cor = i.dir.cor = false;
                    if(i==raiz){
                        i.cor = false;
                    }else if(pai.cor == true){
                        balancear(bisavo,avo,pai,i);
                    }
                }
                if(elemento.getName().compareTo(i.elemento.getName())<0){
                    inserir(elemento,avo,pai,i,i.esq);
                }else if(elemento.getName().compareTo(i.elemento.getName())>0){
                    inserir(elemento,avo,pai,i,i.dir);
                }else{
                    throw new Exception("Erro ao inserir(elemento repetido)!");
                }
            }
        }

        private NoAN rotacaoDir(NoAN no){
            NoAN noEsq = no.esq;
            NoAN noEsqDir = noEsq.dir;

            noEsq.dir = no;
            no.esq = noEsqDir;

            return noEsq;
        }

        private NoAN rotacaoEsq(NoAN no) {
            NoAN noDir = no.dir;
            NoAN noDirEsq = noDir.esq;
      
            noDir.esq = no;
            no.dir = noDirEsq;
            return noDir;
         }
      
         private NoAN rotacaoDirEsq(NoAN no) {
            no.dir = rotacaoDir(no.dir);
            return rotacaoEsq(no);
         }
      
         private NoAN rotacaoEsqDir(NoAN no) {
            no.esq = rotacaoEsq(no.esq);
            return rotacaoDir(no);
         }

        public boolean pesquisar(String elementoAPesquisar){
            return pesquisar(elementoAPesquisar, this.raiz);
        }

        private boolean pesquisar(String pesquisado, NoAN i) {
            boolean resp;
            if (i == null) {
                resp = false;
            } else if (pesquisado.equals(i.elemento.getName())) {
                resp = true;
            } else if (pesquisado.compareTo(i.elemento.getName()) < 0) {
                System.out.print("esq ");
                resp = pesquisar(pesquisado, i.esq);
            } else {
                System.out.print("dir ");
                resp = pesquisar(pesquisado, i.dir);
            }
            return resp;
        }
        
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;
        entrada=  sc.nextLine();
            
        Alvinegra pokemons= new Alvinegra();//Array dos pokemons que serão pegos pelos ids
        
        while(!entrada.equals("FIM")){
            String id = entrada.trim();
                
            Pokemon pokemon = new Pokemon();
            pokemon.read(id);
            try{
                pokemons.inserir(pokemon);
            }catch(Exception e){
                System.out.println(e);
            }
            entrada = sc.nextLine();
        }
        
        ArrayList<String> pesquisados = new ArrayList<>();
        
        
        
        
        pesquisados.add(entrada = sc.nextLine());
        
        while(!entrada.equals("FIM")){
            System.out.println(pesquisados.get(0));
            System.out.print("raiz ");
            if(pokemons.pesquisar(pesquisados.get(0)) == true){
                System.out.println("SIM");
            }else{
                System.out.println("NAO");
            }
            pesquisados.remove(pesquisados.get(0));
            pesquisados.add(entrada = sc.nextLine());
        }
        
        sc.close();
    
            
    }
}
