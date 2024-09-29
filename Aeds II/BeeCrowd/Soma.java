import java.io.IOException;
import java.util.*;
/**
 * IMPORTANT: 
 *      O nome da classe deve ser "Main" para que a sua solução execute
 *      Class name must be "Main" for your solution to execute
 *      El nombre de la clase debe ser "Main" para que su solución ejecutar
 */
public class Soma
 {
 
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int N,K;
        N = sc.nextInt();
        K = sc.nextInt();
        ArrayList<Integer> x = new ArrayList<>();

        for(int i=0;i<N;i++){
            x.add(sc.nextInt());
        }

        int count=0;
        while(!x.isEmpty()){
            int contaKs = 0;

            for(int i=0;i<x.size();i++){
                if(x.get(i)==K){
                    count++;
                    x.remove(i);
                    i--;
                }else if(contaKs+x.get(i)<=K){
                    contaKs+= x.get(i);
                }else{
                    count++;
                    x.remove(i);
                    i--;
                }
            }
            count++;
            x.clear();
        }
        System.out.println(count);
        sc.close();
    }
    //return 0;
}