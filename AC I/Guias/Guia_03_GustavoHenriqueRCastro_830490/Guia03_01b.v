/*
 Guia_0301.v
 830490 - Gustavo Henrique Rodrigues de Castro
*/

module Guia_0301;
// define data
 reg[5:0] a = 6'b001010;
 reg[7:0] b = 8'b00001101;
 reg[5:0] c = 6'b101001;
 reg[5:0] cc2 =0;
 reg[5:0] d = 6'b101111;
 reg[6:0] dc2 =0;
 reg[5:0] e = 6'b110100;
 reg[7:0] ec2 =0;
// actions
 initial
 begin : main
 $display ( "Guia_0301 - Tests" );
 $display("a = %4b, a C1.6 = %6b",a[3:0],~a);
 $display("b = %4b, b C1.8 = %8b",b[3:0],~b);
 cc2 = ~c+1;
 $display("c = %6b, c C2.6 = %6b",c,cc2);
 dc2 = ~d+1;
 $display("d = %6b, d C2.7 = %7b",d,dc2);
 ec2 = ~e+1;
 $display("e = %6b, e C2.8 = %8b",e,ec2);
 end // main
endmodule // Guia_0301
