import java.util.Scanner;

public class AquecimentoRecursivo{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada;
        int count;

        entrada = sc.nextLine();
        while(!entrada.equals("FIM")){
            count =0;
            count = contaMaiusculas(entrada,entrada.length()-1, count);
            System.out.println(count);
            entrada = sc.nextLine();
        }

        sc.close();
    }


    public static int contaMaiusculas(String s, int i, int count){

        if(i<0){
            return count;
        }
        
        if(Character.isUpperCase(s.charAt(i))){
           count++;
        }

        return  contaMaiusculas(s, i-1, count);
    }

}

//Feito em conjunto por Gustavo H R Castro e João Marcos Freitas