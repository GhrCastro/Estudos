.text
.globl main
main:

# $8 = 0x12345678
ori $8, $0, 0x1234
sll $8, $8, 16
ori $8, $8, 0x5678

# registrador 9
srl $9, $8, 24

# registrador 10
srl $10, $8, 16
andi $10, $10, 0x00FF

# registrador 11
srl $11, $8, 8
andi $11, $11, 0xFF

# registrador 12
andi $12, $8, 0xFF