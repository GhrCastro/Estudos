import java.util.Scanner;

public class vinteTres {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // número de cartas comuns/rodadas do jogo
        int[] joao = new int[2];
        int[] maria = new int[2];
        int valorM, valorJ;
        int[] comuns = new int[n];
        
        // Contador de cartas (1 a 13), posição 0 será ignorada
        int[] contaNumeros = new int[14];

        // Coleta das cartas de João
        for (int i = 0; i < 2; i++) {
            joao[i] = sc.nextInt();
            contaNumeros[joao[i]]++;
        }

        // Coleta das cartas de Maria
        for (int i = 0; i < 2; i++) {
            maria[i] = sc.nextInt();
            contaNumeros[maria[i]]++;
        }

        // Coleta das cartas comuns
        for (int i = 0; i < n; i++) {
            comuns[i] = sc.nextInt();
            contaNumeros[comuns[i]]++;
        }

        // Calcula a pontuação atual de João e Maria
        valorM = contaCartas(maria, comuns, n);
        valorJ = contaCartas(joao, comuns, n);
        
        int mariaPrecisa = 23 - valorM; // Quanto falta para Maria atingir 23

        // Verificar se a carta que Maria precisa está disponível e não foi usada 4 vezes
        if (mariaPrecisa > 0 && mariaPrecisa <= 13 && contaNumeros[mariaPrecisa] < 4) {
            // Verificar se Maria venceria (somaria <= 23 e maior que João)
            if (valorJ + valorCarta(mariaPrecisa) > 23 || valorM + valorCarta(mariaPrecisa) > valorJ) {
                System.out.println(mariaPrecisa);  // Maria pode ganhar
            } else {
                System.out.println(-1); // Maria não pode ganhar
            }
        } else {
            // Se a carta que Maria precisa não existe ou já foi usada 4 vezes
            System.out.println(-1);
        }

        sc.close();
    }

    // Função para retornar o valor da carta
    public static int valorCarta(int carta) {
        if (carta >= 11 && carta <= 13) {
            return 10; // Valetes, Damas e Reis valem 10
        }
        return carta;
    }

    // Função para calcular o valor total das cartas
    public static int contaCartas(int[] pessoa, int[] cartas, int n) {
        int valor = 0;
        for (int i = 0; i < 2; i++) {
            valor += valorCarta(pessoa[i]); // Soma das cartas do jogador
        }
        for (int i = 0; i < n; i++) {
            valor += valorCarta(cartas[i]); // Soma das cartas comuns
        }
        return valor;
    }
}
