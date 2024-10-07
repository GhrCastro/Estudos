/*
 Guia_0304.v
 830490 - Gustavo Henrique Rodrigues de Castro
*/
module Guia_0304;
// define data
 reg signed [4:0] a = 5'b11001;//binary a1
 reg signed [4:0] a2 = 5'b01101;//binary a2

 reg signed [3:0] b = 4'b0101;//binary integer b1 part 
 reg signed [3:0] bDecimal = 4'b1101;//binary fractional b1 part  
 reg signed [3:0] b2 = 4'b0010;//binary integer b2 part
 reg signed [3:0] b2Decimal = 'b0100;//binary fractional b2 part 

 reg  [5:0] c = 6'b111001;//quaternary c1
 reg  [5:0] c2 = 6'b101101;//quaternary c2

 reg  [7:0] d = 8'o376;//octal d1
 reg  [7:0] d2 =8'o267;//octal d2

 reg  [11:0] e = 12'h7D2;//hexa
 reg  [11:0] e2 =  12'hA51;//hexa

 reg signed [7:0] result =0;
 reg signed [7:0] b_full,b2_full;
 reg signed [11:0] b_result;
 reg signed [11:0] resultBig = 0;
// actions
 initial
 begin : main
 $display ( "Guia_0304 - Tests" );
 $display(" ---------------------------------------- ");

 result = a + (~a2 + 1);
 $display ( "\na) a1 = %5b\n   a2= %5b\n   a1-a2 = %5b",a,a2,result[4:0]);
 $display(" ---------------------------------------- ");

 b_full = {b,bDecimal};
 b2_full = {b2,b2Decimal};
 b_result = b_full + (~b2_full+1);
 $display("\nb) b1 = %4b.%4b\n   b2 = %4b.%4b\n    b1-b2 = %4b.%4b",b,
 bDecimal,b2,b2Decimal,b_result[7:4],b_result[3:0]);
 $display(" ---------------------------------------- ");

 result = c + (~c2+1);
 $display("\nc) c1 = %d%d%d (4)\n   c2 = %d%d%d (4)\n   c1-c2 = %d%d%d (4)",c
 [5:4],c[3:2],c[1:0],c2[5:4],c2[3:2],c2[1:0],result[5:4],result[3:2],result[1:0]);
 $display(" ---------------------------------------- ");

 result = d + (~d2 + 1);
 $display("\nd) d1 = %o (8)\n   d2 = %o (8)\n   d1-d2 = %o (8)",d,d2,result);
 $display(" ---------------------------------------- ");

 resultBig = e + (~e2 + 1);
 $display("\ne) e1 = %h(16)\n   e2 = %h (16)\n   e1-e2 = %2h (16)", e,e2,resultBig);
 $display(" ---------------------------------------- ");
 
 end // main
endmodule // Guia_0304
