.text
.globl main

.data
x: .word 30

.text
main:

# preparar para endereçamento na memoria
lui $t0, 0x1001
ori $t1, $t0, 0x0020

# int* x
sw $t0, 0($t1)

# int** x
sw $t1, 4($t1)

# int*** x
addi $t2, $t1, 4
sw $t2, 4($t0)

# ler dados, a partir do ponteiro x ( 4($t0) )

# primeiro endereco
lw $t1, 4($t0)

# primeira dereferencia
lw $t1, 0($t1)

# segunda dereferencia
lw $t1, 0($t1)

# terceira dereferencia (guardando o valor de x em t2)
lw $t2, 0($t1)

# agora t2 contem ***x e t1 contem o seu endereco
# logo, basta multiplicar t2 por 2 e gravar no endereço armazenado em t1
sll $t2 $t2, 1
sw $t2, 0($t1)