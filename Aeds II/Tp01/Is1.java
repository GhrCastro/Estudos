
import java.util.Scanner;

public class Is1 {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada = sc.nextLine();

        while(!entrada.equals("FIM")){
            System.out.println(isOnlyVogal(entrada) + " " + isOnlyConsoante(entrada) + " " + isOnlyInteger(entrada ) + " " + isOnlyReal(entrada) );
            entrada = sc.nextLine();
        }
        sc.close();
    }

    public static String isOnlyVogal(String s){
        String saida = "SIM";

        for(int i=0;i<s.length();i++){
            if((s.charAt(i)=='a' || s.charAt(i)=='e' || s.charAt(i)=='i' || s.charAt(i)=='o' || s.charAt(i)=='u') || (s.charAt(i)=='A' || s.charAt(i)=='E' || s.charAt(i)=='I' || s.charAt(i)=='O' || s.charAt(i)=='U')){
                saida = "SIM";
            }else{
                return "NAO";
            }
        }

        return saida;
    }

    public static String isOnlyConsoante(String s){
        String saida = "SIM";

        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='a' || s.charAt(i)=='e' || s.charAt(i)=='i' || s.charAt(i)=='o' || s.charAt(i)=='u' || s.charAt(i)=='A' || s.charAt(i)=='E' || s.charAt(i)=='I' || s.charAt(i)=='O' || s.charAt(i)=='U' || s.charAt(i)!='1'|| s.charAt(i)!='2'|| s.charAt(i)!='3'|| s.charAt(i)!='4'|| s.charAt(i)!='5'|| s.charAt(i)!='6'|| s.charAt(i)!='7'|| s.charAt(i)!='8'|| s.charAt(i)!='9'|| s.charAt(i)!='0'){
                return "NAO";
            }
        }

        return saida;
    }

    public static String isOnlyInteger(String s){
        String saida = "SIM";

        for(int i=0;i<s.length();i++){
            if(s.charAt(i)!='1'&&s.charAt(i)!='2'&&s.charAt(i)!='3'&&s.charAt(i)!='4'&&s.charAt(i)!='5'&&s.charAt(i)!='6'&&s.charAt(i)!='7'&&s.charAt(i)!='8'&&s.charAt(i)!='9'&&s.charAt(i)!='0'){
                return "NAO";
            }
        }

        return saida;
    }

    public static String isOnlyReal(String s){
        String saida = "NAO"; 
        int count = 0;

        for(int i=0;i<s.length();i++){

            if(s.charAt(i)!='1'&&s.charAt(i)!='2'&&s.charAt(i)!='3'&&s.charAt(i)!='4'&&s.charAt(i)!='5'&&s.charAt(i)!='6'&&s.charAt(i)!='7'&&s.charAt(i)!='8'&&s.charAt(i)!='9'&&s.charAt(i)!='0'){//se for diferente de 1 a 0, entra no if

                if(s.charAt(i)!='.' && s.charAt(i)!= ','){//se for diferente de . e , significa que não é algo que simbolize reais
                    return "NAO";
                }else{//se for um ponto ou virgula, significa que pode se tratar de um número real
                    count++;
                }

                if(count<=1){//se o contador exceder 1, indica que não é um número real 
                    saida = "SIM";
                }else{
                    return "NAO";
                }
            }else{
                saida  ="SIM";
            }
        }

        return saida;
    }

}
