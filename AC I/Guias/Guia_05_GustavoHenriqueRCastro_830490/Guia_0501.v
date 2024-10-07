// -------------------------
// Guia_0500.v - GATES
// Nome: Gustavo Henrique Rodrigues de Castro
// Matricula: 830490

// -------------------------
// f5_gate
// m a b s
// 0 0 0 0
// 1 0 1 1 <- a'.b
// 2 1 0 0
// 3 1 1 0
//
// -------------------------
module f5a ( output s,
 input a,
 input b );
// definir dado local
 wire nor_a,nor_b,not_a;
// descrever por portas

//também poderia ter sido escrito como: 
// assign s = ~(a | ~b);
//caso queira testar :)
 nor NOTa (not_a,a,a);
 nor NOR1 ( nor_a, not_a,not_a );
 nor NOR2 ( nor_b, b, b );
 nor NOR3 (s, nor_a, nor_b);
endmodule // f5a

module test_f5;
// ------------------------- definir dados
 reg x;
 reg y;
 wire s;
 f5a moduloA ( s, x, y );
// ------------------------- parte principal
 initial
 begin : main
 $display("Guia_0501 - Gustavo Henrique Rodrigues De Castro - 830490");
 $display("Test module");
 $display("   x    y    s ");
 // projetar testes do modulo
 $monitor("%4b %4b %4b ", x, y, s);
 x = 1'b0; y = 1'b0;
 #1 x = 1'b0; y = 1'b1;
 #1 x = 1'b1; y = 1'b0;
 #1 x = 1'b1; y = 1'b1;
 end
endmodule // test_f5