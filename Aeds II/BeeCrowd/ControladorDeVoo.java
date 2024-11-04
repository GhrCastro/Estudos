import java.util.*;


public class ControladorDeVoo{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<String> ladoOeste = new ArrayList<>();
        List<String> ladoLeste = new ArrayList<>();
        List<String> ladoNorte = new ArrayList<>();
        List<String> ladoSul = new ArrayList<>();

        String entrada = sc.nextLine();
        int prioridade = Integer.parseInt(entrada);

        while(prioridade!=0){
            entrada = sc.nextLine();
            
            if(entrada.equals("0")){
                break;
            }else if(entrada.startsWith("A")){
                switch (prioridade){
                    case -1:
                        ladoOeste.add(entrada);
                        break;
                    case -4:
                        ladoLeste.add(entrada);
                        break;
                    case -2:
                        ladoSul.add(entrada);
                        break;
                    case -3:
                        ladoNorte.add(entrada);
                        break;
                }
            }else{
                prioridade = Integer.parseInt(entrada);
            }
        }

        sc.close();

        List<String> filaFinal = new ArrayList<>();


        int tamanhoNorte = ladoNorte.size();
        int tamanhoOeste = ladoOeste.size();
        int tamanhoLeste = ladoLeste.size();
        int tamanhoSul = ladoSul.size();
        int maxTamLO =  Math.max(tamanhoOeste,tamanhoLeste);
        int maxTamSN = Math.max(tamanhoNorte,tamanhoSul);
        int maxTam =  Math.max(maxTamLO,maxTamSN);

        for(int i=0;i< maxTam; i++){
            if(i<tamanhoOeste){
                filaFinal.add(ladoOeste.get(i));
            }

            if(i<tamanhoNorte){
                filaFinal.add(ladoNorte.get(i));
            }

            if(i<tamanhoSul){
                filaFinal.add(ladoSul.get(i));
            }

            if(i<tamanhoLeste){
                filaFinal.add(ladoLeste.get(i));
            }
        }
        for(String aviao : filaFinal){
            System.out.print(aviao + " ");
        }
        System.out.println();
    }
}