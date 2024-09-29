import java.util.Scanner;

public class Sinuca {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = Integer.parseInt(sc.nextLine());
        
        int bolas[] = new int[N];

        for(int i=0;i<N;i++){
            bolas[i] = sc.nextInt();
        }

        for(int i=N-1;i>0;i--){
            for(int j=0;j<i;j++){
                if(bolas[j]==bolas[j+1]){
                    bolas[j]=-1;
                }else{
                    bolas[j]=1;
                }
            }
        }

        switch (bolas[0]) {
            case 1:
                System.out.println("branca");
                break;
            case -1:
                System.out.println("pretas");
                break;
            default:
                System.out.println("Erro");
                break;
        }


        sc.close();
    }
}
