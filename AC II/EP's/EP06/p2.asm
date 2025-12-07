# x = $s1
# y = $s2

.text
.globl main
main:

# x = 1
ori $s1, $0, 0x1

# y = 5*x
add $s2, $s1, $s1
add $s2, $s2, $s2
add $s2, $s2, $s1

# y += 15
addi $s2, $s2, 15