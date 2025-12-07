.text
.globl main
main:

    ori $s1, $zero, 3     # x = 3
    ori $s2, $zero, 4     # y = 4

    # 15x usando deslocamento: 16x - x
    sll $s3, $s1, 4
    sub $s3, $s3, $s1

    # 67*y = 64y + 3y
    sll $t1, $s2, 6
    add $t1, $t1, $s2
    add $t1, $t1, $s2
    add $t1, $t1, $s2

    # z = 4*(15x + 67y)
    add $s3, $s3, $t1
    sll $s3, $s3, 2
