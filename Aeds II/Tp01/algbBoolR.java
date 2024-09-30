import java.util.Scanner;

public class algbBoolR {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numbers;

        do {
            numbers = sc.nextInt();
            if (numbers == 0)
                break;

            int[] vals = new int[numbers];
            for (int j = 0; j < numbers; j++) {
                vals[j] = sc.nextInt();
            }

            String exp = sc.nextLine().trim();
            exp = replaceValues(exp, vals);
            exp = solveExpression(exp);

            System.out.println(exp);
        } while (numbers != 0);

        sc.close();
    }

    // Resolve a expressão booleana de forma recursiva
    public static String solveExpression(String exp) {
        // Se a expressão contém apenas um caractere, retorna o próprio caractere
        if (exp.length() == 1) {
            return exp;
        }

        // Encontra o índice do operador mais à direita
        int lastIndex = findLastOperatorIndex(exp);

        // Se não houver operadores, a expressão é um valor booleano
        if (lastIndex == -1) {
            return exp;
        }

        // Avalia o operador e chama a função recursivamente
        char operator = exp.charAt(lastIndex);
        String leftOperand = exp.substring(0, lastIndex).trim();
        String rightOperand = exp.substring(lastIndex + 1).trim();

        // Avalia o operador e chama a função recursivamente
        if (operator == 'N') {
            return solveNot(solveExpression(rightOperand));
        } else if (operator == 'A') {
            return solveAnd(solveExpression(leftOperand), solveExpression(rightOperand));
        } else if (operator == 'O') {
            return solveOr(solveExpression(leftOperand), solveExpression(rightOperand));
        }

        return null; // Retorna nulo se a expressão não for válida
    }

    // Resolve operação NOT
    public static String solveNot(String s) {
        if (s.equals("1")) {
            return "0";
        } else {
            return "1";
        }
    }

    // Resolve operação AND
    public static String solveAnd(String a, String b) {
        if (a.equals("1") && b.equals("1")) {
            return "1";
        } else {
            return "0";
        }
    }

    // Resolve operação OR
    public static String solveOr(String a, String b) {
        if (a.equals("1") || b.equals("1")) {
            return "1";
        } else {
            return "0";
        }
    }

    // Encontra o índice do operador mais à direita na expressão
    private static int findLastOperatorIndex(String exp) {
        int notIndex = exp.lastIndexOf('N');
        int andIndex = exp.lastIndexOf('A');
        int orIndex = exp.lastIndexOf('O');

        return Math.max(notIndex, Math.max(andIndex, orIndex));
    }

    // Substitui as variáveis pelos seus valores na expressão
    // Substitui as variáveis pelos seus valores na expressão
private static String replaceValues(String exp, int[] vals) {
    for (int i = 0; i < vals.length; i++) {
        String variable = Character.toString((char) ('A' + i)); // Converte o índice para o caractere correspondente ('A', 'B', 'C', ...)
        exp = exp.replaceAll(variable, i < vals.length ? String.valueOf(vals[i]) : "0");
    }
    return exp;
}

}
