.text
.globl main

.data
x: .word 57

.text
main:
lui $t0, 0x1001
lw $s0, 0($t0)

andi $t1, $s0, 0x0001

sw $t1, 4($t0)