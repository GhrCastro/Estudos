import java.io.IOException;
import java.util.Scanner;

public class passeiosCotidianos {
 
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int n,h,w;
        n= sc.nextInt();
        h=sc.nextInt();
        w=sc.nextInt();
        sc.nextLine();
        char[] linhas = new char [n*2];
        char[] saidas = new char [n*2];
        for(int i=0;i<n*2;i++){
            linhas[i] = sc.next().charAt(0);
        }
        
        for(int i=0;i<n*2;i+=2){
            if(linhas[i]=='Y' && h>0){
                saidas[i]='Y';
                h--;
                w++;
            }else if(w==0){
                saidas[i]='Y';
                h--;
                w++;
            }else{
                saidas[i]='N';
            }
            
            if(linhas[i+1]=='Y' && w>0){
                saidas[i+1]='Y';
                w--;
                h++;
            }else if(h==0){
                saidas[i+1]='Y';
                w--;
                h++;
            }else{
                saidas[i+1]='N';
            }
        }
        
        for(int i=0;i<n*2;i+=2){
            System.out.println(saidas[i] + " " + saidas[i+1]);
        }
        
        sc.close();
    }
 
}