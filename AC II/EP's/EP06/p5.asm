# x = $s1
# y = $s2
# z = $s3

.text
.globl main
main:

# x = 100000
ori $s1, $0, 0x1000
sll $s1, $s1, 4
ori $s1, $s1, 0x86A0

# y = 200000
ori $s2, $0, 0x3000
sll $s2, $s2, 4
ori $s2, $s2, 0x0D40

# z = x + y
add $s3, $s1, $s2