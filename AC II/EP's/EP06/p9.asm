.text
.globl main

.data
x1: .word 15
x2: .word 25
x3: .word 13
x4: .word 17
soma: .word -1

.text

main:
lui $t0, 0x1001

lw $t1, 0($t0)
lw $t2, 4($t0)

# adiciomar primeiros 2 valores ao resultado
add $t3, $t1, $t2

lw $t1, 8($t0)
lw $t2, 12($t0)

# adicionar outros 2 valores
add $t3, $t3, $t1
add $t3, $t3, $t2

# gravar resultado
sw $t3, 16($t0)