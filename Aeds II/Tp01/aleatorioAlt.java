import java.util.Scanner;
import java.util.Random;





public class aleatorioAlt {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String palavra;
        Random gerador = new Random();
        gerador.setSeed(4);
        int valid=0;
        
       do{
        palavra=sc.nextLine();
        if(palavra.equals("FIM")){
            valid=1;
        }else{
            palavra=randomCreator(palavra,gerador);
            System.out.println(palavra);
        }
        
       }while(valid!=1);
        sc.close();
    }
    
    public static String randomCreator(String palavra, Random gerador){
    
        String palavraNova="";

        char letra = (char)('a'+(Math.abs(gerador.nextInt())%26));
        char novaLetra = (char)('a' + (Math.abs(gerador.nextInt()) % 26));

        for(int i = 0; i < palavra.length(); i++){
            if(palavra.charAt(i)==letra){ // Verifica se é uma letra
                palavraNova += novaLetra;
            } else {
                palavraNova += palavra.charAt(i);
            }
        }
    
        return palavraNova;
    }

    
    
}
