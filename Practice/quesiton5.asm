############################################## QUESTION 5 ##############################################

############################################ # Data Segment ############################################ 
.data
msg:
	.asciiz "Enter a number: "
empty:
	.asciiz "  "
.text 
############################################ # Main Routine ############################################ 
main:
	la $a0, msg
	jal printf_str 				#call function to print message
	jal scanf 				#call function to read an integer 
	add $s0, $v0, $zero
	# your code will come here
	la $a0,($s0)
	jal recursiveSum
	jal exit
	
recursiveSum:
	addi $sp,$sp,-8
	sw $ra,4($sp)
	sw $a0,0($sp)
	slti $t0,$a0,2
	beq $t0,$zero,L1
	addi $v0,$zero,1
	addi $sp,$sp,8
	jr $ra
	
L1:
	addi $a0,$a0,-1	
	jal recursiveSum
	
block:
	lw $a0,0($sp)
	lw $ra,4($sp)
	addi $sp,$sp,8
	add $v0,$v0,$a0
	jr $ra
	
	
exit:
	la $a0,($v0)
	jal printf_int
	addi $v0, $zero, 10 			# system code for exit 
	syscall 				# exit main routine

############################################ 
scanf: 						# $v0 contains the read int
	addi $v0, $zero, 5 			# system code for read_int 
	syscall 				# read it
	jr $ra 					# return

############################################ 
printf_int: 					# $a0 has integer to be printed
	addi $v0, $zero, 1 			# system code to print check 
	syscall 				# print it
	jr $ra 					# return
