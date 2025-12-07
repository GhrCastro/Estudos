# x = $s1
# y = $s2
# z = $s3

.text
.globl main
main:

# x = 3
ori $s1, $0, 3

# y = 4
ori $s2, $0, 4

# tmp1 = 15*x
add $t1, $s1, $s1 # t1 = 2x
add $t1, $t1, $t1 # t1 = 4x
add $t1, $t1, $t1 # t1 = 8x
add $t1, $t1, $t1 # t1 = 16x
sub $t1, $t1, $s1 # t1 = 15x

# tmp2 = 67*y
add $t2, $s2, $s2 # t2 = 2y
add $t2, $t2, $t2 # t2 = 4y
add $t2, $t2, $t2 # t2 = 8y
add $t2, $t2, $t2 # t2 = 16y
add $t2, $t2, $t2 # t2 = 32y
add $t2, $t2, $t2 # t2 = 64y
add $t2, $t2, $s2 # t2 = 65y
add $t2, $t2, $s2 # t2 = 66y
add $t2, $t2, $s2 # t2 = 67y

# tmp1 = tmp1 + tmp2
add $t1, $t1, $t2

# z = tmp1 * 4
add $t1, $t1, $t1
add $s3, $t1, $t1