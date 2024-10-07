/*
 Guia_0302.v
 830490 - Gustavo Henrique Rodrigues de Castro
*/
module Guia_0302;
// define data
 reg [7:0] b = 8'hB2 ; // hexadecimal
 reg [6:0] d = 8'o146 ; // octal
 reg [9:0] dc2 = 0;
 reg [5:0] a = 6'b111001 ; // quaternário(321)
 reg [5:0] c = 6'b101101 ; // quaternário(231)
 reg [5:0] cc2 = 0;
 reg [7:0] e = 8'h6F ; // binary
 reg [7:0] ec2 = 0;
// actions
 initial
 begin : main
 $display ( "Guia_0302 - Tests" );
 $display ( "a = %6b -> C1(a) = %6b", a, ~a);
 $display ( "b = %8b -> C1(b) = %8b", b, ~b);
 cc2 = ~c+1;
 $display ( "c = %6b -> C1(c) = %6b -> C2(c) = %6b", c, ~c, cc2 );
 dc2 = ~d+1;
 $display ( "d = %6b -> C1(d) = %6b -> C2(d) = %6b", d, ~d, dc2 );
 ec2 = ~e+1;
 $display ( "e = %8b -> C1(e) = %8b -> C2(e) = %8b", e, ~e, ec2 );
 end // main
endmodule // Guia_0302