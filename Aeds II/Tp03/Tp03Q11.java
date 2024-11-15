import java.util.Scanner;

public class Tp03Q11 {

    public static class CelulaMatriz {
        public int elemento;
        public CelulaMatriz inf, sup, esq, dir;

        public CelulaMatriz() {
            this(0);
        }

        public CelulaMatriz(int elemento) {
            this(elemento, null, null, null, null);
        }

        public CelulaMatriz(int elemento, CelulaMatriz inf, CelulaMatriz sup, CelulaMatriz esq, CelulaMatriz dir) {
            this.elemento = elemento;
            this.inf = inf;
            this.sup = sup;
            this.esq = esq;
            this.dir = dir;
        }
    }

    public static class Matriz {
        private CelulaMatriz inicio;
        private int linha, coluna;

        public Matriz() {
            this(3, 3);
        }

        public Matriz(int linha, int coluna) {
            this.linha = linha;
            this.coluna = coluna;

            if (linha > 0 && coluna > 0) {
                this.inicio = new CelulaMatriz();
                CelulaMatriz atual = inicio;
                CelulaMatriz linhaAnterior = null;
                CelulaMatriz colunaInicial = inicio;

                for (int i = 0; i < linha; i++) {
                    for (int j = 1; j < coluna; j++) {
                        CelulaMatriz novaCelula = new CelulaMatriz();
                        atual.dir = novaCelula;
                        novaCelula.esq = atual;
                        atual = novaCelula;

                        if (linhaAnterior != null) {
                            atual.sup = linhaAnterior.dir;
                            linhaAnterior.dir.inf = atual;
                            linhaAnterior = linhaAnterior.dir;
                        }
                    }

                    if (i < linha - 1) {
                        CelulaMatriz novaLinha = new CelulaMatriz();
                        colunaInicial.inf = novaLinha;
                        novaLinha.sup = colunaInicial;
                        linhaAnterior = colunaInicial;
                        colunaInicial = novaLinha;
                        atual = novaLinha;
                    }
                }
            }
        }

        public Matriz soma(Matriz matriz) {
            if (this.linha != matriz.linha || this.coluna != matriz.coluna) {
                System.out.println("As matrizes não têm as mesmas dimensões para soma.");
                return null;
            }

            Matriz result = new Matriz(this.linha, this.coluna);
            for (CelulaMatriz i = result.inicio, a1 = this.inicio, b1 = matriz.inicio; i != null; i = i.inf, a1 = a1.inf, b1 = b1.inf) {
                for (CelulaMatriz j = i, a = a1, b = b1; j != null; j = j.dir, a = a.dir, b = b.dir) {
                    j.elemento = a.elemento + b.elemento;
                }
            }
            return result;
        }

        public Matriz multiplicacao(Matriz matriz) {
            if (this.coluna != matriz.linha) {
                System.out.println("Dimensões incompatíveis para multiplicação.");
                return null;
            }

            Matriz result = new Matriz(this.linha, matriz.coluna);
            for (CelulaMatriz i = result.inicio, a1 = this.inicio; i != null; i = i.inf, a1 = a1.inf) {
                for (CelulaMatriz j = i, b1 = matriz.inicio; j != null; j = j.dir, b1 = b1.dir) {
                    int soma = 0;
                    CelulaMatriz a = a1;
                    CelulaMatriz b = b1;
                    while (a != null && b != null) {
                        soma += a.elemento * b.elemento;
                        a = a.dir;
                        b = b.inf;
                    }
                    j.elemento = soma;
                }
            }
            return result;
        }

        public void mostrarDiagonalPrincipal() {
            for (CelulaMatriz i = this.inicio; i != null; i = i.inf != null ? i.inf.dir : null) {
                System.out.print(i.elemento + " ");
            }
            System.out.println();
        }

        public void mostrarDiagonalSecundaria() {
            CelulaMatriz i = this.inicio;
            while (i.dir != null) {
                i = i.dir;
            }
            for (CelulaMatriz j = i; j != null; j = j.inf != null ? j.inf.esq : null) {
                System.out.print(j.elemento + " ");
            }
            System.out.println();
        }

        public void mostrar() {
            for (CelulaMatriz i = this.inicio; i != null; i = i.inf) {
                for (CelulaMatriz j = i; j != null; j = j.dir) {
                    System.out.print(j.elemento + " ");
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int casosDeTeste = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < casosDeTeste; i++) {
            int m1 = Integer.parseInt(sc.nextLine());
            int m2 = Integer.parseInt(sc.nextLine());

            Matriz mat1 = new Matriz(m1, m2);
            for (CelulaMatriz j = mat1.inicio; j != null; j = j.inf) {
                for (CelulaMatriz k = j; k != null; k = k.dir) {
                    if (sc.hasNextInt()) {
                        k.elemento = sc.nextInt();
                    }
                }
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
            }

            m1 = Integer.parseInt(sc.nextLine());
            m2 = Integer.parseInt(sc.nextLine());

            Matriz mat2 = new Matriz(m1, m2);
            for (CelulaMatriz j = mat2.inicio; j != null; j = j.inf) {
                for (CelulaMatriz k = j; k != null; k = k.dir) {
                    if (sc.hasNextInt()) {
                        k.elemento = sc.nextInt();
                    }
                }
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
            }

            mat1.mostrarDiagonalPrincipal();
            mat1.mostrarDiagonalSecundaria();

            Matriz soma = mat1.soma(mat2);
            if (soma != null) {
                soma.mostrar();
            }

            Matriz multiplicacao = mat1.multiplicacao(mat2);
            if (multiplicacao != null) {
                multiplicacao.mostrar();
            }
        }
        sc.close();
    }
}
