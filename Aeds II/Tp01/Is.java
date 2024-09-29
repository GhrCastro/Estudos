
import java.util.Scanner;

public class Is {
    
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
        return isOnlyVogal( s, s.length(),saida);
    }

    public static String isOnlyVogal(String s,int i,String saida){
        if(i==0){
            return saida="SIM";
        }
            if((s.charAt(i-1)=='a' || s.charAt(i-1)=='e' || s.charAt(i-1)=='i' || s.charAt(i-1)=='o' || s.charAt(i-1)=='u') || (s.charAt(i-1)=='A' || s.charAt(i-1)=='E' || s.charAt(i-1)=='I' || s.charAt(i-1)=='O' || s.charAt(i-1)=='U')){
                return isOnlyVogal(s,i-1,saida);
            }else{
                return "NAO";
            }
    }

    public static String isOnlyConsoante(String s){
        String saida = "SIM";
        return isOnlyConsoante(s,s.length(),saida);
    }

    public static String isOnlyConsoante(String s,int i,String saida){
        if(i==0){
            return saida="SIM";
        }

        if ((s.charAt(i - 1) != 'a' && s.charAt(i - 1) != 'e' && s.charAt(i - 1) != 'i' && s.charAt(i - 1) != 'o'
                && s.charAt(i - 1) != 'u') && (s.charAt(i - 1) != '1' && s.charAt(i - 1) != '2'
                        && s.charAt(i - 1) != '3' && s.charAt(i - 1) != '4' && s.charAt(i - 1) != '5'
                        && s.charAt(i - 1) != '6' && s.charAt(i - 1) != '7' && s.charAt(i - 1) != '8'
                        && s.charAt(i - 1) != '9' && s.charAt(i - 1) != '0')) {
            return isOnlyConsoante(s,i-1,saida);
        }
        
       return saida="NAO";
    }

    public static String isOnlyInteger(String s){
        String saida = "SIM";
        return isOnlyInteger(s,s.length(),saida);
    }

    public static String isOnlyInteger(String s,int i,String saida){
        if(i==0){
            return saida="SIM";
        }

        char currentChar = s.charAt(i - 1);
    
        if (currentChar == '.' || currentChar == ',') {
            return saida="NAO";
        }
    
        if (currentChar < '0' || currentChar > '9') {
            return saida="NAO";
        }
        
        return isOnlyInteger(s,i-1,saida);
    }

    public static String isOnlyReal(String s){
        String saida="NAO";
        int count = 0;
        return isOnlyReal(s,s.length(),saida,count);
    }

    public static String isOnlyReal(String s,int i,String saida,int count){ 
               // Condição de parada: Quando todas as letras foram verificadas
               if (i == 0) {
                if(count<=1){
                    return saida="SIM"; // Verifica se há no máximo um ponto ou vírgula

                }else{
                    return saida="NAO";
                }
            }
    
            // Verifica se o caractere atual é um dígito ou ponto/vírgula
            if ((s.charAt(i - 1) >= '0' && s.charAt(i - 1) <= '9') || s.charAt(i - 1) == '.' || s.charAt(i - 1) == ',') {
                // Se for um dígito ou ponto/vírgula, chama recursivamente para o próximo caractere
                if (s.charAt(i - 1) == '.' || s.charAt(i - 1) == ',') {
                    count++; // Incrementa o contador se encontrar um ponto/vírgula
                }
                return isOnlyReal(s, i - 1,saida, count);
            } else {
                // Se o caractere atual não for um dígito ou ponto/vírgula, retorna false imediatamente
                return saida="NAO";
            }
        }
    
    }

