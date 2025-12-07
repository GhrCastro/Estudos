.text
.globl main

.data
x: .word 3
y: .word 5

.text
# (0x186A00 * 0x13880) / 0x61A80
main:
lui $s0, 0x1001
lw $s3, 0($s0)
lw $s4, 4($s0)
jal pot_s2_s3_s4
sw $s2, 8($s0) # Gravar resultado na memoria

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

# funcao para s2 = s3 ^ s4
pot_s2_s3_s4:
	or $t9, $0, $ra
	
	addi $t8, $s4, -1
	or $s7, $s3, $0		# s7 = s3
	or $s2, $s3, $0		# s2 = s3
	loop2:
	or $s6, $s2, $0
	jal mult_s5_s6_s7
	or $s2, $0, $s5
	
	addi $t8, $t8, -1
	srl $t2, $t8, 31
	bne $t8, $0, loop2
	


	or $ra, $0, $t9
	jr $ra
fim: