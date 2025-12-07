.text
.globl main
main:
lui $t0, 0x1001
lw $s0, 0($t0)
andi $t1, $s0, 0x1
beq $t1, $zero, par # Se o resultado for 0, eh par. Se 1, eh impar
impar:
mult $s0, $s0 # x^2
mflo $t3 # t3 = x^2
mult $t3, $s0 # x^3
mflo $t3 # t3 = x^3
mult $t3, $s0 # x^4
mflo $t2 # t2 = x^4
mult $t2, $s0 # x^5
mflo $t2 # t2 = x^5

sub $t2, $t2, $t3 # t2 = x^5 - x^3
addi $s1, $t2, 1 # y = x^5 - x^3 + 1
j resultado

par:
mult $s0, $s0 # x^2
mflo $t4 # t2 = x^2
mult $t4, $s0 # x^3
mflo $t3 # t3 = x^3
mult $t3, $s0 # x^4
mflo $t2 # t2 = x^4

sll $t4, $t4, 1 # t4 = 2x^2

add $t2, $t2, $t3 # t2 = x^4 + x^3
sub $s1, $t2, $t4 # y = x^4 + x^3 - 2x^2

resultado:
sw $s1, 4($t0) # MEM[t0 + 4] = y

.data
x: 3
y: -1 # sera sobrescrito no final