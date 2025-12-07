
.text
.globl main
main:

ori $8, $0, 0x01
srl $8, $8, 1
nor $8, $8, $8
