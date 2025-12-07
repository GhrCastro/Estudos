.text
.globl main

.data
x: .word 13
y: .word 12


.text
# (0x186A00 * 0x13880) / 0x61A80
main:
lui $s0, 0x1001
lw $s6, 0($s0)
lw $s7, 4($s0)
jal mult_s5_s6_s7
sw $s5, 8($s0) # Gravar resultado na memoria

j fim

# funcao para s5 = s6 * s7
mult_s5_s6_s7:
	or $t1, $0, $s6
	or $s5, $0, $0
	loop1:
	add $s5, $s5, $s7
	addi $t1, $t1, -1
	bne $t1, $0, loop1
	jr $ra

fim: