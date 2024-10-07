/*
 Guia_0303.v
 830490 - Gustavo Henrique Rodrigues de Castro
*/
module Guia_0303;
// define data
 reg signed [4:0] a = 5'b10110;
 reg signed [8:0] aPos = 0;
 reg signed [5:0] b = 6'b110011;
 reg signed [8:0] bPos = 0;
 reg signed [5:0] c = 6'b100100;
 reg signed [8:0] cPos = 0;
 reg signed [6:0] d = 7'b1011011;
 reg signed [8:0] dPos = 0;
 reg signed [6:0] e = 7'b1110011;
 reg signed [8:0] ePos = 0;
// actions
 initial
 begin : main
 $display ( "Guia_0303 - Tests" );
 aPos = ~(a-1);
 $display("a =%5b, Número positivo que originou a = %d",a,aPos);
  bPos = ~(b-1);
 $display("b =%5b, Número positivo que originou b = %d",b,bPos);
  cPos = ~(c-1);
 $display("c =%5b, Número positivo que originou c = %8b",c,cPos);
  dPos = ~(d-1);
 $display("d =%5b, Número positivo que originou d = %8b",d,dPos);
  ePos = ~(e-1);
 $display("e =%5b, Número positivo que originou e = %h",e,ePos);

 end // main end // main
endmodule // Guia_0303
