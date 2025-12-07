# x = $s1
# y = $s2
# z = $s3

.text
.globl main
main:

# x = maior inteiro possivel
ori $s1, $0, 0x7FFF
sll $s1, $t1, 16
ori $s1, $s1, 0xFFFF

# y = 300000
ori $s2, $0, 0x0004
sll $s2, $s2, 16
ori $s2, $s2, 0x93E0

# z = x - 4y
sll $t1, $s2, 2 # t1 = 4y
sub $s3, $s1, $t1 # z = x - t1