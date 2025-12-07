.text
.globl main

.data
x: .word 0x186A001
y: .word 0x13880
z: .word 0x61A80


.text
# (0x186A00 * 0x13880) / 0x61A80
main:

lui $s1, 0x0018
ori $s1, $s1, 0x6A00

lui $s2, 0x0001
ori $s2, $s2, 0x3880

lui $s3, 0x0006
ori $s3, $s3, 0x1A80

or $s6, $0, $s1
or $s7, $0, $s3
jal div_s5_s6_s7

or $s6, $0, $s5
or $s7, $0, $s2
jal mult_s5_s6_s7

or $s4, $0, $s5

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

# funcao para s5 = s6 / s7
div_s5_s6_s7:
	or $t1, $0, $s6
	or $s5, $0, $0
	loop2:
	addi $s5, $s5, 1
	sub $t1, $t1, $s7
	srl $t2, $t1, 31
	beq $t2, $0, loop2
	addi $s5, $s5, -1
	jr $ra

fim: