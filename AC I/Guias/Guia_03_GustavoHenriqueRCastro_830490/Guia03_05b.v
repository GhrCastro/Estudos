/*
 Guia_0305.v
 999999 - Xxx Yyy Zzz
*/
module Guia_0305;
// define data
 reg [7:0] a = 8'b00110101 ; 
 reg [7:0] a2 = 8'b00001011 ; 
 
 reg [7:0] b = 8'b00000101 ; 
 reg [7:0] bDecimal = 8'b10010000 ; 
 reg [7:0] b2 = 8'o3 ; 
 reg [7:0] b2Decimal = 8'b00100000 ;

 reg [7:0] c = 8'b00100111 ; 
 reg [7:0] c2 = 8'h3D ; 
 reg [7:0] d = 8'hC5; 
 reg [7:0] d2 = 8'b01011001 ; 
 reg [7:0] e = 8'h7E ; 
 reg [7:0] e2 = 8'h2D ; 
 reg [7:0] result_int = 0;
 reg [7:0] result = 0;
 reg [7:0] var_full,var2_full;
// actions
 initial
 begin : main
 $display ( "Guia_0305 - Tests" );
 $display ( "-----------------------");
 result = a + (~a2+1);
 $display ( "a = %8b \n a2= %8b \n a - a2 = %8b",a,a2,result );
 $display ( "-----------------------");
 
 result_int = b + (~b2 + 1);
 result = bDecimal + (~b2Decimal + 1);

 $display ( "b = %8b.%8b \n b2= %8b.%8b \n b - b2 = %8b.%8b",b,bDecimal,b2,b2Decimal,result_int,result);
 $display ( "-----------------------");

 result = c + (~c2 + 1);
 $display ( "c = %8b \n c2= %8b \n c - c2 = %8b",c,c2,result );
 $display ( "-----------------------"); 

 result = d + (~d2 + 1);
 $display ( "d = %8b \n d2= %8b \n d - d2 = %8b",d,d2,result );
 $display ( "-----------------------");

 result = e + (~e2 + 1);
 $display ( "e = %8b \n e2= %8b \n e - e2 = %8b",e,e2,result );
 $display ( "-----------------------");

 


 end // main
endmodule // Guia_0305