import java.util.Scanner;

public class AquecimentoIterativo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;
        int count;

        entrada = sc.nextLine();
        while(!entrada.equals("FIM")){
            count=0;

            for(int i=entrada.length()-1;i>=0;i--){
                if(Character.isUpperCase(entrada.charAt(i))){
                    count++;
                }
            }

            System.out.println(count);
            entrada = sc.nextLine();
        }

        sc.close();
    }
}

//Feito em conjunto por Gustavo H R Castro e João Marcos Freitas