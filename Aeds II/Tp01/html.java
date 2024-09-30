import java.io.*;
import java.net.*;
import java.util.Scanner;

public class html {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String url , phrs , html;
        phrs = sc.nextLine();

        int vet[] = new int[26];

        while(!phrs.equals("FIM")){
            url = sc.nextLine();
            html = doHtml(url);
            vet = countChar(html);

            countTgs(html,vet,phrs);

            System.out.println(
                    "\u0061" + "(" + (vet[0]) + ")" + " " + "\u0065" + "(" + (vet[1]) + ")" + " " + "\u0069" + "("
                            + (vet[2]) + ")" + " " + "\u006F" + "(" + (vet[3]) + ")"
                            + " " + "\u0075" + "(" + (vet[4]) + ")" + " " + "\u00E1" + "(" + (vet[5]) + ")" + " "
                            + "\u00E9" + "(" + (vet[6])
                            + ")" + " " + "\u00ED" + "(" + (vet[7]) + ")"
                            + " " + "\u00F3" + "(" + (vet[8]) + ")" + " " + "\u00FA" + "(" + (vet[9]) + ")" + " "
                            + "\u00E0" + "(" + (vet[10]) + ")" + " " + "\u00E8" + "(" + (vet[11])
                            + ")" + " " + "\u00EC" + "(" + (vet[12]) + ")" + " " + "\u00F2" + "(" + (vet[13]) + ")"
                            + " " + "\u00F9" + "("
                            + (vet[14]) + ")" + " " + "\u00E3" + "(" + (vet[15]) + ")"
                            + " " + "\u00F5" + "(" + (vet[16]) + ")" + " " + "\u00E2" + "(" + (vet[17]) + ")" + " "
                            + "\u00EA" + "(" + (vet[18]) + ")" + " "
                            + "\u00EE" + "(" + (vet[19]) + ")"
                            + " " + "\u00F4" + "(" + (vet[20]) + ")" + " " + "\u00FB" + "(" + (vet[21]) + ")" + " "
                            + "consoante" + "("
                            + (vet[22]) + ")" + " " + "<br>"
                            + "(" + (vet[23]) + ")" + " " + "<table>" + "(" + (vet[24]) + ")" + " " + phrs);

                    phrs = sc.nextLine();
            
        }

        sc.close();
    }

    public static int[] countChar(String html){
        int val[] = new int[26];

        for(int i=0;i<html.length();i++){
            switch (html.charAt(i)) {
                case '\u0061':
                    val[0]=val[0]+1;
                    break;
                case '\u0065':
                    val[1]=val[1]+1;
                    break;
                case '\u0069':
                    val[2]=val[2]+1;
                    break;
                case '\u006F':
                    val[3]=val[3]+1;
                    break;
                case '\u0075':
                    val[4]=val[4]+1;
                    break;
                case '\u00E1':
                    val[5]=val[5]+1;
                    break;
                case '\u00E9':
                    val[6]=val[6]+1;
                    break;
                case '\u00ED':
                    val[7]=val[7]+1;
                    break;
                case '\u00F3':
                    val[8]=val[8]+1;
                    break;
                case '\u00FA':
                    val[9]=val[9]+1;
                    break;
                case '\u00E0':
                    val[10]=val[10]+1;
                    break;
                case '\u00E8':
                    val[11]=val[11]+1;
                    break;
                case '\u00EC':
                    val[12]=val[12]+1;
                    break;
                case '\u00F2':
                    val[13]=val[13]+1;
                    break;
                case '\u00F9':
                    val[14]=val[14]+1;
                    break;
                case '\u00E3':
                    val[15]=val[15]+1;
                    break;
                case '\u00F5':
                    val[16]=val[16]+1;
                    break;
                case '\u00E2':
                    val[17]=val[17]+1;
                    break;
                case '\u00EA':
                    val[18]=val[18]+1;
                    break;
                case '\u00EE':
                    val[19]=val[19]+1;
                    break;
                case '\u00F4':
                    val[20]=val[20]+1;
                    break;
                case '\u00FB':
                    val[21]=val[21]+1;
                    break;

                default:
                    break;
                }

                if(html.charAt(i)>=97 && 
                html.charAt(i)<=122 && 
                html.charAt(i)!='\u0061'&& 
                html.charAt(i)!='\u0065'&&
                html.charAt(i)!='\u0069'&&
                html.charAt(i)!='\u006F'&&
                html.charAt(i) != '\u0075'){
                    val[22]=val[22]+1;
                }
        }
        return val;
    }

    public static void countTgs(String html, int vet[], String name){

        for(int i=0;i<html.length();i++){

            if(html.charAt(i)=='<'&&
               html.charAt(i+1)=='b'&&
               html.charAt(i+2)=='r'&&
               html.charAt(i+3)=='>'){

                vet[23]=vet[23]+1;
                vet[22]=vet[22]-2;

            }else if (html.charAt(i) == '<' &&
                      html.charAt(i + 1) == 't'&&
                      html.charAt(i + 2) == 'a'&&
                      html.charAt(i + 3) == 'b'&&
                      html.charAt(i + 4) == 'l'&&
                      html.charAt(i + 5) == 'e'&&
                      html.charAt(i + 6) == '>') {

                        vet[24] = vet[24] + 1;
                        vet[22] = vet[22] - 3;
                        vet[0] = vet[0] - 1;
                        vet[1] = vet[1] - 1;

            }
        }
    }
    

    public static String doHtml(String link){
       URL url;
       InputStream InptStrm = null;
       BufferedReader bddrReader;
       String answ ="", line;

       try{
        url =new URL(link);
        InptStrm = url.openStream();
        bddrReader = new BufferedReader(new InputStreamReader(InptStrm));

        while((line = bddrReader.readLine())!=null){
            answ+=line +"\n";
        }
       }catch(MalformedURLException malformedURLException){
        malformedURLException.printStackTrace();
       }catch(IOException IOException){
        IOException.printStackTrace();
       }

       try{
        InptStrm.close();
       }catch(IOException IOException){}

       return answ;
    }
}
