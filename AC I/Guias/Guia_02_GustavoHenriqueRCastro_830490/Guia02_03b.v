/*
 Guia_0203.v
 999999 - Xxx Yyy Zzz
*/
module Guia_0203;
// define data
 reg [7:0] a = 8'b00011110 ; // binary
 reg [7:0] b = 8'b00101001; // binary
 reg [7:0] c = 8'b10011000 ; // binary
 reg [7:0] d_int = 8'b00000001 ;//binary
 reg [7:0] d_frac = 8'b00111011 ; // binary
 reg [7:0] e_int = 8'b00001101 ;
 reg [7:0] e_frac = 8'b00001001 ; // binary
// actions
 initial
 begin : main
 $display ( "Guia_0203 - Tests" );
 $display ( "a = 0.%8b (2)", a );
 $display ( "b = 0.%8b (2)", b );
 $display ( "c = 0.%8b (2)", c );
 $display ( "d = %8b.%8b (2)", d_int,d_frac );
 $display ( "e = %8b.%8b (2)", e_int,e_frac );
 $display ( "a = 0.%o%o%o (4)", a[5:4],a[3:2],a[1:0]);
 $display ( "b = 0.%o%o (8) ",b[5:3],b[2:0] );
 $display ( "c = 0.%x%x (16)", c[7:4],c[3:0] );
 $display ( "d = %o.%o%o (8)", d_int[2:0],d_frac[5:3],d_frac[2:0] );
 $display ( "e = %x.%x (16)", e_int[3:0],e_frac[3:0]);
 end // main
endmodule // Guia_0203