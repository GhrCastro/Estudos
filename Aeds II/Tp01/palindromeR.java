import java.util.Scanner;
public class palindromeR {
    public static void main(String[] args){
        String str1;
        Scanner sc = new Scanner(System.in);
        do{
            str1=sc.nextLine();
            if(!str1.equals("FIM")){
                String str2 = "";
                String str3 = isPalindrome(str1,str2,str1.length());
                System.out.println(str3);
            }
        }while(!str1.equals("FIM"));

        sc.close();
    }

    public static String isPalindrome(String s1,String s2,int i){
        String valor;
        if(s1.equals(s2)){
            valor="SIM";
        }else{
            valor="NAO";
        }

        if(i==0){
            return valor;

        }

        s2+=s1.charAt(i-1);
        return isPalindrome(s1, s2, i-1);
    }
}
