/*
 Guia_0204.v
 999999 - Xxx Yyy Zzz
*/
module Guia_0204;
// define data
 real a = 0.321; //quaternario
 real temp;
 reg [0:7] a_binario;
 reg [11:0] b = 12'h3D2;//hexa
 reg [8:0] c = 9'o751;//octal
 reg [2:0] d_int = 3'o7;//octal
 reg [8:0] d_frac = 9'o345;//octal
 reg [7:0] d_int_b;
 reg [7:0] d_frac_b;
 reg [3:0] e_int = 4'hF;//hexa
 reg [11:0] e_frac =12'hA5E;//hexa
 integer q1[2:0];
 integer q [3:0];
 integer i;
 reg [1:0] digito_binario;
 
// actions
 initial
 begin : main
 $display ( "Guia_0204 - Tests" );
 $display ("-----------------------------");
 $display ( "a =%f (4)", a);
 $display ( "b =0.%x (16)", b);
 $display ( "c =0.%o (8)", c);
 $display ( "d =%o.%o (8)", d_int, d_frac);
 $display ( "e =%x.%x (16)", e_int,e_frac);
 $display ("-----------------------------");

 //-----------------------------------------------------------------------------------
 // Conversão do a
 temp = a;
 for (i = 0; i < 3; i = i + 1) begin
     // Extrai o dígito da posição mais significativa
     temp = temp * 10;
     digito_binario = temp;
     temp = temp - digito_binario;

     // Converte o dígito quaternário para binário
     case (digito_binario)
         2'd0: a_binario = a_binario << 2;
         2'd1: a_binario = (a_binario << 2) | 2'b01;
         2'd2: a_binario = (a_binario << 2) | 2'b10;
         2'd3: a_binario = (a_binario << 2) | 2'b11;
     endcase
 end
 $display ("a = 0.%6b (2)", a_binario[2:7]);
 //-----------------------------------------------------------------------------------

 // Conversão de b = 0.3D2(16) para quaternário
 $display ("b = 0.%02d%02d%02d (4)", b[11:8], b[7:4] + 18, b[3:0]);
 //-----------------------------------------------------------------------------------

 // Conversão de c = 0.751(8) para binário
 $display ("c = 0.%8b (2)", c);
 //-----------------------------------------------------------------------------------

 // Conversão de d = 7.345(8) para quaternário
 d_int_b = d_int;
 d_frac_b = d_frac;
 $display ("d = %8b.%8b (2)", d_int_b, d_frac_b);
 $display ("d = %02d%02d.%02d%02d (4)", d_int_b[7:4] / 2, d_int_b[3:0] / 2, d_frac_b[7:4] / 2, d_frac_b[3:0] / 2);
 //-----------------------------------------------------------------------------------

 // Conversão de e = F.A5E(16) para quaternário
 $display ("e = %02d.%02d%02d%02d (4)", e_int % 16 / 4, e_frac[11:8] % 16 / 4, e_frac[7:4] % 16 / 4, e_frac[3:0] % 16 / 4);
 //-----------------------------------------------------------------------------------
 
 end // main
endmodule // Guia_0204
/*o código final ainda tem problemas para converter as letras d e e*/



