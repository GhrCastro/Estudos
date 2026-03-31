// HL <- endereço de dado01
	   MOV A,M
	   MOV B,A
	   MOV A,M	// A <- MEM[HL] (dado01)
	   MOV B,A
	   ADD B	// A <- A + A (dobro de A)
	   MOV B,A
	   ADD B	// A <- A + A (quádruplo de A)
	   INX H	// HL <- HL + 1 (endereço de dado02)
	   MOV B,M	// B <- MEM[HL] (dado02)
	   MOV C,B
	   RRC	// B <- B / 2 (deslocamento à direita)
	   ADD C	// A <- A + B
// Área de dados
// exemplo de valor para dado01
// exemplo de valor para dado02
// resultado (inicializado com zero)
// MEM[dado03] <- A (resultado)
