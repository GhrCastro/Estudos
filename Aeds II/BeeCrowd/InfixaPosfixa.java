import java.util.*;

public class InfixaPosfixa{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());

        for(int i=0;i<n;i++){
            String infixa = sc.nextLine().trim();
            System.out.println(converterParaPosfixa(infixa));
        }
        sc.close();
    }

    public static String converterParaPosfixa(String infixa){
        StringBuilder posfixa = new StringBuilder();
        Stack<Character> operadores = new Stack<>();

        for(int i=0;i<infixa.length();i++){//For que percorre cada caracter da infixa
            char c = infixa.charAt(i);//variável a armazenar cada caracter da infixa

            if(Character.isLetterOrDigit(c)){//se for letra ou digito, coloca na nova string
                posfixa.append(c);
            }

            else if(c == '('){//se for parentese abrindo, empilha na pilha de execução de operadores
                operadores.push(c);
            }

            else if(c == ')'){//se for fechando parentese, começa a desempilhar os operadores
                while (!operadores.isEmpty() && operadores.peek() != '(') {
                    posfixa.append(operadores.pop());
                }
                operadores.pop();//Remove o ( da pilha já que ele não irá para a nova string
            }

            else if(isOperator(c)){
                while(!operadores.isEmpty() && precedencia(operadores.peek()) >= precedencia(c)){
                    if(c == '^' && operadores.peek() =='^'){
                        break;//Se o primero operador da pilha, assim como o caracter atual forem '^', quebra o loop, já que o operadore '^' tem associatividade pela direita
                    }
                    posfixa.append(operadores.pop());
                }
                operadores.push(c);//Coloca na pilha o operador de c
            }
        }

        while(!operadores.isEmpty()){//Loop para retirar os últimos operadores e adicioná-los à nova string
            posfixa.append(operadores.pop());
        }

        return posfixa.toString();
    }

    public static boolean isOperator(char c){
        return c == '+' || c == '-' || c=='*' || c=='/' || c=='^';
    }

    public static int precedencia (char operador){
        switch (operador){
            case '^':
                return 3;//Maior precedência sendo de exponencial
            case '*': case'/':
                return 2;
            case '+': case '-':
                return 1;
            default:
                return -1;
        }
    }
}