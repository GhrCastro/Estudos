import java.util.Scanner;

public class AlgebraBoole {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String expression;

        do {
            expression = sc.nextLine();

            if (!expression.equals("0")) {
                if (evaluateExpression(expression)) {
                    System.out.println('1');
                } else {
                    System.out.println("0");
                }
            }

        } while (!expression.equals("0"));
        sc.close();
    }

    public static boolean evaluateExpression(String expression) {
        int entryNum = expression.charAt(0) - '0';
        boolean[] entryVal = new boolean[entryNum];

        // Parse the input expression to get the entry values
        int j = 0;
        for (int i = 2; i < 2 * (entryNum + 1); i += 2) {
            char val = expression.charAt(i);
            entryVal[j] = (val == '1');
            j++;
        }

        // Parse the expression to evaluate it
        int i = 2 * (entryNum + 1);
        while (i < expression.length()) {
            char op = expression.charAt(i);
            i++; // Move to the next character

            if (op == '(') {
                char type = expression.charAt(i); // Get the type of operation (not, and, or)
                i += 2; // Skip the operation type and move to the variable

                boolean result;
                switch (type) {
                    case 'n': // 'n' represents 'not'
                        char notVar = expression.charAt(i);
                        int indexNot = notVar - 'A';
                        result = !entryVal[indexNot];
                        entryVal[indexNot] = result;
                        break;
                    case 'a': // 'a' represents 'and'
                        result = true;
                        while (expression.charAt(i) != ',') {
                            char andVar = expression.charAt(i);
                            int indexAnd = andVar - 'A';
                            result = result && entryVal[indexAnd];
                            i++; // Move to the next variable
                        }
                        entryVal[expression.charAt(i + 1) - 'A'] = result;
                        break;
                    case 'o': // 'o' represents 'or'
                        result = false;
                        while (expression.charAt(i) != ',') {
                            char orVar = expression.charAt(i);
                            int indexOr = orVar - 'A';
                            result = result || entryVal[indexOr];
                            i++; // Move to the next variable
                        }
                        entryVal[expression.charAt(i + 1) - 'A'] = result;
                        break;
                }
            }

            i++; // Move to the next character
        }

        return entryVal[0];
    }
}
    