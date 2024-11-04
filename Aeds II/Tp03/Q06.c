#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>

#define CSV_FILE_PATH "/tmp/pokemon.csv" //C:\Users\gugsh\Documents\GitHub\Estudos\Aeds II\tmp\pokemon.csv
#define IDNUBMERS_AND_MAXTAM_FILE 802
#define MAX_BUFFER_SIZE 1000
#define MAXTAM_LISTA 200
#define true 1
#define false 0

#define tamvet(vet) (sizeof(vet)/sizeof(vet[0]))
#define foreach(poke, collection, size) \
    pokemon *poke; \  
    for (poke= collection; poke < collection + size; poke++)


typedef struct lista{
    char lista[MAXTAM_LISTA][500];      //elementos da lista de Strings (pode ser usado para abilities ou types)
    int n;                              //Quantidade de elementos presentes atualmente na lista
}lista;

typedef struct pokemon{
    char id[4];                        //id é um int que vai de 0-800
    char generation[200];
    char name[200];                 //cada nome tem no máximo 12 caracteres
    char description[200];          //cada descrição tem no máxmio 51 caracteres
    lista *types;                   //cada tipo tem no máximo 8 caracteres
    lista *abilities;               //o campo abilities como um todo tem no máximo 81 caracteres, incluindo vírgulas e aspas e colchetes
    char weight[200];                 //peso é em kg
    char height[200];                 //altura é em m
    char capture_rate[200];              //rate é em porcentagem
    bool is_legendary;              //este boolean no csv na verdade se trata de um 0 ou 1, e não true ou false
    char capture_date[11];          //data de captura

}pokemon;

typedef struct celula{
    pokemon elemento;
    struct celula* prox;
}celula;

typedef struct poke_stack{
    celula* primeiro, *ultimo;
}poke_stack;


//Prototypes:
char **str_split(char*, const char);
char *str_substring(const char *, size_t, size_t );
char *str_trim(char *);
void replace(char *,char,char);
int comparaElementos(pokemon a, pokemon b);
lista string_to_list(char[]);
void inserir_fim(lista*, char[]);
void mostrar_lista(lista*);
pokemon construtor(char id[], char generation[], char name[], char description[], lista *types, lista *abilities, char weight[], char height[], char capture_rate[], bool is_legendary,char capture_date[]);
pokemon construtor_vazio();
void preencherVetor(pokemon pokemons[]);
char *my_strsep(char **stringp, const char *delim);
int comparar_pokemon(const void *a, const void *b);
void swap(pokemon *a, pokemon *b);
float get_weight_as_float(char *weight);
int compara_int(pokemon el1, pokemon el2);
int get_char_val(const char *ability, int pos);
int calcular_tam_max_nome(pokemon *pokes, int n);
int calcular_tam_max_ability(pokemon *pokes, int n) ;
struct tm create_date(int day, int month, int year);
int compare_dates(struct tm date1, struct tm date2);
struct tm string_to_date(const char *date_str);
void poke_start(poke_stack*);
void poke_inserir(pokemon ,poke_stack*);
char* poke_remover(poke_stack*);
void poke_mostrar(poke_stack*);
void poke_operar(char*,pokemon,poke_stack*);
celula* nova_celula(pokemon );


//Funções de uso geral-->
//Str_split apresentou como resultado problemas de memory leak no passado, se possível, evite o seu uso!
char** str_split( char *_Str, const char _Delimiter )
{
    char** _Sequence = NULL;
    size_t _Size = 0;
    if( _Str )
    {
        size_t _Len_Str = strlen( _Str );
        // Contar a quantidade de delimitadores
        char *_Temp = _Str;
        while ( *_Temp )
        {
            if ( *_Temp == _Delimiter ) {
                _Size = _Size + 1;
            } // end if
            _Temp = _Temp + 1;
        } // end while
        _Size = _Size + 1; // Adicionar espaço NULL para terminação da Lista de Strings 

        _Sequence = (char**) malloc( _Size * sizeof(char*) );

        if ( _Sequence )
        {
            size_t _Var   = 0;
            size_t _Start = 0;
            size_t _End   = 0;
            while ( _Var < _Size )
            {
                while ( _End < _Len_Str && *(_Str+_End) != _Delimiter ) { _End++; }

                *(_Sequence+_Var) = str_substring( _Str, _Start, _End-1 );

                if ( *(_Sequence+_Var) == NULL ) {
                    *(_Sequence+_Var) = "";
                } // end if
                _Start = _End + 1; 
                _End = _Start;
                _Var++;
            } // end while
            *(_Sequence+_Var) = NULL;
        } // end if
    } // end if
    return ( _Sequence );
} // end str_split ( )

char* str_substring ( const char *_Str, size_t _Start, size_t _End )
{
    char *_Sub = NULL;
    size_t _Len_Str = strlen( _Str );
    if ( _Start < _Len_Str && _End < _Len_Str  && _Start <= _End )
    {
        size_t _Len = _End - _Start + 1;
        _Sub = (char*) malloc( (_Len+1) * sizeof(char) );
        if( _Sub )
        {
            size_t j = 0;
            size_t i;
            for (  i = _Start; i <= _End; i = i + 1, j = j + 1 ) {
                *(_Sub+j) = *(_Str+i);
            } // end for
            *(_Sub+j) = '\0';
        } // end if
    } // end if
    return ( _Sub );
} // end str_substring ( )


/*Função para remover espaços em branco à esquerda e à direita de uma string*/
char* str_trim ( char *_Str )
{
    char *_Dest = NULL;
    if ( _Str )
    {
        size_t _Len   = strlen(_Str);
        size_t _Start = 0;
        size_t _End   = _Len-1;
        while ( _Start < _End && ( *(_Str+_Start) == ' ' || *(_Str+_Start) == '\n' || *(_Str+_Start) == '\r' || *(_Str+_Start) == '\t' ) )
        { _Start = _Start + 1; }
        while ( _End > _Start && ( *(_Str+_End) == ' ' || *(_Str+_End) == '\n' || *(_Str+_End) == '\r' || *(_Str+_End) == '\t' ) )
        { _End = _End - 1; }
        _Dest = str_substring( _Str, _Start, _End );
    } // end if
    return ( _Dest );
} // end str_ trim( )

/*Função para substituir ocorrências de caracteres dentro de uma mesma String*/
void replace(char *string, char searchchar, char replacechar) {
    char *valueptr = strchr(string, searchchar);  // Encontra a primeira ocorrência de searchchar
    while (valueptr != NULL) {
        // Substitui o caractere encontrado por replacechar
        *valueptr = replacechar;
        // Procura a próxima ocorrência de searchchar
        valueptr = strchr(valueptr + 1, searchchar);
    }
}

int comparaElementos(pokemon a, pokemon b){
    int Comparison = strcmp(a.id, b.id);
    if(Comparison != 0) {
        return Comparison;
    }else {
        return strcmp(a.name, b.name);
    }
    return Comparison;
} 

//<--Funções de uso geral


//Funções relacionados à Lista-->

lista string_to_list(char elementos []){
    lista novaLista;
    novaLista.n=0;

    replace(elementos,'[',' ');                  //remove colchetes
    replace(elementos,']',' ');
    replace(elementos,'\'',' ');                 //remove aspas simples

    char *token = strtok(elementos, ",");        //Divide os elementos pela vírgula e adiciona à Lista
    while(token!=NULL){
        char *trimmedToken = str_trim(token);        //Remove os espaçoes em branco ao redor do token
        inserir_fim(&novaLista,trimmedToken);    //Insere o token na lista

        token = strtok(NULL,",");                //Pega o próximo token
    }

    return novaLista;

}

/*Códigos de erro presentes: 001*/
//Exception 001: Lista cheia, ou n contando elementos a mais
void inserir_fim(lista *list, char x []){
     if (list->n == 0) {//verifica se o n de lista já foi inicializado, senão, inicializa
        list->n = 0;
    }

    if(list->n>=MAXTAM_LISTA){
        printf("!!! This code particular Exception!!! Exception 001: Erro ao inserir! Lista cheia!\n");
        exit(1);
    }

    strcpy(list->lista[list->n], x);//copia a String recebida para a última posição da lista
    list->n++;
}

//Função para imprimir cada lista, afim de não precisar formatar individualmente cada lista na hora de imprimir abilities ou types
void mostrar_lista(lista *list){
    int i;
    printf("[");

    for(i=0;i<list->n;i++){
        printf("'%s'", list->lista[i]);
        if(i + 1 < list->n){
            printf(", ");
        }
    }
    printf("]");
}

//<--Funções relacionados à Lista


//Funções relacionadas aos pokemon-->
pokemon construtor(char id[], char generation[], char name[], char description[], lista *types, lista *abilities, char weight[], char height[], char capture_rate[], bool is_legendary,char capture_date[]){
    pokemon p;

    strcpy(p.id, id);
    strcpy(p.generation, generation);
    strcpy(p.name,name);
    strcpy(p.description,description);

    p.types = types;
    p.abilities = abilities;

    strcpy(p.weight,weight);
    strcpy(p.height,height);
    strcpy(p.capture_rate,capture_rate);
    p.is_legendary = is_legendary;
    strcpy(p.capture_date,capture_date);

    return p;
}

pokemon construtor_vazio(){
    pokemon p;
    strcpy(p.id, "");
    strcpy(p.generation, "");
    strcpy(p.name,"");
    strcpy(p.description,"");

    p.types = malloc(sizeof(lista));
    p.types->n = 0;
    p.abilities = malloc(sizeof(lista));
    p.abilities->n = 0;


    strcpy(p.weight,"");
    strcpy(p.height,"");
    strcpy(p.capture_rate,"");
    p.is_legendary = false;
    strcpy(p.capture_date,"");

    return p;
}

char *my_strsep(char **stringp, const char *delim) {
    char *start = *stringp;  // Ponto de partida da string
    char *end;

    // Se a string for nula, retorna nulo
    if (start == NULL) {
        return NULL;
    }

    // Procura pelo próximo delimitador
    end = strpbrk(start, delim);
    
    if (end) {
        // Substitui o delimitador por um caractere nulo
        *end = '\0';
        *stringp = end + 1;  // Avança o ponteiro para o próximo token
    } else {
        // Se não houver mais delimitadores, aponta para NULL
        *stringp = NULL;
    }

    return start;  // Retorna o token encontrado
}

/*Códigos de erro presentes: 002*/
//Exception 002: Arquivo não encontrado
void preencherVetor(pokemon pokemons[]) {
    FILE *file = fopen(CSV_FILE_PATH, "r");
    if (!file) {
        printf("!!! This code particular Exception!!! Exception 002: Arquivo não encontrado\n");
        exit(1);
    }

    char buffer[MAX_BUFFER_SIZE];
    fgets(buffer, MAX_BUFFER_SIZE, file);  // Ignora a linha de cabeçalho

    int i = 0;
    while (fgets(buffer, MAX_BUFFER_SIZE, file)) {
        // Dividir a linha por aspas
        char *preParts[3] = {NULL, NULL, NULL};
        int prePartsCount = 0;

        char *part = strtok(buffer, "\"");
        while (part && prePartsCount < 3) {
            preParts[prePartsCount++] = part;
            part = strtok(NULL, "\"");
        }

        char novaLinha[MAX_BUFFER_SIZE];
        if (prePartsCount > 2) {
            snprintf(novaLinha, MAX_BUFFER_SIZE, "%s%s", preParts[0], preParts[2]);
        } else {
            strncpy(novaLinha, buffer, MAX_BUFFER_SIZE);
        }

        // Agora, dividimos a nova linha por vírgula
        char *parts[12] = {NULL};  // Inicializa com NULL para lidar com campos vazios
        int partsCount = 0;
        char *tmp = novaLinha;

        while ((part = my_strsep(&tmp, ",")) != NULL && partsCount < 12) {
            parts[partsCount++] = part;
        }

        if (partsCount >= 10) {
            // Preenche o objeto pokemon com os dados lidos
            pokemon p = construtor_vazio();
            strcpy(p.id, parts[0]);
            strcpy(p.generation, parts[1]);
            strcpy(p.name, parts[2]);
            strcpy(p.description, parts[3]);

            // Tipos
            lista types = string_to_list(parts[4]);
            if (parts[5] && strlen(parts[5]) > 0) {
                inserir_fim(&types, parts[5]);
            }
            *p.types = types;

            // Habilidades
            lista abilities = string_to_list(preParts[1]);
            *p.abilities = abilities;

            // Peso, altura e taxa de captura
            if (parts[7] && strlen(parts[7]) > 0) {
                strcpy(p.weight, parts[7]);
            } else {
                strcpy(p.weight, "0.0");  // Define como 0.0 se vazio
            }

            if (parts[8] && strlen(parts[8]) > 0) {
                strcpy(p.height, parts[8]);
            } else {
                strcpy(p.height, "0.0");  // Define como 0.0 se vazio
            }

            strcpy(p.capture_rate, parts[9]);

            // Lendário
            p.is_legendary = strcmp(parts[10], "1") == 0 ? true : false;

            // Data de captura
            if (partsCount >= 12 && strlen(parts[11]) > 0) {
                strcpy(p.capture_date, parts[11]);
            }

            // Adiciona o pokémon ao array
            pokemons[i++] = p;

            if (i >= IDNUBMERS_AND_MAXTAM_FILE) {
                break;  // Evita exceder o limite do vetor
            }
        }
    }

    fclose(file);
}



void imprimir(pokemon pokemon) {
    // Começo do formato: [#ID -> Nome: Descrição - [Tipos] - [Habilidades] - Peso - Altura - CapturaRate - isLegendary - Geração - Data de Captura]

    // Imprime ID, Nome, e Descrição
    printf("[#%s -> %s: %s - ", pokemon.id, pokemon.name, pokemon.description);

    // Imprime a lista de tipos
    if (pokemon.types != NULL) {
        mostrar_lista(pokemon.types);  // Chama a função que já imprime os tipos formatados
    }

    printf(" - ");

    // Imprime a lista de habilidades
    if (pokemon.abilities != NULL) {
        mostrar_lista(pokemon.abilities);  // Chama a função que já imprime as habilidades formatadas
    }

    // Imprime peso, altura, taxa de captura e geração
    printf(" - %skg - %sm - %s%% - %s - %s gen]", pokemon.weight, pokemon.height, pokemon.capture_rate,
           pokemon.is_legendary ? "true" : "false", pokemon.generation);

    char *trimmed_capture_date = str_trim(pokemon.capture_date);
    // Imprime a data de captura, se disponível
    if (strlen(trimmed_capture_date) > 0) {
        printf(" - %s", trimmed_capture_date);
    } else {
        printf(" - Data não disponível");
    }

    // Finaliza a impressão com uma nova linha
    printf("\n");

    // Finaliza a impressão com uma nova linha
}
//<--Funções relacionadas aos pokemon

// Função para trocar dois elementos no array
void swap(pokemon *a, pokemon *b) {
    pokemon temp = *a;
    *a = *b;
    *b = temp;
}

float get_weight_as_float(char *weight){
        return strtof(weight, NULL); // Converte string para float
}


//Q10->
//função comparar para a função qsort
int comparar_pokemon(const void *a, const void *b) {
    const pokemon *p1 = (const pokemon *)a;
    const pokemon *p2 = (const pokemon *)b;
    int result =  strcmp(p1->generation,p2->generation);
    if(result==0){
        result = strcmp(p1->name,p2->name);
    }
    return result;
}

//Q12->
int compara_int(pokemon el1, pokemon el2){
    int id1 = atoi(el1.id);
    int id2 = atoi(el2.id);

    return id1>id2 ? 1 : 0;
}

// Função para retornar o valor do caractere em uma posição específica da habilidade
int get_char_val(const char *ability, int pos) {
    if (pos < strlen(ability)) {
        return ability[pos];  // Retorna o valor ASCII do caractere na posição 'pos'
    } else {
        return 0;  // Retorna 0 se a posição for maior que o tamanho da string
    }
}

// Função para calcular o maior comprimento de nome
int calcular_tam_max_nome(pokemon *pokes, int n) {
    int max_len = 0,i;
    for ( i = 0; i < n; i++) {
        int len = strlen(pokes[i].name);
        if (len > max_len) {
            max_len = len;
        }
    }
    return max_len;
}

// Função para calcular o maior comprimento de habilidade
int calcular_tam_max_ability(pokemon *pokes, int n) {
    int max_len = 0,i;
    for ( i = 0; i < n; i++) {
        int len = strlen(pokes[i].abilities->lista[0]);  // Considera apenas a primeira habilidade
        if (len > max_len) {
            max_len = len;
        }
    }
    return max_len;
}

// Função para converter uma string no formato "dd/mm/yyyy" para struct tm
struct tm string_to_date(const char *date_str) {
    struct tm date = {0};  // Inicializa a estrutura tm com 0
    int day, month, year;

    // Usa sscanf para fazer o parsing da string
    sscanf(date_str, "%d/%d/%d", &day, &month, &year);
    day -=48;
    month -=48;
    year -=48;

    // Preenche os campos da estrutura tm
    date.tm_mday = day;
    date.tm_mon = month - 1;  // Mês começa em 0 (Janeiro = 0)
    date.tm_year = year - 1900;  // Anos desde 1900

    return date;
}

struct tm create_date(int day, int month, int year) {
    struct tm date = {0};  // Inicializa a estrutura tm com 0
    date.tm_mday = day;    // Define o dia
    date.tm_mon = month - 1;  // Mês, ajustado para começar em 0 (Janeiro = 0)
    date.tm_year = year - 1900;  // Anos desde 1900
    return date;
}

// Função para comparar duas datas (struct tm)
int compare_dates(struct tm date1, struct tm date2) {
    // Compara os anos
    if (date1.tm_year < date2.tm_year) {
        return -1;
    } else if (date1.tm_year > date2.tm_year) {
        return 1;
    }

    // Se os anos forem iguais, compara os meses
    if (date1.tm_mon < date2.tm_mon) {
        return -1;
    } else if (date1.tm_mon > date2.tm_mon) {
        return 1;
    }

    // Se os meses forem iguais, compara os dias
    if (date1.tm_mday < date2.tm_mday) {
        return -1;
    } else if (date1.tm_mday > date2.tm_mday) {
        return 1;
    }

    // As datas são iguais

}


void poke_start(poke_stack *lista){
    lista->primeiro = nova_celula(construtor_vazio());
    lista->ultimo = lista->primeiro;
}

celula* nova_celula(pokemon elemento){
    celula* nova = (celula*)malloc(sizeof(celula));
    nova->elemento = elemento;
    nova->prox = NULL;
    return nova;
}



void poke_inserir(pokemon pokemon, poke_stack *lista){
    celula* tmp = nova_celula(pokemon);
    tmp->prox = lista->primeiro->prox; //|Tmp|->|Tmp.prox| ~~> |Tmp|->|lista->primeiro->prox| salva o elemento logo após o nó cabeça
    lista->primeiro->prox = tmp; //Coloca o novo elemento no lugar deste
    if(lista->primeiro==lista->ultimo){
        lista->ultimo=tmp;
    }

    tmp = NULL;
}

char* poke_remover(poke_stack *lista){
    if(lista->primeiro == lista->ultimo){
        printf("Erro ao remover!");
    }

    celula* tmp = lista->primeiro;
    lista->primeiro = lista->primeiro->prox;
    char* resp = lista->primeiro->elemento.name;
    tmp->prox = NULL;
    free(tmp);
    tmp = NULL;
    return resp;
}

void poke_mostrar(poke_stack *lista){
    int j=0,k=0;
    celula* i;

    for(i=lista->primeiro->prox;i!=NULL;i=i->prox,j++);
    int tam = j;
    j=0;
    pokemon array[tam];

    for(i=lista->primeiro->prox;i!=NULL;i=i->prox,j++){
        array[j] = i->elemento;
    }

    j=0;
    for(int i=tam-1;i>=0;i--,j++){
        printf("[%d] ",j);
        imprimir(array[i]);
    }


}

void poke_operar(char* operacao, pokemon objeto, poke_stack* lista){
    if(strcmp(operacao,"I")==0){
        poke_inserir(objeto,lista);
    }else if(strcmp(operacao,"R")==0){
        printf("(R) %s\n",poke_remover(lista));
    }
    //poke_mostrar(lista);
}


int main(){
  char entrada[200];
   int result;          
   int i,j=0,count=0;
    //printf("1\n");
    pokemon pokemons[IDNUBMERS_AND_MAXTAM_FILE];
    preencherVetor(pokemons);
    
    pokemon *temp = malloc(IDNUBMERS_AND_MAXTAM_FILE*sizeof(pokemon));

    //printf("2\n");
    scanf("%s" , entrada);

    while(strcmp(entrada,"FIM")!=0){

        for(i=0; i< IDNUBMERS_AND_MAXTAM_FILE;i++){
            result = strcmp(pokemons[i].id,entrada);
            if(result == 0){
                temp[j] =  pokemons[i];
                //printf("tmp:%s pokes:%s \n",temp[j].name,pokemons[i].name);
                count++;
                j++;
            }
        }
        scanf("%s",entrada);
    }
    
    pokemon pokemons_salvos[count];
    poke_stack lista;
    poke_start(&lista);     

    for(i=0; i < count; i++){
    pokemons_salvos[i] = temp[i];
    //printf("pokes:%s tmp:%s  \n",pokemons_salvos[i].name,temp[i].name);
    }
    free(temp);


    foreach(poke , pokemons_salvos, count){
        poke_inserir(*poke,&lista);
        //printf("%d\n", lista.n);
    }

    int num_operacoes;
    scanf("%d",&num_operacoes);

   for(i = 0; i < num_operacoes; i++) {
    scanf(" %[^\n]", entrada);  // Lê a linha inteira de entrada
    char *token[3];
    token[0] = strtok(entrada, " ");
    token[1] = strtok(NULL, " ");
    token[2] = strtok(NULL, " ");

    char *operacao = token[0];
    int pos = -1;
    pokemon pokemon;

    if (token[1] != NULL) { // Para operações como "II" ou "IF"
        int id = atoi(token[1]);
        id--;
        if (id >= 0 && id < IDNUBMERS_AND_MAXTAM_FILE) {
            pokemon = pokemons[id];
            //printf("Pokemon a ser operado:%s\n\n",pokemon.name);
        } else {
            printf("Erro: ID de Pokémon inválido.\n");
            continue;
        }
    }

    // Executa a operação
    poke_operar(operacao, pokemon, &lista);
}

   poke_mostrar(&lista);


    return 0;
}