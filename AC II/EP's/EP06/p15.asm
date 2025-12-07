.text
.globl main

.data
arr: .word 0

.text
main:

# t0 = enderecamento
# t3 = soma
# t1 = iterador
# t2 = valor atual
# t4 = end

lui $t0, 0x1001
addi $t4, $t0, 400

loop:
add $t2, $t1, $t1
addi $t2, $t2, 1

add $t3, $t3, $t2

sw $t2, 0($t0)
addi $t0, $t0, 4
addi $t1, $t1, 1

bne $t4, $t0, loop

sw $t3, 0($t0)