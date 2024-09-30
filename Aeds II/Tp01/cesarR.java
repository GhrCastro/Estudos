import java.util.Scanner;
public class cesarR {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String palavra;
        String palavraCifrada="";
        int i;
        do{
            palavra=sc.nextLine();
            i=0;
            if(!palavra.equals("FIM")){
                palavra=cifrar(palavra,palavraCifrada,i);
                System.out.println(palavra);
            }
        }while(palavra.equals("FIM")==false);
        sc.close();

    }

    public static String cifrar(String palavra,String pC,int i){

        if(i==palavra.length()){
            return pC;
        }

        char orgChar = palavra.charAt(i);
        if(orgChar>=32 && orgChar<=126){
            char cifrado = (char)(orgChar+3);

            if(cifrado>126){
                cifrado=(char)(cifrado-95);
            }
            pC+=cifrado;
        }else{
            pC+=orgChar;
        }
            
        return cifrar(palavra,pC,i+1);


    }
}
