##################################### QUESTION 2 #####################################

# assume that $s1 = i, $s2 = j and $s3 = &arr[0]

######################################################################################
#this part is prepared for 'if-else'
slti $t0,$s1,30			# if $s1 < 30, then $t0 = 1 else $t0 = 0
slti $t1,$s1,20			# if $s1 < 20, then $t0 = 1 else $t0 = 0

bne $t0,$t1,Else		# if $t0 and $t1 isn't same, we can say that 
				# 20<=x<=30 and enter Else. Otherwise it goes to if. 
 				
add $s1,$s1,$s2			# if situation
j Exit				# it jumps to 'Exit' without reaching 'Else'
Else: add $s2,$s1,$s2		# else situation
Exit: sub $s1,$s1,$s2		# subtraction

######################################################################################
#this part is prepared for 'for loop'
lw $t0,36($s3)			# I load $t0 to $s3 + 36 which equals to &arr[9]	
add $t1,$t1,$zero		# I set $t1 = 0 because it will be my condition
add $s1,$s1,9			# I set $s1 = 9 because i = 9
ForLoop: beq $t1,$s1,Exit2	# it controls whether i > 0
	 sw $t0,-4($t0)		# it stores arr[i] to arr[i-1]
	 lw $t0,-4($t0)		# it changes its location from arr[i] to arr[i-1]
	 addi $s1,$s1,-1	# decrementation of i
	 j ForLoop		# goes to the begging of the loop
Exit2:				# exits from loop
