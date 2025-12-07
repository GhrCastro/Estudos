.text
.globl main

.data
x: .word 5
z: .word 7
y: .word 0

# y = 127x – 65z + 1
.text

main:
lui $t0, 0x1001

lw $t1, 0($t0)
lw $t2, 4($t0)

# t1 = 127 * t1
sll $t3, $t1, 7
sub $t1, $t3, $t1

# t2 = 65 * t2
sll $t3, $t2, 6
add $t2, $t3, $t2

# t1 = t1 - t2 + 1
sub $t1, $t1, $t2
addi $t1, $t1, 1

# gravar resultado
sw $t1, 8($t0)