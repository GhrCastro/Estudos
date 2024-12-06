import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Pokemon {
    private int id;
    private int generation;
    private String name;
    private String description;
    private String[] types;
    private String[] abilities;
    private Double weight;
    private Double height;
    private int captureRate;
    private Boolean isLegendary;
    private String captureDate;

    //construtores
    public Pokemon(int id, int generation, String name, String description, String[] types, String[] abilities,
     Double weight, Double height, int captureRate, Boolean isLegendary, String captureDate) {
        this.id = id;
        this.generation = generation;
        this.name = name;
        this.description = description;
        this.types = types;
        this.abilities = abilities;
        this.weight = weight;
        this.height = height;
        this.captureRate = captureRate;
        this.isLegendary = isLegendary;
        this.captureDate = captureDate;
    }

    public Pokemon() {
        this.id = 0;
        this.generation = 0;
        this.name = "";
        this.description = "";
        this.types = new String[0];
        this.abilities = new String[0];
        this.weight = 0.0;
        this.height = 0.0;
        this.captureRate = 0;
        this.isLegendary = false;
        this.captureDate = "";
    }

    //gets
    public int getId() {
        return id;
    }
    public int getGeneration() {
        return generation;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String[] getTypes() {
        return types;
    }
    public String[] getAbilities() {
        return abilities;
    }
    public Double getWeight() {
        return weight;
    }
    public Double getHeight() {
        return height;
    }
    public int getCaptureRate() {
        return captureRate;
    }
    public Boolean getIsLengendary() {
        return isLegendary;
    }
    public String getCaptureDate() {
        return captureDate;
    }

    //sets
    public void setId(int id) {
        this.id = id;
    }
    public void setGeneration(int generation) {
        this.generation = generation;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setTypes(String[] types) {
        this.types = types;
    }
    public void setAbilities(String[] abilities) {
        this.abilities = abilities;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    public void setHeight(Double height) {
        this.height = height;
    }
    public void setCaptureRate(int captureRate) {
        this.captureRate = captureRate;
    }
    public void setIsLegendary(Boolean isLegendary) {
        this.isLegendary = isLegendary;
    }
    public void setCaptureDate(String capturDate) {
        this.captureDate = capturDate;
    }

    //clone
    public Pokemon clone() {
        return new Pokemon(this.id, this.generation, this.name, this.description, 
                           this.types.clone(), this.abilities.clone(), 
                           this.weight, this.height, this.captureRate, 
                           this.isLegendary, this.captureDate);
    }

    //imprimir
    public void imprimir(Pokemon pokemon) {
        System.out.print("[#" + pokemon.getId() + " -> " + pokemon.getName() + ": " + pokemon.getDescription() + " - ");
    
        // Imprimir tipos
        String[] tipos = pokemon.getTypes();
        String tiposFormatados = Arrays.toString(tipos).replace("[", "").replace("]", "");
        System.out.print("[" + tiposFormatados + "] - ");
    
        // Imprimir habilidades
        String[] habilidades = pokemon.getAbilities();
        String habilidadesFormatadas = Arrays.toString(habilidades).replace("[", "").replace("]", "");
        System.out.print("[" + habilidadesFormatadas + "] - ");
    
        // Imprimir outros atributos
        System.out.print(pokemon.getWeight() + "kg - " + pokemon.getHeight() + "m - " +
                         pokemon.getCaptureRate() + "% - " + pokemon.getIsLengendary() + 
                         " - " + pokemon.getGeneration() + " gen] - " + pokemon.getCaptureDate());
        System.out.println();
    }

    //ler
    public void ler() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID do pokemon: ");
        this.id = scanner.nextInt();
        System.out.println("Geração do pokemon: ");
        this.generation = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Nome do pokemon: ");
        this.name = scanner.nextLine();
        System.out.println("Descrição do pokemon: ");
        this.description = scanner.nextLine();
        System.out.println("Tipo(s) do pokemon: ");
        this.types = scanner.nextLine().split(",");
        System.out.println("Abilidades do pokemon: ");
        this.abilities = scanner.nextLine().split(",");
        System.out.println("Peso do pokemon: ");
        this.weight = scanner.nextDouble();
        System.out.println("Tamanho do pokemon: ");
        this.height = scanner.nextDouble();
        System.out.println("Chance de captura: ");
        this.captureRate = scanner.nextInt();
        System.out.println("É um pokemon Lendario? ");
        this.isLegendary = scanner.nextBoolean();
        System.out.println("Data de captura: ");
        this.captureDate = scanner.nextLine();
        scanner.close();
    }
}

class NoPrimario {
    public int chave; // captureRate % 15
    public NoPrimario esq, dir;
    public ArvoreBinariaSecundaria arvoreSecundaria; // Aponta para uma árvore de nomes

    public NoPrimario(int chave) {
        this.chave = chave;
        this.esq = this.dir = null;
        this.arvoreSecundaria = new ArvoreBinariaSecundaria();
    }
}

class NoSecundario {
    public String chave; // Nome do Pokémon
    public NoSecundario esq, dir;
    public Pokemon pokemon;

    public NoSecundario(Pokemon pokemon) {
        this.pokemon = pokemon;
        this.chave = pokemon.getName();
        this.esq = this.dir = null;
    }
}

class ArvoreBinariaSecundaria {
    private NoSecundario raiz;

    public ArvoreBinariaSecundaria() {
        this.raiz = null;
    }

    public void inserir(Pokemon pokemon) throws Exception {
        raiz = inserir(pokemon, raiz);
    }

    private NoSecundario inserir(Pokemon pokemon, NoSecundario no) throws Exception {
        if (no == null) {
            no = new NoSecundario(pokemon);
        } else if (pokemon.getName().compareTo(no.chave) < 0) {
            no.esq = inserir(pokemon, no.esq);
        } else if (pokemon.getName().compareTo(no.chave) > 0) {
            no.dir = inserir(pokemon, no.dir);
        } else {
            throw new Exception("Erro ao inserir: Nome duplicado!");
        }
        return no;
    }

    public boolean pesquisar(Pokemon pokemon) {
        return pesquisar(pokemon.getName(), raiz);
    }

    private boolean pesquisar(String nome, NoSecundario no) {
        if (no == null) {
            return true;
        } else if(nome.compareTo(no.chave)>0){
            System.out.print("dir ");
            return pesquisar(nome, no.dir);
        } else if (nome.compareTo(no.chave) < 0) {
            System.out.print("esq ");
            return pesquisar(nome, no.esq);
        } else {
            return false;
        }
    }

    public void mostrar() {
        mostrar(raiz);
    }

    private void mostrar(NoSecundario no) {
        if (no != null) {
            mostrar(no.esq);
            System.out.println(no.chave);
            mostrar(no.dir);
        }
    }
}

class ArvoreBinariaPrimaria {
    private NoPrimario raiz;

    public ArvoreBinariaPrimaria() {
        this.raiz = null;
    }

    public void construirArvoreInicial() {
        int[] chaves = {7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14};
        for (int chave : chaves) {
            try {
                inserir(chave);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void inserir(int chave) throws Exception {
        raiz = inserir(chave, raiz);
    }

    private NoPrimario inserir(int chave, NoPrimario no) throws Exception {
        if (no == null) {
            no = new NoPrimario(chave);
        } else if (chave < no.chave) {
            no.esq = inserir(chave, no.esq);
        } else if (chave > no.chave) {
            no.dir = inserir(chave, no.dir);
        } else {
            throw new Exception("Erro ao inserir: Chave duplicada!");
        }
        return no;
    }

    public void inserirPokemon(Pokemon pokemon) throws Exception {
        int chave = pokemon.getCaptureRate() % 15;
        NoPrimario no = pesquisarNo(chave, raiz);
        if (no == null) {
            throw new Exception("Erro: Nó principal com chave " + chave + " não encontrado!");
        }
        no.arvoreSecundaria.inserir(pokemon);
    }

    private NoPrimario pesquisarNo(int chave, NoPrimario no) {
        if (no == null) {
            return null;
        } else if (chave == no.chave) {
            return no;
        } else if (chave < no.chave) {
            return pesquisarNo(chave, no.esq);
        } else {
            return pesquisarNo(chave, no.dir);
        }
    }

    public boolean pesquisar(Pokemon pokemon, int chave) {
        System.out.print("raiz ");
        return pesquisar(pokemon, chave, raiz);
    }

    private boolean pesquisar(Pokemon pokemon, int chave, NoPrimario i) {
        boolean resp = true;
        if(i != null) {
            if (resp) {
                resp = i.arvoreSecundaria.pesquisar(pokemon);
            }
            
            if (resp) {
                System.out.print("ESQ ");
                resp = pesquisar(pokemon, chave, i.esq);
            }
            
            
            if(resp){
                System.out.print("DIR ");
                resp = pesquisar(pokemon, chave, i.dir);
            }
        }
        return resp;
    }

    public void mostrar() {
        mostrar(raiz);
    }

    private void mostrar(NoPrimario no) {
        if (no != null) {
            mostrar(no.esq);
            System.out.println("Chave: " + no.chave);
            no.arvoreSecundaria.mostrar();
            mostrar(no.dir);
        }
    }
}

public class Tp04Q02 {
    public static Pokemon parsePokemon(String[] parts) {
        try {
            int id = Integer.parseInt(parts[0].trim());
            int gen = Integer.parseInt(parts[1].trim());
            String name = parts[2].trim();
            String description = parts[3].trim();
            String type1 = parts[4].trim();
            String type2 = parts[5].trim();
            String[] types = type2.isEmpty() ? new String[]{"'" + type1 + "'"} : new String[]{"'" + type1 + "'", "'" + type2 + "'"};
            String habilidadesString = parts[6].replace(".", ",").replace("[", "").replace("]", "").trim();
            String[] habilidades = habilidadesString.split(",");  
            Double peso = parts[7].trim().equals("0") ? 0.0 : Double.parseDouble(parts[7].trim());
            Double altura = parts[8].trim().equals("0") ? 0.0 : Double.parseDouble(parts[8].trim());
            int captura = Integer.parseInt(parts[9].trim());
            Boolean lendario = parts[10].trim().equals("1");
            String data = parts[11].trim();
    
            return new Pokemon(id, gen, name, description, types, habilidades, peso, altura, captura, lendario, data);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Erro ao processar o Pokémon com dados: " + Arrays.toString(parts));
            e.printStackTrace();
            return null;
        }
    }

    public static void lerNomes(List<String> nomesPokemons, Scanner scanner) {
        String nome;
        do{
            nome = scanner.nextLine();
            if(!nome.equals("FIM")) {
                nomesPokemons.add(nome);
            }
        }while(!(nome.equals("FIM")));
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        ArvoreBinariaPrimaria arvore = new ArvoreBinariaPrimaria();
        arvore.construirArvoreInicial();
        
        List<String> requestedIds = new ArrayList<>();
        List<Pokemon> listaPokemon = new ArrayList<>();



        while (true) {
            String input = scanner.nextLine();

            if(input.equals("FIM")) break;

            requestedIds.add(input);
        }

        try{
            BufferedReader reader = new BufferedReader(new FileReader("/tmp/pokemon.csv"));

            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                line = line.replace("\",,,", "\", , ,");
                line = line.replace(",,", ", ,");
                line = line.replace("\"", "");
                line = line.replace(", ", ".");
                line = line.replace(",", ";");
                line = line.replace(".;[", "[; ;");
                line = line.replace("]..;", "];0.0;0.0;");
                line = line.replace("].; ;", "];0.0;0.0;");

                String[] parts = line.split(";");

                Pokemon novoPokemon = parsePokemon(parts);
                listaPokemon.add(novoPokemon);
            }
            reader.close();
        } catch(IOException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }

        for (int i = 0; i < requestedIds.size(); i++) {
            for (Pokemon j : listaPokemon) {
                if (Integer.parseInt(requestedIds.get(i)) == j.getId()) {
                    try {
                        arvore.inserirPokemon(j);
                    } catch (Exception e) {
                        System.err.println("Erro ao inserir Pokémon na árvore: " + e.getMessage());
                    }
                }
            }
        }

        List<String> nomesPokemons = new ArrayList<>();
        lerNomes(nomesPokemons, scanner);

        for (String nome : nomesPokemons) {
            System.out.println("=> " + nome);
            boolean encontrado = false;
            try {
                Pokemon pokemon = null;
                for(Pokemon i : listaPokemon) {
                    if(i.getName().equals(nome)) pokemon = i;
                }
                if(pokemon != null) encontrado = arvore.pesquisar(pokemon, (pokemon.getCaptureRate() % 15));
            } catch (Exception e) {
                System.err.println("Erro durante a pesquisa: " + e.getMessage());
            }
            System.out.println(encontrado ? "NAO" : "SIM");
        }
    }
}