import java.util.*;
public class Fila {
    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);

        int n;
        n=sc.nextInt();
        ArrayList<Integer> ids = new ArrayList<>();
        for(int i=0;i<n;i++){
            ids.add(sc.nextInt());
        }

        int m;
        m=sc.nextInt();

        ArrayList<Integer> idsDesistencia = new ArrayList<>();
        for(int i=0;i<m;i++){
            idsDesistencia.add(sc.nextInt());
        }

        
       for (int i = 0; i < idsDesistencia.size(); i++) {
            ids.remove(idsDesistencia.get(i));  // Remove diretamente o ID, se existir
        }

        for (int i = 0; i < ids.size(); i++) {
            if (i > 0 && i<ids.size()) System.out.print(" ");  // Evitar espaço extra no início
            System.out.print(ids.get(i));
        }
        System.out.println();
        sc.close();
    }
}
