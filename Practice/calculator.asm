############################################
# Data Segment
# messages 
############################################  

.data
	input_msg:	.asciiz	" Expression: \n "
	input_str:	.space 48
	equal:		.word 61

############################################
# Text Segment
############################################
############################################
# Main Routine 		   	
############################################  
	.text
main:
	
	la	$a0, input_msg		
	jal	print_str	
	
	la	$a0, input_str		# load the space for the expression into register	
	addi 	$a1, $zero, 48  	# the length of the string is 48
	jal	read_str		# read the input
	move	$a0, $v0
	la	$a0, toInteger
	jal	print_int
	
	
exit:
	addi	$v0, $zero, 10		# system code for exit
	syscall				# exit gracefully


############################################
# I/O Routines
############################################
print_str:				# $a0 has string to be printed
	addi	$v0, $zero, 4		# system code for print_str
	syscall				# print it
	jr 	$ra			# return
	
print_int:				# $a0 has number to be printed
	addi	$v0, $zero, 1		# system code for print_int
	syscall
	jr 	$ra

read_str:				# address of str in $a0, 
					# length is in $a1.
	addi	$v0, $zero, 8		# system code for read_str
	syscall
	jr 	$ra
	
	
##############################################
# toInteger Routine	   
# $a0 : memory address of the string str. 
# $v0 : a 32-bit 2's complement binary integer.
##############################################  	
toInteger:
	add	$s1, $s1, $zero
	addi	$t9, $t9, 10
	lbu	$s0, 0($a0)
loop:	addi	$s0, $s0, -48
	slti	$t8, $s0, 0
	bne	$t8, $zero, end
	multu	$s1, $t9
	mflo	$s1
	add	$s1, $s1, $s0
	addi	$a0, $a0, 1
	lbu	$s0, 0($a0)
	bne	$s0, $zero, loop
end:	move	$v0, $s1
	jr	$ra			

##############################################
# toString Routine	   
# $a0 : memory address of the signed integer int. 
# $v0 : memory address of the string str.
##############################################  	
toString:
	lbu 	$s0, 0($a0)
loop2:	addi	$s3, $s0, 48
	sb	$s0, 0($v0)
	addi	$s0, $s0, 1
	addi	$v0, $v0, 1
	bne	$s0, $v0, loop2
	jr 	$ra
	
##############################################
# toPrefix Routine	   
# $a0 : memory address of the string str in
# infix notation. 
# $v0 : memory address of the string in prefix
# notation.	
##############################################  	
toPrefix:
	lbu	$t7, 0($a0)
	add	$s7, $s7, $zero
for:	addi	$t7, $t7, 1
	addi	$s7, $s7, 1
	bne	$s0, equal, for

	la	$v0, ($s1)
	jr 	$ra
	
##############################################
# evaluate Routine	   
# $a0 : memory address of the prefix expression.
# $v0 : memory address of the string str. 
##############################################  	
evaluate: 
	jr 	$ra	
