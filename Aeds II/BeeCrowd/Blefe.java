import java.util.*;
public class Blefe {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n,m;
        n = sc.nextInt();
        m = sc.nextInt();

        ArrayList <Integer> a = new ArrayList<>();

        for(int i=0;i<n;i++){
            a.add(sc.nextInt());
        }

        ArrayList <Integer> b = new ArrayList<>();

        for(int i=0;i<m;i++){
            b.add(sc.nextInt());
        }

        ArrayList <Integer> sums = new ArrayList<>();

        for(int i=0;i<m;i++){
            for(int j=i;j<m;j++){
                sums.add(b.get(i) + b.get(j));
            }
        }

        int []count = new int[m];

        for(int i=0;i<m;i++){
            count[i]=0;
        }

        for(int i=0;i<sums.size();i++){
           for(int j=0;j<b.size();j++){
            for(int k=0;k<a.size();k++){
                if(b.get(j)==a.get(k)||b.get(j)==sums.get(i)){
                    count[j]++;
                }
            }
           }
        }

        int status=0;
        for(int i=0;i<b.size();i++){
            if(count[i]==0){
                System.out.println(b.get(i));
                status = -1;
                break;
            }
        }

        if(status==0){
            System.out.println("sim");
        }
        sc.close();
    }
}
