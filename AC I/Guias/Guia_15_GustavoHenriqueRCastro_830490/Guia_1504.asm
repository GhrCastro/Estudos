// HL <- endereço de dado01
	   MOV A,M
	   INX H
	   MOV A,M	// A <- MEM[HL] (dado01)
	   INX H	// HL <- HL + 1 (endereço de dado02)
	   MOV B,M	// B <- MEM[HL] (dado02)
	   MOV C,B
	   ADD B	// B <- 2 * dado02
	   SUB C	// A <- dado01 - 2 * dado02
	   MOV C,A
	   ADD C	// A <- A + A (dobro de A)
	   MOV B,A
	   ADD B	// A <- A + A (dobro de A)
// Área de dados
// exemplo de valor para dado01
// exemplo de valor para dado02
// resultado (inicializado com zero)
// MEM[dado03] <- A (resultado)
