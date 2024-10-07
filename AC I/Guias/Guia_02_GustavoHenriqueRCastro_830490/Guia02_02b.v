/*
 Guia_0201g.v
 Gustavo Henrique Rodrigues De Castro - 830490
*/

module Guia_0202b;
// define data
 real a = 0.375; // decimal
 real b = 2.125; // decimal
 real c = 3.625; // decimal
 real d = 5.03125; // decimal
 real e = 6.75; // decimal
 integer y; // counter for fractional part
 integer i; // counter for integer part
 integer c1;//loop counter
 reg [5:0] r_int; // binary result for integer part
 reg [5:0] r_frac; //binary result for fractionary part
 real fraction[4:0];
 real fraction_part;//temporary for storing fractional part
 integer integer_part;//temporary for storing integer part

// actions
 initial

begin : main
 fraction[0]=a;
 fraction[1]=b;
 fraction[2]=c;
 fraction[3]=d;
 fraction[4]=e;
 $display ( "guia_0202b" );
 $display ( "a = %f" , a );
 $display ( "b = %f" , b );
 $display ( "c = %f" , c );
 $display ( "d = %f" , d );
 $display ( "e = %f" , e );
 $display ( "r_int = 0.%8b", r_int );
 $display ( "r_frac = 0.%8b", r_frac );

 c1=0;
 while(c1<5) begin//{ 
 /* loop while de 0-4 que itera sobre cada alternativa de a - e */

    y=5;//initializing position in fractionary to binary result
    i=0;//initializing position in integer to binary result
    r_int=0;
    r_frac=0;

    integer_part = $floor(fraction[c1]); // Extracting integer part using floor function
    fraction_part = fraction[c1] - integer_part; // Extracting fractional partfractionary part

    while(integer_part>0 && i>=0)begin//{
        r_int[i] = integer_part % 2;
        integer_part = integer_part/2;
        i = i+1;
    end//}

    while ( fraction_part > 0 && y >= 0 ) begin//{
    /* loop while que itera individualmente cada alternativa em sua "respectiva posição em fraction" para que possa transformar cada número em binário */

    if ( fraction_part*2 >= 1 ) begin//{
        r_frac[y] = 1;
        fraction_part = fraction_part*2.0 - 1.0;
    end//}

    else begin//{
        r_frac[y] = 0;
        fraction_part = fraction_part*2.0;
    end //}

        y=y-1;
    end//} 
 // end while
 
    $display ( "fraction %0d = %6b.%6b",c1, r_int,r_frac );
    c1=c1+1;
 end//}
 // while c<5

end//}
 //main
endmodule // Guia_0202