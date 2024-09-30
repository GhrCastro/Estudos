
import java.util.*;
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
 class Q09 {
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

public class Q13 {

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
    

    public static void main(String[] args) {
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

    }
}

