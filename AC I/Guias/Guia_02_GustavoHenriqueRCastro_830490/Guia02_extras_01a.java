import java.util.Scanner;

public class Guia02_extras_01a{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        

            while (sc.hasNextLine()) {
                String binaryString = sc.nextLine();
                System.out.println(bin2double(binaryString));
            }

        sc.close();
    }

    public static double bin2double(String s){
        //remove espaços
        s = s.trim();
        //Separa a parte inteira da fracionária
        String[] parts = s.split("\\.");
        if(parts.length>2){
            throw new IllegalArgumentException("!!Inválido!!: O número binário possui mais de um ponto decimal, apenas números com no máximo um ponto são permitidos!");
        }

        String parteInteira = parts[0];
        String parteFracionaria = (parts.length == 2) ? parts[1] : "";

        //convertendo
        //parte inteira binária para decimal
        double decimalInteiro = binaryToDecimal(parteInteira);
        //parte fracionária binária para decimal
        double decimalFracionario = binaryFractionToDecimal(parteFracionaria);

        //concatenando novamente as partes
        return decimalInteiro + decimalFracionario;
    }
    
    public static double binaryToDecimal(String binary){
        double decimal = 0;
        int power = 0;

        //começa a partir do fim da string e vai para o inicío
        for(int i = binary.length()-1;i>=0;i--){
            if(binary.charAt(i)=='1'){
                decimal += Math.pow(2,power);
            }
            power++;
        }
        return decimal;
    }

    public static double binaryFractionToDecimal(String binaryFraction){
        double decimal = 0;
        double divisor = 2.0;

        //converte para decimal
        for(char c : binaryFraction.toCharArray()){
            if(c=='1'){
                decimal+=1 / divisor;
            }
            divisor*=2;
        }
        return decimal;
    }
}