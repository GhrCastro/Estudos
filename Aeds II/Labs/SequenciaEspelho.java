package Labs;



import java.util.Scanner;

public class SequenciaEspelho {
    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        String numS;

        while(sc.hasNextLine()){
            numS = sc.nextLine();
            String[] parts = numS.split(" ");
            String sequencia = "";

            for(int i=Integer.parseInt(parts[0]); i<=Integer.parseInt(parts[1]);i++){
                sequencia += i;
            }

            //System.out.println(sequencia);

            String espelhada = espelhar(sequencia);
            System.out.println(espelhada);



        }
        sc.close();
    }

    static String espelhar (String s){
        String espelhado = "";
        
        for(int i=0;i<s.length();i++){
            espelhado += s.charAt(i);
        }

        for(int i=s.length()-1;i>=0;i--){
            espelhado += s.charAt(i);
        }


        return espelhado;
        
    }
}
