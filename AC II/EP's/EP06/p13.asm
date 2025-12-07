.text
.globl main

.data
x: .word 30

.text
main:
lui $t0, 0x1001
lw $s0, 0($t0)

lui $t2, 0x8000
and $t1, $s0, $t2

bne $t2, $t1, par

sub $s0, $0, $s0
sw $s0, 0($t0)

par: