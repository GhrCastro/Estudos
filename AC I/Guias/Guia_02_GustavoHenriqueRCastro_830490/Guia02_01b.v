/*
 Guia_0201b.v
 Gustavo Henrique Rodrigues De Castro - 830490
*/

/*
Alterei ligeiramente as entradas e saídas do programa e também adicionei um loop while com um novo contador "z", que itera sobre cada alternativa da questão, desta forma todos são exibidos de uma vez
*/

module Guia_0201b;

// define data
 real x = 0 ; // decimal
 real integerPart = 0 ;
 real power2 = 1.0; // power of 2
 integer y = 7 ; // counter
 integer z=0; //counter for each reg
 reg [7:0] a = 8'b00011000; // binary (only fraction part, Big Endian)
 reg [7:0] b = 8'b01001000; // binary (only fraction part, Big Endian)
 reg [7:0] c = 8'b10101000; // binary (only fraction part, Big Endian)
 reg [7:0] d = 8'b11101000; // binary (only fraction part, Big Endian)
 reg [7:0] e = 8'b11001000; // binary (only fraction part, Big Endian)
 reg[7:0]fraction[4:0];

// actions
 initial
 begin : main//{
 //initializing the array
 fraction[0]=a;
 fraction[1]=b;
 fraction[2]=c;
 fraction[3]=d;
 fraction[4]=e;
 $display ( "Guia_0201 - Tests" );
 $display ( "x = %f" , x );
 $display ( "a = 0.%8b", a );//showing initial value for var a
 $display ( "b = 0.%8b", b );//showing initial value for var b
 $display ( "c = 0.%8b", c );//showing initial value for var c
 $display ( "d = 1.%8b", d );//showing initial value for var d
 $display ( "e = 11.%8b", e );//showing initial value for var e

 while(z<5) begin//{
 //reseting variables for each new fraction
 y=7;   
 power2 = 1.0;
 x=0;

 //Determinando o valor inicial para a parte inteira
  integerPart = 0 ;
 if(z == 3) integerPart = 1.0;//d has integer part 1
 if(z == 4) integerPart = 3.0;//e has integer part 11

 while (y>=0) begin//{

 power2 = power2 / 2.0;

 if ( fraction[z][y] == 1 )begin//{
 x = x + power2;
 end// }
 y=y-1;

 end//   }
 //end y while

 x=x+integerPart;
 $display ( "fraction %0d to decimal: x = %f",z, x );
 z=z+1;
 end//  }
 //end z while
 end// }
 //end main

endmodule // Guia_0201