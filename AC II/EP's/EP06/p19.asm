# $s0 -> x
# $s1 -> y
# $s2 -> MEM[lo]
# $s3 -> MEM[hi]
# $t0 -> TAM_X
# $t1 -> TAM_Y
# $t2 -> endereco base

.text
.globl main
main:
	lui $t2, 0x1001 # t2 = MEM[0]
	lw $s0, 0($t2) # x = MEM[t2]
	lw $s1, 4($t2) # y = MEM[t2 + 1]
   pre_loop_x:
   	ori $t3, $zero, 0 # TAM = 0
   	beq $s0, $zero, pre_loop_y
   	ori $t3, $zero, 32 # TAM = 32
		lui $t4, 0x8000 # t4 = 0x80000000
   	and $t5, $s0, $t4 # testa bit significativo
		bne $t5, $zero, pre_loop_y
	loop_x:
		addi $t3, $t3, -1 # TAM_X = TAM_X - 1
		srl $t4, $t4, 1 # t4 >> 1 
		and $t5, $s0, $t4 # testa bit significativo
		beq $t5, $zero, loop_x
	pre_loop_y:
		or $t0, $zero, $t3 # t0 = TAM_X
		ori $t3, $zero, 0 # TAM = 0
   	beq $s1, $zero, pre_multi
		ori $t3, $zero, 32 # TAM = 32
		lui $t4, 0x8000 # t4 = 0x80000000
		and $t5, $s1, $t4 # testa bit significativo
		bne $t5, $zero, pre_multi
	loop_y:
		addi $t3, $t3, -1 # TAM_Y = TAM_Y - 1
		srl $t4, $t4, 1 # t4 >> 1 
		and $t5, $s1, $t4 # testa bit significativo
		beq $t5, $zero, loop_y
	pre_multi:
		or $t1, $zero, $t3 # t1 = TAM_Y
		add $t4, $t0, $t1 # t4 = TAM_X + TAM_Y
		slti $t4, $t4, 33 # Se t4 for menor que 32 bits, dar� 1
		beq $t4, $zero, maior32bits
	menor32bits:
		mult $s0, $s1
		mflo $s2
		addi $v0, $zero, 10
		syscall
	maior32bits:
		mult $s0, $s1
		mflo $s2
		mfhi $s3
		addi $v0, $zero, 10
		syscall
.data
x: .word 1073741823
y: .word 2