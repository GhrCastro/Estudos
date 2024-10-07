import java.util.Scanner;

public class Guia02_extras_02c{
    public static void main(String[] args){
        int i = 0, y = 5;
        Scanner sc = new Scanner(System.in);
    
        while (sc.hasNextLine()) {
            String decimal = sc.nextLine();
            System.out.println(double2bin(decimal, i, y));
        }
    
        sc.close();
    }
    

    public static String double2bin(String decimal, int i, int y){
        decimal = decimal.trim();
        String[] parts = decimal.split("\\.");
    
        int integerPart = Integer.parseInt(parts[0]);
        double fractionalPart = Double.parseDouble("0." + parts[1]);
    
        String s = "";
    
        // Converte a parte inteira
        s += integer2bin(integerPart);
    
        // Adiciona o ponto para separar a parte inteira da fracionária
        s += ".";
    
        // Converte a parte fracionária
        s += double2bin(fractionalPart, y);
    
        return s;
    }
    

    public static String integer2bin(int integerPart){
        String s="";
        while (integerPart > 0) {
            s = (integerPart % 2) + s; // Adiciona o dígito binário à esquerda
            integerPart = integerPart / 2;
        }
        return s;
    }

    public static String double2bin(double doublePart,int precision){
        String s="";
        for (int i = 0; i < precision; i++) {
            doublePart *= 2;
            if (doublePart >= 1) {
                s += "1";
                doublePart -= 1;
            } else {
                s += "0";
            }
        }
        return s;
    }

}