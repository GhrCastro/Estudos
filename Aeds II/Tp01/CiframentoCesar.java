
import java.util.Scanner;


public class CiframentoCesar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str;
    
        do{
            str = sc.nextLine();
            
            if(!str.equals("FIM")){
                str = cifra(str);
                System.out.println(str);
            }

        }while(!str.equals("FIM"));

        sc.close();
    }

    public static String cifra(String str){
        String strCifrada="";//cria uma string para  a palavra a ser cifrada

        for(int i=0;i<str.length();i++){//inicializa um for a se percorrer toda a string original a fim de cifra-la

            char orgChar = str.charAt(i);//seleciona um caracter por iteração de dentro da string original

            if(orgChar>=32 && orgChar<=126){//somente se o char original estiver entre 32 e 126 (númeração de caracteres da tabela ascii)
                char cifrado = (char)(orgChar+3);//cria-se um novo char, que terá como valor atribuído o valor do caracter original, mais 3 posições na tabela ascii

                if(cifrado>126){

                    cifrado=(char)(cifrado-95);//se após esta mudança o caracter em questão tiver extrapolado os 126 digitos da ascii, subtrai-se dele 95 posições, tornando a tabela uma ciclo, e fazendo-o voltar ao "próximo" caracter, no começo

                }
                strCifrada+=cifrado;//concatenação do caracter cifrado à palavra

            }else{
                strCifrada += orgChar;//caso a palavra não esteja na tabela ascii, é impossível cifra-la desta forma, portanto, apenas a acrescento novamente
            }
        }

        return strCifrada;//retorno a string cifrada
    }
   
}
