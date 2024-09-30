import java.util.Scanner;
public class algebraBooleanaNw {
      public static void main(String[] args) {
          Scanner sc = new Scanner(System.in);
          String exp = " ";
               String answr = " ";
               int numbers = 0;
               numbers = sc.nextInt();
               
               while (true){
                   if (numbers == 0) {
                      break;
                  }
                  int vals[] = new int[numbers];
                  for (int j = 0; j < numbers; j++){
                      vals[j] = sc.nextInt();
                  }
                  
                  exp = sc.nextLine();
                  exp = espaco(exp);
                  
                  if (numbers == 1){
                      exp = replcA(exp, String.valueOf(vals[0])); 
                  }
                  else if (numbers == 2){
                      exp = replcA(exp, String.valueOf(vals[0])); 
                      exp = replcB(exp, String.valueOf(vals[1])); 
                  }
                  else if (numbers == 3){
                      exp = replcA(exp, String.valueOf(vals[0])); 
                      exp = replcB(exp, String.valueOf(vals[1])); 
                      exp = replcC(exp, String.valueOf(vals[2])); 
                   }
      
                  exp = solveNot(exp);
                  
                  while (exp.length() > 1){
                       String last = lstOp(exp);
                       
                       if (last == "not"){
                          exp = solveNot(exp);
                      }
                       else if (last == "and"){
                          exp = solveAnd(exp);
                       }
                       else if (last == "or") {
                           exp = solveOr(exp);
                       }
                   }

                   answr = exp;
                   
                   System.out.println(answr); 
                   
                   numbers = sc.nextInt();
                   
                  }
                  
                  sc.close();
              }


 
     public static String espaco(String s){
        s = s.replace(" ", ""); 

        return s;
     }

    

//resolve Not
     public static String solveNot(String s){
         s = s.replace("not(0)", "1");
         s = s.replace("not(1)", "0");
         
         return s;
        }
        
      
        public static String lstOp(String s){
            String answer = " ";
        int a = s.lastIndexOf("and"); 
        int o = s.lastIndexOf("or"); 
        int n = s.lastIndexOf("not"); 
    
        if (a > o && a > n) {
            answer = "and";
        }
        else if (o > a && o > n) {
            answer = "or";
         }
         else if (n > a && n > o) {
            answer = "not";
        }
       
        
        return answer;
    } 
    
    //resolve And
    public static String solveAnd(String s){
        String and = " ";
        int a = s.lastIndexOf("and");
        int parentese = s.indexOf(")", a); 
        int resp = 1; 
        
        and = s.substring(a, parentese+1);
        
        for (int i = 0; i < and.length(); i++){
            if (and.charAt(i) == '0'){
                resp = 0;
            }
        }
        s = s.replace(and, String.valueOf(resp));
        
        return s;
    }
    
//resolve Or
    public static String solveOr(String s){
        String or = " ";
       int o = s.lastIndexOf("or"); 
       int parentese = s.indexOf(")", o);
       int resp = 0;
       
       or = s.substring(o, parentese+1); 
       
       for (int i = 0; i < or.length(); i++){
           if (or.charAt(i) == '1') {
            resp = 1;
        }
    }
    
    s = s.replace(or, String.valueOf(resp)); 
    
    return s;
    }





        
        //substitui a por seu valor
        public static String replcA(String s, String val){
            s = s.replace("A", val);
        
            return s;
         }
        
        
        
        // substitui b por seu valor
         public static String replcB(String s, String val){
            s = s.replace("B", val);
        
            return s;
         }
        
        
        
        // substitui c por seu valor
         public static String replcC(String s, String val){
            s = s.replace("C", val);
        
            return s;
         }

    }