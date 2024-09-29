import java.io.IOException;
import java.util.Scanner;

public class PacMan {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int N = Integer.parseInt(sc.nextLine());
        String[] tabuleiro = new String[N];

        for (int i = 0; i < N; i++) {
            tabuleiro[i] = sc.nextLine();
        }

        int k = 0;
        int maior = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int col = (i % 2 == 0) ? j : (N - 1 - j); // Varredura alternada
                char currentChar = tabuleiro[i].charAt(col);

                if (currentChar == 'o') {
                    k++;
                } else if (currentChar == 'A') {
                    k = 0; // Zera o contador de comida ao encontrar um 'A'
                }

                if (k > maior) {
                    maior = k; // Substitui maior se k for maior
                }
            }
        }

        System.out.println(maior);

        sc.close();
    }
}