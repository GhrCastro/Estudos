.text
.globl main

.data
x: .word 100000
z: .word 200000
y: .word 0

# y = x – z + 300000
.text

main:
lui $t0, 0x1001

lw $t1, 0($t0)
lw $t2, 4($t0)

# carregar 300000 em t3
lui $t3, 0x0004
ori $t3, $t3, 0x93E0

sub $t1, $t1, $t2
add $t1, $t1, $t3

sw $t1, 8($t0)