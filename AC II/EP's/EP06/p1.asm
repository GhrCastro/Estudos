# a = $s1
# b = $s2
# c = $s3
# d = $s4
# x = $s5
# y = $s6

.text
.globl main
main:

# Atribuir valores das variaveis iniciais
addi $s1, $0, 2
addi $s2, $0, 3
addi $s3, $0, 4
addi $s4, $0, 5

# Fazer calculos

# x = a + b - c - d
add $s5, $s1 $s2
sub $s5, $s5, $s3
sub $s5, $s5, $s4

# y = a - b + x  --> y = a + x - b
add $s6, $s1, $s5
sub $s6, $s6, $s2

# b = x - y
sub $s2, $s5, $s6