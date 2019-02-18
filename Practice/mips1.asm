

############################################ # Data Segment ############################################
.data
msg:
	.asciiz	"Enter a number:"
.text
############################################ # Main Routine ############################################
main:
	la $a0, msg
	jal printf_str #call function to print message 
	jal scanf #call function to read an integer 
	add $s0, $v0, $zero	
	
	addi $s1, $zero, 2		
L1: 	bgt $s1, $s0, done	
	addi $s2, $zero, 1		
	addi $s3, $zero, 2		
L2: 	srl $t0, $s1, 1				 
	blt $t0, $s3, L5		
	rem $t0, $s1, $s3		 
	bne $t0, $zero, L3	
	addi $s2, $zero, 0
L3: 	beq $s2, $zero, L4	
	la $a0, ($s1)		
	jal printf_int
L4: 	addi $s1, $s1, 1			
	j L1			
L5: 	addi $s3, $s3, 1			
	j L2										
done:				 
############################################
recSum: addi $sp, $sp, -8
	sw $ra, 4($sp)
	sw $a0, 8($sp)
	bne $a0, $0, L1
	add $v0, $0, $0
	addi $sp, $sp, 8
	jr $ra

############################################ 
L1:	addi $a0, $a0, -1
	jal recSum
	lw $a0, 0($sp)
	lw $ra, 4($sp)
	addi $sp, $sp, 8
	add $v0, $a0, $v0
	jr $ra

############################################ 
printf_str:  			 
    	addi $v0, $zero, 4 	 
	syscall			 
	jr $ra  	 		

############################################ 
scanf:   				 
	addi $v0, $zero, 5 	 
	syscall   			
	jr $ra   		 		

############################################ 
printf_int:   			 
	addi $v0, $zero, 1	 
	syscall   			 
	jr $ra   			

############################################ 	
exit:
	addi $v0, $zero, 10 			 
	syscall        