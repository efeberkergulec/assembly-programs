############################################## QUESTION 4 ##############################################

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
	addi $s0,$s0,1
	# your code will come here
	addi $t1,$zero,2			# num = 2
while:	addi $t3,$zero,1			# isprime = 1
	addi $t4,$zero,2			# j = 2
for:	beq $t1,$t4,if
	div $t1,$t4				# num / j
	mfhi $t6				# remainder = (num % j)
	bne $t6,$zero,else			# if remainder == 0
	add $t3,$zero,$zero			# if part (isptrime = 0)
else:	div $t5,$t1,2				# $t5 = num / 2
	addi $t4,$t4,1				# j++
	ble $t4,$t5,for				# if j <= num /2 go to for
if:	beq $t3,$zero,else2			# if isprime != 0 pass if
	la $a0,($t1)				
	jal printf_int				# print int
	la $a0,empty
	jal printf_str				# print string
else2:	addi $t1,$t1,1				# increment number
	slt $t2,$t1,$s0				# check num <= maxnum
	bne $t2,$zero,while			# continue loop

exit:
	addi $v0, $zero, 10 			# system code for exit 
	syscall 				# exit main routine

############################################ 
scanf: 						# $v0 contains the read int
	addi $v0, $zero, 5 			# system code for read_int 
	syscall 				# read it
	jr $ra 					# return

############################################ 
printf_str: 					# $a0 has string to be printed
	addi $v0, $zero, 4 			# system code for print_str 
	syscall 				# print it
	jr $ra					# return

############################################ 
printf_int: 					# $a0 has integer to be printed
	addi $v0, $zero, 1 			# system code to print check 
	syscall 				# print it
	jr $ra 					# return
