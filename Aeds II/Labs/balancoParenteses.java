package Labs;
import java.util.Scanner;
public class balancoParenteses {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str= sc.nextLine();
        
        while(!str.equals("FIM")){
            int count = 0,countE=0;
            
            for(int i=0;i<str.length();i++){

                if(str.charAt(i)=='('){
                    count++;
                }else if(str.charAt(i)==')' && count>0){
                    count--;
                }else if(str.charAt(i)==')'){
                    countE++;
                }
            }

            if(count==0 && countE==0){
                System.out.println("correto");
            }else{
                System.out.println("incorreto");
            }
            str = sc.nextLine();
        }


        sc.close();
    }


}
