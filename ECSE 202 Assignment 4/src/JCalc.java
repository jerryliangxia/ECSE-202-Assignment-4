/* 
 * @author Jerry Xia
 * ID 260917329
 * 
 * parts taken from ferrie's assignment pdf
 */

import java.util.StringTokenizer;
import acm.program.*;


public class JCalc extends Program {

	//main method for JCalc class. Used to convert infix to postfix.
	public void run() {
		//prints what it does
		println("Infix expression evaluator, enter expression of blank line to exit.");
		//while loop created for multiple user inputs
		while(true) {
			String infix = readLine("expr: ");	//takes input from user
			if(infix.equals("")) break;	//if blank, then break from loop
			result = postFix(infix);	//takes double result from postFix method
			println(infix+" = "+result);	//prints final statement before next loop commences
		}
		println("Program terminated.");	//prints that it is terminated if loop is done.
	}
	
	double result;	//result that is eventually returned to the user, in class postFix
	Stack Data = new Stack();	//stack that gives the final output, used towards the end
		
	/* This program below is the postFix method, used from A3, with additional inpiut for this assignment at end.
	 * 
	 * Basic summary: Converts String from infix to postfix.
	 * get prompt from user
	 * cycle through each part of the string using StringTokenizer (must be imported)
	 * if an operator is seen, push the operator onto the operator stack
	 * if an integer/anything other than an operator is seen, enqueue the integer onto the queue
	 * if an operator is of greater precedence than the one on top of the operator stack, push the
	 * operator onto the operator stack. otherwise, print out all operators, and enqueue the remaining
	 * integers onto the queue until the next operator.
	 * When this is done, the operator stack will contain the operator of lesser degree, and the process
	 * continues.
	 * 
	 * The use of queues and stacks are important as they follow, respectively, FIFO and LILO systems.
	 * as the program proceeds, everything (with exception of brackets) gets pushed onto the queue
	 */
	
	public double postFix(String infix) {			//returns a double, result which is used in JCalcGUI.java
		
		//initializes operator stack, queue and gets input from user.
		//@param opStack - stack used for storing operators
		//@param postfix - queue used for storing and printing out final result
		//@param input - obtained from JCalc class
			
			Stack opStack = new Stack();

			Queue postfix = new Queue();

			//@param st - StringTokenizer which cycles through each token of the string
			StringTokenizer st = new StringTokenizer(infix, "+-/*^()", true);

			/*
			 * main while loop
			 * 
			 * continues while tokens are still available (taken from string str)
			 * @param token - this method allows one to cycle through the loop
			 * with the next respective token
			 * 
			 * this loop contains if statements that cycle through each token
			 */

			while (st.hasMoreTokens()) {	//while there are tokens to be read
				 String token = st.nextToken();	//explained above
				 
				 //if the token is an operator (this method is allocated below, second from last, which takes in a token)
				 if (isOperator(token)) {
						
					if(opStack.top != null) {	//rules applied if top of stack contains an operator already
							
						//if precedence of token is higher than precedence of top value on operator stack.
						//push the token onto the stack
						if (precedence(token) > precedence(opStack.top.value)) {
							opStack.push(token);
						}
						
						//else if precedence of token is less than or equal to precedence of value on top of stack,
						//the following applies
						else if (precedence(token) <= precedence(opStack.top.value)) {
							//while loop is created to go through
							while(true) {
								if(opStack.top != null) {	//if top of stack has an operator
									
									//if precedence of the token is higher than that of the value on top of opStack,
									//push all operators from opStack onto the queue
									
									if(precedence(token) > precedence(opStack.top.value)) break;
									
								} else break;				//otherwise push the token onto opStack
								postfix.Enqueue(opStack.pop());
							}
							opStack.push(token);					
						}
											
					}
					
					//if top of opStack is null, push token onto operator stack
					else if (opStack.top == null) {
						opStack.push(token);	//pushes token onto opStack
						
					}
				
				//if token is left parentheses
				} else if (token.equals("(")){	//push onto stack. Will be used later with right parentheses for postfix
					opStack.push(token);
					
				//if token is right parentheses
				} else if(token.equals(")"))
					//establishes while loop to push ALL operands onto the queue
					while(true) {
						//queues all operators onto postfix
						//applies only to operators, not left bracket
						while (opStack.top.value != null && !opStack.top.value.equals("(")) {
							postfix.Enqueue(opStack.pop());
						}
						//if left bracket is present on opStack, discard the bracket
						if (opStack.top.value.equals("(")) {
							opStack.pop();
							break;	//break from while loop
						}
					}
					
				else {	//ELSE if not an operand, "(" or ")" then enqueue the value onto the postfix queue
						//(in this assignment the postfix contains all numbers and operands and no variables such as a or b)
					postfix.Enqueue(token);
				}	
			}	//end of while loop
				
			while(opStack.top != null) {		//while top of opStack contains operators
				postfix.Enqueue(opStack.pop());	//push all final operators onto the postfix queue
			}
			
			/*this part uses the queue created and operates on it with a stack called Data
			 * @param Data - stack used for operations and eventually is able to return type double as final result
			 *1. dequeue a token from the postfix queue
			 *2. if token is an operand, push onto stack
			 *3. if token is an operator, pop operands off stack (2 for binary operator); push result
			 *onto stack
			 *4. repeat until queue is empty
			 *5. item remaining in stack is final result 
			 *6. returns double as final result
			 */
			
			//copied from assignment 4 pdf outline (located on myCourses)
			while(postfix.front!=null) {
				String token = postfix.Dequeue();
			
				if(isOperator(token)) {
					String OP_A = Data.pop();	//string is equal to the data that is popped
					String OP_B = Data.pop();
					Double A = Double.parseDouble(OP_A);	//changes the string to a double
					Double B = Double.parseDouble(OP_B);
					
					switch(token) {
					case"+":
						result = B+A;
						break;
					case"*":
						result = B*A;
						break;
					case"/":
						result = B/A;
						break;
					case"-":
						result = B-A;
						break;
					case"^":
						result = Math.pow(B,A);
					}
					Data.push(Double.toString(result));	//pushes result onto Data stack as a string
				}
			else
				Data.push(token);	//pushes token onto Data stack
			
			}
			return result;	//returns type double for JCalcGUI.java class
			
	}

	//below are the methods used for the postFix method
	
	private boolean isOperator(String token)	//isOperator method used for specifying operators entered from user
										//returns boolean
	{
		
		if (token.equals("+") || token.equals("-") || token.equals("/") || token.equals("^") 
				|| token.equals("*") || token.equals("^"))	//or statements used for this method
			// if string is an operator, return true
			return true;
		else
		{
			// otherwise, return false
			return false;
		}
	}

	private int precedence(String token)	//ranks precedence and takes in token from user
	{
		{
			if(token.equals("^"))								//highest precedence - ^
				return 3;
			if (token.equals("*") || token.equals("/"))			//higher precedence includes * (multiplication) and /
				return 2;
			if (token.equals("+") || token.equals("-"))			//second highest includes + and -
				return 1;
			if (token.equals("("))								//final state of precedence -
																//includes just left parentheses, used in popping off of
																//stack when needed
				return -1;
			else
				return 0;
		}
		
	}

}